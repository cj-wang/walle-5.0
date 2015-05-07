package cn.walle.framework.tools;

import cn.walle.framework.tools.base.MainFrame;

public class Main {

	public static void main(String[] args) {

		try {
			
			new MainFrame(new MainPanel());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
