/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocr.ocr;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 * @author pawan
 */
public class showOutput extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3806393440790700107L;
	static JFrame frame = new JFrame();

	public void displayOutput(String st) {
		JOptionPane.showMessageDialog(frame, st);
	}

}
