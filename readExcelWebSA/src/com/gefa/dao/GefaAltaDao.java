package com.gefa.dao;

import java.util.List;

import com.gefa.model.GefaAlta;

public class GefaAltaDao extends Dao {
public GefaAltaDao() {
	// TODO Auto-generated constructor stub
	super();
}
	@Override
	public void addOne(Object gfa) {
		// Session session = null;
		// Transaction tx = null;
		if (gfa != null) {
			// efectua modificaciones
			try {
				// session = ConectionManager.getSessionFactory().openSession();
				// tx = session.beginTransaction();
				session.save((GefaModificacionDao) gfa);
				tx.commit();
				session.close();

			} catch (Exception e0) {
				// errores
				try {
					tx.rollback();
				} catch (Exception er) {
					// log.error("Error al efectuar el rollback", er);
					er.printStackTrace();
				}
				// log.error("Error al efectuar la modificación", e0);
				e0.printStackTrace();
			}
		}
	}

	@Override
	public void addList(List gfaLista)throws Exception {
		// Session session = null;
		// Transaction tx = null;
		// efectua modificaciones
		try {
			// session = ConectionManager.getSessionFactory().openSession();
			// tx = session.beginTransaction();
			for (GefaAlta gfa : (List<GefaAlta>) gfaLista) {
				session.save(gfa);
				System.out.println(gfa.getAnioFact());
			}
			tx.commit();
			session.close();

		} catch (Exception e0) {
			// errores
			try {
				tx.rollback();
			} catch (Exception er) {
				// log.error("Error al efectuar el rollback", er);
				er.printStackTrace();
			}
			// log.error("Error al efectuar la modificación", e0);
			e0.printStackTrace();
            throw e0;
		}

	}

}
