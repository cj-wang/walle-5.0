package cn.walle.framework.tools.base;

import javax.swing.JPanel;

public class BasePanel extends JPanel {
	
	protected String title;
	
	protected Object[] parameters;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object... parameters) {
		this.parameters = parameters;
	}

	public boolean init() {
		return true;
	}
	
	public boolean refresh() {
		return true;
	}
	
	public boolean save() {
		return true;
	}
	
	public boolean cancel() {
		return true;
	}
	
	public boolean close() {
		return true;
	}
}
