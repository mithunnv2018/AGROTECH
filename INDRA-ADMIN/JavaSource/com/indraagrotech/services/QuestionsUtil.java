package com.indraagrotech.services;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.indraagrotech.commonbeans.TblLevelMaster;
import com.indraagrotech.utils.HibernateUtils;

public class QuestionsUtil {

	public QuestionsUtil() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * Creates a catID of table TblCategoryMaster using TblLevelMaster
	 */
	public static String doCreateCatId(Integer level)
	{
		System.out.println("QuestionsUtil.doCreateCatId()");
//		int level=1;
		TblLevelMaster tblLevelMaster=null;
		String primarykey="";
		List<TblLevelMaster> retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblLevelMaster(), "TblLevelMaster", "level_innumber="+level);
		if(retrieveWherClause.size()>0)
		{
			 tblLevelMaster = retrieveWherClause.get(0);
			 primarykey=tblLevelMaster.getLevelInnumber()+"";
			 primarykey+=tblLevelMaster.getLevelChar();
			 primarykey+=tblLevelMaster.getLevelIdTag();
			 System.out.println("Primary key is "+primarykey);
			 int a=tblLevelMaster.getLevelIdTag()+1;
			 tblLevelMaster.setLevelIdTag(a);
			 QuestionsUtil.updateToDB(tblLevelMaster);
			 
			 return primarykey;
		}
		else
		{
			System.err.println("Sorry nothin in table level="+level);
		}
		return null;
	}
	public static void saveToNewDB(Object mobj) {
		Session session = HibernateUtils.currentSession();
		Transaction tx = null;
		boolean rollback = true;
		try {
			tx = session.beginTransaction();
			 session.save(mobj);
			
			tx.commit();
			rollback = false;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FacesException("saveToNewDB Failed for "
					+ mobj.getClass(), e.getCause());
		} finally {
			if (rollback && tx != null) {
				tx.rollback();
			}
			HibernateUtils.closeSession();
		}
	}

	public static void updateToDB(Object mobj) {
		Session session = HibernateUtils.currentSession();
		Transaction tx = null;
		boolean rollback = true;
		try {
			tx = session.beginTransaction();
			session.update(mobj);

			tx.commit();
			rollback = false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rollback && tx != null) {
				tx.rollback();
			}
			HibernateUtils.closeSession();
		}
	}

	public static int deleteFromDB(String tablename, String whereclause) {
		Session session = HibernateUtils.currentSession();
		Transaction tx = null;
		int ret = 0;
		boolean rollback = true;
		try {
			tx = session.beginTransaction();
			Query createQuery = session.createQuery("delete from " + tablename
					+ " where " + whereclause);
			ret = createQuery.executeUpdate();
			System.out.println("Mith Rows Deleted:" + ret);
			tx.commit();
			rollback = false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rollback && tx != null) {
				tx.rollback();
			}
			HibernateUtils.closeSession();

		}
		return ret;
	}

	public static Integer doGetNextPK(String tablename, String pkid) {

		if (tablename.isEmpty()) {
			Integer ret = new Random().nextInt(10000);
			return ret;
		} else {
			StringBuffer sqlQry = new StringBuffer();
			sqlQry.append(" SELECT  max(E." + pkid + ") from  " + tablename
					+ " E");

			try {
				Session session = HibernateUtils.currentSession();
				Query dashBrdQry = session.createQuery(sqlQry.toString());

				List dataList = dashBrdQry.list();
				System.out.println("size is" + dataList.size());

				if (dataList.get(0) == null) {
					return 1;
				}
				System.out.println("class is" + dataList.get(0).getClass()
						+ "valui is " + dataList.get(0));
				Integer a = 0;

				System.out.println("IS a string here");
				if (dataList.get(0).getClass().getName().indexOf("String") != -1) {
					String r = (String) dataList.get(0);
					a = Integer.parseInt(r);
				} else if (dataList.get(0).getClass().getName()
						.indexOf("Integer") != -1) {
					a = (Integer) dataList.get(0);
				}
				a++;
				System.out.println("Value is " + a);
				return a;

				// Integer ret=(Integer) dataList.get(0);
				// ret++;

				// return ret;

			} catch (Exception e) {
				System.out.println("dogetNextPK method has error "
						+ e.getMessage());
				e.printStackTrace();
			} finally {

				HibernateUtils.closeSession();
			}
		}
		return null;
	}

