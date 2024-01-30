package com.indraagrotech.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.indraagrotech.commonbeans.TblCategoryMaster;
import com.indraagrotech.commonbeans.TblFormMaster;
import com.indraagrotech.commonbeans.TblPowderMaster;
import com.indraagrotech.commonbeans.TblProductDetails;
import com.indraagrotech.commonbeans.TblProductMaster;
import com.indraagrotech.utils.CommonParams2;

public class ProductMasterController implements Serializable{

	private String productName;
	private String description;
	private String productapplication;
	private String productcategory;
	private String productpacking;
	private ArrayList<TblFormMaster> oldform;
	private ArrayList<TblPowderMaster> oldpowder;
	private Integer tempform, temppowder;
	private String temppropertyvalue;
	private String productKeywords="";
	private String productstockpasteprepare;

	private ArrayList<HashMap> listofformpowder;
	private HashMap selectedformpowder;

	String FORMOBJECT = "formobject";
	String POWDEROBJECT = "powderobject";
	String FORMNAME = "formname";
	String POWDERNAME = "powdername";
	String PRODUCTDETAILSOBJECT = "productdetailsobject";

	private ArrayList<TblCategoryMaster> listofoldcategorymaster;

	private Boolean toEdit;
	private ArrayList<TblProductMaster> listofoldproductmaster;
	private TblProductMaster selectedproductmaster;

	private String productthumbnailimage = "";
	private String productlargeimage = "";

	private UploadedFile filethumbnailimage;
	private UploadedFile  filelargeimage;
	
	private Boolean show=false;
	private Boolean showedit=true;
	private TblProductMaster globalselectedproductmaster;
	
	private String errormessage="";

	
	public ProductMasterController() {
		System.out.println("ProductMasterController.ProductMasterController()");
		productName = "";
		description = "";
		productapplication = "";
		productcategory = "";
		productpacking = "";
		productKeywords="";
		productstockpasteprepare="";
		listofoldcategorymaster = new ArrayList<TblCategoryMaster>();
		
		// doLoadOldCategoryMaster();
	}

	@PostConstruct
	public void doLoadOldCategoryMaster() {
		try {
			List<TblCategoryMaster> retrieveALLwithHB = QuestionsUtil
					.retrieveALLwithHB(new TblCategoryMaster(),
							"TblCategoryMaster", "");
			listofoldcategorymaster.addAll(retrieveALLwithHB);

			List<TblFormMaster> retrieveALLwithHB2 = QuestionsUtil
					.retrieveALLwithHB(new TblFormMaster(), "TblFormMaster", "");
			oldform = new ArrayList<TblFormMaster>();
			oldform.addAll(retrieveALLwithHB2);

			List<TblPowderMaster> retrieveALLwithHB3 = QuestionsUtil
					.retrieveALLwithHB(new TblPowderMaster(), "TblPowderMaster", "");
			oldpowder = new ArrayList<TblPowderMaster>();
			oldpowder.addAll(retrieveALLwithHB3);

			listofoldproductmaster = new ArrayList<TblProductMaster>();
			List<TblProductMaster> retrieveALLwithHB4 = QuestionsUtil
					.retrieveALLwithHB(new TblProductMaster(), "TblProductMaster",
							"");
			listofoldproductmaster.addAll(retrieveALLwithHB4);

			listofformpowder = new ArrayList<HashMap>();
			toEdit =true;// false;
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}
	}
	
	public Boolean doShowEditButton()
	{
		Boolean returnshowedit=false;
		try {
			if(showedit==true && toEdit==true)
			{
				returnshowedit=true;
			}
			else if(show==true && showedit==false)
			{
				returnshowedit=false;
			}
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}
		
		return returnshowedit;
	}

	public void doMakeEditable() {
		try {
			System.out.println("ProductMasterController.doMakeEditable()");
			System.out.println("Edit is " + toEdit);
			
			clearalldata();
			if(toEdit==true)
			{
				showedit=true;
				show=false;
			}
			else
			{
				showedit=false;
				show=true;
			}
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}

	}
	
