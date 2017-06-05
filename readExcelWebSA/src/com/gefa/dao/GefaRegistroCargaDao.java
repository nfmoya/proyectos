package com.gefa.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import com.gefa.beans.RegistroCarga;
import com.gefa.model.GefaAlta;
import com.gefa.model.GefaModificaciones;
import com.gefa.utils.ConectionManager;

public class GefaRegistroCargaDao extends Dao {

	@SuppressWarnings("unchecked")
	public List<RegistroCarga> getRegistrosCarga() {

		Session session = null;

		List<RegistroCarga> regCarga = new ArrayList<RegistroCarga>();
		// result ;
		try {
			session = ConectionManager.getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = null;
			Criteria criteria2 = null;
			// if (name.isAssignableFrom(GefaAlta.class)) {

			criteria = session.createCriteria(GefaAlta.class);

			// } else if (name.isAssignableFrom(GefaModificaciones.class)) {

			criteria2 = session.createCriteria(GefaModificaciones.class);
			// }

			criteria.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("nombreArchivoCarga"))
					.add(Projections.rowCount())
					.add(Projections.property("fechaAlta")));

			criteria2.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("nombreArchivoCarga"))
					.add(Projections.rowCount())
					.add(Projections.property("fechaAlta")));
			try {
				// List result = getResultList(criteria);

				regCarga.addAll(getListaRegistroCarga(getResultList(criteria)));
				regCarga.addAll(getListaRegistroCarga(getResultList(criteria2)));

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			closeSession();
			System.out.println(regCarga.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return regCarga;

	}

	private List<RegistroCarga> getListaRegistroCarga(List result) {
		List<RegistroCarga> listado = new ArrayList<RegistroCarga>();

		// List<Object> result1 = (List<Object>) result;
		Iterator itr = result.iterator();
		while (itr.hasNext()) {
			RegistroCarga carga = new RegistroCarga();

			Object[] obj = (Object[]) itr.next();
			System.out.println("cosito");
			carga.setNombreArchivo(String.valueOf(obj[0]));
			carga.setCantidadRegistros(String.valueOf(obj[1]));
			carga.setFechaAlta(String.valueOf(obj[2]));

			listado.add(carga);

		}

		return listado;
	}

	@Override
	public void addOne(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addList(List o) {
		// TODO Auto-generated method stub

	}

}
