package cn.walle.framework.tools.base;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.WindowConstants;

import cn.walle.framework.tools.util.UIUtils;

public class ModalDialog extends javax.swing.JDialog {
	private BasePanel editPanel;
	private JPanel jPanelControl;
	private JButton jButtonOK;
	private JButton jButtonCancel;
	private JSeparator jSeparator;
	private JPanel jPanelBottom;
	
	private ModalDialog(Dialog owner, BasePanel editPanel) {
		super(owner);
		setTitle(editPanel.getTitle());
		this.editPanel = editPanel;
		initGUI();
		UIUtils.locateInOwnerCenter(this);
		UIUtils.setCurrentWindow(this);
		jButtonOK.requestFocus();
		setVisible(true);
	}
	
	private ModalDialog(Frame owner, BasePanel editPanel) {
		super(owner);
		setTitle(editPanel.getTitle());
		this.editPanel = editPanel;
		initGUI();
		UIUtils.locateInOwnerCenter(this);
		UIUtils.setCurrentWindow(this);
		jButtonOK.requestFocus();
		setVisible(true);
	}
	
	public static ModalDialog newModalDialog(JComponent component, BasePanel editPanel)
			throws Exception {
		Container container = component.getTopLevelAncestor();
		if (container instanceof Dialog) {
			return new ModalDialog((Dialog) container, editPanel);
		} else if (container instanceof Frame) {
			return new ModalDialog((Frame) container, editPanel);
		} else {
			throw new Exception("Parent type not valid");
		}
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setModal(true);
			getContentPane().add(editPanel, BorderLayout.CENTER);
			{
				jPanelBottom = new JPanel();
				BorderLayout jPanelBottomLayout = new BorderLayout();
				getContentPane().add(jPanelBottom, BorderLayout.SOUTH);
				jPanelBottom.setLayout(jPanelBottomLayout);
				{
					jPanelControl = new JPanel();
					jPanelBottom.add(jPanelControl, BorderLayout.CENTER);
					{
						jButtonOK = new JButton();
						jPanelControl.add(jButtonOK);
						jButtonOK.setText("确定ȷ��");
						jButtonOK.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jButtonOKActionPerformed(evt);
							}
						});
					}
					{
						jButtonCancel = new JButton();
						jPanelControl.add(jButtonCancel);
						jButtonCancel.setText("取消ȡ��");
						jButtonCancel.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								jButtonCancelActionPerformed(evt);
							}
						});
					}
				}
				{
					jSeparator = new JSeparator();
					jPanelBottom.add(jSeparator, BorderLayout.NORTH);
				}
			}
			this.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent evt) {
					rootWindowClosed(evt);
				}
				public void windowOpened(WindowEvent evt) {
					rootWindowOpened(evt);
				}
			});
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void jButtonOKActionPerformed(ActionEvent evt) {
		if (editPanel.save()) {
			dispose();
		}
	}
	
	private void jButtonCancelActionPerformed(ActionEvent evt) {
		if (editPanel.cancel()) {
			dispose();
		}
	}
	
	private void rootWindowOpened(WindowEvent evt) {
		editPanel.init();
	}

	private void rootWindowClosed(WindowEvent evt) {
		editPanel.cancel();
		UIUtils.setCurrentWindow(this.getOwner());
	}

}
