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
 * Home object for domain model class TblPowderMaster.
 * @see com.indraagrotech.commonbeans.TblPowderMaster
 * @author Hibernate Tools
 */
public class TblPowderMasterHome {

	private static final Log log = LogFactory.getLog(TblPowderMasterHome.class);

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

	public void persist(TblPowderMaster transientInstance) {
		log.debug("persisting TblPowderMaster instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TblPowderMaster instance) {
		log.debug("attaching dirty TblPowderMaster instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblPowderMaster instance) {
		log.debug("attaching clean TblPowderMaster instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TblPowderMaster persistentInstance) {
		log.debug("deleting TblPowderMaster instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblPowderMaster merge(TblPowderMaster detachedInstance) {
		log.debug("merging TblPowderMaster instance");
		try {
			TblPowderMaster result = (TblPowderMaster) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblPowderMaster findById(java.lang.Integer id) {
		log.debug("getting TblPowderMaster instance with id: " + id);
		try {
			TblPowderMaster instance = (TblPowderMaster) sessionFactory
					.getCurrentSession()
					.get("com.indraagrotech.commonbeans.TblPowderMaster", id);
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

	public List<TblPowderMaster> findByExample(TblPowderMaster instance) {
		log.debug("finding TblPowderMaster instance by example");
		try {
			List<TblPowderMaster> results = (List<TblPowderMaster>) sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.indraagrotech.commonbeans.TblPowderMaster")
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