	public void clearalldata()
	{
		productName="";
		description="";
		productapplication="";
		productpacking="";
		productcategory="";
		tempform=-1;
		temppropertyvalue="";
		listofformpowder.clear();
		productthumbnailimage = "";
		productlargeimage = "";
		productKeywords="";
		productstockpasteprepare="";
		
	}

	public void doLoadUI() {
		try {
			System.out.println("ProductMasterController.doLoadUI()");
			productapplication = selectedproductmaster.getProductApp();
			description = selectedproductmaster.getProductDesc();
			productName = selectedproductmaster.getProductName();
			productpacking = selectedproductmaster.getProductPacking();
			productcategory = selectedproductmaster.getTblCategoryMaster()
					.getCatId();
			productthumbnailimage = selectedproductmaster.getProductThumbnailUrl();
			productlargeimage = selectedproductmaster.getProductLargeimageUrl();
			Integer productId = selectedproductmaster.getProductId();
			productKeywords= selectedproductmaster.getProductKeywords();
			productstockpasteprepare=selectedproductmaster.getProductStockpasteprepare();
			globalselectedproductmaster=selectedproductmaster;
			
			List<TblProductDetails> retrieveWherClause = QuestionsUtil
					.retrieveWherClause(new TblProductDetails(),
							"TblProductDetails", "product_id=" + productId);
			if (retrieveWherClause.size() <= 0) {
				CommonParams2.showMessageOnLog(getClass(),
						"There is no such product in TblProductDetails");

				return;
			}
			listofformpowder.clear();

			for (int i = 0; i < retrieveWherClause.size(); i++) {
				TblProductDetails tblProductDetails = retrieveWherClause.get(i);
				Integer formId = tblProductDetails.getTblFormMaster().getFormId();
//			Integer powId = tblProductDetails.getTblPowderMaster().getPowId();
				
				List<TblFormMaster> retrieveWherClause2 = QuestionsUtil
						.retrieveWherClause(new TblFormMaster(), "TblFormMaster",
								"form_id=" + formId);
//			List<TblPowderMaster> retrieveWherClause3 = QuestionsUtil
//					.retrieveWherClause(new TblPowderMaster(),
//							"TblPowderMaster", "pow_id=" + powId);

				TblFormMaster tblFormMaster = retrieveWherClause2.get(0);
//			TblPowderMaster tblPowderMaster = retrieveWherClause3.get(0);

				HashMap map2 = new HashMap();
				map2.put(FORMNAME, tblFormMaster.getFormName());
				map2.put(FORMOBJECT, tblFormMaster);
				map2.put(POWDERNAME, tblProductDetails.getProdetailsPropertyvalue());
//tblPowderMaster.getPowName());
//			map2.put(POWDEROBJECT, tblPowderMaster);
				map2.put(PRODUCTDETAILSOBJECT, tblProductDetails);

				listofformpowder.add(map2);

			}

			System.out.println("The no of rows retireved is ="
					+ listofformpowder.size());
			
			show=true;
			showedit=false;
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}

	}
	
