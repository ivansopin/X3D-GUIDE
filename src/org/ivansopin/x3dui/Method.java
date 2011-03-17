package org.ivansopin.x3dui;

public class Method {
	private String name;
	private String description;
	private String type;
	private String accessType;
	
	public Method(String name, String description, String type, String accessType) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.accessType = accessType;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription(String path, String pathFromRoot, String protoName) {
		String text = description;
		text = DocumentationBuilder.linker.embedFieldReferences(text, pathFromRoot, protoName, path);
		text = DocumentationBuilder.linker.embedProtoReferences(text, path);
		text = DocumentationBuilder.linker.embedGroupReferences(text, path);
		
		return text;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getAccessType() {
		return accessType;
	}
	
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}	
}
