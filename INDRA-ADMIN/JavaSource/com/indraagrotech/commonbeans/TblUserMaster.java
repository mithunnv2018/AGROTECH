package com.indraagrotech.commonbeans;

// Generated 21 Nov, 2012 11:40:22 AM by Hibernate Tools 3.4.0.CR1

/**
 * TblUserMaster generated by hbm2java
 */
public class TblUserMaster implements java.io.Serializable {

	private Integer UId;
	private String UFullName;
	private String UUserName;
	private String UPass;
	private int UTag;

	public TblUserMaster() {
	}

	public TblUserMaster(String UFullName, String UUserName, String UPass,
			int UTag) {
		this.UFullName = UFullName;
		this.UUserName = UUserName;
		this.UPass = UPass;
		this.UTag = UTag;
	}

	public Integer getUId() {
		return this.UId;
	}

	public void setUId(Integer UId) {
		this.UId = UId;
	}

	public String getUFullName() {
		return this.UFullName;
	}

	public void setUFullName(String UFullName) {
		this.UFullName = UFullName;
	}

	public String getUUserName() {
		return this.UUserName;
	}

	public void setUUserName(String UUserName) {
		this.UUserName = UUserName;
	}

	public String getUPass() {
		return this.UPass;
	}

	public void setUPass(String UPass) {
		this.UPass = UPass;
	}

	public int getUTag() {
		return this.UTag;
	}

	public void setUTag(int UTag) {
		this.UTag = UTag;
	}

}
