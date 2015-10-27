/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ocr.input;

/**
 *
 * @author pawan
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.ocr.database.reader.FileReadAll;
import com.ocr.database.reader.FileReadAll16;

public class ocr extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField m_fileNameTF = new JTextField(15);
	JFileChooser m_fileChooser = new JFileChooser();
	DrawingPanel imagepanel;
	JavaFilter fJavaFilter;
	int mouseX, mouseY, mx, my;
	Image img;
	int flag = 3;
	int hhh;
	int xarr[] = new int[40];
	int yarr[] = new int[40];
	int clear;
	int flag3 = 0;
	int flag4 = 0;
	int flag5 = 0;
	seg myseg = new seg();

	public static void main(String[] args) {
		ocr window = new ocr();
		window.setVisible(true);
	}

	// constructor
	ocr() {

		// set the Text field to Read Only mode
		m_fileNameTF.setEditable(false);

		// Choose only files, not directories
		m_fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		// Set filter for Java source files.
		fJavaFilter = new JavaFilter();
		m_fileChooser.setFileFilter(fJavaFilter);

		// ... create Button and its listeners
		JButton openButton = new JButton("Open");
		JButton lineButton = new JButton("Recognize 8x8");

		JButton wordButton = new JButton("Recognize 16x16");
		JButton charButton = new JButton("char segment");
		JButton clearButton = new JButton("clear");
		// setting tool tips for various buttons
		openButton.setToolTipText("click here to choose a file");
		lineButton.setToolTipText("click here for line segmentation");
		// wordButton.setToolTipText("click here for word segmentation");
		charButton.setToolTipText("click here for char segmentation");
		clearButton.setToolTipText("click here to clear the panel");
		// adding mouse listener to various buttons
		openButton.addActionListener(new OpenAction());
		lineButton.addActionListener(new LineAction());
		wordButton.addActionListener(new wordAction());
		charButton.addActionListener(new charAction());
		clearButton.addActionListener(new clearAction());
		// ... Create contant pane, layout components
		JPanel content = new JPanel();
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		JMenu helpmenu = new JMenu("Help ...Alt+H");
		helpmenu.setMnemonic('H');
		JMenuItem aboutopen = new JMenuItem("About open. A.");
		JMenuItem lineseg = new JMenuItem("Line segmentation   L");

		aboutopen.setMnemonic('A');
		lineseg.setMnemonic('L');
		aboutopen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"To load an image,click open");
			}
		});
		lineseg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								null,
								"To perform line segmentation:\n click Line segment.You may draw lines by clicking\n          You may delete lines by clicking on a particular line\n you may also drag lines");
			}
		});
		helpmenu.add(aboutopen);
		helpmenu.add(lineseg);

		content.setLayout(new BorderLayout());
		JPanel buttonpanel = new JPanel();
		buttonpanel.add(openButton);

		buttonpanel.add(m_fileNameTF);
		buttonpanel.add(lineButton);

		buttonpanel.add(wordButton);
		buttonpanel.add(charButton);
		buttonpanel.add(clearButton);
		content.add(helpmenu, "North");
		content.add(buttonpanel, "South");
		bar.add(helpmenu);

		// Create JPanel canvas to hold the picture
		imagepanel = new DrawingPanel();
		// imagepanel.addMouseListener(new MouseClickListener());

		// Create JScrollPane to hold the canvas containing the picture
		JScrollPane scroller = new JScrollPane(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroller.setPreferredSize(new Dimension(500, 300));
		scroller.setViewportView(imagepanel);
		scroller.setViewportBorder(BorderFactory.createLineBorder(Color.black));
		// Add scroller pane to Panel
		content.add(scroller, "Center");

		// Set window characteristics
		this.setTitle("File Browse and View");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(content);
		this.pack();
	} // ocr contructor

	class DrawingPanel extends JPanel {

		int x, y;

		public DrawingPanel() {

			setBackground(Color.white);
			setPreferredSize(new Dimension(750, 950));
			MouseClickListener listener = new MouseClickListener();
			addMouseListener(listener);
			Mouserelease listen = new Mouserelease();
			addMouseListener(listen);

		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (img != null && clear == 0) {
				g.drawImage(img, 0, 0, this);

			}

			if (flag == 0 && clear == 0) {
				int x = 0;
				while (myseg.line[x] != -1)

				{

					g.drawLine(0, myseg.line[x], img.getWidth(imagepanel),
							myseg.line[x]);
					x++;
				}

			}
			if (flag3 == 1) {
				for (int x = 0; x < 17; x++)

				{
					int y = 0;
					while (myseg.wordarr[x][y] != -1) {
						xarr[y] = myseg.wordarr[x][y];
						if (y % 2 == 0) {
							hhh = myseg.line[2 * x + 1] - myseg.line[2 * x];
						} else if (y != 1 && y % 2 == 1) {
							System.out.println(+y);
							yarr[y] = xarr[y] - xarr[y - 1];
						}
						// add scan image here
						g.drawLine(myseg.wordarr[x][y], myseg.line[2 * x],
								myseg.wordarr[x][y], myseg.line[2 * x + 1]);
						System.out.println("height  " + hhh + "  width "
								+ yarr[y]);
						// ScanImage image= new ScanImage();
						if (y != 0 && y % 2 == 0) {
							makearray(img, yarr[y], hhh, myseg.wordarr[x][y],
									myseg.line[2 * x]);
						}
						y++;

					}
					// makearray(img,myseg.wordarr[x][y],myseg.line[2*x],myseg.wordarr[x][y],myseg.line[2*x+1]);

				}

			}
			if (flag4 == 1) {
				int z;
				int x = 0;
				myseg.line[32] = -1;
				while (myseg.line[2 * x] != -1) {
					int y = 0;
					while (myseg.wordarr[x][2 * y] != -1) {
						z = 0;
						while (myseg.carr[x][y][2 * z] != -1) {
							// System.out.println(x + " " + y + " " + 2*z +
							// carr[x][y][2*z] + carr[x][y][2*z+1]);
							g.drawRect(myseg.carr[x][y][2 * z],
									myseg.line[2 * x] + 11,
									myseg.carr[x][y][2 * z + 1]
											- myseg.carr[x][y][2 * z],
									myseg.line[2 * x + 1] - myseg.line[2 * x]
											- 11);

							z++;
						}
						y++;
					}
					x++;
				}
			}
			if (flag5 == 1) {
				int z;
				int x = 0;
				myseg.line[32] = -1;
				while (myseg.line[2 * x] != -1) {
					int y = 0;
					while (myseg.wordarr[x][2 * y] != -1) {
						z = 0;
						while (myseg.carr[x][y][2 * z] != -1) {
							// System.out.println(x + " " + y + " " + 2*z +
							// carr[x][y][2*z] + carr[x][y][2*z+1]);
							g.drawRect(myseg.tarr[x][y][2 * z],
									myseg.line[2 * x],
									myseg.tarr[x][y][2 * z + 1]
											- myseg.tarr[x][y][2 * z], 11);
							z++;
						}
						y++;
					}
					x++;
				}
			}
		}

		public int linesegdriver() {
			int width = img.getWidth(imagepanel);
			int height = img.getHeight(imagepanel);
			int w = width;
			int h = height;
			int m = 0;

			int hHisto[] = new int[h];
			HHISTO(0, 0, w, h, hHisto);
			m = myseg.lineseg(w, h, hHisto);

			return m;
		}

		public int wordsegdriver(int lineno) {
			// System.out.println("In wordseg function");
			int w = img.getWidth(imagepanel);
			int h = myseg.line[2 * lineno + 1] - myseg.line[2 * lineno];
			int lnb = myseg.line[2 * lineno];
			int lne = myseg.line[2 * lineno + 1];
			int vHisto[] = new int[w + 1];
			int m = 0;
			VHISTO(0, lnb, w, h, vHisto);
			m = myseg.wordseg(lineno, w, h, vHisto);
			// PixelGrabber pg = new PixelGrabber (img,0,lnb,w,h, pixels,0,w);
			// PixelGrabber pg = new PixelGrabber (img,0,lnb,w,lne-lnb,
			// pixels,0,w);

			// class wordAction implements ActionListener
			return m;
		}

		public void charsegdriver(int ln, int wn) {
			if (myseg.wordarr[ln][2 * wn] == -1) {
				myseg.carr[ln][wn][0] = -1;
				return;
			}
			int w = myseg.wordarr[ln][2 * wn + 1] - myseg.wordarr[ln][2 * wn];
			int h = myseg.line[ln * 2 + 1] - myseg.line[ln * 2];

			int hHisto[] = new int[h];
			int hlinepos = -1;

			HHISTO(myseg.wordarr[ln][2 * wn], myseg.line[ln * 2], w, h, hHisto);
			hlinepos = myseg.hline(ln, wn, w, h, hHisto);

			// System.out.println("hlinepos "+hlinepos);
			flag3 = 1;
			flag4 = 1;
			flag5 = 1;

			h = myseg.line[ln * 2 + 1] - myseg.line[ln * 2] - hlinepos - 2;

			int vHisto[] = new int[w + 1];

			VHISTO(myseg.wordarr[ln][wn * 2],
					myseg.line[ln * 2] + hlinepos + 3, w, h, vHisto);
			myseg.ccharseg(ln, wn, w, h, vHisto);

			h = hlinepos - 2;
			VHISTO(myseg.wordarr[ln][wn * 2], myseg.line[ln * 2], w, h, vHisto);
			myseg.tcharseg(ln, wn, w, h, vHisto);
			// lcharseg(vHisto);

		}// char

		public void sethLine() {
			repaint();
		}

		public void setvLine(int lineno, int nowords) {
			flag3 = 1;
			repaint();
		}

		public void setRect(int lineno, int nowords) {
			flag4 = 1;
			flag3 = 0;
			flag5 = 1;
			repaint();
		}

		void VHISTO(int x, int y, int w, int h, int vHisto[]) {
			int pixels[] = new int[w * h];
			int pix[][] = new int[h][w];

			PixelGrabber pg = new PixelGrabber(img, x, y, w, h, pixels, 0, w);
			try {
				pg.grabPixels();
			} catch (InterruptedException ee) {
			}

			for (int t = 0; t < w; t++)
				vHisto[t] = 0;

			for (int i = 0; i < w * h; i++) {
				int p = pixels[i];

				int r = 0xff & (p >> 16);
				int g = 0xff & (p >> 8);
				int b = 0xff & (p);

				if (r >= 127 && g >= 127 && b >= 127)
					pixels[i] = 1;
				else
					pixels[i] = 0;

			}
			int j = 0;
			for (int m = 0; m < h; m++) {
				for (int n = 0; n < w; n++) {
					pix[m][n] = pixels[j];
					if (pix[m][n] == 0)
						vHisto[n] = vHisto[n] + 1;
					j++;
				}
			}
			// for(int m=0;m<w;m++)
			// System.out.println("vHisto= "+vHisto[m]);

			return;

		} // VHISTO

		void HHISTO(int x, int y, int w, int h, int hHisto[]) {
			int pixels[] = new int[w * h];
			int pix[][] = new int[h][w];

			PixelGrabber pg = new PixelGrabber(img, x, y, w, h, pixels, 0, w);
			try {
				pg.grabPixels();
			} catch (InterruptedException ee) {
			}

			for (int t = 0; t < h; t++)
				hHisto[t] = 0;

			for (int i = 0; i < w * h; i++) {
				int p = pixels[i];

				int r = 0xff & (p >> 16);
				int g = 0xff & (p >> 8);
				int b = 0xff & (p);

				if (r >= 127 && g >= 127 && b >= 127)
					pixels[i] = 1;
				else
					pixels[i] = 0;

			}
			int j = 0;
			for (int m = 0; m < h; m++) {
				for (int n = 0; n < w; n++) {
					pix[m][n] = pixels[j];
					if (pix[m][n] == 0)
						hHisto[m] = hHisto[m] + 1;
					j++;
				}
			}
			// for(int m=0;m<h;m++)
			// System.out.println("hHisto= "+hHisto[m]);

			return;

		} // HHISTO

	} // drawing panel

	class MouseClickListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();

			mx = mouseX;
			my = mouseY;

		}
	}

	class Mouserelease extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			imagepanel.repaint();
		}
	}

	// OpenAction
	class OpenAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			// ... Open a file dialog.
			clear = 0;
			int retval = m_fileChooser.showOpenDialog(ocr.this);
			if (retval == JFileChooser.APPROVE_OPTION) {
				// ... The user selected a file, process it.
				File file = m_fileChooser.getSelectedFile();
				// store the file path in a string and send to JNI
				String fPath = file.getPath();
				System.out.println("The Path is:\n" + fPath);
				// ... Update user interface.
				img = Toolkit.getDefaultToolkit().getImage(file.getPath());

				m_fileNameTF.setText(file.getName());
				imagepanel.repaint();
			}
		}
	}

	// this tries to build lines
	class LineAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int width = img.getWidth(imagepanel);
			int height = img.getHeight(imagepanel);
			flag = 0;
			// System.out.println("in line button");

			int nolines = imagepanel.linesegdriver();
			System.out.println(nolines);
			imagepanel.sethLine();
			makearray(img, width, height, 0, 0);

		}
	}

	class wordAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int width = img.getWidth(imagepanel);
			int height = img.getHeight(imagepanel);
			makearray16(img, width, height, 0, 0);
			// int nowords = 0;
			// //System.out.println("in wordseg");
			// for(int i = 0; i < 17; i++)
			// {
			// nowords = imagepanel.wordsegdriver(i);
			// System.out.println("line" + i + "nowords" + nowords);
			// imagepanel.setvLine(i, nowords);
			// }
			// return;
		}

	}

	class charAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < 17; i++) {
				int j = 0;
				while (myseg.wordarr[i][j * 2] != -1) {
					// System.out.println("in charbox");
					imagepanel.charsegdriver(i, j);
					imagepanel.setRect(i, j);
					j++;
				}
			}
			return;
		}
	}

	class clearAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			imagepanel.removeAll();
			imagepanel.revalidate();
			// System.out.println("in clear button");
			clear = 1;
			flag3 = 0;
			flag = 1;
			imagepanel.repaint();
		}
	}

	public Image makearray(Image im, int newwidth, int newheight, int xpos,
			int ypos) {
		int matrixSize = 256;
		int[][] myimage = new int[matrixSize][matrixSize];
		System.out.println("into scan image");
		int awh = (newwidth);
		int ahw = (newheight);

		try {
			int[] pgPixels = new int[ahw * awh];
			System.out.println(pgPixels.length);

			PixelGrabber pg = new PixelGrabber(im, 0, 0, awh, ahw, pgPixels, 0,
					awh);

			if (pg.grabPixels() && ((pg.status() & ImageObserver.ALLBITS) != 0)) {

				for (int y = 0; y < newheight; y++) {
					// System.out.println("xpos="+xpos);
					for (int x = 0; x < newwidth; x++) {
						// System.out.println("ypos="+ypos);

						int i = y * awh + x;

						int a = (pgPixels[i] & 0xff000000) >> 24;
						int r = (pgPixels[i] & 0x00ff0000) >> 16;
						int g = (pgPixels[i] & 0x0000ff00) >> 8;
						int b = (pgPixels[i] & 0x000000ff);
						// System.out.println("r="+r+"g="+g+"b="+b);
						if (r <= 252 && g <= 252 && b <= 252) {
							// System.out.println("i="+i);
							int a1 = x / (awh / matrixSize);
							int a2 = y / (ahw / matrixSize);
							// System.out.println("i="+i);
							// System.out.println(" "+x+" "+y);
							myimage[a2][a1] = 1;

						}
					}
				}
				for (int xc = 0; xc < matrixSize; xc++) {
					for (int yc = 0; yc < matrixSize; yc++) {
						System.out.print(" " + myimage[xc][yc]);
					}
					System.out.println("");
				}

			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		FileReadAll fr = new FileReadAll();
		// fr.readfile(myimage, newwidth, newheight, xpos, ypos);
		return im;
	}

	public Image makearray16(Image im, int nwidth, int nheight, int xpos,
			int ypos) {
		int[][] myimage1 = new int[16][16];
		int aw = nwidth + 20;
		int ah = nheight + 20;
		try {
			int[] pgPixels = new int[aw * ah];

			PixelGrabber pg = new PixelGrabber(im, 0, 0, nwidth, nheight,
					pgPixels, 0, nwidth);
			int newwidth = (aw / 16);
			int newheight = (ah / 16);
			System.out.println("The width of image: " + aw);
			System.out.println("The height of image: " + ah);
			if (pg.grabPixels() && ((pg.status() & ImageObserver.ALLBITS) != 0)) {
				for (int y = ypos; y < nheight; y++) {
					for (int x = xpos; x < nwidth; x++) {
						int i = y * nwidth + x;
						int a = (pgPixels[i] & 0xff000000) >> 24;
						int r = (pgPixels[i] & 0x00ff0000) >> 16;
						int g = (pgPixels[i] & 0x0000ff00) >> 8;
						int b = (pgPixels[i] & 0x000000ff);
						if (r <= 50 && g <= 50 && b <= 50) {
							int a1 = x / newwidth;
							int a2 = y / newheight;
							// System.out.println(" "+x+" "+y);
							myimage1[a2][a1] = 1;

						}
					}
				}// mymthd(myimage);
					// im = createImage(new MemoryImageSource(width, height,
					// pgPixels, 0, width));
				System.out.println("16*16 array representation of image");
				for (int j = 0; j < 16; j++) {
					for (int k = 0; k < 16; k++) {
						System.out.print(" " + myimage1[j][k]);
					}
					System.out.println();
				}

			}
			FileReadAll16 fr16 = new FileReadAll16();
			fr16.readfile16(myimage1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return im;
	}

	/** Filter to work with JFileChooser to select java file types. **/
	class JavaFilter extends javax.swing.filechooser.FileFilter {
		// this function is internally used for the Filtering action
		public boolean accept(File f) {
			return f.getName().toLowerCase().endsWith(".jpeg")
					|| f.getName().toLowerCase().endsWith(".jpg")
					|| f.getName().toLowerCase().endsWith(".gif")
					|| f.isDirectory();
		}

		// this function is internally used for the Filter Option drop down menu
		public String getDescription() {
			return "*.jpeg, *.gif, *.jpg";
		}
	}

}
