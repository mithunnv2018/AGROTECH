package com.indrawebsite.services;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import com.indraagrotech.commonbeans.TblProductMaster;
import com.indraagrotech.services.QuestionsUtil;
import com.indraagrotech.utils.CommonParams2;

public class AutoFeature implements Serializable {

	private String autostring="";
	public AutoFeature()
	{
		
	}
	@PostConstruct
	public void setDoAutoComplete()
	{
		String returndata="";
		try {
			List<TblProductMaster> retrieveALLwithHB = QuestionsUtil.retrieveALLwithHB(new TblProductMaster(), "TblProductMaster", "");
			for(int i=0;i<retrieveALLwithHB.size();i++)
			{
				TblProductMaster tblProductMaster = retrieveALLwithHB.get(i);
				returndata+=tblProductMaster.getProductKeywords();
				if((i+1)!=retrieveALLwithHB.size())
				{
					returndata+=",";
				}
			}
		} catch (Exception e) {
			 System.err.println("Error here "+e.getMessage());
			 CommonParams2.showAlertBox("Reloading page due to idle time.");
			 CommonParams2.refreshPage();
				
//			e.printStackTrace();
		}
		System.out.println("data is "+returndata);
		autostring=returndata;
		
	}
	/* 
	 * Returns comma seperated product keywords.
	 */
	public String doAutoComplete()
	{
		System.out.println("AutoFeature.doAutoComplete()");
		if(autostring.trim().isEmpty())
		{
			System.out.println("Reloaded autocomplete ");
			setDoAutoComplete();
		}
		System.out.println(autostring);
		return autostring;
	}
	
}
