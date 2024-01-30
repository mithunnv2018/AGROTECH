package com.indraagrotech.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

import org.junit.Test;

import com.indraagrotech.commonbeans.TblCategoryMaster;
import com.indraagrotech.commonbeans.TblFormMaster;
import com.indraagrotech.commonbeans.TblLevelMaster;
import com.indraagrotech.commonbeans.TblUserMaster;
import com.indraagrotech.services.QuestionsUtil;


public class TestAgrotech {

	public TestAgrotech() {
		// TODO Auto-generated constructor stub
	}
	@Test
	public void doTestDemo()
	{
		List<TblUserMaster> retrieveALLwithHB = QuestionsUtil.retrieveALLwithHB(new TblUserMaster(), "TblUserMaster", "");
		
		System.out.println("No of users="+retrieveALLwithHB.size());
		
	}
	
	@Test
	public void doTestCreateCatId()
	{
		System.out.println("TestAgrotech.doTestCreateCatId()");
		int level=1;
		TblLevelMaster tblLevelMaster=null;
		String primarykey="";
		List<TblLevelMaster> retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblLevelMaster(), "TblLevelMaster", "level_innumber="+level);
		if(retrieveWherClause.size()>0)
		{
			 tblLevelMaster = retrieveWherClause.get(0);
			 primarykey=tblLevelMaster.getLevelId().toString();
			 primarykey+=tblLevelMaster.getLevelChar();
			 primarykey+=tblLevelMaster.getLevelIdTag();
			 System.out.println("Primary key is "+primarykey);
			 int a=tblLevelMaster.getLevelIdTag()+1;
			 tblLevelMaster.setLevelIdTag(a);
			 QuestionsUtil.updateToDB(tblLevelMaster);
			 
		}
		else
		{
			System.err.println("Sorry nothin in table level="+level);
		}
		
	}
	
	@Test
	public void doTestIfSessionReturns()
	{
		System.out.println("TestAgrotech.doTestIfSessionReturns()");
		TblFormMaster abc=new TblFormMaster("test2", "test desc");
		System.err.println("Uncomment below line for code  to work");
//		QuestionsUtil.saveToNewDB(abc);
		
		if(abc.getFormId()!=null)
		{
			System.out.println(abc.getFormId());
		}
		else
		{
			System.err.println("Sorry No formid ");
		}
	}
	
	@Test
	public void doTestBreadCrumb()
	{
		String catid="3ABC13";
		
		Boolean stoploop=false;
		String primarycatkey="";
		ArrayList<String> breadcrumblist=new ArrayList<String>();
		while(!stoploop)
		{
			
			List<TblCategoryMaster> retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblCategoryMaster(), "TblCategoryMaster", "cat_id='"+catid+"' ");
			if(retrieveWherClause==null || retrieveWherClause.size()<=0)
			{
				stoploop=true;
				break;
			}
			breadcrumblist.add(catid);
			TblCategoryMaster tblCategoryMaster = retrieveWherClause.get(0);
			String catSubid = tblCategoryMaster.getCatSubid();
			if(catSubid.equals("0"))
			{
				primarycatkey=catid;
				stoploop=true;
				break;
			}
			catid=catSubid;
			
		}
		System.out.println("Breadcrumb before reversesort = "+breadcrumblist.toString());
		Collections.reverse(breadcrumblist);
		System.out.println("Breadcrumb after reversesort = "+breadcrumblist.toString());
		String breadcrumb = breadcrumblist.toString();
//		Matcher.quoteReplacement(breadcrumb)
		breadcrumb= breadcrumb.replace('[', ' ');
		breadcrumb= breadcrumb.replaceAll("]", "");
		breadcrumb= breadcrumb.replaceAll(" ", "");
		
		breadcrumb= breadcrumb.trim();
		System.out.println("Bread crumb string is="+breadcrumb+".");
		System.out.println("Primary Cat id is ="+primarycatkey+".");
	}
	
	@Test
	public void doTestNOTWhereClause()
	{
		List<TblCategoryMaster> retrieveALLwithHB = QuestionsUtil.retrieveWherClause(new TblCategoryMaster(), "TblCategoryMaster", "cat_subid <> '0'");
		System.out.println("TestAgrotech.doTestNOTWhereClause()");
		System.out.println("Size of not equla to 0 is "+retrieveALLwithHB.size());
	}
	
}
