package com.indraagrotech.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.indraagrotech.commonbeans.TblCategoryMaster;
import com.indraagrotech.utils.CommonParams2;

public class EditCategoryMasterController implements Serializable{

	private String categoryName;
	private String description;
	private ArrayList<TblCategoryMaster> listofcategorymaster;
	private TblCategoryMaster selectedtblcategorymaster;
	private Boolean show = true;
	private TblCategoryMaster globalselectedtblcategorymaster;

	public EditCategoryMasterController() {
		listofcategorymaster = new ArrayList<TblCategoryMaster>();
		// doLoadOldCategoryMaster();
	}

	@PostConstruct
	private void doLoadOldCategoryMaster() {
		try {
			System.out
					.println("EditCategoryMasterController.doLoadOldCategoryMaster()");
			listofcategorymaster.clear();
			// List<TblCategoryMaster> retrieveALLwithHB =
			// QuestionsUtil.retrieveALLwithHB(new TblCategoryMaster(),
			// "TblCategoryMaster", "");
			List<TblCategoryMaster> retrieveALLwithHB = QuestionsUtil
					.retrieveWherClause(new TblCategoryMaster(),
							"TblCategoryMaster", "cat_subid='0'");
			listofcategorymaster.addAll(retrieveALLwithHB);
			System.out.println("done old loading");
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2
					.showAlertBox("Something gone wrong , please refresh page");
		}
		// globalselectedtblcategorymaster=null;

	}

	public void clearcache() {
		categoryName = "";
		description = "";
		selectedtblcategorymaster = null;
		show = true;
		doLoadOldCategoryMaster();
	}

	public void doLoadUI() {
		try {
			if (selectedtblcategorymaster == null) {
				CommonParams2
						.showAlertBox("Not selected a valid Category from list");
				CommonParams2.showMessageOnLog(this.getClass(),
						"Not selected a valid Category from list");
				return;
			}

			categoryName = selectedtblcategorymaster.getCatName();
			description = selectedtblcategorymaster.getCatDesc();

			globalselectedtblcategorymaster = selectedtblcategorymaster;

			show = false;
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2
					.showAlertBox("Something gone wrong , please refresh page");
		}
	}

	public String doUpdateCategoryMaster() {
		try {
			if (globalselectedtblcategorymaster == null
					|| categoryName.trim().isEmpty()
					|| description.trim().isEmpty()) {
				CommonParams2.showAlertBox("Please Enter Required Details");
				return "";
			}
			selectedtblcategorymaster = globalselectedtblcategorymaster;

			selectedtblcategorymaster.setCatName(categoryName);
			selectedtblcategorymaster.setCatDesc(description);

			QuestionsUtil.updateToDB(selectedtblcategorymaster);
			selectedtblcategorymaster = null;
			show = true;
			// clearcache();
			globalselectedtblcategorymaster = null;
			return "editcategorymaster.jsf";
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2
					.showAlertBox("Something gone wrong , please refresh page");
			return "";
		}
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

	public ArrayList<TblCategoryMaster> getListofcategorymaster() {
		return listofcategorymaster;
	}

	public void setListofcategorymaster(
			ArrayList<TblCategoryMaster> listofcategorymaster) {
		this.listofcategorymaster = listofcategorymaster;
	}

	public TblCategoryMaster getSelectedtblcategorymaster() {
		return selectedtblcategorymaster;
	}

	public void setSelectedtblcategorymaster(
			TblCategoryMaster selectedtblcategorymaster) {
		this.selectedtblcategorymaster = selectedtblcategorymaster;
	}

	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}
}