	public void handleFileUploadLargeImage(FileUploadEvent event)
	{
		try {
			filelargeimage=event.getFile();
			System.out
					.println("ProductMasterController.handleFileUploadLargeImage()");
			productlargeimage=filelargeimage.getFileName();
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");		}
	}

	public void handleFileUploadThubmnailImage(FileUploadEvent event)
	{
		try {
			filethumbnailimage=event.getFile();
			System.out
					.println("ProductMasterController.handleFileUploadThubmnailImage()");
			productthumbnailimage=filethumbnailimage.getFileName();
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}
	}
	public String doUpdateProductMaster() {
		try {
			System.out
					.println("ProductMasterController.doUpdateProductMaster()");
			if (productName.trim().isEmpty() || description.trim().isEmpty()
					|| productapplication.trim().isEmpty()
					|| productcategory.trim().isEmpty()
					|| productpacking.trim().isEmpty()
					|| productKeywords.trim().isEmpty()
					|| productstockpasteprepare.trim().isEmpty()) {
				CommonParams2
						.showAlertBox("You have to enter product details below");
				return "";
			}
			if (listofformpowder.size() <= 0) {
				CommonParams2
						.showAlertBox("You have not entered Form or Powder Details below");
				return "";
			}

			TblCategoryMaster tblcategorymaster = new TblCategoryMaster();
			tblcategorymaster.setCatId(productcategory);
			String[] doGetBreadcrumb = doGetBreadcrumb(productcategory);
			selectedproductmaster=globalselectedproductmaster;

			if (doGetBreadcrumb != null && doGetBreadcrumb.length == 2) {
				selectedproductmaster
						.setProductPrimaryCatId(doGetBreadcrumb[0]);
				selectedproductmaster.setProductBreadcrumb(doGetBreadcrumb[1]);
			} else {
				System.err
						.println("In update The Bread crumb is null for catid="
								+ productcategory);
			}
			selectedproductmaster.setProductApp(productapplication);
			selectedproductmaster.setProductDesc(description);
			selectedproductmaster.setProductName(productName);
			selectedproductmaster.setProductPacking(productpacking);
			selectedproductmaster.setProductTag(0);
			selectedproductmaster.setProductKeywords(productKeywords);
			selectedproductmaster.setProductStockpasteprepare(productstockpasteprepare);
			selectedproductmaster.setTblCategoryMaster(tblcategorymaster);
			UserMasterController dogetSessionValue2 = QuestionsUtil.dogetSessionValue2(new UserMasterController(), "userMaster");
			selectedproductmaster.setProductCompanyname(dogetSessionValue2.getUUserName());
			if(filelargeimage!=null)
			{
				productlargeimage= goSaveFile(filelargeimage);
			}
			else
			{
				System.out.println("Sorry mith largeimage is null");
				if(selectedproductmaster.getProductLargeimageUrl().trim().isEmpty())
				{
					productlargeimage="empty.gif";
				}
				else
				{
					productlargeimage=selectedproductmaster.getProductLargeimageUrl();
				}
			}
			if(filethumbnailimage!=null)
			{
				productthumbnailimage= goSaveFile(filethumbnailimage);
			}
			else
			{
				System.out.println("Sorry mith small image is null");
				if(selectedproductmaster.getProductThumbnailUrl().trim().isEmpty())
				{
					productthumbnailimage="empty.gif";
				}
				else
				{
					productthumbnailimage=selectedproductmaster.getProductThumbnailUrl();
				}
				
			}
			selectedproductmaster.setProductThumbnailUrl(productthumbnailimage);
			selectedproductmaster.setProductLargeimageUrl(productlargeimage);

			for (int i = 0; i < listofformpowder.size(); i++) {
				HashMap hashMap = listofformpowder.get(i);
//				TblPowderMaster obj1 = 
//					(TblPowderMaster) hashMap.get(POWDEROBJECT);
				String propertyvalue = (String) hashMap.get(POWDERNAME);
				TblFormMaster obj2 = (TblFormMaster) hashMap.get(FORMOBJECT);
				TblProductDetails obj3 = (TblProductDetails) hashMap
						.get(PRODUCTDETAILSOBJECT);

				if (obj3 != null) {
					 
					obj3.setTblFormMaster(obj2);
					obj3.setTblProductMaster(selectedproductmaster);
					obj3.setProdetailsPropertyvalue(propertyvalue);

					QuestionsUtil.updateToDB(obj3);
					System.out
							.println("ok done update of tblproductdetails for index="
									+ i);
				} else {
					obj3 = new TblProductDetails(selectedproductmaster,  
							obj2,propertyvalue);
					QuestionsUtil.saveToNewDB(obj3);
					System.out
							.println("ok done saving new copy of tblproductdetails for index="
									+ i);
				}

			}

			QuestionsUtil.updateToDB(selectedproductmaster);
			globalselectedproductmaster=null;
			System.out.println("ok done update for productmaster");
			show=false;
			
			QuestionsUtil.doRedirectToURL("productmaster.jsf");
			
			return "productmaster";

		} catch (Exception ex) {
//			ex.printStackTrace();
			CommonParams2.showMessageOnLog(this.getClass(), ex.getMessage());
		}
		return "";
	}

	public String doSaveProductMaster() {
		try {
			System.out.println("ProductMasterController.doSaveProductMaster()");
			if (productName.trim().isEmpty() || description.trim().isEmpty()
					|| productapplication.trim().isEmpty()
					|| productcategory.trim().isEmpty()
					|| productpacking.trim().isEmpty() 
					|| productKeywords.trim().isEmpty()
					|| productstockpasteprepare.trim().isEmpty()) {
//				CommonParams2.showMessageOnScreen("You have to enter product details below");
				CommonParams2.showAlertBox("You have to enter product details below");
				errormessage="You have to enter product details below";
				return "";
			}
			if (listofformpowder.size() <= 0) {
//				CommonParams2.showMessageOnScreen("You have not entered Form or Powder Details below");
				CommonParams2.showAlertBox("You have not entered Form or Powder Details below");
				return "";
				
			}

			TblProductMaster tblproductmaster = new TblProductMaster();
			TblCategoryMaster tblcategorymaster = new TblCategoryMaster();
			tblcategorymaster.setCatId(productcategory);
			String[] doGetBreadcrumb = doGetBreadcrumb(productcategory);

			if (doGetBreadcrumb != null && doGetBreadcrumb.length == 2) {
				tblproductmaster.setProductPrimaryCatId(doGetBreadcrumb[0]);
				tblproductmaster.setProductBreadcrumb(doGetBreadcrumb[1]);
			} else {
				System.err.println("The Bread crumb is null for catid="
						+ productcategory);
			}

			tblproductmaster.setProductApp(productapplication);
			tblproductmaster.setProductDesc(description);
			tblproductmaster.setProductName(productName);
			tblproductmaster.setProductPacking(productpacking);
			tblproductmaster.setProductTag(0);
			tblproductmaster.setProductKeywords(productKeywords);
			tblproductmaster.setProductStockpasteprepare(productstockpasteprepare);
			tblproductmaster.setTblCategoryMaster(tblcategorymaster);
			UserMasterController dogetSessionValue2 = QuestionsUtil.dogetSessionValue2(new UserMasterController(), "userMaster");
			tblproductmaster.setProductCompanyname(dogetSessionValue2.getUUserName());
//			productlargeimage= goSaveFile(filelargeimage);
//			productthumbnailimage= goSaveFile(filethumbnailimage);
			if(filelargeimage!=null)
			{
				productlargeimage= goSaveFile(filelargeimage);
			}
			else if(productlargeimage==null || productlargeimage.isEmpty())
			{
				productlargeimage="empty.gif";
			}
			if(filethumbnailimage!=null)
			{
				productthumbnailimage= goSaveFile(filethumbnailimage);
			}
			else if(productthumbnailimage==null || productthumbnailimage.isEmpty())
			{
				productthumbnailimage="empty.gif";
			}
			tblproductmaster.setProductThumbnailUrl(productthumbnailimage);
			tblproductmaster.setProductLargeimageUrl(productlargeimage);

			QuestionsUtil.saveToNewDB(tblproductmaster);

			Integer productId = tblproductmaster.getProductId();

			for (int i = 0; i < listofformpowder.size(); i++) {
				HashMap hashMap = listofformpowder.get(i);
				TblPowderMaster obj1 = null;
//					(TblPowderMaster) hashMap.get(POWDEROBJECT);
				TblFormMaster obj2 = (TblFormMaster) hashMap.get(FORMOBJECT);
				String propertyvalue = (String) hashMap.get(POWDERNAME);
				TblProductDetails saveme = new TblProductDetails(
						tblproductmaster,   obj2,propertyvalue);

				QuestionsUtil.saveToNewDB(saveme);
				System.out.println("saved productdetails for row = " + i);

			}
			show=false;
			QuestionsUtil.doRedirectToURL("productmaster.jsf");

			return "productmaster";
		} catch (Exception e) {

//			e.printStackTrace();
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage()); 
		}
		return "";
	}

