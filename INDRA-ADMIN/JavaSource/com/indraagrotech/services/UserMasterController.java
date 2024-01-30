package com.indraagrotech.services;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;

import com.indraagrotech.commonbeans.TblUserMaster;
import com.indraagrotech.utils.CommonParams2;
import com.indraagrotech.utils.HibernateUtils;

public class UserMasterController implements Serializable{

	private String UUserName;
	private String UPass;

	public UserMasterController() {
		UUserName = "";
		UPass = "";
	}

	public String reset() {
		this.UUserName = "";
		this.UPass = "";
		// U_FullName="";
		// U_Tag="";
		return "loginpage";
	}

	public String checklogin() {
		if (UUserName == null || UUserName.trim().isEmpty() || UPass == null
				|| UPass.isEmpty()) {
			return reset();
		}
		try {
			List<TblUserMaster> dataList = QuestionsUtil.retrieveWherClause(
					new TblUserMaster(), "TblUserMaster", "U_UserName='"
							+ UUserName + "' AND U_Pass='" + UPass + "' ");

			if (dataList.size() > 0) {
				CommonParams2
						.showMessageOnScreen("Successfull Login for Admin!");
				return "productmaster";//"successlogin";
			} else {
				// reset();
				System.out.println("BAD LOG");
				// FacesContext.getCurrentInstance().addMessage("idpanelgrid2",
				// new
				// FacesMessage("Your Login was not successfull!","Please Reenter your credentials."));
				CommonParams2
						.showMessageOnScreen("Your Login was not successfull!,Please Reenter your credentials.");

				return reset();
			}
		} catch (Exception e) {
			System.out.println("checklogin method error:" + e.getMessage());
//			e.printStackTrace();
		}
		return "success";
	}

	public String updateLogin() {
		try {
			System.out.println("UserMasterController.updateLogin()");

			ExternalContext ctx = FacesContext.getCurrentInstance()
					.getExternalContext();
			UserMasterController retobj=null;
			
			HttpSession session = (HttpSession) ctx.getSession(false);
			String nameofsession="userMaster";
			if (session.getAttribute(nameofsession) != null) {
				System.out.println("Has value in session");
				 retobj = (UserMasterController) session.getAttribute(nameofsession);
			}
			

			List<TblUserMaster> retrieveWherClause = QuestionsUtil
					.retrieveWherClause(new TblUserMaster(), "TblUserMaster",
							"U_UserName='" + retobj.getUUserName() + "'");
			if (retrieveWherClause.size() > 0) {
				TblUserMaster tblUserMaster = retrieveWherClause.get(0);
				tblUserMaster.setUPass(UPass);
				QuestionsUtil.updateToDB(tblUserMaster);
				 

				System.out.println("Done Updating password");
			}
			return "loginpage";
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}
		return "";
	}

	public String getUUserName() {
		return UUserName;
	}

	public void setUUserName(String uUserName) {
		UUserName = uUserName;
	}

	public String getUPass() {
		return UPass;
	}

	public void setUPass(String uPass) {
		UPass = uPass;
	}

}
