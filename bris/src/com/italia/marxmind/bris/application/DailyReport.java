package com.italia.marxmind.bris.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import com.italia.marxmind.bris.bean.ReportGeneratorBean;
import com.italia.marxmind.bris.controller.BusinessPermit;
import com.italia.marxmind.bris.controller.CaseFilling;
import com.italia.marxmind.bris.controller.Cases;
import com.italia.marxmind.bris.controller.UserDtls;
import com.italia.marxmind.bris.enm.Bris;
import com.italia.marxmind.bris.enm.CaseStatus;
import com.italia.marxmind.bris.enm.DateFormat;
import com.italia.marxmind.bris.enm.EmailType;
import com.italia.marxmind.bris.reader.ReadConfig;
import com.italia.marxmind.bris.reports.Reports;
import com.italia.marxmind.bris.utils.DateUtils;

public class DailyReport {
	
	public static final String CONFIG_FILE_NAME="dashboard-data.bris";
	
	public static void startCollectReport() {
		String log = "";
		if(!isUserFileExist()) {
			log += "File User is not existing....\n";
			log +="Start saving new report user....\n";
			saveUsers();
			log +="End saving new report user....\n";
		}
		
		if(isDateFileExist()) {
			
			if(readDate().equalsIgnoreCase(DateUtils.getCurrentDateYYYYMMDD())) {
				System.out.println("No report to be generate....");
			}else {
			
				log +="Start fetching data for report generation....\n";
				collectReport();
				log +="Done fetching data for report generation....\n";
				log +="Start saving new report date generation....\n";
				saveDate();
				log +="End saving new report date generation....\n";
				saveLog(log);
				
				createGraphData();
			}
		}else {
			log +="File date is not existing....\n";
			log +="Start saving new report date generation....\n";
			saveDate();
			log +="End saving new report date generation....\n";
			
			if(readDate().equalsIgnoreCase(DateUtils.getCurrentDateYYYYMMDD())) {
				System.out.println("No report to be generate....");;
			}else {
			
				log +="Start fetching data for report generation....\n";
				collectReport();
				log +="Done fetching data for report generation....\n";
				log +="Start saving new report date generation....\n";
				saveDate();
				log +="End saving new report date generation....\n";
				saveLog(log);
					
			}
			createGraphData();
		}
		
		if(!isUserSummonFileExist()) {
			log += "File User is not existing....\n";
			log +="Start saving new summon user....\n";
			saveUsersSummon();
			log +="End saving new summon user....\n";
		}
		
		if(isSummonFileExist()) {
			if(readSummonDate().equalsIgnoreCase(DateUtils.getCurrentDateYYYYMMDD())) {
				System.out.println("No report to be generate....");
			}else {
				
				log +="Start fetching data for summon generation....\n";
				collectSummonReport();
				log +="Done fetching data for summon generation....\n";
				
				log +="Start saving new date report summon....\n";
				saveSummonDate();
				log +="End saving new date report summon....\n";
			}
		}else {
			
			log +="Start saving fresh date report summon....\n";
			saveSummonDate();
			log +="End saving fresh date report summon....\n";
			
			log +="Start fetching data for summon generation....\n";
			collectSummonReport();
			log +="Done fetching data for summon generation....\n";
			
			log +="Start saving new date report summon....\n";
			saveSummonDate();
			log +="End saving new date report summon....\n";
		}
	}
	
	public static void main(String[] args) {
		
		
		
		String log = "";
		if(!isUserFileExist()) {
			log += "File User is not existing....\n";
			log +="Start saving new report user....\n";
			saveUsers();
			log +="End saving new report user....\n";
		}
		
		if(isDateFileExist()) {
			
			if(readDate().equalsIgnoreCase(DateUtils.getCurrentDateYYYYMMDD())) {
				System.out.println("No report to be generate....");
			}else {
			
				log +="Start fetching data for report generation....\n";
				collectReport();
				log +="Done fetching data for report generation....\n";
				log +="Start saving new report date generation....\n";
				saveDate();
				log +="End saving new report date generation....\n";
				saveLog(log);
				
				createGraphData();
			}
		}else {
			log +="File date is not existing....\n";
			log +="Start saving new report date generation....\n";
			saveDate();
			log +="End saving new report date generation....\n";
			
			if(readDate().equalsIgnoreCase(DateUtils.getCurrentDateYYYYMMDD())) {
				System.out.println("No report to be generate....");;
			}else {
			
				log +="Start fetching data for report generation....\n";
				collectReport();
				log +="Done fetching data for report generation....\n";
				log +="Start saving new report date generation....\n";
				saveDate();
				log +="End saving new report date generation....\n";
				saveLog(log);
					
			}
			createGraphData();
		}
		
		if(!isUserSummonFileExist()) {
			log += "File User is not existing....\n";
			log +="Start saving new summon user....\n";
			saveUsersSummon();
			log +="End saving new summon user....\n";
		}
		
		if(isSummonFileExist()) {
			if(readSummonDate().equalsIgnoreCase(DateUtils.getCurrentDateYYYYMMDD())) {
				System.out.println("No report to be generate....");
			}else {
				
				log +="Start fetching data for summon generation....\n";
				collectSummonReport();
				log +="Done fetching data for summon generation....\n";
				
				log +="Start saving new date report summon....\n";
				saveSummonDate();
				log +="End saving new date report summon....\n";
			}
		}else {
			
			log +="Start saving fresh date report summon....\n";
			saveSummonDate();
			log +="End saving fresh date report summon....\n";
			
			log +="Start fetching data for summon generation....\n";
			collectSummonReport();
			log +="Done fetching data for summon generation....\n";
			
			log +="Start saving new date report summon....\n";
			saveSummonDate();
			log +="End saving new date report summon....\n";
		}
		
	}
	
