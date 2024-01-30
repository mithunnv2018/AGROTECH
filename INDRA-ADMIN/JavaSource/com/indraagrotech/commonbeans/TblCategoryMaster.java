package com.indraagrotech.commonbeans;

// Generated 21 Nov, 2012 11:40:22 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * TblCategoryMaster generated by hbm2java
 */
public class TblCategoryMaster implements java.io.Serializable {

	private String catId;
	private String catName;
	private String catDesc;
	private String catSubid;
	private Set<TblProductMaster> tblProductMasters = new HashSet<TblProductMaster>(
			0);

	public TblCategoryMaster() {
	}

	public TblCategoryMaster(String catId, String catName, String catDesc,
			String catSubid) {
		this.catId = catId;
		this.catName = catName;
		this.catDesc = catDesc;
		this.catSubid = catSubid;
	}

	public TblCategoryMaster(String catId, String catName, String catDesc,
			String catSubid, Set<TblProductMaster> tblProductMasters) {
		this.catId = catId;
		this.catName = catName;
		this.catDesc = catDesc;
		this.catSubid = catSubid;
		this.tblProductMasters = tblProductMasters;
	}

	public String getCatId() {
		return this.catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return this.catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getCatDesc() {
		return this.catDesc;
	}

	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}

	public String getCatSubid() {
		return this.catSubid;
	}

	public void setCatSubid(String catSubid) {
		this.catSubid = catSubid;
	}

	public Set<TblProductMaster> getTblProductMasters() {
		return this.tblProductMasters;
	}

	public void setTblProductMasters(Set<TblProductMaster> tblProductMasters) {
		this.tblProductMasters = tblProductMasters;
	}

}