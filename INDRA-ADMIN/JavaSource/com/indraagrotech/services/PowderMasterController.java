package com.indraagrotech.services;

import java.util.ArrayList;
import java.util.List;

import com.indraagrotech.commonbeans.TblCategoryMaster;
import com.indraagrotech.commonbeans.TblFormMaster;
import com.indraagrotech.commonbeans.TblPowderMaster;
import com.indraagrotech.utils.CommonParams2;

public class PowderMasterController {

	private String powderName;
	private String description;
	private ArrayList<TblPowderMaster> listofoldpowdermaster;
	
	public PowderMasterController()
	{
		powderName="";
		description="";
		listofoldpowdermaster=new ArrayList<TblPowderMaster>();
		doLoadOldFormMaster();
	}
	
	
	public void doLoadOldFormMaster()
	{
		List<TblPowderMaster> retrieveALLwithHB = QuestionsUtil.retrieveALLwithHB(new TblPowderMaster(), "TblPowderMaster", "");
		listofoldpowdermaster.addAll(retrieveALLwithHB);
		
	}
	
	public String doSavePowderMaster()
	{
		try {
			if(powderName.trim().isEmpty() || description.trim().isEmpty())
			{
				CommonParams2.showMessageOnScreen("You have to enter powder master details below");
				return "error";
			}
			TblPowderMaster tblPowderMaster=new TblPowderMaster(powderName,description);
			QuestionsUtil.saveToNewDB(tblPowderMaster);
			
			return "index";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";
	}

	public String getPowderName() {
		return powderName;
	}

	public void setPowderName(String categoryName) {
		this.powderName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<TblPowderMaster> getListofoldpowdermaster() {
		return listofoldpowdermaster;
	}

	public void setListofoldcategorymaster(
			ArrayList<TblPowderMaster> listofoldcategorymaster) {
		this.listofoldpowdermaster = listofoldcategorymaster;
	}
}
