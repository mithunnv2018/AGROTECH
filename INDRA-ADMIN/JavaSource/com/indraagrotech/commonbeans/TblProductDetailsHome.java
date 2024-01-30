package com.indraagrotech.commonbeans;

// Generated 21 Nov, 2012 11:40:23 AM by Hibernate Tools 3.4.0.CR1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class TblProductDetails.
 * @see com.indraagrotech.commonbeans.TblProductDetails
 * @author Hibernate Tools
 */
public class TblProductDetailsHome {

	private static final Log log = LogFactory
			.getLog(TblProductDetailsHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(TblProductDetails transientInstance) {
		log.debug("persisting TblProductDetails instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TblProductDetails instance) {
		log.debug("attaching dirty TblProductDetails instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblProductDetails instance) {
		log.debug("attaching clean TblProductDetails instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TblProductDetails persistentInstance) {
		log.debug("deleting TblProductDetails instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblProductDetails merge(TblProductDetails detachedInstance) {
		log.debug("merging TblProductDetails instance");
		try {
			TblProductDetails result = (TblProductDetails) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblProductDetails findById(java.lang.Integer id) {
		log.debug("getting TblProductDetails instance with id: " + id);
		try {
			TblProductDetails instance = (TblProductDetails) sessionFactory
					.getCurrentSession().get(
							"com.indraagrotech.commonbeans.TblProductDetails",
							id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TblProductDetails> findByExample(TblProductDetails instance) {
		log.debug("finding TblProductDetails instance by example");
		try {
			List<TblProductDetails> results = (List<TblProductDetails>) sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.indraagrotech.commonbeans.TblProductDetails")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
