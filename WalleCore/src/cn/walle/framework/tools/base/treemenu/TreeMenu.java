package cn.walle.framework.tools.base.treemenu;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.LogFactory;

import cn.walle.framework.tools.base.BasePanel;


public class TreeMenu extends JTree {
	
	private TreeNode rootNode;
	
	private JPanel operatePanel;
	
	private File pathFile = new File("TreeMenuPath");
	
	private Map<Class<? extends BasePanel>, BasePanel> panels = new HashMap<Class<? extends BasePanel>, BasePanel>();
	
	public TreeMenu(TreeNode rootNode) {
		super(rootNode);
		this.rootNode = rootNode;
		
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		this.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent evt) {
				rootTreeValueChanged(evt);
			}
		});

		this.addTreeWillExpandListener(new TreeWillExpandListener() {
			public void treeWillExpand(TreeExpansionEvent evt) {
				rootTreeWillExpand(evt);
			}
			public void treeWillCollapse(TreeExpansionEvent evt) {
			}
		});

	}
	
	private BasePanel getPanel(Class<? extends BasePanel> clazz) {
		if (panels.containsKey(clazz)) {
			return panels.get(clazz);
		} else {
			try {
				BasePanel panel = clazz.newInstance();
				panel.init();
				panels.put(clazz, panel);
				return panel;
			} catch (Exception ex) {
				LogFactory.getLog(getClass()).error("Error", ex);
				return new BasePanel();
			}
		}
	}
	
	private void rootTreeValueChanged(TreeSelectionEvent evt) {
		if (operatePanel == null) {
			return;
		}
		operatePanel.removeAll();
		TreePath treePath = getSelectionPath();
		if (treePath == null) {
			return;
		}
		TreeNode treeNode = (TreeNode) treePath.getLastPathComponent();
		Class<? extends BasePanel> panelClass = treeNode.getPanelClass();
		if (panelClass != null) {
			BasePanel panel = getPanel(panelClass);
			Object[] parameters = treeNode.getParameters();
			if (parameters != null) {
				panel.setParameters(parameters);
			}
			panel.refresh();
			operatePanel.add(panel, BorderLayout.CENTER);
			panel.requestFocus();
		}
		operatePanel.validate();
		operatePanel.repaint();
	}
	
	private void rootTreeWillExpand(TreeExpansionEvent evt) {
		final TreeNode treeNode = (TreeNode) evt.getPath().getLastPathComponent();
		if (treeNode.isChildrenLoaded()) {
			return;
		}
		final TreeNodeLoader childrenLoader = treeNode.getChildrenLoader();
		if (childrenLoader != null) {
			new Thread() {
				public void run() {
					try {
						synchronized (treeNode) {
							childrenLoader.loadChildren(treeNode);
							((DefaultTreeModel) getModel()).reload(treeNode);
							treeNode.setChildrenLoaded(true);
							treeNode.notifyAll();
						}
					} catch (Exception ex) {
						LogFactory.getLog(getClass()).error("Error", ex);
					}
				}
			}.start();
		}
	}
	
	public void refresh() {
		refresh(getRootNode());
	}
	
	public void refresh(final TreeNode treeNode) {
		if (treeNode.getChildrenLoader() != null) {
			treeNode.removeAllChildren();
			treeNode.add(new TreeNode("loading...", null, null));
			treeNode.setChildrenLoaded(false);
			((DefaultTreeModel) getModel()).reload(treeNode);
			if (isExpanded(new TreePath(treeNode.getPath()))) {
				new Thread() {
					public void run() {
						try {
							synchronized (treeNode) {
								treeNode.getChildrenLoader().loadChildren(treeNode);
								((DefaultTreeModel) getModel()).reload(treeNode);
								treeNode.setChildrenLoaded(true);
								treeNode.notifyAll();
							}
						} catch (Exception ex) {
							LogFactory.getLog(getClass()).error("Error", ex);
						}
					}
				}.start();
			}
		} else {
			for (int i = 0; i < treeNode.getChildCount(); i++) {
				refresh((TreeNode) treeNode.getChildAt(i));
			}
		}
	}
	
	public void refreshPanels() {
		panels.clear();
	}

	public void saveTreePath() throws Exception {
		TreePath selectionPath = getSelectionPath();
		Object[] treePath = selectionPath == null ? new Object[0] : selectionPath.getPath();
		List<String> nodeNames = new ArrayList<String>();
		for (Object treeNodeObject : treePath) {
			nodeNames.add(((TreeNode) treeNodeObject).getName());
		}
		
		FileUtils.writeLines(pathFile, nodeNames);
	}
	
	public void loadTreePath() throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				setSelectionRow(0);
				expandRow(0);
			}
		});
		
		if (! pathFile.exists()) {
			return;
		}
		List<String> nodeNames = FileUtils.readLines(pathFile);
		
		TreeNode treeNode = getRootNode();
		List<TreeNode> pathNodes = new ArrayList<TreeNode>();
		for (String nodeName : nodeNames) {
			boolean found = false;
			if (pathNodes.size() == 0) {
				if (nodeName.equals(treeNode.getName())) {
					pathNodes.add(treeNode);
					found = true;
				}
			} else {
				for (int i = 0; i < treeNode.getChildCount(); i++) {
					TreeNode childTreeNode = (TreeNode) treeNode.getChildAt(i);
					if (nodeName.equals(childTreeNode.getName())) {
						treeNode = childTreeNode;
						pathNodes.add(treeNode);
						found = true;
						break;
					}
				}
			}
			if (found) {
				final TreePath path = new TreePath(pathNodes.toArray());
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						expandPath(path);
						setSelectionPath(path);
						scrollPathToVisible(path);
					}
				});
				if (! treeNode.isChildrenLoaded() && treeNode.getChildrenLoader() != null) {
					synchronized (treeNode) {
						while (! treeNode.isChildrenLoaded()) {
							try {
								treeNode.wait();
							} catch (InterruptedException ex) {
							}
						}
					}
				}
				if (! path.equals(getSelectionPath())) {
					break;
				}
			} else {
				break;
			}
		}
	}

	public TreeNode getRootNode() {
		return rootNode;
	}

	public JPanel getOperatePanel() {
		return operatePanel;
	}

	public void setOperatePanel(JPanel operatePanel) {
		operatePanel.setLayout(new BorderLayout());
		this.operatePanel = operatePanel;
	}

	public Collection<BasePanel> getPanels() {
		return panels.values();
	}

	public void setPathFileName(String pathFileName) {
		this.pathFile = new File(pathFileName);
	}
	
}
