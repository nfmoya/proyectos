package com.bbva.cfs.commons.dao;

import com.bbva.cfs.conciliacion.dao.ConciliacionDao;
import com.bbva.cfs.conciliacion.dao.DifConciliacionCRUDDao;
import com.bbva.cfs.general.dao.TablaDao;
import com.bbva.cfs.lotes.dao.ConsActLotesCRUDDao;
import com.bbva.cfs.nomedibles.dao.NoMediblesDao;
import com.bbva.cfs.parameters.dao.ParametersDao;
import com.bbva.cfs.producto.dao.ProductoDao;
import com.bbva.cfs.producto.dao.ProductoPeriodoDao;
import com.bbva.cfs.producto.dao.ProductoPeriodoTarifaDao;
import com.bbva.cfs.producto.dao.ProductoPrecioDao;
import com.bbva.cfs.producto.dao.ProductoSectorDao;
import com.bbva.cfs.producto.dao.ProductoAgrupadoDao;
import com.bbva.cfs.proveedor.dao.ProveedorDao;
import com.bbva.cfs.proveedor.dao.ProveedorPeriodoDao;
import com.bbva.cfs.proveedor.dao.ProveedorTipoCambioDao;
import com.bbva.cfs.proveedor.dao.ProveedorValorDao;
import com.bbva.cfs.sector.dao.SectorDao;
import com.bbva.cfs.servfact.dao.ServFactDao;
import com.bbva.cfs.servprest.dao.ServPrestDao;
import com.bbva.cfs.servprestdetalle.dao.ServPrestDetalleDao;
import com.bbva.cfs.users.dao.UserDao;
import com.bbva.cfs.usuario.dao.UsuarioDao;

/**
 * @author xah1257
 * 
 */
public abstract class DaoFactory {


	public abstract LoginDao getLoginDao();

	public abstract CommonDao getCommonDao();

	public abstract UserDao getUserDao();
	
	public abstract ParametersDao getParametersDao();

	public abstract UsuarioDao getUsuarioDao();
	
	public abstract ProveedorDao getProveedorDao();

	public abstract ProveedorPeriodoDao getProveedorPeriodoDao();

	public abstract ProveedorValorDao getProveedorValorDao();

	public abstract SectorDao getSectorDao();

	public abstract ProductoDao getProductoDao();

	public abstract ProductoPrecioDao getProductoPrecioDao();

	public abstract ProductoSectorDao getProductoSectorDao();

	public abstract ProductoPeriodoDao getProductoPeriodoDao();

	public abstract ProductoPeriodoTarifaDao getProductoPeriodoTarifaDao();
	
	public abstract TablaDao getTablaDao();

	public abstract ConciliacionDao getConciliacionDao();
	
	public abstract DifConciliacionCRUDDao getDifConciliacionCRUDDao();

	public abstract ServFactDao getServFactDao();

	public abstract ConsActLotesCRUDDao getConsActLotesCRUDDao();
	
	public abstract ServPrestDao getServPrestDao();
        
    public abstract ServPrestDetalleDao getServPrestDetalleDao();
    
    public abstract CommonValidationDao getCommonValidationDao();

	public abstract ProveedorTipoCambioDao getProveedorTipoCambioDao();

	public abstract NoMediblesDao getNoMediblesDao();
	
	public abstract ProductoAgrupadoDao getProductoAgrupadoDao();
	
}
