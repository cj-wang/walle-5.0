package cn.walle.framework.tools.fieldDefinitions;

import java.io.File;
import java.util.List;
import java.util.TreeMap;

import cn.walle.framework.core.support.SystemConfig;
import cn.walle.framework.core.util.ClassUtils;
import cn.walle.framework.core.util.ContextUtils;
import cn.walle.framework.tools.base.treemenu.TreeNode;
import cn.walle.framework.tools.base.treemenu.TreeNodeLoader;

public class FieldDefinitionsNodeLoader implements TreeNodeLoader {

	public void loadChildren(TreeNode treeNode) throws Exception {
		
		ContextUtils.getApplicationContext();
		
		File baseDirFile = new File(SystemConfig.CLASS_DIR);
		String baseDirFileName = baseDirFile.getAbsolutePath();
		int baseDirFileNameLength = baseDirFileName.length() + 1;
		
		TreeMap<String, TreeMap<String, File>> fieldDefinitionXmlFiles = new TreeMap<String, TreeMap<String, File>>();
		
		List<File> files = ClassUtils.getFieldDefinitionXmlFiles();
		for (File file : files) {
			String dirName = file.getParent();
			dirName = dirName.substring(baseDirFileNameLength);
			String packageName = dirName.replace('/', '.').replace('\\', '.');
			TreeMap<String, File> currentPackageFiles;
			if (fieldDefinitionXmlFiles.containsKey(packageName)) {
				currentPackageFiles = fieldDefinitionXmlFiles.get(packageName);
			} else {
				currentPackageFiles = new TreeMap<String, File>();
				fieldDefinitionXmlFiles.put(packageName, currentPackageFiles);
			}
			currentPackageFiles.put(file.getName(), file);
		}
		
		treeNode.removeAllChildren();
		for (String packageName : fieldDefinitionXmlFiles.keySet()) {
			TreeNode packageNode = new TreeNode(packageName, null, null);
			TreeMap<String, File> currentPackageFiles = fieldDefinitionXmlFiles.get(packageName);
			for (String fileName : currentPackageFiles.keySet()) {
				TreeNode fileNode = new TreeNode(fileName, null, FieldDefinitionsPanel.class, packageName, currentPackageFiles.get(fileName));
				packageNode.add(fileNode);
			}
			treeNode.add(packageNode);
		}
		
	}

}
