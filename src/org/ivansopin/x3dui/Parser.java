package org.ivansopin.x3dui;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parser {
	static Document parseXML(String x3d) throws Exception {
		Document doc = null;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;

		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(new ByteArrayInputStream(x3d.getBytes()));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			
			return null;
		} catch (SAXException e) {
			e.printStackTrace();
			
			return null;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();

			return null;
		}

		doc.getDocumentElement().normalize();
		
		return doc;
	}
	
	Prototype parseX3DFile(String x3d) throws Exception {
		Document document = parseXML(x3d);
		
		if (document == null) {
			return null;
		}

		Prototype prototype = new Prototype();
		Method method;
		Attribute attribute;
		
        String name = "";
   		String description = "";
   		String comment = "";
   		
   		String value = "";
   		String type = "";
   		String accessType = "";
   		String desc = "";
		
		NodeList x3dElements = document.getFirstChild().getChildNodes();
		NodeList sceneElements;
		NodeList prototypeElements;
		NodeList interfaceElements;
		
		Node x3dElement;
		Node sceneElement;
		Node prototypeElement;
		Node interfaceElement;
		
		Node valueNode;
		
		NamedNodeMap fieldAttributes;
		
		int a, b, c, d, e, f, g, h;
		int i, j, k, l, m, n, o, p;
		
		a = x3dElements.getLength();
				
		for (i = 0; i < a; i++) {
			x3dElement = x3dElements.item(i);
			
			if (x3dElement.getNodeName().equals("Scene")) {	    		
				sceneElements = x3dElement.getChildNodes();
    			
    			b = sceneElements.getLength();
    			
    			for (j = 0; j < b; j++) {
    				sceneElement = sceneElements.item(j);
    				
    				if (sceneElement.getNodeName().equals("ExternProtoDeclare")) {
    					prototype.addInheritedName(
    						sceneElement.getAttributes().getNamedItem("name").getNodeValue());
    				} else if (sceneElement.getNodeName().equals("#comment")) {
						description = sceneElement.getNodeValue();
    				} else if (sceneElement.getNodeName().equals("ProtoDeclare")) {
    					prototype.setName(
        					sceneElement.getAttributes().getNamedItem("name").getNodeValue());
    					prototype.setDescription(description);
    					
    					prototypeElements = sceneElement.getChildNodes();
    	    			
    	    			c = prototypeElements.getLength();
    	    			
    	    			for (k = 0; k < c; k++) {
    	    				prototypeElement = prototypeElements.item(k);
    	    				
    	    				if (prototypeElement.getNodeName().equals("ProtoInterface")) {
    	    					interfaceElements = prototypeElement.getChildNodes();
    	    					
    	    					d = interfaceElements.getLength();
    	    					
    	    					for (l = 0; l < d; l++) {
    	    						interfaceElement = interfaceElements.item(l);

    	    						if (interfaceElement.getNodeName().equals("#comment")) {
    	    							desc = interfaceElement.getNodeValue();
    	    						} else if (interfaceElement.getNodeName().equals("field")) {
    	    							fieldAttributes = interfaceElement.getAttributes();
    	    							
    	    							valueNode = fieldAttributes.getNamedItem("value");
    	    							
	    								name = fieldAttributes.getNamedItem("name").getNodeValue();
	    								type = fieldAttributes.getNamedItem("type").getNodeValue();
	    								accessType = fieldAttributes.getNamedItem("accessType").getNodeValue();
	    								
	    								// settings and children objects do not have an initial value
	    								if (name.equals("settings") || name.equals("children")) {
	    									prototype.addAttribute(
    	    									new Attribute(name, desc, "null", type, accessType)
    	    								);
        	    							
        	    							prototype.addAttributeName(name);
	    								} else if (valueNode != null) {
        	    							value = valueNode.getNodeValue();
    	    								
        	    							if (type.startsWith("M") ||
        	    								type.startsWith("SFVec") || 
        	    								type.startsWith("SFColor") || 
        	    								type.startsWith("SFRotation") ||
        	    								type.startsWith("SFMatrix")) {
        	    								
        	    								value = "[" + value + "]";
        	    							} else if (type.startsWith("SFString")) {
        	    								value = "\"" + value + "\"";
        	    							}
        	    							
        	    							prototype.addAttribute(
    	    									new Attribute(name, desc, value, type, accessType)
    	    								);
        	    							
        	    							prototype.addAttributeName(name);
    	    							} else {
    	    								prototype.addMethod(
    	    									new Method(name, desc, type, accessType)
    	    								);
    	    								
    	    								prototype.addMethodName(name);
    	    							}
    	    							
    	    							desc = "";
    	    						}
    	    					}
    	    				} else if (prototypeElement.getNodeName().equals("ProtoBody")) {
    	    					
    	    				}
    	    			}
    				} 
    			}
			}
		}

		return prototype;
	}
	
	public static ArrayList<ExternPrototype> parseLibraryFile(String x3d) throws Exception {
		Document document = parseXML(x3d);
		
		if (document == null) {
			return null;
		}
		
		ArrayList<ExternPrototype> externPrototypes = new ArrayList<ExternPrototype>();

		ExternPrototype externPrototype;
		
        String name = "";
   		String value = "";
   		String type = "";
   		String accessType = "";
		
		NodeList x3dElements = document.getFirstChild().getChildNodes();
		NodeList sceneElements;
		NodeList prototypeElements;
		NodeList interfaceElements;
		
		Node x3dElement;
		Node sceneElement;
		Node prototypeElement;
		Node interfaceElement;
		
		Node valueNode;
		
		NamedNodeMap fieldAttributes;
		
		int a, b, c, d;
		int i, j, k, l;
		
		a = x3dElements.getLength();

		for (i = 0; i < a; i++) {
			x3dElement = x3dElements.item(i);
			
			if (x3dElement.getNodeName().equals("Scene")) {	    		
				sceneElements = x3dElement.getChildNodes();

    			b = sceneElements.getLength();
    			
    			for (j = 0; j < b; j++) {
    				sceneElement = sceneElements.item(j);
    				
    				if (sceneElement.getNodeName().equals("ProtoDeclare")) {
    					externPrototype = new ExternPrototype();
    					
    					externPrototype.setName(
        					sceneElement.getAttributes().getNamedItem("name").getNodeValue());
    					
    					prototypeElements = sceneElement.getChildNodes();
    	    			
    	    			c = prototypeElements.getLength();
    	    			
    	    			for (k = 0; k < c; k++) {
    	    				prototypeElement = prototypeElements.item(k);
    	    				
    	    				if (prototypeElement.getNodeName().equals("ProtoInterface")) {
    	    					interfaceElements = prototypeElement.getChildNodes();
    	    					
    	    					d = interfaceElements.getLength();
    	    					
    	    					for (l = 0; l < d; l++) {
    	    						interfaceElement = interfaceElements.item(l);

    	    						if (interfaceElement.getNodeName().equals("field")) {
    	    							fieldAttributes = interfaceElement.getAttributes();
    	    							
    	    							valueNode = fieldAttributes.getNamedItem("value");
    	    							
	    								name = fieldAttributes.getNamedItem("name").getNodeValue();
	    								type = fieldAttributes.getNamedItem("type").getNodeValue();
	    								accessType = fieldAttributes.getNamedItem("accessType").getNodeValue();
	    								
	    								if (valueNode != null) {
        	    							externPrototype.addAttribute(
    	    									new Attribute(name, "", "", type, accessType)
    	    								);
        	    							
        	    							externPrototype.addAttributeName(name);
    	    							} else {
    	    								externPrototype.addMethod(
    	    									new Method(name, "", type, accessType)
    	    								);
    	    								
    	    								externPrototype.addMethodName(name);
    	    							}
    	    						}
    	    					}
    	    				}
    	    			}
    	    			
    	    			externPrototypes.add(externPrototype);
    				} 
    			}
			}
		}

		return externPrototypes;
	}
}
