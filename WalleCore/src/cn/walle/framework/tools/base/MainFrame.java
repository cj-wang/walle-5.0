package cn.walle.framework.tools.base;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.WindowConstants;

import cn.walle.framework.tools.util.UIUtils;

public class MainFrame extends javax.swing.JFrame {
	private BasePanel mainPanel;

	public MainFrame(BasePanel mainPanel) {
		setTitle(mainPanel.getTitle());
		this.mainPanel = mainPanel;
		initGUI();
		UIUtils.locateInScreenCenter(this);
		UIUtils.setCurrentWindow(this);
		setVisible(true);
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent evt) {
					rootWindowClosed(evt);
				}
				public void windowOpened(WindowEvent evt) {
					rootWindowOpened(evt);
				}
			});
			{
				getContentPane().add(mainPanel, BorderLayout.CENTER);
			}
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void rootWindowOpened(WindowEvent evt) {
		mainPanel.init();
	}

	private void rootWindowClosed(WindowEvent evt) {
		mainPanel.close();
		System.exit(0);
	}
	
}
