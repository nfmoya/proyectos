package com.gefa.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gefa.model.GefaModificaciones;
import com.gefa.utils.ConectionManager;

public class GefaModificacionDao extends Dao{
public GefaModificacionDao() {
	// TODO Auto-generated constructor stub
	super();
}
	public void addOne(Object gfm){
//        Session session = null;
//        Transaction tx = null;
        if (gfm != null) {
            //efectua modificaciones
            try {
				// session = ConectionManager.getSessionFactory().openSession();
				//
				// tx = session.beginTransaction();
                session.save((GefaModificaciones) gfm);
                tx.commit();
                session.close();
                
            } catch (Exception e0) {
                //errores
                try {
                    tx.rollback();
                } catch (Exception er) {
                    //log.error("Error al efectuar el rollback", er);
                    er.printStackTrace();
                }
                // log.error("Error al efectuar la modificación", e0);
                e0.printStackTrace();
            }
        }
	}
	
	public void addList(List gfmLista) throws Exception{
//        Session session = null;
//        Transaction tx = null;
            //efectua modificaciones
        try {
			// session = ConectionManager.getSessionFactory().openSession();
			// tx = session.beginTransaction();
            for (GefaModificaciones gfm : (List<GefaModificaciones>)gfmLista) {
                session.save(gfm);					
			}
            tx.commit();
            session.close();
            
        } catch (Exception e0) {
            //errores
            try {
                tx.rollback();
                
            } catch (Exception er) {
                //log.error("Error al efectuar el rollback", er);
                er.printStackTrace();
            }
            // log.error("Error al efectuar la modificación", e0);
            e0.printStackTrace();
            throw e0;
        }
        
	}
	
	
}
