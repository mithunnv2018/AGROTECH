package com.indraagrotech.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.indraagrotech.commonbeans.TblCategoryMaster;
import com.indraagrotech.utils.CommonParams2;

public class CategoryMasterController implements Serializable{

	private String categoryName;
	private String description;
	private ArrayList<TblCategoryMaster> listofoldcategorymaster;
	
	public CategoryMasterController()
	{
		categoryName="";
		description="";
		listofoldcategorymaster=new ArrayList<TblCategoryMaster>();
		doLoadOldCategoryMaster();
	}
	
	
	public void doLoadOldCategoryMaster()
	{
		try {
			List<TblCategoryMaster> retrieveALLwithHB =QuestionsUtil.retrieveWherClause(new TblCategoryMaster(), "TblCategoryMaster", "cat_subid='0'");
//		 QuestionsUtil.retrieveALLwithHB(new TblCategoryMaster(), "TblCategoryMaster", "");
			listofoldcategorymaster.addAll(retrieveALLwithHB);
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}
		
	}
	
	public String doSaveCategoryMaster()
	{
		try {
			if(categoryName.trim().isEmpty() || description.trim().isEmpty())
			{
//				CommonParams2.showMessageOnScreen("You have to enter category details below");
				CommonParams2.showAlertBox("You have to enter category details below");
				return "";
			}
			String catId=QuestionsUtil.doCreateCatId(1);
			if(catId==null)
			{
//				CommonParams2.showMessageOnScreen("sorry could not generate CATID from TblLevelMaster");
				CommonParams2.showMessageOnLog(this.getClass(),"sorry could not generate CATID from TblLevelMaster");
				CommonParams2.showAlertBox("sorry could not generate CATID from TblLevelMaster");
				return "";
			}
			TblCategoryMaster tblcategorymaster=new TblCategoryMaster(catId,categoryName, description, "0");
			QuestionsUtil.saveToNewDB(tblcategorymaster);
			
			return "editcategorymaster.jsf";
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}
		return "";
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<TblCategoryMaster> getListofoldcategorymaster() {
		return listofoldcategorymaster;
	}

	public void setListofoldcategorymaster(
			ArrayList<TblCategoryMaster> listofoldcategorymaster) {
		this.listofoldcategorymaster = listofoldcategorymaster;
	}
}
