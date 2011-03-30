package org.ivansopin.x3dui;

import java.util.ArrayList;

public class ExternPrototype {
	private String name;

	private ArrayList<String> attributeNames;
	private ArrayList<String> methodNames;
	
	private ArrayList<String> inheritingNames;
	
	private ArrayList<Attribute> attributeObjects;
	private ArrayList<Method> methodObjects;
	
	public ExternPrototype() {
		attributeNames = new ArrayList<String>();
		methodNames = new ArrayList<String>();
		
		inheritingNames = new ArrayList<String>();
		
		attributeObjects = new ArrayList<Attribute>();
		methodObjects = new ArrayList<Method>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getMethodNameAt(int index) {
		return methodNames.get(index);
	}

	public void addMethodName(String method) {
		this.methodNames.add(method);
	}
	
	public boolean hasMethod(String method) {
		return methodNames.contains(method);
	}
	
	public int getNumOfMethods() {
		return methodNames.size();
	}
	
	public Method getMethodAt(int index) {
		return methodObjects.get(index);
	}

	public void addMethod(Method method) {
		this.methodObjects.add(method);
	}
	
	
	public String getAttributeNameAt(int index) {
		return attributeNames.get(index);
	}

	public void addAttributeName(String attribute) {
		this.attributeNames.add(attribute);
	}
	
	public boolean hasAttribute(String attribute) {
		return attributeNames.contains(attribute);
	}
	
	public int getNumOfAttributes() {
		return attributeNames.size();
	}
	
	public Attribute getAttributeAt(int index) {
		return attributeObjects.get(index);
	}

	public void addAttribute(Attribute attribute) {
		this.attributeObjects.add(attribute);
	}

	public String getInheritingNameAt(int index, boolean fromLinker) {
		return inheritingNames.get(index);
	}
	
	public void setInheritingName(int index, String inheriting) {
		this.inheritingNames.set(index, inheriting);
	}

	public void addInheritingName(String inheriting) {
		this.inheritingNames.add(inheriting);
	}
	
	public int getNumOfInheriting() {
		return inheritingNames.size();
	}
}