	/*
	 * Param is the Category Id Return value is [0]=primary cat id ,
	 * [1]=breadcrumb
	 */
	public String[] doGetBreadcrumb(String paramcatid) {
		String catid = paramcatid;// "3ABC13";
		System.out.println("ProductMasterController.doGetBreadcrumb()");
		String[] returnvalue = new String[2];
		Boolean stoploop = false;
		String primarycatkey = "";
		ArrayList<String> breadcrumblist = new ArrayList<String>();
		while (!stoploop) {

			List<TblCategoryMaster> retrieveWherClause = QuestionsUtil
					.retrieveWherClause(new TblCategoryMaster(),
							"TblCategoryMaster", "cat_id='" + catid + "' ");
			if (retrieveWherClause == null || retrieveWherClause.size() <= 0) {
				stoploop = true;
				break;
			}
			breadcrumblist.add(catid);
			TblCategoryMaster tblCategoryMaster = retrieveWherClause.get(0);
			String catSubid = tblCategoryMaster.getCatSubid();
			if (catSubid.equals("0")) {
				primarycatkey = catid;
				stoploop = true;
				break;
			}
			catid = catSubid;

		}
		System.out.println("Breadcrumb before reversesort = "
				+ breadcrumblist.toString());
		Collections.reverse(breadcrumblist);
		System.out.println("Breadcrumb after reversesort = "
				+ breadcrumblist.toString());
		String breadcrumb = breadcrumblist.toString();
		// Matcher.quoteReplacement(breadcrumb)
		breadcrumb = breadcrumb.replace('[', ' ');
		breadcrumb = breadcrumb.replaceAll("]", "");
		breadcrumb = breadcrumb.replaceAll(" ", "");

		breadcrumb = breadcrumb.trim();
		System.out.println("Bread crumb string is=" + breadcrumb + ".");
		System.out.println("Primary Cat id is =" + primarycatkey + ".");
		returnvalue[0] = primarycatkey;
		returnvalue[1] = breadcrumb;

		return returnvalue;
	}

