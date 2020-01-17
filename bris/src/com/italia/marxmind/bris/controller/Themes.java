package com.italia.marxmind.bris.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.italia.marxmind.bris.enm.Bris;

/**
 * 
 * @author mark italia
 * @since 09/09/2017
 * @version 1.0
 *
 */

public class Themes {

	private int id;
	private String styleName;
	
	private static final String THEMES_FILE = Bris.PRIMARY_DRIVE.getName() + Bris.SEPERATOR.getName() + 
			Bris.APP_FOLDER.getName() + Bris.SEPERATOR.getName() +
			Bris.APP_CONFIG_FLDR.getName() + Bris.SEPERATOR.getName() +
			Bris.THEMES.getName();
	
	private static final String APPLICATION_FILE = Bris.PRIMARY_DRIVE.getName() + Bris.SEPERATOR.getName() + 
			Bris.APP_FOLDER.getName() + Bris.SEPERATOR.getName() +
			Bris.APP_CONFIG_FLDR.getName() + Bris.SEPERATOR.getName() +
			Bris.APP_CONFIG_FILE.getName();
	
	 public static void updateThemes(Bris[] tag, String[] value){
			
			try{
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(APPLICATION_FILE);
			
			// Get the root element
			Node license = doc.getFirstChild();
			
			// Get the module element by tag name directly
			Node module = doc.getElementsByTagName("databaseDetails").item(0);
			
			// loop the module child node
			NodeList list = module.getChildNodes();
			
			for (int i = 0; i < list.getLength(); i++) {
		
		            Node node = list.item(i);
				
					   // get the salary element, and update the value
		            //if(i<=tag.length){
			            for(int x=0; x<tag.length; x++){
						   if (tag[x].getName().equals(node.getNodeName())) {
							node.setTextContent(value[x]);
							//System.out.println("setting " + node.getNodeName() + " : " + value[x]);
						   }
			            }   
		            //}
				}
			
			
			// write the content into xml file
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(new File(APPLICATION_FILE));
					transformer.transform(source, result);

					System.out.println("Done");

			
			} catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			   } catch (TransformerException tfe) {
				tfe.printStackTrace();
			   } catch (IOException ioe) {
				ioe.printStackTrace();
			   } catch (SAXException sae) {
				sae.printStackTrace();
			   }
		}
	
	public static List<Themes> readThemesXML(){
    	List<Themes> themes = Collections.synchronizedList(new ArrayList<Themes>());
    	try {
            File fXmlFile = new File(THEMES_FILE);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile); 
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize(); 
            NodeList nList = doc.getElementsByTagName("style");
            //System.out.println("----------------------------");

                for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
                    //System.out.println("\nCurrent Element :" + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            
            Element eElement = (Element) nNode; 
            	
            	Themes theme = new Themes();
            	theme.setId(Integer.valueOf(eElement.getAttribute("id")));
            	theme.setStyleName(eElement.getElementsByTagName("name").item(0).getTextContent());
            	
            	themes.add(theme);
                }
            }
           } catch (Exception e) {
            e.printStackTrace();
           }
    	return themes;
    }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStyleName() {
		return styleName;
	}
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}
	
}
