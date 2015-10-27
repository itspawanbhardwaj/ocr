/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocr.input;

/**
 * 
 * @author pawan
 */
public class seg {
	final int LINES = 100;
	final int WORDS = 100;
	final int CHARS = 100;

	int line[] = new int[LINES];
	int wordarr[][] = new int[LINES][WORDS];
	int carr[][][] = new int[LINES][WORDS][CHARS];
	int tarr[][][] = new int[LINES][WORDS][CHARS];
	int larr[][][] = new int[LINES][WORDS][CHARS];

	seg() {

		for (int i = 0; i < LINES; i++) {
			line[i] = -1;

			for (int j = 0; j < WORDS; j++) {
				wordarr[i][j] = -1;
				for (int k = 0; k < CHARS; k++) {
					carr[i][j][k] = -1;
					tarr[i][j][k] = -1;
					larr[i][j][k] = -1;
				}
			}
		}
	}

	public int wordseg(int lineno, int w, int h, int vHisto[]) {

		int m = 0;
		int t = 0;
		while (t < w) {
			while (t < w && vHisto[t] == 0)
				t++;

			if (t >= w)
				break;
			wordarr[lineno][m] = t;
			// System.out.println("t=" + t + "m= " + m + "    " + vHisto[t]);
			m++;
			while (t < w && vHisto[t] > 0)
				t++;
			if (t >= w)
				break;
			wordarr[lineno][m] = t;
			// System.out.println("t=" + t + "m= " + m + "    " + vHisto[t]);
			m++;

		}
		wordarr[lineno][m] = -1;
		return m;
	}

	public int lineseg(int w, int h, int hHisto[]) {
		int m = 0;
		int t = 0;
		while (t <= h) {
			while (t < h && hHisto[t] == 0)
				t++;
			if (t >= h)
				break;
			// System.out.println("t=" + t + "m= " + m + "    " + hHisto[t]);
			line[m] = t;
			m++;
			while (t < h && hHisto[t] > 0)
				t++;
			if (t >= h)
				break;
			line[m] = t;
			// System.out.println("t=" + t + "m= " + m + "    " + hHisto[t]);
			m++;
		}
		line[m] = -1;
		return m;
	}

	public int hline(int ln, int wn, int w, int h, int hHisto[]) {
		if (wordarr[ln][2 * wn] == -1) {
			carr[ln][wn][0] = -1;
			return -1;
		}
		int hlinepos = 0;

		int max = 0;
		// PixelGrabber pg = new PixelGrabber
		// (img,ss[ln][0],line[0],w,h,pixels,0,w);

		for (int m = 0; m < h; m++)
			if (hHisto[m] >= max) {
				// System.out.println(m + " " + hHisto[m]);
				hlinepos = m;
				max = hHisto[m];
			}
		if (hlinepos >= 20 || hlinepos < 9) {
			// carr[ln][wn][0]=-1;
			hlinepos = 11;
		}
		return hlinepos;
	}

	public void ccharseg(int ln, int wn, int w, int h, int vHisto[]) { // System.out.println("hlinepos "+hlinepos);

		int m = 0;
		int t = 0;
		while (t < w) {
			while (t < w && vHisto[t] == 0)
				t++;

			if (t >= w)
				break;
			carr[ln][wn][m] = t - 1 + wordarr[ln][2 * wn];
			m++;
			while (t < w && vHisto[t] > 0)
				t++;
			if (t >= w)
				carr[ln][wn][m] = w - 1 + wordarr[ln][2 * wn];
			else
				carr[ln][wn][m] = t - 1 + wordarr[ln][2 * wn];
			m++;
		}
		carr[ln][wn][m] = -1;

	} // System.out.println(ln + " " + wn + " " + m);
		// for(int i = 0; i <m ; i++)
		// System.out.println(carr[ln][wn][i]);

	public void tcharseg(int ln, int wn, int w, int h, int vHisto[]) { // System.out.println("hlinepos "+hlinepos);
		int m = 0;
		int t = 0;
		while (t < w) {
			while (t < w && vHisto[t] == 0)
				t++;
			if (t >= w)
				break;
			tarr[ln][wn][m] = t - 1 + wordarr[ln][2 * wn];
			m++;
			while (t < w && vHisto[t] > 0)
				t++;
			if (t >= w)
				tarr[ln][wn][m] = w - 1 + wordarr[ln][2 * wn];
			else
				tarr[ln][wn][m] = t - 1 + wordarr[ln][2 * wn];
			m++;
		}
		tarr[ln][wn][m] = -1;

	}// tchar
}