//	public static <T> List retrieveALL(T a, String tablename, String columnames) {
//		Session session = HibernateUtils.currentSession();
//		try {
//			Query query = session.createQuery("SELECT   " + columnames
//					+ " FROM " + tablename + " a");
//
//			// +" WHERE u_username='" + U_UserName +
//			// "' AND u_pass='"+U_Pass+"'");
//			List<T> dataList = query.list();
//			return dataList;
//
//		} catch (Exception e) {
//			System.out.println("checklogin method error:" + e.getMessage());
//			e.printStackTrace();
//		} finally {
//
//			HibernateUtils.closeSession();
//		}
//		return null;
//	}

	public static <T> List retrieveALLwithHB(T a, String tablename,
			String columnames) {
		Session session = HibernateUtils.currentSession();
		try {
			Query query = session.createQuery("from  " + tablename + " a");

			// +" WHERE u_username='" + U_UserName +
			// "' AND u_pass='"+U_Pass+"'");
			List<T> dataList = query.list();
			return dataList;

		} catch (Exception e) {
			System.out.println("retieveALlwithHB method error:"
					+ e.getMessage());
			e.printStackTrace();
		} finally {

			HibernateUtils.closeSession();
		}
		return null;
	}

	public static <T> List retrieveWherClause(T a, String tablename,
			String whereclause) {
		Session session = HibernateUtils.currentSession();
		try {
			// Query query = session.createQuery("SELECT   "+columnames
			// + " FROM "+tablename+" a" +" "+whereclause);

			// +" WHERE u_username='" + U_UserName +
			// "' AND u_pass='"+U_Pass+"'");
			Query query = session.createQuery("from " + tablename + " where "
					+ whereclause);
			List<T> dataList = query.list();
			return dataList;

		} catch (Exception e) {
			System.out.println("checklogin method error:" + e.getMessage());
			e.printStackTrace();
		} finally {

			HibernateUtils.closeSession();
		}
		return null;
	}

	public static <T> List retrieveDistinctWhereClause(T a, String tablename,String distinctcolumn,String whereclause )
	{
		Session session = HibernateUtils.currentSession();
		System.out.println("QuestionsUtil.retrieveDistinctWhereClause()");
		try {
			Query createQuery = session.createQuery("SELECT DISTINCT a."+distinctcolumn+" FROM "+tablename+" a WHERE "+whereclause);
			 List<T> list = createQuery.list();
			 return list;
		} catch (Exception e) {
			System.err.println("error here mith : "+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			HibernateUtils.closeSession();
		}
		return null;
	}
	public String resetSessionMith(String sessionname, String retvalue) throws IOException {
		ExternalContext ctx = FacesContext.getCurrentInstance()
				.getExternalContext();
		// Map<String, Object> sessionMap = ctx.getSessionMap();
		// QuestionMaster m=(QuestionMaster)sessionMap.get("questionMaster");

		HttpSession session = (HttpSession) ctx.getSession(false);
		session.removeAttribute(sessionname);
		System.out.println("resetSessionMith done");
//		ctx.getRequestContextPath();
		ctx.redirect(retvalue);
		return retvalue + "?redirect=true";
		// session.invalidate();
	}

	/**
	 * retrieves a random number and checks if the new Primary Key is Not
	 * repeated.
	 * 
	 * @param tablename
	 * @param column
	 *            must be a integer type columnn
	 * @param b
	 *            is just for methodoverloading
	 * @return
	 */
	public static Integer doGetNextPK(String tablename, String column, boolean b) {
		boolean stop2 = false;
		Integer pk2 = 0;

		for (int i = 0; i < 100 && stop2 != true; i++) {
			pk2 = doGetNextPK("", "");
			if (tablename.length() == 0 || column.length() == 0) {
				System.out
						.println("doGetNextPK tablename is empty so returned random number");
				return pk2;
			}
			try {
				List abcd = retrieveWherClause(new Object(), tablename, column
						+ "=" + pk2);
				if (abcd.size() == 0) {
					System.out
							.println("doGetNextPK is a uniques number so returned random number");

					stop2 = true;
					return pk2;
				} else {
					System.out
							.println("doGetNextPK tablename is already existing");

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
				break;
			}
		}
		System.out.println("doGetNextPK(3args) had an error");
		return pk2;
	}

	public static <T> boolean dosaveSessionValue2(T obj, String nameofsession) {

		System.out.println("QuestionsUtil.dosaveSessionValue2()");
		try {
			ExternalContext ctx = FacesContext.getCurrentInstance()
					.getExternalContext();

			HttpSession session = (HttpSession) ctx.getSession(false);
			session.setAttribute(nameofsession, obj);

			return true;
		} catch (Exception e) {

			System.err.println("errror here " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public static <T> T dogetSessionValue2(T obj, String nameofsession) {
		T retobj=null;
		System.out.println("QuestionsUtil.dogetSessionValue2()");
		try {
			ExternalContext ctx = FacesContext.getCurrentInstance()
					.getExternalContext();

			HttpSession session = (HttpSession) ctx.getSession(false);
			if(session.getAttribute(nameofsession)!=null)
			{
				System.out.println("Has value in session");
				retobj=(T)session.getAttribute(nameofsession);
			}
			
		} catch (Exception e) {
			System.err.println("error here"+e.getMessage() );
			e.printStackTrace();
		}
		return retobj;

	}
	
	/*
	 * Inorder to redirect to specific page 
	 */
	public static void doRedirectToURL(String abc)
	{
		System.out.println("QuestionsUtil.doRedirectToURL()");
		ExternalContext ctx = FacesContext.getCurrentInstance()
		.getExternalContext();
		
		try {
			ctx.redirect(abc);
		} catch (IOException e) {
			System.err.println("Exception here ="+e.getMessage());
			e.printStackTrace();
		}

	}

}
