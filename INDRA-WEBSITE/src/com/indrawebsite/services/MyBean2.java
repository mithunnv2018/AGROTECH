package com.indrawebsite.services;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.context.RequestContext;

import com.indraagrotech.commonbeans.TblCategoryMaster;
import com.indraagrotech.commonbeans.TblFormMaster;
import com.indraagrotech.commonbeans.TblProductDetails;
import com.indraagrotech.commonbeans.TblProductMaster;
import com.indraagrotech.services.ProductMasterController;
import com.indraagrotech.services.QuestionsUtil;
import com.indraagrotech.utils.CommonParams2;
import com.indraagrotech.utils.HibernateUtils;

public class MyBean2 implements Serializable{

	ArrayList<TblProductMaster> listofproduct;
	HashMap<String, List<TblProductMaster>> mainmap = new HashMap<String, List<TblProductMaster>>();
	ArrayList<String> listofbreadcrumb = new ArrayList<String>();
	TblProductMaster tblproductmaster;
	String folderforimages = "http://www.indraagrotech.com/product_images";
	private JasperPrint jasperPrint;
	private Boolean fromsearch=false;

	public MyBean2() {
		try {
			listofproduct = new ArrayList<TblProductMaster>();
			List retrieveALLwithHB = QuestionsUtil.retrieveALLwithHB(
					new TblProductMaster(), "TblProductMaster", "");
			listofproduct.addAll(retrieveALLwithHB);

			listofbreadcrumb = new ArrayList<String>();
			mainmap = new HashMap<String, List<TblProductMaster>>();
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), "constructor error="+e.getMessage());
		}
		
	}
	
	@PostConstruct
	public void loadFromSearchRequest()
	{
		
//		String req1 = requestParammap.get("query");
		doSearchProducts();
		
	}

	public void init() throws JRException {

		System.out.println("MyBean2.init()");
		Connection connection = HibernateUtils.currentSession().connection();
		HashMap params = new HashMap();
		params.put("productid2", tblproductmaster.getProductId());
//		params.put("productName", tblproductmaster.getProductName());
//		params.put("productDesc", tblproductmaster.getProductDesc());
//		params.put("productApp", tblproductmaster.getProductApp());
//		params.put("productPacking", tblproductmaster.getProductPacking());
 		String imagePath = FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath("/images");
 		String subreportPath = FacesContext.getCurrentInstance()
		.getExternalContext().getRealPath("/reports");//+"//";
 		String separator = File.separator;
 		subreportPath+=separator;
 		imagePath+=separator;
	 
		params.put("MITHIMAGE", imagePath);
		params.put("SUBREPORT_DIR",subreportPath);
//		passtoreport.setProductThumbnailUrl(imagePath);
		
//		List<TblProductMaster> listofProductslocal = new ArrayList<TblProductMaster>();
//		
//		listofProductslocal.add(tblproductmaster);
//		
//		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
//				listofProductslocal);
		String reportPath = FacesContext.getCurrentInstance()
				.getExternalContext()
				.getRealPath("/reports/ParentReport.jasper");

		System.out.println("Report path is " + reportPath);
		System.out.println("params passed are " + params.toString());
//		jasperPrint = JasperFillManager.fillReport(reportPath, params,beanCollectionDataSource);
		jasperPrint=JasperFillManager.fillReport(reportPath, params, connection);
	}

	public void PDF(ActionEvent actionEvent) throws JRException, IOException {
		init();
		System.out.println("MyBean2.PDF()");
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition",
				"attachment; filename=report.pdf");
		ServletOutputStream servletOutputStream = httpServletResponse
				.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,
				servletOutputStream);
		FacesContext.getCurrentInstance().responseComplete();
		System.out.println("PDF DONE ");

	}

	public void doGetProducts() {
		System.out.println("MyBean2.doGetProducts()");
		clearcache();
		try {
			 
			FacesContext currentInstance = FacesContext.getCurrentInstance();
			Map<String, String> requestParameterMap = currentInstance
					.getExternalContext().getRequestParameterMap();
			String catid = requestParameterMap.get("catid");
			System.out.println("Cat id is:" + catid);
			List<String> retrieveDistinctWhereClause = QuestionsUtil
					.retrieveDistinctWhereClause(new TblProductMaster(),
							"TblProductMaster", "productBreadcrumb",
							"product_primary_cat_id='" + catid + "' ");
			for (int i = 0; i < retrieveDistinctWhereClause.size(); i++) {
				String string2 = retrieveDistinctWhereClause.get(i);
				List<TblProductMaster> retrieveWherClause = QuestionsUtil
						.retrieveWherClause(new TblProductMaster(),
								"TblProductMaster", "product_breadcrumb='"
										+ string2 + "' ");
				String doGetCategoryNames = doGetCategoryNames(string2);

				mainmap.put(doGetCategoryNames, retrieveWherClause);
				listofbreadcrumb.add(doGetCategoryNames);
				System.out.println("Bread crumb " + i + " " + doGetCategoryNames);
			}
			generateDivTags();
		} catch (Exception e) {
			System.err.println("Error here"+e.getMessage());
			CommonParams2.showAlertBox("Reloading page due to idle time.");
			CommonParams2.refreshPage();
			CommonParams2.showMessageOnLog(this.getClass(), "mit error here "+e.getMessage());
		}
	}
	
	private void clearcache()
	{
		mainmap.clear();
		listofbreadcrumb.clear();
	}
	
	public void doSearchProducts()
	{
		System.out.println("MyBean2.doSearchProducts()");
		clearcache();
		try {
			fromsearch=true;
			FacesContext currentInstance = FacesContext.getCurrentInstance();
			Map<String, String> requestParameterMap = currentInstance
					.getExternalContext().getRequestParameterMap();
			String req1 = requestParameterMap.get("query");
			String searchtext = requestParameterMap.get("searchtext");
			
			
			
			if(req1!=null && req1.trim().isEmpty()==false )
			{
				if(!req1.contains("..."))
				{
					searchtext=req1;
				}
				System.out.println("Its from search parameter = "+req1);
			}
			if(searchtext==null || searchtext.trim().isEmpty())
			{
				System.err.println("Sorry mith search text is null");
				fromsearch=false;
				return;
			}
			searchtext=searchtext.toLowerCase().trim();
			searchtext= searchtext.replace("+", " ");
			String whereclause=" lower(a.productName) like '%" + searchtext
			+ "%' OR lower(a.productKeywords) like '%" + searchtext
			+ "%'";
				//" lower(a.productKeywords) like '%"+searchtext+"%' ";
			
			List<String> retrieveDistinctWhereClause = QuestionsUtil
			.retrieveDistinctWhereClause(new TblProductMaster(),
					"TblProductMaster", "productBreadcrumb",whereclause);
			
			for (int i = 0; i < retrieveDistinctWhereClause.size(); i++) {
				String string2 = retrieveDistinctWhereClause.get(i);
				List<TblProductMaster> retrieveWherClause = QuestionsUtil
						.retrieveWherClause(new TblProductMaster(),
								"TblProductMaster", "product_breadcrumb='"
										+ string2 + "' ");
				String doGetCategoryNames = doGetCategoryNames(string2);

				mainmap.put(doGetCategoryNames, retrieveWherClause);
				listofbreadcrumb.add(doGetCategoryNames);
				System.out.println("Bread crumb after search  " + i + " " + doGetCategoryNames);
			}
			generateDivTags();
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), "error here "+e.getMessage());
			CommonParams2.showAlertBox("Reloading page due to idle time.");
			CommonParams2.refreshPage();
