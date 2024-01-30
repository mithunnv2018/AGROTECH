package com.indraagrotech.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.indraagrotech.commonbeans.TblUserMaster;
import com.indraagrotech.services.UserMasterController;
 

//import com.mciter.services.Forms_Center_StudentController;
//import com.mciter.services.QuestionsUtil;
//import com.mciter.services.StudentExamPaperController;
//import com.mciter.services.TypingStudentExamPaperController;

public class CommonParams2 {

	// private String[] noofoptions={"1","2","3","4","5","6","7","8","9","10"};
	private Integer[] noofoptions = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
//	private String[] showABCoptions = { "A", "B", "C", "D", "E", "F", "G", "H",
//			"I", "J" };

//	private String[] course = { "Java", "C#", "Ruby" };

	private static TimeZone indiantimezone;
	private static Calendar indiancalendar;
	private String browsername="";
	private String browserversion="";
 
	public CommonParams2()
	{
		getBrowserInfo();
	}
	
	/**
	 * If user is not logged then redirect to loginpage
	 * 
	 * @param sessionname
	 * @param urltoredirect
	 */
	public void checkCredentialsForLoggedOut(String sessionname,
			String urltoredirect) {
		ExternalContext ctx = FacesContext.getCurrentInstance()
				.getExternalContext();
		HttpSession session = (HttpSession) ctx.getSession(false);

		if (session.getAttribute(sessionname) != null) {
			HttpServletResponse resp = (HttpServletResponse) ctx.getResponse();
			 
			 
			if (sessionname.equals("userMaster")) {
				UserMasterController user2 = (UserMasterController) session
						.getAttribute(sessionname);
				
				if (user2.getUUserName() != null
						&& user2.getUUserName().isEmpty() == false) {
					CommonParams2.showMessageOnLog(getClass(),
							"checkcredentials user2 is not null");
					return;
				}
			}
			CommonParams2.showMessageOnLog(getClass(),
					"checkCredentials working before redirect");
			// resp.sendRedirect(urltoredirect);
			try {
				ctx.redirect(urltoredirect);
			} catch (IOException e) {
				System.out
						.println("checkCredentialsForLoggedOut mehod cannot redirect");
				e.printStackTrace();
			}

			CommonParams2.showMessageOnLog(getClass(),
					"checkCredentials working after redirect");

		} else {
			CommonParams2.showMessageOnLog(getClass(),
					"checkCredentials working before redirect");
			// resp.sendRedirect(urltoredirect);
			try {
				ctx.redirect(urltoredirect);
			} catch (IOException e) {
				System.out
						.println("checkCredentialsForLoggedOut mehod cannot redirect");
				e.printStackTrace();
			}

		}

	}

	/**
	 * If user is already logged in direct to the required page
	 * 
	 * @param sessionname
	 * @param urltoredirect
	 */
	public void checkCredentialsForLoggedIn(String sessionname,
			String urltoredirect) {
		ExternalContext ctx = FacesContext.getCurrentInstance()
				.getExternalContext();

		HttpSession session = (HttpSession) ctx.getSession(false);
		Map<String, Object> sessionMap = ctx.getSessionMap();
		for (String va2 : sessionMap.keySet()) {

			System.out.println(" Key is :" + va2 + " Value is :"
					+ sessionMap.get(va2));
		}
		if (session.getAttribute(sessionname) != null) {
			HttpServletResponse resp = (HttpServletResponse) ctx.getResponse();
			 
			 
			 
			if (sessionname.equals("userMaster")) {
				UserMasterController user2 = (UserMasterController) session
						.getAttribute(sessionname);
				if (user2.getUUserName() == null
						|| user2.getUUserName().isEmpty()) {
					CommonParams2.showMessageOnLog(getClass(),
							"checkCredentials usermaster is null");
					return;
				}
			}
			try {
				CommonParams2.showMessageOnLog(getClass(),
						"checkCredentials working before redirect");
				// resp.sendRedirect(urltoredirect);
				ctx.redirect(urltoredirect);

				CommonParams2.showMessageOnLog(getClass(),
						"checkCredentials working after redirect");

			} catch (IOException e) {
				System.out
						.println("checkCredentialsForLoggedIn mehod cannot redirect");
				e.printStackTrace();
			}
		}

	}
	 
