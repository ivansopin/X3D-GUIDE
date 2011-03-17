package org.ivansopin.x3dui;

import java.util.ArrayList;

public class Prototype {
	private String name;
	private String description;
	private String comment;
	private String group;
	private String path;
	
	private ArrayList<String> inheritedNames;
	private ArrayList<String> generatedNames;
	private ArrayList<String> attributeNames;
	private ArrayList<String> methodNames;
	
	private ArrayList<String> inheritingNames;
	
	private ArrayList<Prototype> inheritedPrototypes;
	private ArrayList<Prototype> generatedPrototypes;
	private ArrayList<Attribute> attributeObjects;
	private ArrayList<Method> methodObjects;

	private ArrayList<Prototype> inheritingPrototypes;
	
	public Prototype() {
		inheritedNames = new ArrayList<String>();
		generatedNames = new ArrayList<String>();
		attributeNames = new ArrayList<String>();
		methodNames = new ArrayList<String>();
		
		inheritingNames = new ArrayList<String>();
		
		inheritedPrototypes = new ArrayList<Prototype>();
		generatedPrototypes = new ArrayList<Prototype>();
		attributeObjects = new ArrayList<Attribute>();
		methodObjects = new ArrayList<Method>();
		
		inheritingPrototypes = new ArrayList<Prototype>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription(String path) {
		String text = description;
		text = DocumentationBuilder.linker.embedFieldReferences(text, getPath(), getName(), path);
		text = DocumentationBuilder.linker.embedProtoReferences(text, path);
		text = DocumentationBuilder.linker.embedGroupReferences(text, path);
		
		return text;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	public String getInheritedNameAt(int index) {
		String text = 
			"<a href=\"" + 
			Utils.getRelativePath(getInheritedAt(index).getPath(), getPath(), true) + 
			getInheritedAt(index).getName() + Settings.DEST_EXT + 
			"\">" +
			getInheritedAt(index).getName() + 
			"</a>";
		
		return text;
	}
	
	public String getInheritedNameAt(int index, boolean fromLinker) {
		return inheritedNames.get(index);
	}
	
	public void setInheritedName(int index, String inherited) {
		this.inheritedNames.set(index, inherited);
	}

	public void addInheritedName(String inherited) {
		this.inheritedNames.add(inherited);
	}
	
	public int getNumOfInherited() {
		return inheritedNames.size();
	}
	
	public Prototype getInheritedAt(int index) {
		return inheritedPrototypes.get(index);
	}

	public void addInherited(Prototype inherited) {
		this.inheritedPrototypes.add(inherited);
	}

	
	public String getGeneratedNameAt(int index) {
		return generatedNames.get(index);
	}
	
	public void addGeneratedName(String generated) {
		this.generatedNames.add(generated);
	}
	
	public int getNumOfGenerated() {
		return generatedNames.size();
	}
	
	public Prototype getGeneratedAt(int index) {
		return generatedPrototypes.get(index);
	}

	public void addGenerated(Prototype generated) {
		this.generatedPrototypes.add(generated);
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
	
	
	public String getInheritingNameAt(int index) {
		String text = 
			"<a href=\"" + 
			Utils.getRelativePath(getInheritingAt(index).getPath(), getPath(), true) + 
			getInheritingAt(index).getName() + Settings.DEST_EXT + 
			"\">" +
			getInheritingAt(index).getName() + 
			"</a>";
		
		return text;
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
	
	public Prototype getInheritingAt(int index) {
		return inheritingPrototypes.get(index);
	}
	
	public void addInheriting(Prototype inheriting) {
		this.inheritingPrototypes.add(inheriting);
	}
}