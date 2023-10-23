package com.base;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLEditor {
	
	private String defaultFileName = "NoName";
	private String defaulXmlFileDirectory = "CollectedData\\";
	private String defaultRootElementName = "Products";
	
	public String fileName;
	public String xmlFileDirectory;
	public String xmlFilePath;
	public Document doc;
	public String rootElName;
	public Element rootElement;
	
	public XMLEditor () {
		
		this.fileName = defaultFileName;
		this.xmlFileDirectory = defaulXmlFileDirectory;
		this.rootElName = defaultRootElementName;
		
		
        try {
        	
        	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			this.doc = docBuilder.newDocument();
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        this.rootElement = doc.createElement(rootElName);
       
	}
	
	public void setFileName(String name) {
		this.fileName = name;
		
	}
	
	public void setFilePath () {
		this.xmlFilePath = xmlFileDirectory + fileName + ".xml";
	}
	
	public void setRootElementName (String name) {
		
		Node rootNode = this.doc.getElementsByTagName(this.rootElName).item(0);
		
		this.rootElName = name;
			
		this.doc.renameNode(rootNode, null, this.rootElName);
	}
	
	public void createFile (String name, boolean overwrite) {
		
		setFileName(name);
		setFilePath();
		
		try {
			
			if (overwrite && new File(xmlFilePath).isFile()) {
				
				new File(xmlFilePath).delete();
				new File(xmlFilePath).createNewFile();
					
			} else {
				new File(xmlFilePath).createNewFile();
			}		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
	
	public Element createItemElement (String itemName, String price) {
		
		Element itemEl = doc.createElement("Item");
		
		Element itemNameEl = doc.createElement("Name");
		itemNameEl.setTextContent(itemName);
		
		Element priceEl = doc.createElement("Price");
		priceEl.setAttribute("currency", "EUR");
		priceEl.setTextContent(price);
		
		itemEl.appendChild(itemNameEl);
		itemEl.appendChild(priceEl);
		
		return itemEl;
	}
	
	public void addItemToRootElement (Element itemEl) {
		this.rootElement.appendChild(itemEl);
	}
	
	public void writeToXMLFile () {
		
		this.doc.appendChild(this.rootElement);
		
		try (FileOutputStream output = new FileOutputStream(this.xmlFilePath)) {
			
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(this.doc);
        StreamResult result = new StreamResult(output);
        
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        
        transformer.transform(source, result);
        
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
	}
}