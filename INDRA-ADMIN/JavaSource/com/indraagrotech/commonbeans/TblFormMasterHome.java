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
 * Home object for domain model class TblFormMaster.
 * @see com.indraagrotech.commonbeans.TblFormMaster
 * @author Hibernate Tools
 */
public class TblFormMasterHome {

	private static final Log log = LogFactory.getLog(TblFormMasterHome.class);

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

	public void persist(TblFormMaster transientInstance) {
		log.debug("persisting TblFormMaster instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TblFormMaster instance) {
		log.debug("attaching dirty TblFormMaster instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblFormMaster instance) {
		log.debug("attaching clean TblFormMaster instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TblFormMaster persistentInstance) {
		log.debug("deleting TblFormMaster instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblFormMaster merge(TblFormMaster detachedInstance) {
		log.debug("merging TblFormMaster instance");
		try {
			TblFormMaster result = (TblFormMaster) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblFormMaster findById(java.lang.Integer id) {
		log.debug("getting TblFormMaster instance with id: " + id);
		try {
			TblFormMaster instance = (TblFormMaster) sessionFactory
					.getCurrentSession().get(
							"com.indraagrotech.commonbeans.TblFormMaster", id);
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

	public List<TblFormMaster> findByExample(TblFormMaster instance) {
		log.debug("finding TblFormMaster instance by example");
		try {
			List<TblFormMaster> results = (List<TblFormMaster>) sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.indraagrotech.commonbeans.TblFormMaster")
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
