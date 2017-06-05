package com.bbva.cfs.commons.dao;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.IContext;

import com.bbva.cfs.commons.dao.impl.CommonDaoImpl;
import com.bbva.cfs.commons.dao.impl.CommonValidationDaoImpl;
import com.bbva.cfs.commons.dao.impl.LoginDaoImpl;
import com.bbva.cfs.conciliacion.dao.ConciliacionDao;
import com.bbva.cfs.conciliacion.dao.DifConciliacionCRUDDao;
import com.bbva.cfs.conciliacion.dao.impl.ConciliacionDaoImpl;
import com.bbva.cfs.conciliacion.dao.impl.DifConciliacionCRUDDaoImpl;
import com.bbva.cfs.lotes.dao.ConsActLotesCRUDDao;
import com.bbva.cfs.lotes.dao.impl.ConsActLotesCRUDDaoImpl;
import com.bbva.cfs.parameters.dao.ParametersDao;
import com.bbva.cfs.parameters.dao.impl.ParametersDaoImpl;
import com.bbva.cfs.proveedor.dao.ProveedorDao;
import com.bbva.cfs.proveedor.dao.ProveedorTipoCambioDao;
import com.bbva.cfs.proveedor.dao.impl.ProveedorDaoImpl;
import com.bbva.cfs.proveedor.dao.impl.ProveedorTipoCambioDaoImpl;
import com.bbva.cfs.proveedor.dao.ProveedorPeriodoDao;
import com.bbva.cfs.proveedor.dao.impl.ProveedorPeriodoDaoImpl;
import com.bbva.cfs.proveedor.dao.ProveedorValorDao;
import com.bbva.cfs.proveedor.dao.impl.ProveedorValorDaoImpl;
import com.bbva.cfs.sector.dao.SectorDao;
import com.bbva.cfs.sector.dao.impl.SectorDaoImpl;
import com.bbva.cfs.servfact.dao.ServFactDao;
import com.bbva.cfs.servfact.dao.impl.ServFactDaoImpl;
import com.bbva.cfs.servprest.dao.ServPrestDao;
import com.bbva.cfs.servprest.dao.impl.ServPrestDaoImpl;
import com.bbva.cfs.servprestdetalle.dao.ServPrestDetalleDao;
import com.bbva.cfs.servprestdetalle.dao.impl.ServPrestDetalleDaoImpl;
import com.bbva.cfs.producto.dao.ProductoDao;
import com.bbva.cfs.producto.dao.ProductoPeriodoDao;
import com.bbva.cfs.producto.dao.ProductoPeriodoTarifaDao;
import com.bbva.cfs.producto.dao.impl.ProductoDaoImpl;
import com.bbva.cfs.producto.dao.impl.ProductoPeriodoDaoImpl;
import com.bbva.cfs.producto.dao.impl.ProductoPeriodoTarifaDaoImpl;
import com.bbva.cfs.producto.dao.ProductoPrecioDao;
import com.bbva.cfs.producto.dao.impl.ProductoPrecioDaoImpl;
import com.bbva.cfs.producto.dao.ProductoSectorDao;
import com.bbva.cfs.producto.dao.impl.ProductoSectorDaoImpl;
import com.bbva.cfs.producto.dao.ProductoAgrupadoDao;
import com.bbva.cfs.producto.dao.impl.ProductoAgrupadoDaoImpl;
import com.bbva.cfs.general.dao.TablaDao;
import com.bbva.cfs.general.dao.impl.TablaDaoImpl;
import com.bbva.cfs.users.dao.UserDao;
import com.bbva.cfs.users.dao.impl.UserDaoImpl;
import com.bbva.cfs.usuario.dao.UsuarioDao;
import com.bbva.cfs.usuario.dao.impl.UsuarioDaoImpl;
import com.bbva.cfs.nomedibles.dao.NoMediblesDao;
import com.bbva.cfs.nomedibles.dao.impl.NoMediblesDaoImpl;

