package org.ivansopin.x3dui;

public class Utils {	
	static String getRelativePath(String pathFromRoot, String currentDir, boolean endWithSlash) {
		int size1 = pathFromRoot.length();
		int size2 = currentDir.length();
		
		if (size1 == 0 || size2 == 0) {
			return "";
		}
		
		if (!pathFromRoot.startsWith(Settings.FILE_SYSTEM_PREFIX) || !currentDir.startsWith(Settings.FILE_SYSTEM_PREFIX)) {
			return "";
		}
		
		if (pathFromRoot.substring(size1 - 1).equals("/")) {
			pathFromRoot = pathFromRoot.substring(0, size1 - 1);
		}
		
		if (currentDir.substring(size2 - 1).equals("/")) {
			currentDir = currentDir.substring(0, size2 - 1);
		}
		
		if (pathFromRoot.equals(currentDir)) {
			return "";
		}
		
		String[] pathFromRootFolders = pathFromRoot.split("/");
		String[] currentDirFolders = currentDir.split("/");
		
		int count1 = pathFromRootFolders.length;
		int count2 = currentDirFolders.length;
		
		int count = (count1 > count2 ? count1 : count2);
		
		String commonPath = "";
		String additionalPath = "";
		
		boolean same = true;
		int currentLevel = 0;
		int commonLevels = 0;
		
		for (int i = 1; i < count; i++) {
			if (i < count1) {
				if (i < count2) {
					if (same && pathFromRootFolders[i].equals(currentDirFolders[i])) {
						commonPath += ("/" + pathFromRootFolders[i]);
						commonLevels++;
					} else {
						additionalPath += ("/" + pathFromRootFolders[i]);
						same = false;
					}
				} else {
					additionalPath += ("/" + pathFromRootFolders[i]);
				}
			}
			
			if (i < count2) {
				currentLevel++;
			}
		}
		
		if (!additionalPath.equals("")) {
			additionalPath = additionalPath.substring(1);
		}
		
		String path = "";

		count = currentLevel > commonLevels ? (currentLevel - commonLevels) : 0;
		
		for (int i = 0; i < count; i++) {
			path += "../";
		}
		
		path += additionalPath;
		
		int size = path.length();
		
		if (endWithSlash) {
			if (!path.substring(size - 1).equals("/")) {
				path += "/";
			}
		} else {
			if (path.substring(size - 1).equals("/")) {
				path = path.substring(0, size - 1);
			}
		}

		return path;
	}

	static void print(String text) {
		if (Settings.LOG) System.out.println(text);
	}
	
	static void print(int text) {
		print(text + "");
	}
	
	static void print(boolean text) {
		print(text + "");
	}
}
