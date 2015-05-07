package cn.walle.framework.tools.base;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JTabbedPane;

public class TabbedEditPanel extends BasePanel {
	private JTabbedPane jTabbedPane;

	public TabbedEditPanel() {
		initGUI();
	}
	
	public void addEditPanel(BasePanel editPanel) {
		jTabbedPane.addTab(editPanel.getTitle(), editPanel);
	}
	
	public boolean init() {
		boolean result = true;
		Component[] componets = jTabbedPane.getComponents();
		for (int i = 0; i < componets.length; i++) {
			if (componets[i] instanceof BasePanel) {
				result = result && ((BasePanel) componets[i]).init();
			}
		}
		return result;
	}
	
	public boolean refresh() {
		boolean result = true;
		Component[] componets = jTabbedPane.getComponents();
		for (int i = 0; i < componets.length; i++) {
			if (componets[i] instanceof BasePanel) {
				result = result && ((BasePanel) componets[i]).refresh();
			}
		}
		return result;
	}
	
	public boolean save() {
		boolean result = true;
		Component[] componets = jTabbedPane.getComponents();
		for (int i = 0; i < componets.length; i++) {
			if (componets[i] instanceof BasePanel) {
				result = result && ((BasePanel) componets[i]).save();
			}
		}
		return result;
	}
	
	public boolean cancel() {
		boolean result = true;
		Component[] componets = jTabbedPane.getComponents();
		for (int i = 0; i < componets.length; i++) {
			if (componets[i] instanceof BasePanel) {
				result = result && ((BasePanel) componets[i]).cancel();
			}
		}
		return result;
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			{
				jTabbedPane = new JTabbedPane();
				this.add(jTabbedPane, BorderLayout.CENTER);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
