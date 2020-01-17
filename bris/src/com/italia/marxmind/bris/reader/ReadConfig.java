package com.italia.marxmind.bris.reader;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import com.italia.marxmind.bris.enm.Bris;

/**
 * 
 * @author mark italia
 * @since 9/27/2016
 * Description: This class is use for reading the configuration file of the application
 *
 */
public class ReadConfig {

	private static final String APPLICATION_FILE = Bris.PRIMARY_DRIVE.getName() + 
			Bris.SEPERATOR.getName() + 
			Bris.APP_FOLDER.getName() + 
			Bris.SEPERATOR.getName() + 
			Bris.APP_CONFIG_FLDR.getName() + 
			Bris.SEPERATOR.getName() +
			Bris.APP_CONFIG_FILE.getName();
	
	
	public static String value(Bris bris){
		
		File xmlFile = new File(APPLICATION_FILE);
			
			try {
				if(xmlFile.exists()){
					SAXReader reader = new SAXReader();
					Document document = reader.read(xmlFile);
					Node node = document.selectSingleNode("/databaseDetails/" + bris.getName());
					return node.getStringValue();
				}
			}catch(Exception e) {}	
			
		return null;
	}
	
	public static void main(String[] args) {
		
		System.out.println(ReadConfig.value(Bris.DB_URL));
		
	}
}




















	