	public static void saveLog(String log) {
		
		String emailPath = Bris.PRIMARY_DRIVE.getName() + 
		Bris.SEPERATOR.getName() + 
		Bris.APP_FOLDER.getName() + 
		Bris.SEPERATOR.getName() + "log" + Bris.SEPERATOR.getName();
		
		
		File file = new File(emailPath);
		
		if(!file.isDirectory()) {
			file.mkdir();
		}
		
		try {
		
		String msg = "Log File\n";
		msg += "Date Created: " + DateUtils.getCurrentDateMMMMDDYYYY();
		msg += "\nAuthor: MARXMIND\n";
		msg += "-------------------\n";
		msg += log;
		File email = new File(emailPath + "bris-runner-log-"+DateUtils.getCurrentDateMMDDYYYYTIMEPlain()+".log");
		PrintWriter pw = new PrintWriter(new FileWriter(email));
		pw.println(msg);
		pw.flush();
		pw.close();
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static boolean isDateFileExist() {
		String BARANGAY = ReadConfig.value(Bris.BARANGAY);
		String MUNICIPALITY = ReadConfig.value(Bris.MUNICIPALITY);
		String pathFile = Bris.PRIMARY_DRIVE.getName() + 
		Bris.SEPERATOR.getName() + 
		Bris.APP_FOLDER.getName() + 
		Bris.SEPERATOR.getName() + "bris-runner" + Bris.SEPERATOR.getName();
		
		pathFile += BARANGAY + "-" + MUNICIPALITY;
		pathFile += Bris.SEPERATOR.getName();
		pathFile += "reportDate.bris";
		
		File file = new File(pathFile);
		if(file.exists()) {
			System.out.println("File exist... " + pathFile);
			return true;
		}
		
		return false;
	}
	
	public static String readDate() {
		String BARANGAY = ReadConfig.value(Bris.BARANGAY);
		String MUNICIPALITY = ReadConfig.value(Bris.MUNICIPALITY);
		String emailPath = Bris.PRIMARY_DRIVE.getName() + 
		Bris.SEPERATOR.getName() + 
		Bris.APP_FOLDER.getName() + 
		Bris.SEPERATOR.getName() + "bris-runner" + Bris.SEPERATOR.getName();
		
		emailPath += BARANGAY + "-" + MUNICIPALITY;
		emailPath += Bris.SEPERATOR.getName();
		emailPath += "reportDate.bris";
		
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream(emailPath));
			return prop.getProperty("Date");
		}catch(Exception e) {}
		
		return DateUtils.getCurrentDateYYYYMMDD();
	}
	
	public static void saveDate() {
		String BARANGAY = ReadConfig.value(Bris.BARANGAY);
		String MUNICIPALITY = ReadConfig.value(Bris.MUNICIPALITY);
		String emailPath = Bris.PRIMARY_DRIVE.getName() + 
		Bris.SEPERATOR.getName() + 
		Bris.APP_FOLDER.getName() + 
		Bris.SEPERATOR.getName() + "bris-runner" + Bris.SEPERATOR.getName();
		
		emailPath += BARANGAY + "-" + MUNICIPALITY;
		emailPath += Bris.SEPERATOR.getName();
		
		File file = new File(emailPath);
		
		if(!file.isDirectory()) {
			file.mkdir();
		}
		
		try {
		
		String msg = "Do not delete this file\n";
		msg += "Date Created: " + DateUtils.getCurrentDateMMMMDDYYYY();
		msg += "\nAuthor: MARXMIND\n";
		msg += "-------------------\n";
		msg += "\n";
		msg += "Date="+DateUtils.getCurrentDateYYYYMMDD();
		File email = new File(emailPath + "reportDate.bris");
		PrintWriter pw = new PrintWriter(new FileWriter(email));
		pw.println(msg);
		pw.flush();
		pw.close();
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isUserFileExist() {
		String BARANGAY = ReadConfig.value(Bris.BARANGAY);
		String MUNICIPALITY = ReadConfig.value(Bris.MUNICIPALITY);
		String pathFile = Bris.PRIMARY_DRIVE.getName() + 
		Bris.SEPERATOR.getName() + 
		Bris.APP_FOLDER.getName() + 
		Bris.SEPERATOR.getName() + "bris-runner" + Bris.SEPERATOR.getName();
		
		pathFile += BARANGAY + "-" + MUNICIPALITY;
		pathFile += Bris.SEPERATOR.getName();
		pathFile += "reportUsers.bris";
		
		File file = new File(pathFile);
		if(file.exists()) {
			System.out.println("File exist... " + pathFile);
			return true;
		}
		
		return false;
	}
	
	public static String readUsers() {
		String BARANGAY = ReadConfig.value(Bris.BARANGAY);
		String MUNICIPALITY = ReadConfig.value(Bris.MUNICIPALITY);
		String emailPath = Bris.PRIMARY_DRIVE.getName() + 
		Bris.SEPERATOR.getName() + 
		Bris.APP_FOLDER.getName() + 
		Bris.SEPERATOR.getName() + "bris-runner" + Bris.SEPERATOR.getName();
		
		emailPath += BARANGAY + "-" + MUNICIPALITY;
		emailPath += Bris.SEPERATOR.getName();
		emailPath += "reportUsers.bris";
		
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream(emailPath));
			return prop.getProperty("Users");
		}catch(Exception e) {}
		
		return "5";
	}
	
