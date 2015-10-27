package com.ocr.database.write;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

/**
 * 
 * @author Windows 4
 */
public class Writer extends DBWriter {

	int j, k;
	int myimage[][];
	char m = 'A';
	String txt;

	@Override
	public void init() {
		// reader();
		while (m <= 90) {
			String jpg = m + ".jpg";
			String pg = m + jpg;
			System.out.println(pg);
			String text = m + ".txt";

			h = getImage(getDocumentBase(), pg);
			myOffScreenImage = createImage(getSize().width, getSize().height);
			try {
				h = makearray(h);
				// writer(m, myimage,txt);
			} catch (Exception e) {
				System.out.println(e);
			}
			m++;
		}
		m = 'a';

		while (m <= 122) {

			String pg = m + ".jpg";
			h = getImage(getDocumentBase(), pg);
			myOffScreenImage = createImage(getSize().width, getSize().height);
			try {
				h = makearray(h);
			} catch (Exception e) {
				System.out.println(e);
			}

			m++;
		}
	}

	int width = 256, height = 256;

	public Image getRidofPink(Image im) {
		try {
			int[] pgPixels = new int[width * height];
			// int myimage[][]=new int[8][8];
			PixelGrabber pg = new PixelGrabber(im, 0, 0, width, height,
					pgPixels, 0, width);
			if (pg.grabPixels() && ((pg.status() & ImageObserver.ALLBITS) != 0)) {
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						int i = y * width + x;
						int a = (pgPixels[i] & 0xff000000) >> 24;
						int r = (pgPixels[i] & 0x00ff0000) >> 16;
						int g = (pgPixels[i] & 0x0000ff00) >> 8;
						int b = (pgPixels[i] & 0x000000ff);
						if (r > 200 && g > 100 && g < 200 && b > 100 && b < 210) {
							a = 0;
							pgPixels[i] = a | (r << 16) | (g << 8) | b;
						}
					}
				}
				im = createImage(new MemoryImageSource(width, height, pgPixels,
						0, width));
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return im;
	}

	public void writer() throws Exception {
		int[][] image = new int[8][8];

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				image[i][j] = myimage[i][j];

			}
			dbwriter(image, m, j);
		}
	}

	public Image makearray(Image im) throws Exception {

		try {
			int[] pgPixels = new int[width * height];
			myimage = new int[8][8];
			PixelGrabber pg = new PixelGrabber(im, 0, 0, width, height,
					pgPixels, 0, width);
			if (pg.grabPixels() && ((pg.status() & ImageObserver.ALLBITS) != 0)) {
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						int i = y * width + x;
						int a = (pgPixels[i] & 0xff000000) >> 24;
						int r = (pgPixels[i] & 0x00ff0000) >> 16;
						int g = (pgPixels[i] & 0x0000ff00) >> 8;
						int b = (pgPixels[i] & 0x000000ff);
						if (r == 0 && r <= 30 && g == 0 && g <= 30 && b == 0
								&& b <= 30) {
							int a1 = x / 32;
							int a2 = y / 32;

							// System.out.println(" "+x+" "+y);
							myimage[a2][a1] = 1;

						}
					}
				}
				im = createImage(new MemoryImageSource(width, height, pgPixels,
						0, width));
				for (j = 0; j < 8; j++) {
					for (k = 0; k < 8; k++) {
						System.out.print(" " + myimage[j][k]);
					}
					dbwriter(myimage, m, j);
					System.out.println();
				}
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return im;

	}
}