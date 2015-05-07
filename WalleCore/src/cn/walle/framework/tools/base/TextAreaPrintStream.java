package cn.walle.framework.tools.base;

import java.io.PrintStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class TextAreaPrintStream extends PrintStream {
	
	private JTextArea jTextArea;
	
	public TextAreaPrintStream(JTextArea jTextArea) {
		super(System.out);
		this.jTextArea = jTextArea;
	}
	
	public void write(byte[] buf, int off, int len) {
		final String message = new String(buf, off, len);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jTextArea.append(message);
				jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
			}
		});
	}

}