/**
 * @author xah1257
 * 
 *         Dao Factory crea varios DAOs
 */
public class DaoFactoryManager extends DaoFactory {

	private static DaoFactoryManager instance;

	private static SAMWebClient samWebClient;

	/**
	 * @param iWebClient
	 *            : obligamos a que reciba este par�metro para poder inicializar
	 *            sam.
	 * @return una instancia unica del objeto <code>DaoFactoryManager</code>
	 */
	public static DaoFactoryManager getInstance(IWebClient iWebClient) {
		if (instance == null) {
			instance = new DaoFactoryManager();
		}
		samWebClient = (SAMWebClient) iWebClient;
		return instance;
	}
	
	@SuppressWarnings("unused")
	private IContext getSamContext(){
		return samWebClient.getSamContext();
	}

	public LoginDao getLoginDao() {
		return new LoginDaoImpl(samWebClient);
	}

	public CommonDao getCommonDao() {
		return new CommonDaoImpl(samWebClient);
	}

	public UserDao getUserDao() {
		return new UserDaoImpl(samWebClient);
	}

	public ParametersDao getParametersDao() {
		return new ParametersDaoImpl(samWebClient);
	}
	
	public UsuarioDao getUsuarioDao() {
		return new UsuarioDaoImpl(samWebClient);
	}
	
	public ProveedorDao getProveedorDao() {
		return new ProveedorDaoImpl(samWebClient);
	}

	public ProveedorPeriodoDao getProveedorPeriodoDao() {
		return new ProveedorPeriodoDaoImpl(samWebClient);
	}

	public ProveedorValorDao getProveedorValorDao() {
		return new ProveedorValorDaoImpl(samWebClient);
	}

	public SectorDao getSectorDao() {
		return new SectorDaoImpl(samWebClient);
	}
	
	public ProductoDao getProductoDao() {
		return new ProductoDaoImpl(samWebClient);
	}

	public ProductoPrecioDao getProductoPrecioDao() {
		return new ProductoPrecioDaoImpl(samWebClient);
	}

	public ProductoPeriodoDao getProductoPeriodoDao() {
		return new ProductoPeriodoDaoImpl(samWebClient);
	}

	public ProductoPeriodoTarifaDao getProductoPeriodoTarifaDao() {
		return new ProductoPeriodoTarifaDaoImpl(samWebClient);
	}

	public ProductoSectorDao getProductoSectorDao() {
		return new ProductoSectorDaoImpl(samWebClient);
	}
	
	public TablaDao getTablaDao() {
		return new TablaDaoImpl(samWebClient);
	}

	public ConciliacionDao getConciliacionDao() {
		return new ConciliacionDaoImpl(samWebClient);
	}
	
	public DifConciliacionCRUDDao getDifConciliacionCRUDDao() {
		return new DifConciliacionCRUDDaoImpl(samWebClient);
	}
	
	public NoMediblesDao getNoMediblesDao() {
		return new NoMediblesDaoImpl(samWebClient);
	}
	
	public ServFactDao getServFactDao() {
		return new ServFactDaoImpl(samWebClient);
	}
	
	public ConsActLotesCRUDDao getConsActLotesCRUDDao() {
		return new ConsActLotesCRUDDaoImpl(samWebClient);
	}

	@Override
	public ServPrestDao getServPrestDao() {
		return new ServPrestDaoImpl(samWebClient);
	}

	@Override
	public ServPrestDetalleDao getServPrestDetalleDao() {
		return new ServPrestDetalleDaoImpl(samWebClient);
	}
	
	@Override
	public CommonValidationDao getCommonValidationDao() {
		return new CommonValidationDaoImpl(samWebClient);
	}
	
	public ProveedorTipoCambioDao getProveedorTipoCambioDao() {
		return new ProveedorTipoCambioDaoImpl(samWebClient);
	}
	
	public ProductoAgrupadoDao getProductoAgrupadoDao() {
		return new ProductoAgrupadoDaoImpl(samWebClient);
	}
	
}
