package cn.walle.framework.tools.base.treemenu;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import cn.walle.framework.tools.base.BasePanel;

public class TreeNode extends DefaultMutableTreeNode {
	
	private String name;
	
	private Class<? extends BasePanel> panelClass;
	
	private Object[] parameters;
	
	private TreeNodeLoader childrenLoader;
	
	private boolean childrenLoaded = false;
	
	public TreeNode(String name, TreeNodeLoader childrenLoader, 
			Class<? extends BasePanel> panelClass, Object... parameters) {
		super(name);
		this.name = name;
		this.childrenLoader = childrenLoader;
		this.panelClass = panelClass;
		this.parameters = parameters;
		if (childrenLoader != null) {
			this.add(new TreeNode("loading...", null, null));
		}
	}

	public void add(MutableTreeNode newChild) {
		if (newChild instanceof TreeNode) {
			super.add(newChild);
		} else {
			throw new IllegalArgumentException("Child node must be of type cn.walle.framework.tools.base.treemenu.TreeNode");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<? extends BasePanel> getPanelClass() {
		return panelClass;
	}

	public void setPanelClass(Class<? extends BasePanel> panelClass) {
		this.panelClass = panelClass;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object... parameters) {
		this.parameters = parameters;
	}

	public TreeNodeLoader getChildrenLoader() {
		return childrenLoader;
	}

	public void setChildrenLoader(TreeNodeLoader childrenLoader) {
		this.childrenLoader = childrenLoader;
	}

	public boolean isChildrenLoaded() {
		return childrenLoaded;
	}

	public void setChildrenLoaded(boolean childrenLoaded) {
		this.childrenLoaded = childrenLoaded;
	}

}