	public void doDeleteTo() {
		try {
			System.out.println("ProductMasterController.doDeleteTo()");
			String tempobjform = (String) selectedformpowder.get(FORMNAME);
			String tempobjpow = (String) selectedformpowder.get(POWDERNAME);

			for (int i = 0; i < listofformpowder.size(); i++) {
				HashMap hashMap = listofformpowder.get(i);
				String str1 = (String) hashMap.get(FORMNAME);
				String str2 = (String) hashMap.get(POWDERNAME);
				if (str1.equals(tempobjform) && str2.equals(tempobjpow)) {

					listofformpowder.remove(i);
					if (toEdit == true) {
						TblProductDetails tbl2 = (TblProductDetails) hashMap
								.get(PRODUCTDETAILSOBJECT);
						Integer prodetailsId = tbl2.getProdetailsId();
						int deleteFromDB = QuestionsUtil.deleteFromDB(
								"TblProductDetails", "prodetails_id="
										+ prodetailsId);
						System.out
								.println("Deleted from tblproductdetails no of rows  "
										+ deleteFromDB);
					}
					System.out.println("Deleted the map from list at index=" + i);
					break;
				}
			}
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}

	}

	public void doAddTo() {
		try {
			System.out.println("ProductMasterController.doAddTo()");
			if (tempform == null || temppropertyvalue == null || tempform == -1) {
				CommonParams2
						.showMessageOnScreen("You have not entered any Form ");
				
				System.err.println("You have not entered any Form ");
				CommonParams2.showAlertBox("You have not entered any Form Value");
				return;
			}
			if(temppropertyvalue.isEmpty()==true)
			{
				System.err.println("You have not entered any Form");
				CommonParams2.showAlertBox("You have not entered any Property Value");
				return;
			}

			System.out.println("Started tempform=" + tempform + " temppropertyvalue="
					+ temppropertyvalue);
			ArrayList dogetTablefromList = dogetTablefromList(tempform);//, temppowder);
			if (dogetTablefromList == null) {
				CommonParams2
						.showMessageOnScreen("Some mistake ! Select form  property");
				System.err.println("Some mistake ! Select form and powder");
				return;
			}

			TblFormMaster obj1 = (TblFormMaster) dogetTablefromList.get(0);
//		TblPowderMaster obj2 = (TblPowderMaster) dogetTablefromList.get(1);
			System.out.println("tbl form name=" + obj1.getFormName()
					+ " tbl property value=" + temppropertyvalue);

			HashMap datahere2 = new HashMap();

			datahere2.put(FORMOBJECT, obj1);
//		datahere2.put(POWDEROBJECT, obj2);
			datahere2.put(FORMNAME, obj1.getFormName());
			datahere2.put(POWDERNAME, temppropertyvalue);

			listofformpowder.add(datahere2);
			temppropertyvalue="";
			tempform=-1;

			System.out.println("Content of row to be added is "
					+ datahere2.toString());
		} catch (Exception e) {
			CommonParams2.showMessageOnLog(this.getClass(), e.getMessage());
			CommonParams2.showAlertBox("Something gone wrong , please refresh page");
		}

	}

