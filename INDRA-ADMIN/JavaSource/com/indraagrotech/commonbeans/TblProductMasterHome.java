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
 * Home object for domain model class TblProductMaster.
 * @see com.indraagrotech.commonbeans.TblProductMaster
 * @author Hibernate Tools
 */
public class TblProductMasterHome {

	private static final Log log = LogFactory
			.getLog(TblProductMasterHome.class);

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

	public void persist(TblProductMaster transientInstance) {
		log.debug("persisting TblProductMaster instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TblProductMaster instance) {
		log.debug("attaching dirty TblProductMaster instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblProductMaster instance) {
		log.debug("attaching clean TblProductMaster instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TblProductMaster persistentInstance) {
		log.debug("deleting TblProductMaster instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblProductMaster merge(TblProductMaster detachedInstance) {
		log.debug("merging TblProductMaster instance");
		try {
			TblProductMaster result = (TblProductMaster) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblProductMaster findById(java.lang.Integer id) {
		log.debug("getting TblProductMaster instance with id: " + id);
		try {
			TblProductMaster instance = (TblProductMaster) sessionFactory
					.getCurrentSession().get(
							"com.indraagrotech.commonbeans.TblProductMaster",
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

	public List<TblProductMaster> findByExample(TblProductMaster instance) {
		log.debug("finding TblProductMaster instance by example");
		try {
			List<TblProductMaster> results = (List<TblProductMaster>) sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.indraagrotech.commonbeans.TblProductMaster")
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
