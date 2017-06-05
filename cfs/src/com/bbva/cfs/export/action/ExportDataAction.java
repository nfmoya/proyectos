/**
 * @author david
 * 
 * Clase Action que se encarga de procesa los datos que vienen en el form para exportar
 */
package com.bbva.cfs.export.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioFormat.Encoding;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.xml.serialize.Encodings;

import com.bbva.cfs.commons.action.CommonAction;
import com.bbva.cfs.export.form.ExportDataForm;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.ChainedProperties;
import com.itextpdf.text.html.simpleparser.ImageProvider;
import com.itextpdf.text.pdf.PdfEncodings;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class ExportDataAction extends CommonAction {

	private ExportDataForm edf;

	public ActionForward exportDataGrid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initialise(request.getSession());

		log.debug("Ingresa a exportDataGrid de la clase ExportDataAction");
		edf = (ExportDataForm) form;

		try {
			String buffer   = edf.getHtml();
			String fileName = edf.getNameFile();
			String fileType = edf.getFileType();
//			String filePath = edf.getFilePath() == null ? "" : edf.getFilePath();
			boolean isPDF = fileType.equals("pdf");
			if (isPDF) {				
				response.setContentType("application/vnd.pdf");	
				response.setHeader("Content-Disposition",
						"attachment;filename=\"" + fileName + "." + fileType
								+ "\"");
				OutputStream out=response.getOutputStream();
				
				Document document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);

               	PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
				document.open();
//				StringReader reader = new StringReader(buffer);
				
				InputStream stream = new ByteArrayInputStream(buffer.getBytes("utf-8"));	
				XMLWorkerHelper worker = XMLWorkerHelper.getInstance();				
				worker.parseXHtml(pdfWriter, document, stream, Charset.forName("utf-8"));
				document.close();
				pdfWriter.flush();
				pdfWriter.close();
			   
				out.close();
			} else {
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"" + fileName + "." + fileType
								+ "\"");
				PrintWriter out = response.getWriter();
				out.print(buffer);
				out.close();
			}

		} catch (Exception e) {
			log.error(e);
			throw new Exception(e);
		}

		return null;
	}
	
    /**
     * Inner class implementing the ImageProvider class.
     * This is needed if you want to resolve the paths to images.
     */
    public static class MyImageFactory implements ImageProvider {
        public Image getImage(String src, Map<String, String> h,
                ChainedProperties cprops, DocListener doc) {
            try {
                return Image.getInstance(
                    String.format("resources/posters/%s",
                        src.substring(src.lastIndexOf("/") + 1)));
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }    
    }

    /**
     * Inner class implementing the FontProvider class.
     * This is needed if you want to select the correct fonts.
     */
    public static class MyFontFactory implements FontProvider {
        public Font getFont(String fontname,
                String encoding, boolean embedded, float size,
                int style, BaseColor color) {
            return new Font(FontFamily.TIMES_ROMAN, size, style, color);
        }

        public boolean isRegistered(String fontname) {
            return false;
        }   
    }
}
