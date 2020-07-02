package com.italia.marxmind.bris.bean;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import com.italia.marxmind.bris.application.ClientInfo;
import com.italia.marxmind.bris.application.DailyReport;
import com.italia.marxmind.bris.controller.AccessAllowed;
import com.italia.marxmind.bris.controller.Business;
import com.italia.marxmind.bris.controller.Login;
import com.italia.marxmind.bris.controller.UserDtls;
import com.italia.marxmind.bris.enm.Bris;
import com.italia.marxmind.bris.enm.Feature;
import com.italia.marxmind.bris.enm.UserAccess;
import com.italia.marxmind.bris.reader.ReadConfig;
import com.italia.marxmind.bris.security.License;
import com.italia.marxmind.bris.security.Module;
import com.italia.marxmind.bris.security.SecureChar;
import com.italia.marxmind.bris.sessions.IBean;
import com.italia.marxmind.bris.sessions.SessionBean;
import com.italia.marxmind.bris.utils.DateUtils;
import com.italia.marxmind.bris.utils.LogU;
import com.italia.marxmind.bris.utils.Whitelist;

/**
 * 
 * @author mark italia
 * @since 09/27/2016
 *
 */

@ManagedBean(name="loginBean", eager=true)
@SessionScoped
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1094801825228386363L;
	
	private String name;
	private String password;
	private String errorMessage;
	private Login login;
	private String keyPress;
	private int businessId;
	private List business;
	private Map<Integer, Business> businessData = Collections.synchronizedMap(new HashMap<Integer, Business>());
	private static final String REPORT_PATH = ReadConfig.value(Bris.REPORT);
	private String ui="6";
	private String idThemes="luna-blue";
	private List themes;
	
	public String getCurrentDate(){//MMMM d, yyyy
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date date_ = new Date();
		String _date = dateFormat.format(date_);
		return _date;
	}
	
	
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@PostConstruct
	public void init(){
		//invalidate session
		//IBean.removeBean();
		
		//load business wallpaper
		//String wallpaper = ReadConfig.value(Bris.BUSINESS_WALLPAPER_FILE);
		
		//copyWallpaperImg(wallpaper);
		
		//String path = REPORT_PATH + ReadConfig.value(Bris.BARANGAY_NAME).replace(" ", "") + Bris.SEPERATOR.getName();
		//String filoLogo = path + "logo.png";
		//copyLogo(filoLogo);
		
		//DailyReport.startCollectReport();
	}
	
	public void copyWallpaperImg(String wallpaper){
		String pathToSave = FacesContext.getCurrentInstance()
                .getExternalContext().getRealPath(Bris.SEPERATOR.getName()) + Bris.SEPERATOR.getName() +
                Bris.APP_RESOURCES_LOC.getName() + Bris.SEPERATOR.getName() + 
                Bris.BUSSINES_WALLPAPER_IMG.getName() + Bris.SEPERATOR.getName();
		//System.out.println("pathToSave " + pathToSave + wallpaper);
		File logdirectory = new File(pathToSave);
		if(logdirectory.isDirectory()){
			//System.out.println("is directory");
		}
		
		
		String productFile = ReadConfig.value(Bris.APP_IMG_FILE) + wallpaper;
		File file = new File(productFile);
		//if(!file.exists()){
			//System.out.println("copying file.... " + file.getName());
			try{
			Files.copy(file.toPath(), (new File(pathToSave + file.getName())).toPath(),
			        StandardCopyOption.REPLACE_EXISTING);
			}catch(IOException e){}
			
		//}
	}
	
	public void copyLogo(String filoLogo){
		String pathToSave = FacesContext.getCurrentInstance()
                .getExternalContext().getRealPath(Bris.SEPERATOR.getName()) + Bris.SEPERATOR.getName() +
                Bris.APP_RESOURCES_LOC.getName() + Bris.SEPERATOR.getName() + 
                Bris.BUSSINES_WALLPAPER_IMG.getName() + Bris.SEPERATOR.getName();
		
		File logdirectory = new File(pathToSave);
		if(logdirectory.isDirectory()){
			//System.out.println("is directory");
		}
		
		//System.out.println("Check path to save: " + pathToSave);
		
		File file = new File(filoLogo);
		
			try{
			Files.copy(file.toPath(), (new File(pathToSave)).toPath(),
			        StandardCopyOption.REPLACE_EXISTING);
			}catch(IOException e){}
		
	}
	
	//validate login
	public String validateUserNamePassword(){
		
		//if(getBusinessId()==0){
		//set database on on business type
		//changeDatabaseConnection();
		//}
		
		
		String sql = "SELECT * FROM login WHERE username=?";// and password=?";
		
		String[] params = new String[1];
		         params[0] = Whitelist.remove(name);
		         //params[1] = Whitelist.remove(password);
		Login in = null;
		try{in = Login.retrieve(sql, params).get(0);}catch(Exception e){}
		
		boolean isOk = false;
		if(in!=null){
			String pass1=password;
			String pass2=SecureChar.decodePassword(in.getPassword());
			if(pass1.equalsIgnoreCase(pass2)){
				isOk=true;
			}
		}
		/*boolean valid = Login.validate(sql, params);
		System.out.println("Valid: " + valid);*/
		
		String result="login";
		LogU.add("Guest with username : " + name + " and password ********* is trying to log in the system.");
		if(isOk){
			
	        HttpSession session = SessionBean.getSession();
	        session.setAttribute("username", name);
			session.setAttribute("userid", in.getLogid());
			session.setAttribute("ui", getUi());
			session.setAttribute("themes", getIdThemes());
			
			boolean isExpired = License.checkLicenseExpiration(Module.BRIS);
			
	        if("6".equalsIgnoreCase(getUi())) {
	        	result = "permit" + getUi();
	        }else {
	        	result = "dashboard";
	        }
			
			LogU.add("The user has been successfully login to the application with the username : " + name + " and password *********");
			
			if(isExpired){
				LogU.add("The application is expired. Please contact application owner.");
				result = "expired";
			}else{
				logUserIn(in);
			}
			
			
		}else{
			FacesContext.getCurrentInstance().addMessage(
					null,new FacesMessage(
							FacesMessage.SEVERITY_WARN, 
							"Incorrect username and password", 
							"Please enter correct username and password"
							)
					);
//			/setErrorMessage("Incorrect username and password.");
			LogU.add("The user was not successfully login to the application with the username : " + name + " and password *********");
			setName("");
			setPassword("");
			result= "login";
		}
		System.out.println(getErrorMessage());
		return result;
	}
	
	public String validateUserNamePasswordMobile() {
		//if(getBusinessId()==0){
				//set database on on business type
				getBusiness();//load barangay
				setBusinessId(0);
				changeDatabaseConnection();
				//}
				
				
				String sql = "SELECT * FROM login WHERE username=?";// and password=?";
				
				String[] params = new String[1];
				         params[0] = Whitelist.remove(name);
				         //params[1] = Whitelist.remove(password);
				Login in = null;
				try{in = Login.retrieve(sql, params).get(0);}catch(Exception e){}
				
				boolean isOk = false;
				if(in!=null){
					String pass1=password;
					String pass2=SecureChar.decodePassword(in.getPassword());
					if(pass1.equalsIgnoreCase(pass2)){
						isOk=true;
					}
				}
				/*boolean valid = Login.validate(sql, params);
				System.out.println("Valid: " + valid);*/
				
				String result="mobilelogin";
				LogU.add("Guest with username : " + name + " and password ********* is trying to log in the system.");
				if(isOk){
					
					result = "mobilemail";
			        HttpSession session = SessionBean.getSession();
			        session.setAttribute("username", name);
					session.setAttribute("userid", in.getLogid());
					
					boolean isExpired = License.checkLicenseExpiration(Module.BRIS);
					
			        
					//result = "main";
					/*if(UserAccess.DEVELOPER.getId()==in.getAccessLevel().getLevel() ||
							UserAccess.CAPTAIN.getId()==in.getAccessLevel().getLevel() ||
							UserAccess.KAGAWAD.getId()==in.getAccessLevel().getLevel()){
						result = "mobilemail";
					}else{
						result = redirectUserPage(in.getUserDtls());
					}*/
					
					LogU.add("The user has been successfully login to the application with the username : " + name + " and password *********");
					
					if(isExpired){
						LogU.add("The application is expired. Please contact application owner.");
						result = "expired";
					}else{
						logUserIn(in);
					}
					
					
				}else{
					FacesContext.getCurrentInstance().addMessage(
							null,new FacesMessage(
									FacesMessage.SEVERITY_WARN, 
									"Incorrect username and password", 
									"Please enter correct username and password"
									)
							);
//					/setErrorMessage("Incorrect username and password.");
					LogU.add("The user was not successfully login to the application with the username : " + name + " and password *********");
					setName("");
					setPassword("");
					result= "mobilelogin";
				}
				System.out.println(getErrorMessage());
				return result;
	}
	
	private String redirectUserPage(UserDtls user){
		String page = "login";
		for(AccessAllowed acc : AccessAllowed.retrieve(" AND ac.isactiveaccess=1  AND usr.userdtlsid="+user.getUserdtlsid() + " limit 1", new String[0])){
			if(Feature.CLEARANCE.getName().equalsIgnoreCase(acc.getFeatures().getModuleName())){
				page = "clearance";
			}else if(Feature.ID_GENERATION.getName().equalsIgnoreCase(acc.getFeatures().getModuleName())){
				page = "card";
			}else if(Feature.BUSINESS.getName().equalsIgnoreCase(acc.getFeatures().getModuleName())){
				page = "adminBusiness";
			}else if(Feature.ASSISTANT.getName().equalsIgnoreCase(acc.getFeatures().getModuleName())){
				page = "assistant";
			}else if(Feature.GRAPH.getName().equalsIgnoreCase(acc.getFeatures().getModuleName())){
				page = "graph";
			}else if(Feature.ORGANIZATION.getName().equalsIgnoreCase(acc.getFeatures().getModuleName())){
				page = "organization";
			}else if(Feature.CHEQUE.getName().equalsIgnoreCase(acc.getFeatures().getModuleName())){
				page = "cheque";
			}else if(Feature.MOE_METER.getName().equalsIgnoreCase(acc.getFeatures().getModuleName())){
				page = "moemeter";
			}else if(Feature.BLOTTERS.getName().equalsIgnoreCase(acc.getFeatures().getModuleName())){
				page = "blotters";
			}else if(Feature.PROFILE_DIRECTORY.getName().equalsIgnoreCase(acc.getFeatures().getModuleName())){
				page = "main";
			}else if(Feature.CITIZEN_REGISTRATION.getName().equalsIgnoreCase(acc.getFeatures().getModuleName())){
				page = "admincustomer";
			}else if(Feature.APPLICATION_USER.getName().equalsIgnoreCase(acc.getFeatures().getModuleName())){
				page = "adminuser";
			}else if(Feature.EMPLOYEE.getName().equalsIgnoreCase(acc.getFeatures().getModuleName())){
				page = "adminEmployees";
			}
		}
		return page;
	}
	
	private void logUserIn(Login in){
		if(in==null) in = new Login();
		ClientInfo cinfo = new ClientInfo();
		in.setLogintime(DateUtils.getCurrentDateMMDDYYYYTIME());
		in.setIsOnline(1);
		in.setClientip(cinfo.getClientIP());
		in.setClientbrowser(cinfo.getBrowserName());
		in.save();
	}
	
	private void logUserOut(){
		String sql = "SELECT * FROM login WHERE username=? and logid=?";
		HttpSession session = SessionBean.getSession();
		String userid = session.getAttribute("userid").toString();
		String username = session.getAttribute("username").toString();
		String[] params = new String[2];
    	params[0] = username;
    	params[1] = userid;
    	Login in = null;
    	try{in = Login.retrieve(sql, params).get(0);}catch(Exception e){}
		ClientInfo cinfo = new ClientInfo();
		if(in!=null){
			in.setLastlogin(DateUtils.getCurrentDateMMDDYYYYTIME());
			in.setIsOnline(0);
			in.setClientip(cinfo.getClientIP());
			in.setClientbrowser(cinfo.getBrowserName());
			in.save();
		}
		LogU.add("The user " + username + " was logging out to the application.");
		
		//Remove registered bean in session
		IBean.removeBean();
		
	}
	
	//logout event, invalidate session
	public String logout(){
		logUserOut();
		setName("");
		setPassword("");
		return "login";
	}

	@Deprecated
	public void changeDatabaseConnection(){
		System.out.println("changing database....");
		
		int size = 11;
		Bris[] tag = new Bris[size];
		String[] value = new String[size];
		int i=0;
		tag[i] = Bris.DB_NAME; value[i] = getBusinessData().get(getBusinessId()).getDatabase(); i++;
		tag[i] = Bris.BARANGAY_NAME; value[i] = getBusinessData().get(getBusinessId()).getName(); i++;
		tag[i] = Bris.DB_DRIVER; value[i] = getBusinessData().get(getBusinessId()).getDriver(); i++;
		tag[i] = Bris.DB_URL; value[i] = getBusinessData().get(getBusinessId()).getUrl(); i++;
		tag[i] = Bris.DB_PORT; value[i] = getBusinessData().get(getBusinessId()).getPort(); i++;
		tag[i] = Bris.DB_SSL; value[i] = getBusinessData().get(getBusinessId()).getSsl(); i++;
		tag[i] = Bris.IDCODE; value[i] = getBusinessData().get(getBusinessId()).getBcode(); i++;
		tag[i] = Bris.PROVINCE; value[i] = getBusinessData().get(getBusinessId()).getProvince(); i++;
		tag[i] = Bris.MUNICIPALITY; value[i] = getBusinessData().get(getBusinessId()).getMunicipality(); i++;
		tag[i] = Bris.BARANGAY; value[i] = getBusinessData().get(getBusinessId()).getBarangay(); i++;
		tag[i] = Bris.YEARLY_BUDGET; value[i] = getBusinessData().get(getBusinessId()).getYearlyBudget(); i++;
		Business.updateBusiness(tag, value);
		
		System.out.println("Database has been changed....");
	}

	public Login getLogin() {
		return login;
	}



	public void setLogin(Login login) {
		this.login = login;
	}



	public String getKeyPress() {
		/*if((getName()!=null && !getName().isEmpty()) && (getPassword()!=null && !getPassword().isEmpty())){
			keyPress = "logId";
		}else{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Please provide information to proceed.", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}*/
		System.out.println("calling login button");
		keyPress = "logId";
		return keyPress;
	}



	public void setKeyPress(String keyPress) {
		this.keyPress = keyPress;
	}



	public int getBusinessId() {
		return businessId;
	}



	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}



	public List getBusiness() {
		businessData = Collections.synchronizedMap(new HashMap<Integer, Business>());
		business = new ArrayList<>();
		
		for(Business bz : Business.readBusinessXML()){
			business.add(new SelectItem(bz.getId(), bz.getName()));
			businessData.put(bz.getId(), bz);
		}
		
		return business;
	}



	public void setBusiness(List business) {
		this.business = business;
	}



	public Map<Integer, Business> getBusinessData() {
		return businessData;
	}



	public void setBusinessData(Map<Integer, Business> businessData) {
		this.businessData = businessData;
	}



	public String getUi() {
		return ui;
	}



	public void setUi(String ui) {
		this.ui = ui;
	}



	public String getIdThemes() {
		return idThemes;
	}



	public void setIdThemes(String idThemes) {
		this.idThemes = idThemes;
	}



	public List getThemes() {
		themes = new ArrayList<>();
		themes.add(new SelectItem("luna-amber", "luna-amber"));
		themes.add(new SelectItem("luna-blue", "luna-blue"));
		themes.add(new SelectItem("luna-green", "luna-green"));
		themes.add(new SelectItem("luna-pink", "luna-pink"));
		themes.add(new SelectItem("nova-colored", "nova-colored"));
		themes.add(new SelectItem("nova-dark", "nova-dark"));
		themes.add(new SelectItem("nova-light", "nova-light"));
		return themes;
	}



	public void setThemes(List themes) {
		this.themes = themes;
	}
	
}


