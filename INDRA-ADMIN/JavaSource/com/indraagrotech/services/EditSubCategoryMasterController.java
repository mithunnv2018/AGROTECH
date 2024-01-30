package com.indraagrotech.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.indraagrotech.commonbeans.TblCategoryMaster;
import com.indraagrotech.utils.CommonParams2;

public class EditSubCategoryMasterController implements Serializable{

	private String categoryName;
	private String description;
	private ArrayList<TblCategoryMaster> listofcategorymaster;
	private TblCategoryMaster selectedtblcategorymaster;
	private String primarykeyCategorMaster = null;
	private Boolean show = true;
	private ArrayList<TblCategoryMaster> oldlistofcategorymaster;

	private TblCategoryMaster globalselectedtblcategorymaster;

	public EditSubCategoryMasterController() {
		listofcategorymaster = new ArrayList<TblCategoryMaster>();
		oldlistofcategorymaster = new ArrayList<TblCategoryMaster>();

		// doLoadOldCategoryMaster();
	}

	@PostConstruct
	private void doLoadOldCategoryMaster() {
		try {
			System.out
					.println("EditCategoryMasterController.doLoadOldCategoryMaster()");
			listofcategorymaster.clear();
			List<TblCategoryMaster> retrieveALLwithHB = QuestionsUtil
					.retrieveWherClause(new TblCategoryMaster(),
							"TblCategoryMaster", "cat_subid <> '0'");
			List<TblCategoryMaster> retrieveALLwithHB2 = QuestionsUtil
					.retrieveALLwithHB(new TblCategoryMaster(),
							"TblCategoryMaster", "");
			// retrieveALLwithHB(new TblCategoryMaster(), "TblCategoryMaster",
			// "");
			listofcategorymaster.addAll(retrieveALLwithHB);
			oldlistofcategorymaster.addAll(retrieveALLwithHB2);
			System.out.println("done old loading");
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2
					.showAlertBox("Something gone wrong , please refresh page");
		}

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
			primarykeyCategorMaster = selectedtblcategorymaster.getCatSubid();
			globalselectedtblcategorymaster = selectedtblcategorymaster;
			show = false;
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2
					.showAlertBox("Something gone wrong , please refresh page");
		}
	}

	/*
	 * Retrieves cateory for displaying in datatable
	 */
	public String retrieveCategoryName(String catid) {
		try {
			List<TblCategoryMaster> retrieveWherClause = QuestionsUtil
					.retrieveWherClause(new TblCategoryMaster(),
							"TblCategoryMaster", "cat_id='" + catid + "' ");
			if (retrieveWherClause.size() > 0) {
				TblCategoryMaster tblCategoryMaster = retrieveWherClause.get(0);
				return tblCategoryMaster.getCatName();
			}
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}
		return "";
	}

	public String doUpdateCategoryMaster() {
		selectedtblcategorymaster = globalselectedtblcategorymaster;
		if (selectedtblcategorymaster == null || categoryName.trim().isEmpty()
				|| description.trim().isEmpty()) {
			CommonParams2.showAlertBox("Please Enter Required Details");
			return "";
		}
		selectedtblcategorymaster.setCatName(categoryName);
		selectedtblcategorymaster.setCatDesc(description);
		selectedtblcategorymaster.setCatSubid(primarykeyCategorMaster);

		QuestionsUtil.updateToDB(selectedtblcategorymaster);
		selectedtblcategorymaster = null;
		return "editsubcategorymaster.jsf";
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

	public String getPrimarykeyCategorMaster() {
		return primarykeyCategorMaster;
	}

	public void setPrimarykeyCategorMaster(String primarykeyCategorMaster) {
		this.primarykeyCategorMaster = primarykeyCategorMaster;
	}

	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

	public ArrayList<TblCategoryMaster> getOldlistofcategorymaster() {
		return oldlistofcategorymaster;
	}

	public void setOldlistofcategorymaster(
			ArrayList<TblCategoryMaster> oldlistofcategorymaster) {
		this.oldlistofcategorymaster = oldlistofcategorymaster;
	}
}
