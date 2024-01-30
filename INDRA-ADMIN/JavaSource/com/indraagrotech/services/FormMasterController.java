package com.indraagrotech.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.indraagrotech.commonbeans.TblCategoryMaster;
import com.indraagrotech.commonbeans.TblFormMaster;
import com.indraagrotech.utils.CommonParams2;

public class FormMasterController implements Serializable{

	private String formName;
	private String description;
	private ArrayList<TblFormMaster> listofoldformmaster;
	
	public FormMasterController()
	{
		formName="";
		description="";
		listofoldformmaster=new ArrayList<TblFormMaster>();
		doLoadOldFormMaster();
	}
	
	
	public void doLoadOldFormMaster()
	{
		try {
			List<TblFormMaster> retrieveALLwithHB = QuestionsUtil.retrieveALLwithHB(new TblFormMaster(), "TblFormMaster", "");
			listofoldformmaster.addAll(retrieveALLwithHB);
		} catch (Exception e) {
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}
		
	}
	
	public String doSaveFormMaster()
	{
		try {
			if(formName.trim().isEmpty() || description.trim().isEmpty())
			{
				CommonParams2.showAlertBox("You have to enter property details below");
				return "";
			}
			TblFormMaster tblFormMaster=new TblFormMaster(formName,description);
			QuestionsUtil.saveToNewDB(tblFormMaster);
			
			return "editformmaster.jsf";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}
		return "";
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String categoryName) {
		this.formName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<TblFormMaster> getListofoldformmaster() {
		return listofoldformmaster;
	}

	public void setListofoldcategorymaster(
			ArrayList<TblFormMaster> listofoldcategorymaster) {
		this.listofoldformmaster = listofoldcategorymaster;
	}
}