	public static void saveUsers() {
		String BARANGAY = ReadConfig.value(Bris.BARANGAY);
		String MUNICIPALITY = ReadConfig.value(Bris.MUNICIPALITY);
		String emailPath = Bris.PRIMARY_DRIVE.getName() + 
		Bris.SEPERATOR.getName() + 
		Bris.APP_FOLDER.getName() + 
		Bris.SEPERATOR.getName() + "bris-runner" + Bris.SEPERATOR.getName();
		
		emailPath += BARANGAY + "-" + MUNICIPALITY;
		emailPath += Bris.SEPERATOR.getName();
		
		File file = new File(emailPath);
		
		if(!file.isDirectory()) {
			file.mkdir();
		}
		
		try {
		
		String msg = "Do not delete this file\n";
		msg += "Date Created: " + DateUtils.getCurrentDateMMMMDDYYYY();
		msg += "\nAuthor: MARXMIND\n";
		msg += "-------------------\n";
		msg += "\n";
		String val = "";
		for(int i=1; i<=5; i++) {
			if(i>1) {
				val += ","+i;
			}else {
				val += i;
			}
		}
		msg += "Users=" + val;
		File email = new File(emailPath + "reportUsers.bris");
		PrintWriter pw = new PrintWriter(new FileWriter(email));
		pw.println(msg);
		pw.flush();
		pw.close();
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void collectReport() {
		
		String htmlHead = "";
		
		htmlHead = "<html><head><title>BRIS Reports</title></head><body><form>";
		
		htmlHead = "<p><center><h2><strong>Generated Report for "+ readDate() +"</strong></h2></center></p>";
		htmlHead += "<br/>";
		//msg += "<p><h3><strong>Please see below details for the daily report</strong></h3></p>";
		htmlHead += "<br/>";
		
		//clearance
		ReportGeneratorBean rpt = new ReportGeneratorBean();
		
		rpt.setDateFrom(DateUtils.convertDateString(readDate(), DateFormat.YYYY_MM_DD()));
		rpt.setDateTo(DateUtils.convertDateString(readDate(), DateFormat.YYYY_MM_DD()));
		rpt.setIncludeClearance(true);
		rpt.setDetailedClerance(true);
		rpt.loadReport();
		
		String htmlDoc = "<p><h2>Documents</h2></p>";
		
		htmlDoc += "<table>";
		System.out.println("Documents: " + rpt.getRpts().size());
		boolean docOk = rpt.getRpts().size()>=5? true : false;
		for(Reports r : rpt.getRpts()) {
			htmlDoc += "<tr>";
			htmlDoc += "<td>"+r.getF1()+"</td><td>" + r.getF2() + "</td><td>" + r.getF3() + "</td><td>" + r.getF4()+"</td>";
			htmlDoc += "</tr>";
			
		}
		htmlDoc += "</table>";
		
		htmlDoc += "<br/><br/><hr/>"; 
		htmlDoc += "<br/><br/>";
		
		//IDs
		rpt = new ReportGeneratorBean();
			
		rpt.setDateFrom(DateUtils.convertDateString(readDate(), DateFormat.YYYY_MM_DD()));
		rpt.setDateTo(DateUtils.convertDateString(readDate(), DateFormat.YYYY_MM_DD()));
		rpt.setIncludeIds(true);
		rpt.setDetailedIds(true);
		rpt.setIncludeIdHolderName(true);
		rpt.loadReport();
		
		String htmlId  = "<p><h2>Identification No</h2></p>"; 
		htmlId += "<table>";
		System.out.println("Ids: " + rpt.getRpts().size());
		boolean idOk = rpt.getRpts().size()>=5? true : false;
		for(Reports r : rpt.getRpts()) {
			htmlId += "<tr>";
			htmlId += "<td>"+r.getF1()+"</td><td>" + r.getF2() + "</td><td>" + r.getF3() + "</td><td>" + r.getF4()+"</td>";
			htmlId += "</tr>";
		}
		htmlId += "</table>";
		htmlId += "<br/><br/><hr/>"; 
		htmlId += "<br/><br/>"; 
		
		
		//cases
		rpt = new ReportGeneratorBean();
		
		rpt.setDateFrom(DateUtils.convertDateString(readDate(), DateFormat.YYYY_MM_DD()));
		rpt.setDateTo(DateUtils.convertDateString(readDate(), DateFormat.YYYY_MM_DD()));
		rpt.setDetailedCases(true);
		rpt.setIncludeCases(true);
		rpt.loadReport();
		String htmlCase = "<p><h2>Log Cases</h2></p>"; 
		htmlCase += "<table>";
		System.out.println("Cases: " + rpt.getRpts().size());
		boolean caseOk = rpt.getRpts().size()>=5? true : false;
		for(Reports r : rpt.getRpts()) {
			htmlCase += "<tr>";
			htmlCase += "<td>"+r.getF1()+"</td><td>" + r.getF2() + "</td><td>" + r.getF3() + "</td><td>" + r.getF4()+"</td>";
			htmlCase += "</tr>";
		}
		htmlCase += "</table>";
		htmlCase += "<br/><br/>";
		
		String htmlFooter ="</form></body></html>";
		int counting = 0;
		String html = htmlHead;
				if(docOk) {
					html += htmlDoc;
					counting=1;
				}
				if(idOk) {
					html += htmlId;
					counting=1;
				}
				if(caseOk) {
					html += htmlCase;
					counting=1;
				}
				html += htmlFooter;
		
		if(counting==1) {		
				
		int cnt=1;
		String toMailUser = "";
		boolean isCheckNote=false;
		for(String id : readUsers().split(",")) {
			
					String sql = "SELECT * FROM userdtls WHERE isactive=1 AND jobtitleid=?";
					String[] params = new String[1];
					params[0] = id;
					List<UserDtls> toUsers = UserDtls.retrieve(sql, params);
					
					if(toUsers.size()>0) {
						isCheckNote=true;
						if(toUsers.size()>1) {
							String toM = "";
							int cntM=1;
							for(UserDtls user : toUsers) {
								if(cntM>1) {
									toM += ":"+user.getUserdtlsid()+"";
								}else {
									toM = user.getUserdtlsid()+"";
								}
								cntM++;	
							}
							if(cnt>1) {
								toMailUser += ":"+toM;
							}else {
								toMailUser = toM;
							}
							
						}else {
							if(cnt>1) {
								toMailUser += ":"+toUsers.get(0).getUserdtlsid()+"";
							}else {
								toMailUser = toUsers.get(0).getUserdtlsid()+"";
							}
						}
					}	
				
			
			cnt++;
		}
		
		System.out.println("send to " + toMailUser);
		
		isCheckNote=true;
		if(isCheckNote && !toMailUser.isEmpty()) {
			
			boolean isMore = false;
			try {
				String[] em = toMailUser.split(":");
				isMore=true;
		    }catch(Exception ex) {}
			if(isMore) {
				for(String sendTo : toMailUser.split(":")) {
					Email e = new Email();
					e.setSendDate(DateUtils.getCurrentDateYYYYMMDD());
					e.setTitle("Report for ["+ readDate() +"] Transactions");
					
					e.setType(EmailType.INBOX.getId());
					e.setIsOpen(0);
					e.setIsDeleted(0);
					e.setIsActive(1);
					
					e.setToEmail(toMailUser);
					e.setPersonCopy(Long.valueOf(sendTo));
					e.setFromEmail("0");
					
					
					String fileName = DateUtils.getCurrentDateMMDDYYYYTIMEPlain() + "-" + sendTo;
					Email.saveAttachment(fileName, html, "html");
					
					String msg = "<p><strong>Hi</strong></p>";
					msg += "<br/>";
					msg += "<p>Please see attached file for the report transactions</p>";
					msg += "<br/>";
					msg += "<p><a href=\"/bris/faces/attachment/"+ fileName +".html\" target=\"_blank\">Click here to see the attachment</p>";
					Email.emailSavePath(msg, fileName);
					//Email.transferAttachment(fileName + ".html");
					e.setContendId(fileName);
					e.save();
					
				}
			}else {
				Email e = new Email();
				e.setSendDate(DateUtils.getCurrentDateYYYYMMDD());
				e.setTitle("Report for ["+ readDate() +"] Transactions");
				
				e.setType(EmailType.INBOX.getId());
				e.setIsOpen(0);
				e.setIsDeleted(0);
				e.setIsActive(1);
				
				e.setToEmail(toMailUser);
				e.setPersonCopy(Long.valueOf(toMailUser));
				e.setFromEmail("0");
				
				
				String fileName = DateUtils.getCurrentDateMMDDYYYYTIMEPlain() + "-" + toMailUser;
				Email.saveAttachment(fileName, html, "html");
				
				String msg = "<p><strong>Hi</strong></p>";
				msg += "<br/>";
				msg += "<p>Please see attached file for the report transactions</p>";
				msg += "<br/>";
				msg += "<p><a href=\"/bris/faces/attachment/"+ fileName +".html\" target=\"_blank\">Click here to see the attachment</p>";
				Email.emailSavePath(msg, fileName);
				//Email.transferAttachment(fileName + ".html");
				e.setContendId(fileName);
				e.save();
				
			}
		}
		
		}
		
	}
	
	public static boolean isSummonFileExist() {
		String BARANGAY = ReadConfig.value(Bris.BARANGAY);
		String MUNICIPALITY = ReadConfig.value(Bris.MUNICIPALITY);
		String pathFile = Bris.PRIMARY_DRIVE.getName() + 
		Bris.SEPERATOR.getName() + 
		Bris.APP_FOLDER.getName() + 
		Bris.SEPERATOR.getName() + "bris-runner" + Bris.SEPERATOR.getName();
		
		pathFile += BARANGAY + "-" + MUNICIPALITY;
		pathFile += Bris.SEPERATOR.getName();
		pathFile += "summonDate.bris";
		
		File file = new File(pathFile);
		if(file.exists()) {
			System.out.println("File exist... " + pathFile);
			return true;
		}
		
		return false;
	}
	
	public static String readSummonDate() {
		
		String BARANGAY = ReadConfig.value(Bris.BARANGAY);
		String MUNICIPALITY = ReadConfig.value(Bris.MUNICIPALITY);
		String emailPath = Bris.PRIMARY_DRIVE.getName() + 
		Bris.SEPERATOR.getName() + 
		Bris.APP_FOLDER.getName() + 
		Bris.SEPERATOR.getName() + "bris-runner" + Bris.SEPERATOR.getName();
		
		emailPath += BARANGAY + "-" + MUNICIPALITY;
		emailPath += Bris.SEPERATOR.getName();
		emailPath += "summonDate.bris";
		
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream(emailPath));
			return prop.getProperty("Date");
		}catch(Exception e) {}
		
		return DateUtils.getCurrentDateYYYYMMDD();
		
	}
	public static void saveSummonDate() {
		String BARANGAY = ReadConfig.value(Bris.BARANGAY);
		String MUNICIPALITY = ReadConfig.value(Bris.MUNICIPALITY);
		String emailPath = Bris.PRIMARY_DRIVE.getName() + 
		Bris.SEPERATOR.getName() + 
		Bris.APP_FOLDER.getName() + 
		Bris.SEPERATOR.getName() + "bris-runner" + Bris.SEPERATOR.getName();
		
		emailPath += BARANGAY + "-" + MUNICIPALITY;
		emailPath += Bris.SEPERATOR.getName();
		
		File file = new File(emailPath);
		
		if(!file.isDirectory()) {
			file.mkdir();
		}
		
		try {
		
		String msg = "Do not delete this file\n";
		msg += "Date Created: " + DateUtils.getCurrentDateMMMMDDYYYY();
		msg += "\nAuthor: MARXMIND\n";
		msg += "-------------------\n";
		msg += "\n";
		msg += "Date="+DateUtils.getCurrentDateYYYYMMDD();
		File email = new File(emailPath + "summonDate.bris");
		PrintWriter pw = new PrintWriter(new FileWriter(email));
		pw.println(msg);
		pw.flush();
		pw.close();
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isUserSummonFileExist() {
		String BARANGAY = ReadConfig.value(Bris.BARANGAY);
		String MUNICIPALITY = ReadConfig.value(Bris.MUNICIPALITY);
		String pathFile = Bris.PRIMARY_DRIVE.getName() + 
		Bris.SEPERATOR.getName() + 
		Bris.APP_FOLDER.getName() + 
		Bris.SEPERATOR.getName() + "bris-runner" + Bris.SEPERATOR.getName();
		
		pathFile += BARANGAY + "-" + MUNICIPALITY;
		pathFile += Bris.SEPERATOR.getName();
		pathFile += "summonUsers.bris";
		
		File file = new File(pathFile);
		if(file.exists()) {
			System.out.println("File exist... " + pathFile);
			return true;
		}
		
		return false;
	}
	
	public static String readUsersSummon() {
		String BARANGAY = ReadConfig.value(Bris.BARANGAY);
		String MUNICIPALITY = ReadConfig.value(Bris.MUNICIPALITY);
		String emailPath = Bris.PRIMARY_DRIVE.getName() + 
		Bris.SEPERATOR.getName() + 
		Bris.APP_FOLDER.getName() + 
		Bris.SEPERATOR.getName() + "bris-runner" + Bris.SEPERATOR.getName();
		
		emailPath += BARANGAY + "-" + MUNICIPALITY;
		emailPath += Bris.SEPERATOR.getName();
		emailPath += "summonUsers.bris";
		
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream(emailPath));
			return prop.getProperty("Users");
		}catch(Exception e) {}
		
		return "5";
	}
	
