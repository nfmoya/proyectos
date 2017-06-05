package com.gefa.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.gefa.beans.RegistroCarga;
import com.gefa.model.GefaAlta;
import com.gefa.utils.ConectionManager;

public abstract class Dao {

	protected Session session = null;
	protected Transaction tx = null;

	public abstract void addOne(Object o);

	public abstract void addList(List o) throws Exception;

	public Dao() {
		// TODO Auto-generated constructor stub
		if (session == null || !session.isOpen()) {
			session = ConectionManager.getSessionFactory().openSession();
			tx = session.beginTransaction();
		}
	}

	public void closeSession() {
		// TODO Auto-generated method stub
		try {
			if (session.isOpen()) {
				session.close();
			}

		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	protected List getResultList(Criteria criteria) {

		List listReturn = new ArrayList();
		criteria.setCacheMode(CacheMode.IGNORE);
		// results = criteria.scroll(FORWARD_ONLY);
		ScrollableResults results = criteria.scroll(ScrollMode.FORWARD_ONLY);
		try {

			if (results.next()) {

				Object[] res = results.get();
				listReturn.add(res);
				while (!results.isLast()) {
					results.next();
					res = results.get();
					listReturn.add(res);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// if (results.last())
		// listReturn.add(results.get());

		return listReturn;
	}

}
