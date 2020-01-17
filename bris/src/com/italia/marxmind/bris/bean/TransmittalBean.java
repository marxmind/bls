package com.italia.marxmind.bris.bean;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import com.italia.marxmind.bris.application.GlobalReportHandler;
import com.italia.marxmind.bris.application.GlobalReportHandler.GlobalReport;
import com.italia.marxmind.bris.controller.BankChequeRpt;
import com.italia.marxmind.bris.controller.BankChequeTrans;
import com.italia.marxmind.bris.controller.Chequedtls;
import com.italia.marxmind.bris.controller.Employee;
import com.italia.marxmind.bris.controller.NumberToWords;
import com.italia.marxmind.bris.controller.Transmittal;
import com.italia.marxmind.bris.controller.TransmittalRpt;
import com.italia.marxmind.bris.controller.Words;
import com.italia.marxmind.bris.enm.Bris;
import com.italia.marxmind.bris.enm.DateFormat;
import com.italia.marxmind.bris.enm.Positions;
import com.italia.marxmind.bris.reader.ReadConfig;
import com.italia.marxmind.bris.reports.ChequesRpt;
import com.italia.marxmind.bris.reports.ReadXML;
import com.italia.marxmind.bris.reports.ReportCompiler;
import com.italia.marxmind.bris.reports.ReportTag;
import com.italia.marxmind.bris.utils.Currency;
import com.italia.marxmind.bris.utils.DateUtils;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 
 * @author Mark Italia
 * @since 08/02/2018
 * @version 1.0
 *
 */