	public static void saveUsersSummon() {
		String BARANGAY = ReadConfig.value(Bris.BARANGAY);
		String MUNICIPALITY = ReadConfig.value(Bris.MUNICIPALITY);
		String emailPath = Bris.PRIMARY_DRIVE.getName() + 
		Bris.SEPERATOR.getName() + 
		Bris.APP_FOLDER.getName() + 
		Bris.SEPERATOR.getName() + "bris-runner" + Bris.SEPERATOR.getName();
		
		emailPath += BARANGAY + "-" + MUNICIPALITY;
		emailPath += Bris.SEPERATOR.getName();
		
		File file = new File(emailPath);
		
		if(!file.isDirectory()) {
			file.mkdir();
		}
		
		try {
		
		String msg = "Do not delete this file\n";
		msg += "Date Created: " + DateUtils.getCurrentDateMMMMDDYYYY();
		msg += "\nAuthor: MARXMIND\n";
		msg += "-------------------\n";
		msg += "\n";
		String val = "";
		for(int i=1; i<=5; i++) {
			if(i>1) {
				val += ","+i;
			}else {
				val += i;
			}
		}
		msg += "Users=" + val;
		File email = new File(emailPath + "summonUsers.bris");
		PrintWriter pw = new PrintWriter(new FileWriter(email));
		pw.println(msg);
		pw.flush();
		pw.close();
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void collectSummonReport() {
		
		List<Cases> caseLookup = Collections.synchronizedList(new ArrayList<Cases>());
		
		String sql = " AND ciz.caseisactive=1 AND ciz.casestatus!=? AND ciz.casestatus!=?";
		String[] params = new String[2];
		params[0] = CaseStatus.MOVED_IN_HIGHER_COURT.getId()+"";
		params[1] = CaseStatus.SETTLED.getId()+"";
		
		List<Cases> cases = Cases.retrieve(sql, params);
		
		boolean sendEmail = false;
		if(cases!=null && cases.size()>0) {
			
			for(Cases cz : cases){
				sql = " AND ciz.caseid=? AND fil.filisactive=1 AND fil.settlementdate=? ORDER BY fil.filid DESC LIMIT 1";
				params = new String[2];
				params[0] = cz.getId()+"";
				params[1] = DateUtils.getCurrentDateYYYYMMDD();
				try{
					
					List<CaseFilling> fil = CaseFilling.retrieve(sql, params);
					
					if(fil!=null && fil.size()>0) {
						cz.setFilling(fil.get(0));
						caseLookup.add(cz);
						sendEmail = true;
					}
				}catch(Exception e){}
			}
		
			
		}
		
		if(sendEmail) {
			//sending email
			sendingSummonMail(caseLookup);
		}
		
	}
	
	private static void sendingSummonMail(List<Cases> caseLookup) {
		
		String html = "";
		
		html = "<p><strong>Hi,</strong></p>";
		html += "<p><br></p>";
		html += "<p>Please see below scheduled summon for today <strong style=\"color: rgb(230, 0, 0);\" class=\"ql-size-large\">"+ DateUtils.getCurrentDateMMMMDDYYYY() +"</strong> for your reference.</p>";
		html += "<p>	</p>";
		
		for(Cases cz : caseLookup) {
			html += "<p>	   <strong style=\"color: rgb(0, 102, 204);\" class=\"ql-size-large\">"+ cz.getFilling().getSettlementTime() +"</strong></p>";
			html += "<p>			<strong>"+ cz.getKindName() +" - "+ cz.getComplainants() +" </strong><strong style=\"color: rgb(230, 0, 0);\">VS</strong><strong> "+ cz.getRespondents() +"</strong></p>";
		}
		
		
		html += "<p>	</p>";
		html += "<p><br></p>";
		html += "<p><strong style=\"color: rgb(230, 0, 0);\">THIS IS A SYSTEM GENERATED EMAIL. PLEASE DO NOT REPLY</strong></p>";
		html += "<p><br></p>";
		html += "<p><br></p>";
		html += "<p><br></p>";
		html += "<p>Best regards,</p>";
		html += "<p><strong>BRIS TEAM</strong></p>";
		html += "<p><hr/></p>";
		html += "<p><br></p>";
		
		int cnt=1;
		String toMailUser = "";
		boolean isCheckNote=false;
		for(String id : readUsersSummon().split(",")) {
			
					String sql = "SELECT * FROM userdtls WHERE isactive=1 AND jobtitleid=?";
					String[] params = new String[1];
					params[0] = id;
					List<UserDtls> toUsers = UserDtls.retrieve(sql, params);
					
					if(toUsers.size()>0) {
						isCheckNote=true;
						if(toUsers.size()>1) {
							String toM = "";
							int cntM=1;
							for(UserDtls user : toUsers) {
								if(cntM>1) {
									toM += ":"+user.getUserdtlsid()+"";
								}else {
									toM = user.getUserdtlsid()+"";
								}
								cntM++;	
							}
							if(cnt>1) {
								toMailUser += ":"+toM;
							}else {
								toMailUser = toM;
							}
							
						}else {
							if(cnt>1) {
								toMailUser += ":"+toUsers.get(0).getUserdtlsid()+"";
							}else {
								toMailUser = toUsers.get(0).getUserdtlsid()+"";
							}
						}
					}	
				
			
			cnt++;
		}
		
		System.out.println("send to " + toMailUser);
		
		isCheckNote=true;
		if(isCheckNote && !toMailUser.isEmpty()) {
			
			boolean isMore = false;
			try {
				String[] em = toMailUser.split(":");
				isMore=true;
		    }catch(Exception ex) {}
			if(isMore) {
				for(String sendTo : toMailUser.split(":")) {
					Email e = new Email();
					e.setSendDate(DateUtils.getCurrentDateYYYYMMDD());
					e.setTitle("Report for ["+ readDate() +"] SUMMON");
					
					e.setType(EmailType.INBOX.getId());
					e.setIsOpen(0);
					e.setIsDeleted(0);
					e.setIsActive(1);
					
					e.setToEmail(toMailUser);
					e.setPersonCopy(Long.valueOf(sendTo));
					e.setFromEmail("0");
					
					
					String fileName = DateUtils.getCurrentDateMMDDYYYYTIMEPlain() + "-" + sendTo;
					Email.emailSavePath(html, fileName);
					e.setContendId(fileName);
					e.save();
					
				}
			}else {
				Email e = new Email();
				e.setSendDate(DateUtils.getCurrentDateYYYYMMDD());
				e.setTitle("Report for ["+ readDate() +"] SUMMON");
				
				e.setType(EmailType.INBOX.getId());
				e.setIsOpen(0);
				e.setIsDeleted(0);
				e.setIsActive(1);
				
				e.setToEmail(toMailUser);
				e.setPersonCopy(Long.valueOf(toMailUser));
				e.setFromEmail("0");
				
				String fileName = DateUtils.getCurrentDateMMDDYYYYTIMEPlain() + "-" + toMailUser;
				Email.emailSavePath(html, fileName);
				
				e.setContendId(fileName);
				e.save();
				
			}
		}
	}
	
	public static void createGraphData() {
		
		String BARANGAY = ReadConfig.value(Bris.BARANGAY);
		String MUNICIPALITY = ReadConfig.value(Bris.MUNICIPALITY);
		String filePath = Bris.PRIMARY_DRIVE.getName() + 
		Bris.SEPERATOR.getName() + 
		Bris.APP_FOLDER.getName() + 
		Bris.SEPERATOR.getName() + "bris-runner" + Bris.SEPERATOR.getName();
		
		filePath += BARANGAY + "-" + MUNICIPALITY;
		filePath += Bris.SEPERATOR.getName();
		
		File file = new File(filePath);
		
		if(!file.isDirectory()) {
			file.mkdir();
		}
		
		
		try {
		
		String msg = "Do not delete this file\n";
		msg += "Date Created: " + DateUtils.getCurrentDateMMMMDDYYYY();
		msg += "\nAuthor: MARXMIND\n";
		msg += "-------------------\n";
		msg += "\n";
		
		msg += "business-last-year="+lastYear()+"\n";
		msg += "business-this-year="+currentYear()+"\n";
		
		msg += "business-new="+newInfo()+"\n";
		msg += "business-renew="+reNewInfo()+"\n";
		msg += "business-added="+addedInfo()+"\n";
		
		
		File conf = new File(filePath + CONFIG_FILE_NAME);
		PrintWriter pw = new PrintWriter(new FileWriter(conf));
		pw.println(msg);
		pw.flush();
		pw.close();
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static String newInfo() {
		
		int year = DateUtils.getCurrentYear();
		
		String sql = " AND bz.isactivebusiness=1 AND bz.year=? AND (bz.datetrans>=? AND bz.datetrans<=?) AND bz.typeof='NEW'";
		String[] params = new String[3];
		params[0] = year+"";
		params[1] = year +"-01-01";
		params[2] = year +"-12-31";
		
		Map<Integer, Integer> yearsData = Collections.synchronizedMap(new HashMap<Integer, Integer>());
		for(BusinessPermit p : BusinessPermit.retrieve(sql, params)) {
			
			int key = Integer.valueOf(p.getDateTrans().split("-")[1]);
			
			if(yearsData!=null) {
				if(yearsData.containsKey(key)) {
					int countNew = yearsData.get(key);
					countNew +=1;
					yearsData.put(key, countNew);
				}else {
					yearsData.put(key, 1);
				}
			}else {
				yearsData.put(key, 1);
			}
		}
		
		
		Map<Integer, Integer> yearsDataSort = new TreeMap<Integer, Integer>(yearsData);
		
		
		String value = "1=0";
		int index = 1;
		for(int key : yearsDataSort.keySet()) {
			if(index==1) {
				value = key + "=" + yearsDataSort.get(key);
			}else {
				value +=","+ key + "=" + yearsDataSort.get(key);
			}
			index++;
		}
		
		return value;
	}
	
private static String reNewInfo() {
		
		int year = DateUtils.getCurrentYear();
		
		String sql = " AND bz.isactivebusiness=1 AND bz.year=? AND (bz.datetrans>=? AND bz.datetrans<=?) AND bz.typeof='RENEW'";
		String[] params = new String[3];
		params[0] = year+"";
		params[1] = year +"-01-01";
		params[2] = year +"-12-31";
		
		Map<Integer, Integer> yearsData = Collections.synchronizedMap(new HashMap<Integer, Integer>());
		for(BusinessPermit p : BusinessPermit.retrieve(sql, params)) {
			
			int key = Integer.valueOf(p.getDateTrans().split("-")[1]);
			
			if(yearsData!=null) {
				if(yearsData.containsKey(key)) {
					int countNew = yearsData.get(key);
					countNew +=1;
					yearsData.put(key, countNew);
				}else {
					yearsData.put(key, 1);
				}
			}else {
				yearsData.put(key, 1);
			}
		}
		
		
		Map<Integer, Integer> yearsDataSort = new TreeMap<Integer, Integer>(yearsData);
		
		
		String value = "1=0";
		int index = 1;
		for(int key : yearsDataSort.keySet()) {
			if(index==1) {
				value = key + "=" + yearsDataSort.get(key);
			}else {
				value +=","+ key + "=" + yearsDataSort.get(key);
			}
			index++;
		}
		
		return value;
	}

private static String addedInfo() {
	
	int year = DateUtils.getCurrentYear();
	
	String sql = " AND bz.isactivebusiness=1 AND bz.year=? AND (bz.datetrans>=? AND bz.datetrans<=?) AND bz.typeof='ADDED'";
	String[] params = new String[3];
	params[0] = year+"";
	params[1] = year +"-01-01";
	params[2] = year +"-12-31";
	
	Map<Integer, Integer> yearsData = Collections.synchronizedMap(new HashMap<Integer, Integer>());
	for(BusinessPermit p : BusinessPermit.retrieve(sql, params)) {
		
		int key = Integer.valueOf(p.getDateTrans().split("-")[1]);
		
		if(yearsData!=null) {
			if(yearsData.containsKey(key)) {
				int countNew = yearsData.get(key);
				countNew +=1;
				yearsData.put(key, countNew);
			}else {
				yearsData.put(key, 1);
			}
		}else {
			yearsData.put(key, 1);
		}
	}
	
	
	Map<Integer, Integer> yearsDataSort = new TreeMap<Integer, Integer>(yearsData);
	
	
	String value = "1=0";
	int index = 1;
	for(int key : yearsDataSort.keySet()) {
		if(index==1) {
			value = key + "=" + yearsDataSort.get(key);
		}else {
			value +=","+ key + "=" + yearsDataSort.get(key);
		}
		index++;
	}
	
	return value;
}
	
	private static String lastYear() {
		
		int lastYear = DateUtils.getCurrentYear() - 1;
		
		String sql = " AND bz.isactivebusiness=1 AND bz.year=? AND (bz.datetrans>=? AND bz.datetrans<=?)";
		String[] params = new String[3];
		params[0] = lastYear+"";
		params[1] = lastYear +"-01-01";
		params[2] = lastYear +"-12-31";
		
		Map<Integer, Integer> lastYearsData = Collections.synchronizedMap(new HashMap<Integer, Integer>());
		for(BusinessPermit p : BusinessPermit.retrieve(sql, params)) {
			
			int key = Integer.valueOf(p.getDateTrans().split("-")[1]);
			
			if(lastYearsData!=null) {
				if(lastYearsData.containsKey(key)) {
					int countNew = lastYearsData.get(key);
					countNew +=1;
					lastYearsData.put(key, countNew);
				}else {
					lastYearsData.put(key, 1);
				}
			}else {
				lastYearsData.put(key, 1);
			}
		}
		
		
		Map<Integer, Integer> lastYearsDataSort = new TreeMap<Integer, Integer>(lastYearsData);
		
		
		String last = "1=0,2=0,3=0,4=0,5=0,6=0,7=0,8=0,9=0,10=0,11=0,12=0";
		int index = 1;
		for(int key : lastYearsDataSort.keySet()) {
			if(index==1) {
				last = key + "=" + lastYearsDataSort.get(key);
			}else {
				last +=","+ key + "=" + lastYearsDataSort.get(key);
			}
			index++;
		}
		
		return last;
	}
	
private static String currentYear() {
		
		int thisYEar = DateUtils.getCurrentYear();
		
		String sql = " AND bz.isactivebusiness=1 AND bz.year=? AND (bz.datetrans>=? AND bz.datetrans<=?)";
		String[] params = new String[3];
		params[0] = thisYEar+"";
		params[1] = thisYEar +"-01-01";
		params[2] = thisYEar +"-12-31";
		
		Map<Integer, Integer> thisYearsData = Collections.synchronizedMap(new HashMap<Integer, Integer>());
		for(BusinessPermit p : BusinessPermit.retrieve(sql, params)) {
			
			int key = Integer.valueOf(p.getDateTrans().split("-")[1]);
			
			if(thisYearsData!=null) {
				if(thisYearsData.containsKey(key)) {
					int countNew = thisYearsData.get(key);
					countNew +=1;
					thisYearsData.put(key, countNew);
					System.out.println("this year "+countNew);
				}else {
					thisYearsData.put(key, 1);
				}
			}else {
				thisYearsData.put(key, 1);
			}
		}
		
		Map<Integer, Integer> thisYearsDataSort = new TreeMap<Integer, Integer>(thisYearsData);
		
		String current = "";
		int index=1;
		for(int key : thisYearsDataSort.keySet()) {
			if(index==1) {
				current += key + "=" + thisYearsDataSort.get(key);
			}else {
				current +=","+ key + "=" + thisYearsDataSort.get(key);
			}
			index++;
		}
		
		return current;
	}
	
}