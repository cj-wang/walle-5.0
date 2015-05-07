package cn.walle.framework.tools;

import cn.walle.framework.core.support.SystemConfig;
import cn.walle.framework.tools.base.treemenu.TreeMenu;
import cn.walle.framework.tools.base.treemenu.TreeNode;
import cn.walle.framework.tools.fieldDefinitions.FieldDefinitionsNodeLoader;
import cn.walle.framework.tools.generator.CrudGeneratorPanel;
import cn.walle.framework.tools.generator.PageGeneratorPanel;
import cn.walle.framework.tools.generator.QueryGeneratorPanel;
import cn.walle.framework.tools.generator.UpdateGeneratorPanel;
import cn.walle.framework.tools.testclient.TestClientNodeLoader;

public class MainMenu extends TreeMenu {

	public MainMenu() {
		super(new TreeNode("Walle Tools - " + SystemConfig.PROJECT_NAME, null, null));
		
		TreeNode generatorNode = new TreeNode("Code Generator", null, null);
		generatorNode.add(new TreeNode("CRUD Generator", null, CrudGeneratorPanel.class));
		generatorNode.add(new TreeNode("Query Generator", null, QueryGeneratorPanel.class));
		generatorNode.add(new TreeNode("Update Generator", null, UpdateGeneratorPanel.class));
//		generatorNode.add(new TreeNode("Page Generator", null, PageGeneratorPanel.class));
		this.getRootNode().add(generatorNode);
		
		TreeNode fieldDefinitionsTreeNode = new TreeNode("Field Definitions", new FieldDefinitionsNodeLoader(), null);
		this.getRootNode().add(fieldDefinitionsTreeNode);
		
		TreeNode testClientTreeNode = new TreeNode("Test Client", new TestClientNodeLoader(), null);
		this.getRootNode().add(testClientTreeNode);
	}
	
}
