package com.italia.marxmind.bris.application;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpSession;

import com.italia.marxmind.bris.controller.ThemesDecoder;
import com.italia.marxmind.bris.enm.Bris;
import com.italia.marxmind.bris.reader.ReadConfig;
import com.italia.marxmind.bris.sessions.SessionBean;
/**
 * 
 * @author mark italia
 * @since 04/09/2017
 * @version 1.0
 *
 */
@ApplicationScoped
@ManagedBean(name="themeBean", eager=true)
public class ThemeBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 147868854437557L;

	public String getApplicationTheme(){
		String theme = "none";
		System.out.println("Applying theme...");
		try{
			/*theme = ReadConfig.value(Bris.THEME_STYLE);
		theme = ThemesDecoder.themeDecode(theme);*/
		HttpSession session = SessionBean.getSession();
		theme = session.getAttribute("themes").toString();	
			
		System.out.println("Theme " + theme + " has been applied...");}catch(Exception e){
			theme = "luna-blue";
		}
		return theme;
	}
	
}

