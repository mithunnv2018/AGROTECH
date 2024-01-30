package com.indraagrotech.services;
import java.util.ArrayList;
import java.util.List;

import com.indraagrotech.commonbeans.TblProductMaster;
public class DemoProduct {

	public ArrayList<TblProductMaster> listofproducts;
	Boolean showproduct=false;
	public DemoProduct()
	{
		listofproducts=new ArrayList<TblProductMaster>();
		List retrieveALLwithHB = QuestionsUtil.retrieveALLwithHB(new TblProductMaster(), "TblProductMaster", "");
		listofproducts.addAll(retrieveALLwithHB);
	}
	
	public void doShowProduct()
	{
		showproduct=true;
	}
	public void doHideProduct()
	{
		showproduct=false;
	}
	public ArrayList<TblProductMaster> getListofproducts() {
		return listofproducts;
	}
	public void setListofproducts(ArrayList<TblProductMaster> listofproducts) {
		this.listofproducts = listofproducts;
	}

	public Boolean getShowproduct() {
		return showproduct;
	}

	public void setShowproduct(Boolean showproduct) {
		this.showproduct = showproduct;
	}
}
