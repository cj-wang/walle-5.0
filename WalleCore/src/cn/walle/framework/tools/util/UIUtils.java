package cn.walle.framework.tools.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JOptionPane;

public class UIUtils {
	
	private static Component currentWindow;
	
	public static void setCurrentWindow(Component currentWindow) {
		UIUtils.currentWindow = currentWindow;
	}
	
	public static Component getCurrentWindow() {
		return currentWindow;
	}
	
	public static void showInformation(String title, String message) {
		JOptionPane.showMessageDialog(currentWindow, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showError(String title, String message) {
		JOptionPane.showMessageDialog(currentWindow, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showError(String title, Throwable throwable) {
		JOptionPane.showMessageDialog(currentWindow, throwable, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static int showConfirmYesNo(String title, String message) {
		return JOptionPane.showConfirmDialog(currentWindow, message, title, JOptionPane.YES_NO_OPTION);
	}
	
	public static int showConfirmYesNoCancel(String title, String message) {
		return JOptionPane.showConfirmDialog(currentWindow, message, title, JOptionPane.YES_NO_CANCEL_OPTION);
	}
	
	public static void locateInScreenCenter(Window window) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension windowSize = window.getSize();
		window.setLocation(
				(screenSize.width - windowSize.width) / 2,
				(screenSize.height - windowSize.height) / 2);
	}
	
	public static void locateInOwnerCenter(Window window) {
		Window owner = window.getOwner();
		if (owner == null) {
			locateInScreenCenter(window);
		} else {
			Dimension ownerSize = owner.getSize();
			Dimension windowSize = window.getSize();
			window.setLocation(
					owner.getX() + (ownerSize.width - windowSize.width) / 2,
					owner.getY() + (ownerSize.height - windowSize.height) / 2);
		}
	}
	
}
