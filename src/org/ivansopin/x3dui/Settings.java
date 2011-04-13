package org.ivansopin.x3dui;

public class Settings {
	static boolean LOG = true;
	
	static final String SRC_EXT = ".x3d";
	static final String DEST_EXT = ".html";
	
	static final String SRC_DIR = "D:/workspace/X3D GUI Library/x3dui/protos/";
	static final String DEST_DIR = "D:/workspace/X3D GUI Library/x3dui/documentation/";
	
	static final String[] FILE_LIST = new String[] {
		// from layout folder
		"layout/LayoutManager", "layout/BorderLayout", "layout/BoxLayout", "layout/FlowLayout", "layout/GridLayout",
		// from visual folder
		"visual/Rectangle", "visual/Layer", "visual/Plane", "visual/Label", "visual/Button", "visual/CheckBox", "visual/ComboBox", "visual/ControlButton", "visual/Frame", "visual/Panel", "visual/RadioButton", "visual/HorizontalRunner", "visual/HorizontalSlider", "visual/TabPanel", "visual/TaskBar", "visual/TextButton", "visual/TextField", "visual/ToggleButton", "visual/TextToggleButton",
		// from group folder
		"group/RadioButtonGroup",
		// from system folder
		"system/Settings", "system/Display"
	};
	
	static final String HTML_HEADER = 
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
		"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
		"<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">\n";
	static final String HTML_FOOTER = "</html>";

	static final String DOC_TITLE_PREFIX = "X3DUI Documentation : ";
	
	static final String FILE_SYSTEM_PREFIX = "D:/";
	
	
	static final String OVERVIEW_DESCRIPTION = 
		"Extensible 3D User Interface Library (X3DUI) is a wide range of cross-compatible X3D components, equipped with configurable appearance and behavior. " + 
		"With this library, we attempt to standardize the GUI construction across various X3D-driven projects and improve the reusability, compatibility, adaptability, readability, and flexibility of many existing applications."; 
}
