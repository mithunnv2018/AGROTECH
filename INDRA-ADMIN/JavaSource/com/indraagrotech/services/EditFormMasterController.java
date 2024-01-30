package com.indraagrotech.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.indraagrotech.commonbeans.TblCategoryMaster;
import com.indraagrotech.commonbeans.TblFormMaster;
import com.indraagrotech.utils.CommonParams2;

public class EditFormMasterController implements Serializable{

	private String formName;
	private String description;
	private ArrayList<TblFormMaster> listofformmaster;
	private TblFormMaster selectedtblformmaster;
	private Boolean show = true;
	private TblFormMaster globalselectedtblformmaster;

	public EditFormMasterController() {
		listofformmaster = new ArrayList<TblFormMaster>();
		// doLoadOldCategoryMaster();
	}

	@PostConstruct
	private void doLoadOldCategoryMaster() {
		try {
			System.out
					.println("EditFormMasterController.doLoadOldCategoryMaster()");
			listofformmaster.clear();
			List<TblFormMaster> retrieveALLwithHB = QuestionsUtil
					.retrieveALLwithHB(new TblFormMaster(), "TblFormMaster", "");
			listofformmaster.addAll(retrieveALLwithHB);
			System.out.println("done old loading");
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}

	}

	public void doLoadUI() {
		try {
			if (selectedtblformmaster == null) {
				CommonParams2
						.showAlertBox("Not selected a valid Property from list");
				CommonParams2.showMessageOnLog(this.getClass(),
						"Not selected a valid Form from list");
				return;
			}
			formName = selectedtblformmaster.getFormName();
			description = selectedtblformmaster.getFormDesc();
			globalselectedtblformmaster = selectedtblformmaster;
			show = false;
		} catch (Exception e) {

			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}
	}

	public String doUpdateFormMaster() {
		try {
			selectedtblformmaster = globalselectedtblformmaster;

			if (selectedtblformmaster == null || formName.trim().isEmpty()
					|| description.trim().isEmpty()) {
				CommonParams2.showAlertBox("Please Enter Required Details");
				return "";
			}
			selectedtblformmaster.setFormName(formName);
			selectedtblformmaster.setFormDesc(description);

			QuestionsUtil.updateToDB(selectedtblformmaster);
			selectedtblformmaster = null;
			show = true;
			globalselectedtblformmaster = null;
			return "editformmaster.jsf";
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
			return "";
		}
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

	public ArrayList<TblFormMaster> getListofformmaster() {
		return listofformmaster;
	}

	public void setListofcategorymaster(
			ArrayList<TblFormMaster> listofcategorymaster) {
		this.listofformmaster = listofcategorymaster;
	}

	public TblFormMaster getSelectedtblformmaster() {
		return selectedtblformmaster;
	}

	public void setSelectedtblformmaster(TblFormMaster selectedtblcategorymaster) {
		this.selectedtblformmaster = selectedtblcategorymaster;
	}

	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}
}
