package com.indraagrotech.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.indraagrotech.commonbeans.TblCategoryMaster;
 
import com.indraagrotech.commonbeans.TblPowderMaster;
import com.indraagrotech.utils.CommonParams2;

public class EditPowderMasterController {

	private String powderName;
	private String description;
	private ArrayList<TblPowderMaster> listofpowdermaster;
	private TblPowderMaster selectedtblpowdermaster;
	
	public EditPowderMasterController() {
		listofpowdermaster=new ArrayList<TblPowderMaster>();
//		doLoadOldCategoryMaster();
	}
	
	@PostConstruct
	private void doLoadOldCategoryMaster()
	{
		System.out
				.println("EditPowderMasterController.doLoadOldCategoryMaster()");
		listofpowdermaster.clear();
		List<TblPowderMaster> retrieveALLwithHB = QuestionsUtil.retrieveALLwithHB(new TblPowderMaster(), "TblPowderMaster", "");
		listofpowdermaster.addAll(retrieveALLwithHB);
		System.out.println("done old loading");
		
	}
	
	public void doLoadUI()
	{
		if(selectedtblpowdermaster==null)
		{
			CommonParams2.showMessageOnScreen("Not selected a valid Powder from list");
			CommonParams2.showMessageOnLog(this.getClass(), "Not selected a valid Powder from list");
			return;
		}
		powderName=selectedtblpowdermaster.getPowName();
		description=selectedtblpowdermaster.getPowDesc();
		
	}
	
	public String doUpdatePowderMaster()
	{
		if(selectedtblpowdermaster==null)
		{
			return "error";
		}
		selectedtblpowdermaster.setPowName(powderName);
		selectedtblpowdermaster.setPowDesc(description);
		
		QuestionsUtil.updateToDB(selectedtblpowdermaster);
		selectedtblpowdermaster=null;
		return "index";
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

	public ArrayList<TblPowderMaster> getListofpowdermaster() {
		return listofpowdermaster;
	}

	 

	public TblPowderMaster getSelectedtblpowdermaster() {
		return selectedtblpowdermaster;
	}

	public void setSelectedtblpowdermaster(
			TblPowderMaster selectedtblcategorymaster) {
		this.selectedtblpowdermaster = selectedtblcategorymaster;
	}

	public void setListofpowdermaster(ArrayList<TblPowderMaster> listofpowdermaster) {
		this.listofpowdermaster = listofpowdermaster;
	}
}
