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
 * Home object for domain model class TblLevelMaster.
 * @see com.indraagrotech.commonbeans.TblLevelMaster
 * @author Hibernate Tools
 */
public class TblLevelMasterHome {

	private static final Log log = LogFactory.getLog(TblLevelMasterHome.class);

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

	public void persist(TblLevelMaster transientInstance) {
		log.debug("persisting TblLevelMaster instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TblLevelMaster instance) {
		log.debug("attaching dirty TblLevelMaster instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblLevelMaster instance) {
		log.debug("attaching clean TblLevelMaster instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TblLevelMaster persistentInstance) {
		log.debug("deleting TblLevelMaster instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblLevelMaster merge(TblLevelMaster detachedInstance) {
		log.debug("merging TblLevelMaster instance");
		try {
			TblLevelMaster result = (TblLevelMaster) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblLevelMaster findById(java.lang.Integer id) {
		log.debug("getting TblLevelMaster instance with id: " + id);
		try {
			TblLevelMaster instance = (TblLevelMaster) sessionFactory
					.getCurrentSession().get(
							"com.indraagrotech.commonbeans.TblLevelMaster", id);
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

	public List<TblLevelMaster> findByExample(TblLevelMaster instance) {
		log.debug("finding TblLevelMaster instance by example");
		try {
			List<TblLevelMaster> results = (List<TblLevelMaster>) sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.indraagrotech.commonbeans.TblLevelMaster")
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