@ManagedBean(name="tranBean", eager=true)
@ViewScoped
public class TransmittalBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 16787894423L;
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
	private static final String REPORT_PATH = ReadConfig.value(Bris.REPORT);
	
	private String BARANGAY = ReadConfig.value(Bris.BARANGAY);
	private String MUNICIPALITY = ReadConfig.value(Bris.MUNICIPALITY);
	private String PROVINCE = ReadConfig.value(Bris.PROVINCE);
	
	private Date dateTrans;
	private List months;
	private int monthId;
	private List<TransmittalRpt> checkSelected;
	private double amount;
	private List<TransmittalRpt> transmittals = Collections.synchronizedList(new ArrayList<TransmittalRpt>());
	private int year;
	
	@PostConstruct
	public void init() {
		loadMonthCheques();
	}
	
	public void loadMonthCheques() {
		transmittals = Collections.synchronizedList(new ArrayList<TransmittalRpt>());
		String sql = " AND bnk.bankrptisactive=1 AND (bnk.dateapplying>=? ANd bnk.dateapplying<=?)";
		String month = "";
		if(getMonthId()<=9) {
			month = "0"+getMonthId();
		}else {
			month = getMonthId()+"";
		}
		String[] params = new String[2];
		if(year==0) {
			year = DateUtils.getCurrentYear();
		}
		System.out.println("Year selected >> " + year);
		params[0] = year +"-" + month +"-01";
		params[1] = year +"-" + month +"-31";
		
		Map<String, BankChequeTrans> unsortCheck = Collections.synchronizedMap(new HashMap<String, BankChequeTrans>());
		
		for(BankChequeRpt pbc : BankChequeRpt.retrieve(sql, params)) {
			sql = " AND tran.bankisactivetrans=1 AND bnk.bankchkid="+pbc.getId() +" ORDER BY chk.checkno";
			for(BankChequeTrans chk : BankChequeTrans.retrieve(sql, new String[0])){
				unsortCheck.put(chk.getChequedtls().getCheckNo(), chk);
				amount += chk.getChequedtls().getAmount();
			}
		}
		
		Map<String, BankChequeTrans> sortCheck = new TreeMap<String, BankChequeTrans>(unsortCheck);
		for(BankChequeTrans chk : sortCheck.values()) {
			TransmittalRpt tran = new TransmittalRpt();
			
			tran.setId(chk.getChequedtls().getId());
			
			tran.setDvDate(chk.getChequedtls().getDateTrans());
			tran.setDvNumber(chk.getChequedtls().getDvNo());
			
			tran.setCheckDate(chk.getChequedtls().getDateTrans());
			tran.setCheckNumber(chk.getChequedtls().getCheckNo());
			
			tran.setPayee(chk.getChequedtls().getIssueTo());
			tran.setAmount(Currency.formatAmount(chk.getChequedtls().getAmount()));
			
			tran.setPbcDate(chk.getChequeRpt().getDateApplying());
			tran.setPbcNumber(chk.getChequeRpt().getPbcNo());
			transmittals.add(tran);
		}
		
		
	}
	
	public void printTransmittal() {
		Employee capitan = Employee.retrievePosition(Positions.CAPTAIN.getId());
		Employee treasurer = Employee.retrievePosition(Positions.TREASURER.getId());
		String REPORT_NAME = GlobalReportHandler.report(GlobalReport.TRANSMITAL_LETTER);
		
		HashMap param = new HashMap();
		String path = REPORT_PATH + ReadConfig.value(Bris.BARANGAY_NAME).replace(" ", "") + Bris.SEPERATOR.getName();
		
		ReportCompiler compiler = new ReportCompiler();
		String jrxmlFile = compiler.compileReport(REPORT_NAME, REPORT_NAME, path);
		
		JRBeanCollectionDataSource beanColl = new JRBeanCollectionDataSource(checkSelected);
		
		param.put("PARAM_PROVINCE", Words.getTagName("province-line").replace("<province>", PROVINCE));
		param.put("PARAM_MUNICIPALITY", Words.getTagName("municipality-line").replace("<municipality>", MUNICIPALITY));
		
		param.put("PARAM_HEAD_DEPARTMENT", Words.getTagName("accounting-recepient"));
		param.put("PARAM_POSITION", Words.getTagName("accounting-position"));
		param.put("PARAM_MUNICIPALITY_ASSIGNED", Words.getTagName("accounting-municipal"));
		param.put("PARAM_PROVINCE_ASSIGNED", Words.getTagName("accounting-province"));
		
		param.put("PARAM_DATEAPPLYING", DateUtils.convertDateToMonthDayYear(DateUtils.convertDate(getDateTrans(), DateFormat.YYYY_MM_DD())).toUpperCase());
		
		param.put("PARAM_SIRMADAM", Words.getTagName("accounting-sirmadam"));
		
		String bodyletter = Words.getTagName("accounting-body-letter");
		bodyletter = bodyletter.replace("<barangay>", BARANGAY.toUpperCase());
		bodyletter = bodyletter.replace("<municipality>", MUNICIPALITY);
		bodyletter = bodyletter.replace("<province>", PROVINCE);
		bodyletter = bodyletter.replace("<month>", DateUtils.getMonthName(getMonthId()));
		bodyletter = bodyletter.replace("<year>", getYear()+"");
		param.put("PARAM_BODY_LETTER", bodyletter);
		
		param.put("PARAM_TOTAL", "Grand Total: Php "+Currency.formatAmount(amount));
		String words = NumberToWords.changeToWords(Currency.formatAmount(amount).replace(",", ""));
		param.put("PARAM_INWORDS", "** "+ words.toUpperCase() +" **");
		
		String documentNote="";
		documentNote += "Brgy. Document\n";
		documentNote += "Series of " + getYear()+"\n";
		try{param.put("PARAM_DOCUMENT_NOTE", documentNote);}catch(NullPointerException e) {}
		try{param.put("PARAM_TAGLINE", Words.getTagName("tagline"));}catch(NullPointerException e) {}
		//background
		String backlogo = path + "documentbg-gen.png";
		try{File file = new File(backlogo);
		FileInputStream off = new FileInputStream(file);
		param.put("PARAM_BACKGROUND", off);
		}catch(Exception e){}
		/**
		 * PARAM_RECEPIENT
		 * PARAM_BANKNAME
		 * PARAM_BANKLOC
		 * PARAM_BANKPROV
		 * PARAM_PBCNO
		 * PARAM_DATEAPPLYING
		 * PARAM_CEDULANO
		 * PARAM_CEDULADATE
		 * PARAM_ISSUEDAT
		 * PARAM_BODY_LETTER
		 * PARAM_TOTAL
		 * PARAM_INWORDS
		 */
		
		param.put("PARAM_OFFICER_DAY", capitan.getFirstName().toUpperCase() + " " + capitan.getMiddleName().substring(0,1).toUpperCase() + ". " + capitan.getLastName().toUpperCase());
		param.put("PARAM_OFFICIAL_TITLE",capitan.getPosition().getName());
		
		Employee treas = Employee.retrievePosition(Positions.TREASURER.getId());
		param.put("PARAM_TREASURER",treas.getFirstName() + " " + treas.getLastName());
		
		
		//logo
		String officialLogo = path + "logo.png";
		try{File file = new File(officialLogo);
		FileInputStream off = new FileInputStream(file);
		param.put("PARAM_LOGO", off);
		}catch(Exception e){}
		
		//officialseallakesebu
		String lakesebuofficialseal = path + "municipalseal.png";
		try{File file1 = new File(lakesebuofficialseal);
		FileInputStream off2 = new FileInputStream(file1);
		param.put("PARAM_LOGO_LAKESEBU", off2);
		}catch(Exception e){}
		
		//logo
		String logo = path + "barangaylogotrans.png";
		try{File file = new File(logo);
		FileInputStream off = new FileInputStream(file);
		param.put("PARAM_SEALTRANSPARENT", off);
		}catch(Exception e){}
		
		//authorization
		String bankcheque = path + "bankchequestitle.png";
		try{File file1 = new File(bankcheque);
		FileInputStream off1 = new FileInputStream(file1);
		param.put("PARAM_TITLE_DOC", off1);
		}catch(Exception e){}
		
		try{
	  		String jrprint = JasperFillManager.fillReportToFile(jrxmlFile, param, beanColl);
	  	    JasperExportManager.exportReportToPdfFile(jrprint,path+ REPORT_NAME +".pdf");
	  		}catch(Exception e){e.printStackTrace();}
		
				try{
					System.out.println("REPORT_PATH:" + path + "REPORT_NAME: " + REPORT_NAME);
		  		 File file = new File(path, REPORT_NAME + ".pdf");
				 FacesContext faces = FacesContext.getCurrentInstance();
				 ExternalContext context = faces.getExternalContext();
				 HttpServletResponse response = (HttpServletResponse)context.getResponse();
					
			     BufferedInputStream input = null;
			     BufferedOutputStream output = null;
			     
			     try{
			    	 
			    	 // Open file.
			            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);

			            // Init servlet response.
			            response.reset();
			            response.setHeader("Content-Type", "application/pdf");
			            response.setHeader("Content-Length", String.valueOf(file.length()));
			            response.setHeader("Content-Disposition", "inline; filename=\"" + REPORT_NAME + ".pdf" + "\"");
			            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

			            // Write file contents to response.
			            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			            int length;
			            while ((length = input.read(buffer)) > 0) {
			                output.write(buffer, 0, length);
			            }

			            // Finalize task.
			            output.flush();
			    	 
			     }finally{
			    	// Gently close streams.
			            close(output);
			            close(input);
			     }
			     
			     // Inform JSF that it doesn't need to handle response.
			        // This is very important, otherwise you will get the following exception in the logs:
			        // java.lang.IllegalStateException: Cannot forward after response has been committed.
			        faces.responseComplete();
			        
				}catch(Exception ioe){
					ioe.printStackTrace();
				}
	}
	
	private void close(Closeable resource) {
	    if (resource != null) {
	        try {
	            resource.close();
	        } catch (IOException e) {
	            // Do your thing with the exception. Print it, log it or mail it. It may be useful to 
	            // know that this will generally only be thrown when the client aborted the download.
	            e.printStackTrace();
	        }
	    }
	}
	
	public Date getDateTrans() {
		if(dateTrans==null) {
			dateTrans = DateUtils.convertDateString(DateUtils.getCurrentDateYYYYMMDD(),DateFormat.YYYY_MM_DD());
		}
		return dateTrans;
	}
	public void setDateTrans(Date dateTrans) {
		this.dateTrans = dateTrans;
	}
	public List getMonths() {
		months = new ArrayList<>();
		
		for(int i=1; i<=12; i++) {
			months.add(new SelectItem(i, DateUtils.getMonthName(i)));
		}
		
		return months;
	}
	public void setMonths(List months) {
		this.months = months;
	}
	public int getMonthId() {
		if(monthId==0) {
			monthId = DateUtils.getCurrentMonth();
		}
		return monthId;
	}
	public void setMonthId(int monthId) {
		this.monthId = monthId;
	}

	public List<TransmittalRpt> getTransmittals() {
		return transmittals;
	}

	public void setTransmittals(List<TransmittalRpt> transmittals) {
		this.transmittals = transmittals;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public List<TransmittalRpt> getCheckSelected() {
		return checkSelected;
	}

	public void setCheckSelected(List<TransmittalRpt> checkSelected) {
		this.checkSelected = checkSelected;
	}

	public int getYear() {
		if(year==0) {
			year = DateUtils.getCurrentYear();
		}
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	
}