	public ArrayList dogetTablefromList(Integer form1 ) {
		System.out.println("ProductMasterController.dogetTablefromList()");
		ArrayList a = new ArrayList();

		for (int i = 0; i < oldform.size(); i++) {
			TblFormMaster tblFormMaster = oldform.get(i);
			if (tblFormMaster.getFormId().equals(form1)) {
				System.out.println("got form master");
				a.add(tblFormMaster);
				break;
			}
		}

//		for (int i = 0; i < oldpowder.size(); i++) {
//			TblPowderMaster tblPowderMaster = oldpowder.get(i);
//			if (tblPowderMaster.getPowId().equals(powder1)) {
//				System.out.println("got powder master");
//				a.add(tblPowderMaster);
//				break;
//			}
//		}
		if (a.size() != 1) {
			System.err
					.println("Big problem setting arraylist to null! size was"
							+ a.size());
			return null;
		}
		return a;
	}

	

	/**
	 * @param file
	 */
	private String goSaveFile(UploadedFile file) {
		System.out.println("ProductMasterController.goSaveFile()");
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext();
		String realPath = ctx.getRealPath("/");
		HttpServletRequest request= (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String pathseparator = File.separator;
		realPath+=pathseparator;
		realPath+=".."+pathseparator;
		realPath+="product_images"+pathseparator;
		
		
		//www.abc.com/abc/images/
		System.out.println("Context path is ="+realPath);
//		String fileName = realPath+"\\product_images\\"
		String fileName=realPath+ (file.getFileName().toLowerCase());
		System.out.println("Mith filename is "+fileName);
		String returnfilename=file.getFileName().toLowerCase();
		
		try {
			InputStream inputstream = file.getInputstream();
			ImageInputStream createImageInputStream = ImageIO
					.createImageInputStream(inputstream);
			BufferedImage read = ImageIO.read(createImageInputStream);
//			File f = new File("/product_images");
//			f.createNewFile();
			System.out.println("Content typee=" + file.getContentType());
			if (fileName.contains("jpg") || fileName.contains("jpeg")) {

				boolean write = ImageIO.write(read, "jpg", new File(fileName));
				System.out.println(" jpg " + write);

			} else if (fileName.contains("gif")) {

				boolean write = ImageIO.write(read, "gif", new File(fileName));
				System.out.println(" gif " + write);

			} else if (fileName.contains("png")) {
				boolean write = ImageIO.write(read, "png", new File(fileName));
				System.out.println(" png " + write);

			}

		} catch (IOException e) {
			System.err.println("exception here" + fileName + " error="
					+ e.getMessage());
			e.printStackTrace();
		}
		return returnfilename;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String categoryName) {
		this.productName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<TblCategoryMaster> getListofoldcategorymaster() {
		return listofoldcategorymaster;
	}

	public void setListofoldcategorymaster(
			ArrayList<TblCategoryMaster> listofoldcategorymaster) {
		this.listofoldcategorymaster = listofoldcategorymaster;
	}

	public String getProductapplication() {
		return productapplication;
	}

	public void setProductapplication(String productapplication) {
		this.productapplication = productapplication;
	}

	public String getProductcategory() {
		return productcategory;
	}

	public void setProductcategory(String productcategory) {
		this.productcategory = productcategory;
	}

	public String getProductpacking() {
		return productpacking;
	}

	public void setProductpacking(String productpacking) {
		this.productpacking = productpacking;
	}

	public ArrayList<TblFormMaster> getOldform() {
		return oldform;
	}

	public void setOldform(ArrayList<TblFormMaster> oldform) {
		this.oldform = oldform;
	}

	public ArrayList<TblPowderMaster> getOldpowder() {
		return oldpowder;
	}

	public void setOldpowder(ArrayList<TblPowderMaster> oldpowder) {
		this.oldpowder = oldpowder;
	}

	public Integer getTempform() {
		return tempform;
	}

	public void setTempform(Integer tempform) {
		this.tempform = tempform;
	}

	public Integer getTemppowder() {
		return temppowder;
	}

	public void setTemppowder(Integer temppowder) {
		this.temppowder = temppowder;
	}

	public ArrayList<HashMap> getListofformpowder() {
		return listofformpowder;
	}

	public void setListofformpowder(ArrayList<HashMap> listofformpowder) {
		this.listofformpowder = listofformpowder;
	}

	public HashMap getSelectedformpowder() {
		return selectedformpowder;
	}

	public void setSelectedformpowder(HashMap selectedformpowder) {
		this.selectedformpowder = selectedformpowder;
	}

	public Boolean getToEdit() {
		return toEdit;
	}

	public void setToEdit(Boolean toEdit) {
		this.toEdit = toEdit;
	}

	public ArrayList<TblProductMaster> getListofoldproductmaster() {
		return listofoldproductmaster;
	}

	public void setListofoldproductmaster(
			ArrayList<TblProductMaster> listofoldproductmaster) {
		this.listofoldproductmaster = listofoldproductmaster;
	}

	public TblProductMaster getSelectedproductmaster() {
		return selectedproductmaster;
	}

	public void setSelectedproductmaster(TblProductMaster selectedproductmaster) {
		this.selectedproductmaster = selectedproductmaster;
	}

	public String getProductthumbnailimage() {
		return productthumbnailimage;
	}

	public void setProductthumbnailimage(String thumbnailimage) {
		this.productthumbnailimage = thumbnailimage;
	}

	public String getProductlargeimage() {
		return productlargeimage;
	}

	public void setProductlargeimage(String largeimage) {
		this.productlargeimage = largeimage;
	}

	public UploadedFile getFilethumbnailimage() {
		return filethumbnailimage;
	}

	public void setFilethumbnailimage(UploadedFile filethumbnailimage) {
		this.filethumbnailimage = filethumbnailimage;
	}

	public UploadedFile getFilelargeimage() {
		return filelargeimage;
	}

	public void setFilelargeimage(UploadedFile filelargeimage) {
		this.filelargeimage = filelargeimage;
	}

	public String getTemppropertyvalue() {
		return temppropertyvalue;
	}

	public void setTemppropertyvalue(String temppropertyvalue) {
		this.temppropertyvalue = temppropertyvalue;
	}

	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

	public Boolean getShowedit() {
		return showedit;
	}

	public void setShowedit(Boolean showedit) {
		this.showedit = showedit;
	}

	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	public String getProductKeywords() {
		return productKeywords;
	}

	public void setProductKeywords(String productKywords) {
		this.productKeywords = productKywords;
	}

	public String getProductstockpasteprepare() {
		return productstockpasteprepare;
	}

	public void setProductstockpasteprepare(String productstockpasteprepare) {
		this.productstockpasteprepare = productstockpasteprepare;
	}
}
