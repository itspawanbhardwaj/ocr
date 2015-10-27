/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocr.ocr;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Initial extends java.applet.Applet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Image spirit, rolls, h;
	protected Image myOffScreenImage;
	protected Graphics myOffScreenGraphics;
	public int new_x = 550;
	public int new_y = 100;

	public void init() {
		spirit = getImage(getDocumentBase(), "spirit.jpg");
		rolls = getImage(getDocumentBase(), "rolls.jpg");
		myOffScreenImage = createImage(getSize().width, getSize().height);
		myOffScreenGraphics = myOffScreenImage.getGraphics();
		addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {
				System.out.println("what a drag");
				new_x = e.getX();
				new_y = e.getY();
				repaint();
			}

			public void mouseMoved(MouseEvent e) {
				// throw new
				// UnsupportedOperationException("Not supported yet.");
			}
		});
	}

	public void paint(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, getSize().width, getSize().height);
		g.drawImage(rolls, 5, 5, this);
		g.drawImage(spirit, new_x - 25, new_y - 25, this);
	}

	public void update(Graphics g) {
		paint(myOffScreenGraphics);
		g.drawImage(myOffScreenImage, 0, 0, this);
	}
}
