package com.indraagrotech.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.indraagrotech.commonbeans.TblCategoryMaster;
import com.indraagrotech.utils.CommonParams2;

public class SubCategoryMasterController implements Serializable{

	private String categoryName;
	private String description;
	private ArrayList<TblCategoryMaster> listofoldcategorymaster;
	private String primarykeyCategorMaster=null;
	
	public SubCategoryMasterController()
	{
		categoryName="";
		description="";
		listofoldcategorymaster=new ArrayList<TblCategoryMaster>();
		doLoadOldCategoryMaster();
	}
	
	
	public void doLoadOldCategoryMaster()
	{
		try {
			List<TblCategoryMaster> retrieveALLwithHB = QuestionsUtil.retrieveWherClause(new TblCategoryMaster(), "TblCategoryMaster", "cat_subid <> '0'");
//		retrieveALLwithHB(new TblCategoryMaster(), "TblCategoryMaster", "");
			List<TblCategoryMaster> retrieveALLwithHB2 = QuestionsUtil.retrieveALLwithHB(new TblCategoryMaster(), "TblCategoryMaster", "");
//		
			listofoldcategorymaster.addAll(retrieveALLwithHB2);
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}
		
	}
	
	/*
	 * Retrieves cateory for displaying in datatable
	 */
	public String retrieveCategoryName(String catid)
	{
		try {
			List<TblCategoryMaster> retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblCategoryMaster(), "TblCategoryMaster",	"cat_id='"+catid+"' ");
			if(retrieveWherClause.size()>0){
				TblCategoryMaster tblCategoryMaster = retrieveWherClause.get(0);
				return tblCategoryMaster.getCatName();
			}
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}
		return "";
	}
	
	/* 
	 * The format for param a is = 1ABC47
	 * we need to extract only the part before ABC ie only the first few numerical cahracter. 
	 * 
	 */
	private Integer doRetrieveFromPrimaryKey(String a)
	{
		if(!(a.length()>0))
		{
			return 0;
		}
		String numerical="";
		for(int i=0;i<a.length();i++)
		{
			char charAt = a.charAt(i);
			if(Character.isDigit(charAt))
			{
				numerical+=charAt;
			}
			else
			{
				break;
			}
		}
		
		int parseInt = Integer.parseInt(numerical);
		return parseInt;
	}
	
	public String doSaveCategoryMaster()
	{
		try {
			if(categoryName.trim().isEmpty() || description.trim().isEmpty())
			{
				CommonParams2.showAlertBox("You have to enter category details below");
				return "";
			}
			if(primarykeyCategorMaster==null || primarykeyCategorMaster.isEmpty())
			{
				CommonParams2.showAlertBox("You have to select valid category details from dropdown");
				return "";
			}
			Integer num1=doRetrieveFromPrimaryKey(primarykeyCategorMaster);
			num1++;
			String catId=QuestionsUtil.doCreateCatId(num1);
			if(catId==null)
			{
				CommonParams2.showAlertBox("sorry could not generate CATID from TblLevelMaster");
				CommonParams2.showMessageOnLog(this.getClass(),"sorry could not generate CATID from TblLevelMaster");
				
				return "";
			}
			TblCategoryMaster tblcategorymaster=new TblCategoryMaster(catId,categoryName, description, primarykeyCategorMaster);
			QuestionsUtil.saveToNewDB(tblcategorymaster);
			
			return "editsubcategorymaster.jsf";
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
//			
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


	public String getPrimarykeyCategorMaster() {
		return primarykeyCategorMaster;
	}


	public void setPrimarykeyCategorMaster(String primarykey) {
		this.primarykeyCategorMaster = primarykey;
	}
}