 	public static Date getIndianDate() 
	{
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
	    df.setTimeZone(TimeZone.getTimeZone("GMT-8:00"));//GMT+5:30"));
	    String format = df.format(new Date());
	    System.out.println("Format="+format);
	    Date parse=null;
		try {
			parse = df.parse(format);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("date is "+parse.toString());
	    return parse;
//	    Calendar cal=df.getCalendar();
//	    cal.setTime(parse);
//	    return cal;
	    
	}
	public static Calendar getIndiancalendar() {
		java.util.TimeZone tz = java.util.TimeZone.getTimeZone("GMT+5:30");
		java.util.Calendar c = java.util.Calendar.getInstance(tz);
		indiancalendar = c;
		return indiancalendar;
	}
	public boolean isValidBorwser(String redirecturl)
	{
		ExternalContext extcontext = FacesContext.getCurrentInstance().getExternalContext();
		String [] browserinfo=(String[]) getBrowserInfo().toArray(new String[0]);
		
		boolean retvalue=true;
		if(browserinfo==null)
		{
			return true;
		}
		try {
			String browsername=browserinfo[0];
			Double version=Double.parseDouble(browserinfo[1]);
			
			if(browsername.indexOf("Chrome")!=-1 && version>=20.0 )
			{
				retvalue=true;
			}
			else if(browsername.indexOf("Firefox")!=-1 && version>=7.0 )
			{
				retvalue=true;
			}
			else
			{
				retvalue=false;
			}
			if(retvalue==false)
			{
				extcontext.redirect(redirecturl);
				
			}
			
		} catch (Exception e) {
			System.out.println("isValidBrowser method error here: "+e.getMessage());
			e.printStackTrace();
		}
		
		return retvalue;
	}

	public ArrayList getBrowserInfo() {
		ExternalContext extcontext = FacesContext.getCurrentInstance().getExternalContext();
		String Information = extcontext.getRequestHeaderMap().get("User-Agent");
		ArrayList retvalue=new ArrayList();
		
//		String browsername = "";
//		String browserversion = "";
		String browser = Information;
		if (browser.contains("MSIE")) {
			String subsString = browser.substring(browser.indexOf("MSIE"));
			String Info[] = (subsString.split(";")[0]).split(" ");
			browsername = Info[0];
			browserversion = Info[1];
		} else if (browser.contains("Firefox")) {

			String subsString = browser.substring(browser.indexOf("Firefox"));
			String Info[] = (subsString.split(" ")[0]).split("/");
			browsername = Info[0];
			browserversion = Info[1];
		} else if (browser.contains("Chrome")) {

			String subsString = browser.substring(browser.indexOf("Chrome"));
			String Info[] = (subsString.split(" ")[0]).split("/");
			browsername = Info[0];
			browserversion = Info[1];
		} else if (browser.contains("Opera")) {

			String subsString = browser.substring(browser.indexOf("Opera"));
			String Info[] = (subsString.split(" ")[0]).split("/");
			browsername = Info[0];
			browserversion = Info[1];
		} else if (browser.contains("Safari")) {

			String subsString = browser.substring(browser.indexOf("Safari"));
			String Info[] = (subsString.split(" ")[0]).split("/");
			browsername = Info[0];
			browserversion = Info[1];
		}
		if(browserversion.indexOf(".")!=-1)
		{
			StringTokenizer token=new StringTokenizer(browserversion,".");
			browserversion=token.nextToken();
			
		}
		retvalue.add(browsername);
		retvalue.add(browserversion);
		
		return retvalue;
		//new String[] { browsername, browserversion };
	}

	public TimeZone getIndiantimezone() {
		java.util.TimeZone tz = java.util.TimeZone.getTimeZone("GMT+5:30");
		java.util.Calendar c = java.util.Calendar.getInstance(tz);
		indiantimezone = tz;
		return indiantimezone;
	}

	public void setIndiantimezone(TimeZone indiantimezone) {
		this.indiantimezone = indiantimezone;
	}

 

 
	public static void showMessageOnScreen(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(message, message));
	}
	
	public static void showAlertBox(String message)
	{
		System.out.println("CommonParams2.showAlertBox()");
		RequestContext currentInstance = RequestContext.getCurrentInstance();
		String command="alert('"+message+"')";
		if(currentInstance!=null)
		{
			System.out.println("In Requestcontext method");
			currentInstance.execute(command);
			
		}
		else
		{
			System.err.println("Sorry Mith RequestContext is null");
		}
		System.out.println("Shown Alert from server = "+command);
	}
	/* 
	 * Java Script to refresh page 
	 */
	public static void refreshPage()
	{
		System.out.println("CommonParams2.refreshPage()");
		RequestContext currentInstance = RequestContext.getCurrentInstance();
		
		String command="document.location.reload(true)";
		if(currentInstance!=null)
		{
			System.out.println("In Requestcontext method");
			currentInstance.execute(command);
			
		}
		else
		{
			System.err.println("Sorry Mith RequestContext is null");
		}
		System.out.println("done calling refreshpage");
	}

	public static void showMessageOnLog(Class a, String message) {
		Logger l = Logger.getLogger(a.getName());
		l.setLevel(Level.INFO);
		// l.warning(message);
		l.info(message);
	}
	public String getBrowsername() {
		
		return browsername;
	}
	public void setBrowsername(String browsername) {
		this.browsername = browsername;
	}
	public String getBrowserversion() {
		return browserversion;
	}
	public void setBrowserversion(String browserversion) {
		this.browserversion = browserversion;
	}
	 
}
