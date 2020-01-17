package com.italia.marxmind.bris.application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import com.italia.marxmind.bris.enm.Bris;
import com.italia.marxmind.bris.reader.ReadConfig;

/**
 * 
 * @author Mark Italia
 * @since 02/21/2019
 * @version 1.0
 *
 */

public class ReadDashboardInfo {
	
	public static final String CONFIG_FILE_NAME="dashboard-data.bris";
	
	public static void main(String[] args) {
		
	}
	
	public static Map<String, Double> getInfo(String key){
		Map<String, Double> info = Collections.synchronizedMap(new HashMap<String, Double>());
		
		String BARANGAY = ReadConfig.value(Bris.BARANGAY);
		String MUNICIPALITY = ReadConfig.value(Bris.MUNICIPALITY);
		String fileName = Bris.PRIMARY_DRIVE.getName() + 
		Bris.SEPERATOR.getName() + 
		Bris.APP_FOLDER.getName() + 
		Bris.SEPERATOR.getName() + "bris-runner" + Bris.SEPERATOR.getName();
		
		fileName += BARANGAY + "-" + MUNICIPALITY;
		fileName += Bris.SEPERATOR.getName();
		fileName += CONFIG_FILE_NAME;
		
		Properties prop = new Properties();
		if("business-last-year".equalsIgnoreCase(key)) {
				
				try {
					prop.load(new FileInputStream(fileName));
					
					String[] vals = prop.getProperty("business-last-year").split(",");
					for(String a : vals) {
						info.put(a.split("=")[0], Double.valueOf(a.split("=")[1]));
					}
					
					
				}catch(IOException e) {}
			
		}else if("business-this-year".equalsIgnoreCase(key)) {
			
			try {
				prop.load(new FileInputStream(fileName));
				
				String[] vals = prop.getProperty("business-this-year").split(",");
				for(String a : vals) {
					info.put(a.split("=")[0], Double.valueOf(a.split("=")[1]));
				}
				
				
			}catch(IOException e) {}
		
		}else if("business-new".equalsIgnoreCase(key)) {
			
			try {
				prop.load(new FileInputStream(fileName));
				
				String[] vals = prop.getProperty("business-new").split(",");
				for(String a : vals) {
					info.put(a.split("=")[0], Double.valueOf(a.split("=")[1]));
				}
				
				
			}catch(IOException e) {}
		
		}else if("business-renew".equalsIgnoreCase(key)) {
			
			try {
				prop.load(new FileInputStream(fileName));
				
				String[] vals = prop.getProperty("business-renew").split(",");
				for(String a : vals) {
					info.put(a.split("=")[0], Double.valueOf(a.split("=")[1]));
				}
				
				
			}catch(IOException e) {}
		
		}else if("business-added".equalsIgnoreCase(key)) {
			
			try {
				prop.load(new FileInputStream(fileName));
				
				String[] vals = prop.getProperty("business-added").split(",");
				for(String a : vals) {
					info.put(a.split("=")[0], Double.valueOf(a.split("=")[1]));
				}
				
				
			}catch(IOException e) {}
		
		}
		
		Map<String, Double> sorted = new TreeMap<String, Double>(info);
		
		return sorted;
		
	}
	
}
