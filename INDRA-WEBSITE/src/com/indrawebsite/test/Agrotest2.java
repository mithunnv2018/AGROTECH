package com.indrawebsite.test;

import java.util.List;

import org.junit.Test;

import com.indraagrotech.commonbeans.TblProductMaster;
import com.indraagrotech.services.QuestionsUtil;

public class Agrotest2 {

	@Test
	public void doTestRetrieveDistinct() {
		List<String> retrieveDistinctWhereClause = QuestionsUtil
				.retrieveDistinctWhereClause(new TblProductMaster(),
						"TblProductMaster", "productBreadcrumb",
						"product_primary_cat_id='1A47'");
		for (int i = 0; i < retrieveDistinctWhereClause.size(); i++) {
			String productBreadcrumb = retrieveDistinctWhereClause.get(i);
			// = tblProductMaster.getProductBreadcrumb();
			System.out.println("breadcrumb is " + productBreadcrumb);
		}
		System.out.println("End of distinct");
	}

	@Test
	public void doTestProductSearch() {
		System.out.println("Agrotest2.doTestProductSearch()");
		String searchtext = "demo";
		List<TblProductMaster> retrieveWherClause = QuestionsUtil
				.retrieveWherClause(new TblProductMaster(),
						"TblProductMaster as prod",
						" lower(prod.productName) like '%" + searchtext + "%' ");
		System.out.println("No of Products found=" + retrieveWherClause.size());
		
		
		String whereclause = " lower(a.productName) like '%" + searchtext
				+ "%' OR lower(a.productKeywords) like '%" + searchtext
				+ "%'";

		List<String> retrieveDistinctWhereClause = QuestionsUtil
				.retrieveDistinctWhereClause(new TblProductMaster(),
						"TblProductMaster", "productBreadcrumb", whereclause);
		System.out.println("No of breadcrumbs ="+retrieveDistinctWhereClause.size());

	}

}