//			e.printStackTrace();
		}
		
//		List<TblProductMaster> retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblProductMaster(), "TblProductMaster as prod", whereclause);
		 
		
	}

	public void generateDivTags() {
		String data = "";
		int index=0;
		for (String breadcrumb : listofbreadcrumb) {
			data+="<div id =\"product_content\">";
			data += "<div id=\"product_heading\"> "
					+ breadcrumb + "</div>";
			if(index==0 && fromsearch==false)
			{
				data+="<div id=\"close_btn\"> <img src=\"images/x.png\" width=\"25\" height=\"29\" /> </div>";
			}
			data+="<div style=\"clear: both\"></div>";
			
			data+="</div>";
			data+="<div id =\"details_content\">";

			List<TblProductMaster> list = mainmap.get(breadcrumb);
			for (TblProductMaster tblProductMaster : list) {
				String thumbnail = folderforimages + "/"
						+ tblProductMaster.getProductThumbnailUrl();
				Integer productId = tblProductMaster.getProductId();
				String onclickcall = "showproductdetails(" + productId + ")";
				// class=\"trigger\"
//				data+="<div id =\"details_content\">";
				
				data += "<div class=\"profile\"> <a href=\"#\" title=\""+tblProductMaster.getProductName()+"\" class=\"trigger\" onclick=\""
						+ onclickcall
						+ "\" >" 
						+"<div class=\"avtar_image\"> <img 	src=\""
						+ thumbnail
						+ "\" alt=\""+tblProductMaster.getProductName()+"\" /> </div> <br /> ";
				data += "<span class=\"name construction\" id=\"product_item_"+(index+1)+"\">"
					+ tblProductMaster.getProductName()
					+ "</span> </a> </div>";

				
			}
			data+="<div style=\"clear: both\"></div></div>";
//			data += "<div class=\"clear\"></div>";
			index++;

		}
		System.out.println("data html is " + data);
		// data="hit atul here";
		callJavascript(data);
		// callJavascript(data);
	}

	public void loadProductOnly() {
		System.out.println("MyBean2.loadProductOnly()");
		try {
			FacesContext currentInstance = FacesContext.getCurrentInstance();
			Map<String, String> requestParameterMap = currentInstance
					.getExternalContext().getRequestParameterMap();
			String prodid = requestParameterMap.get("productid2");

			System.out.println("product  id is:" + prodid);

			List<TblProductMaster> retrieveWherClause = QuestionsUtil
					.retrieveWherClause(new TblProductMaster(), "TblProductMaster",
							"product_id=" + prodid);
			if (retrieveWherClause.size() > 0) {
				TblProductMaster tblProductMaster2 = retrieveWherClause.get(0);
				tblproductmaster = tblProductMaster2;
				CommonParams2.showMessageOnLog(this.getClass(),
						"Product loaded name=" + tblproductmaster.getProductName());
				String extradivs=generateLightBoxDiv(tblproductmaster);
				callJavascript(tblproductmaster.getProductName(),
						tblproductmaster.getProductDesc(), folderforimages + "/"
								+ tblproductmaster.getProductLargeimageUrl(),extradivs);

			} else {
				CommonParams2.showMessageOnLog(this.getClass(),
						"Sorry No product to load");
			}
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), "Error here "+e.getMessage());
			CommonParams2.showAlertBox("Reloading page due to idle time.");
			CommonParams2.refreshPage();
			e.printStackTrace();
		}

	}
	
	public String generateLightBoxDiv(TblProductMaster prodmaster)
	{
		String returndata="";
		List<TblProductDetails> retrieveWherClause = QuestionsUtil.retrieveWherClause(new TblProductDetails(), "TblProductDetails", "product_id="+prodmaster.getProductId());
		
		returndata+="<div id=\"applciationx2\" style=\"font-weight:bold;font-style: italic;\">Description </div>";
		
		returndata += prodmaster.getProductDesc() + "<br/>";
		
		returndata+="<div id=\"applciationx3\" style=\"font-weight:bold;font-style: italic;\">Application </div>";
		
		returndata+=prodmaster.getProductApp() + "<br/><br/>";
		
//		returndata+="<br/>";

		
		
		
		returndata+="<table border=\"0\"  >";
		
		for(int i=0;i<retrieveWherClause.size();i++)
		{
			TblProductDetails tblProductDetails = retrieveWherClause.get(i);
			Integer formId = tblProductDetails.getTblFormMaster().getFormId();
			List<TblFormMaster> retrieveWherClause2 = QuestionsUtil.retrieveWherClause(new TblFormMaster(), "TblFormMaster", "form_id="+formId);
			TblFormMaster tblFormMaster = retrieveWherClause2.get(0);
			
			String prodetailsPropertyvalue = tblProductDetails.getProdetailsPropertyvalue();
			String formName = tblFormMaster.getFormName();
			
			returndata+="<tr align=\"left\"> <td>"+formName+"</td>";
			returndata+="<td>"+prodetailsPropertyvalue+"</td></tr>";

			
		}
		returndata+="</table><br/>";
		
		returndata+="<div id=\"applciationx4\" style=\"font-weight:bold;font-style: italic;\">Stock Paste Preparation </div>";
		
		returndata+=prodmaster.getProductStockpasteprepare();
		returndata+="<br/><br/>";
		
		returndata+="<div id=\"packingx\" style=\"font-weight:bold;font-style: italic;\"> Packing </div>";
		returndata+=prodmaster.getProductPacking();
		
		
		return returndata;
	}

	public void callJavascript(String a, String b, String img,String c) {
		RequestContext currentInstance = RequestContext.getCurrentInstance();
		
		if (currentInstance!=null) {
			currentInstance.execute("displayInId('" + a + "' , '" + b + "' , '"
					+ img + "' , '" + c + "')");
			currentInstance.update("producttoexportmith");
		}
		else
		{
			CommonParams2.showMessageOnLog(this.getClass(), "calljavascript(,,,) Reqctxt is null");
		}
	}

	public void callJavascript(String data) {
		RequestContext currentInstance = RequestContext.getCurrentInstance();
		if(currentInstance!=null)
		{
			currentInstance.execute("datafromserver('" + data + "')");
		}
		else
		{
			System.err.println("Sorry mith currentInstance is null");
		}
//		currentInstance.execute("datafromserver('" + data + "')");
		
	}

	private String doGetCategoryNames(String s) {
		String returnvalue2 = "";
		if (s.contains(",")) {
			String[] split = s.split(",");
			for (int i = 0; i < split.length; i++) {
				List<TblCategoryMaster> retrieveWherClause = QuestionsUtil
						.retrieveWherClause(new TblCategoryMaster(),
								"TblCategoryMaster", "cat_id='" + split[i]
										+ "' ");
				TblCategoryMaster tblCategoryMaster = retrieveWherClause.get(0);
				if ((split.length - 1) != i) {
					if(i==0)
					{
						returnvalue2 +=   "<font color=\"#333\">"   +   tblCategoryMaster.getCatName() + "</font> | ";
					}
					else
					{
						returnvalue2 += tblCategoryMaster.getCatName() + ">>";
					}
				} else {
					returnvalue2 += tblCategoryMaster.getCatName() + "";
				}
			}
		} else {
			List<TblCategoryMaster> retrieveWherClause = QuestionsUtil
					.retrieveWherClause(new TblCategoryMaster(),
							"TblCategoryMaster", "cat_id='" + s + "' ");
			TblCategoryMaster tblCategoryMaster = retrieveWherClause.get(0);
			returnvalue2 += "<font color=\"#333\">"   +tblCategoryMaster.getCatName()+ "</font>";// + "s";

		}
		return returnvalue2;
	}

	public ArrayList<TblProductMaster> getListofproduct() {
		return listofproduct;
	}

	public void setListofproduct(ArrayList<TblProductMaster> listofproduct) {
		this.listofproduct = listofproduct;
	}

	public HashMap<String, List<TblProductMaster>> getMainmap() {
		return mainmap;
	}

	public void setMainmap(HashMap<String, List<TblProductMaster>> mainmap) {
		this.mainmap = mainmap;
	}

	public ArrayList<String> getListofbreadcrumb() {
		return listofbreadcrumb;
	}

	public void setListofbreadcrumb(ArrayList<String> listofbreadcrumb) {
		this.listofbreadcrumb = listofbreadcrumb;
	}

	public TblProductMaster getTblproductmaster() {
		return tblproductmaster;
	}

	public void setTblproductmaster(TblProductMaster tblproductmaster) {
		this.tblproductmaster = tblproductmaster;
	}

}
