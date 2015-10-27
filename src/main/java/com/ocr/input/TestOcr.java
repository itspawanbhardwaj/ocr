package com.ocr.input;

import java.awt.Image;
import java.awt.Toolkit;

public class TestOcr {

	public static void main(String[] args) {
		Image image = Toolkit.getDefaultToolkit().getImage(
				"/home/ideata/setup/pawan/ocr/image/AA.jpg");
		ocr o = new ocr();
		o.makearray(image, 256, 256, 0, 0);
	}
}
