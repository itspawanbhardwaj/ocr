package com.ocr.database.reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.ocr.comparator.Comperator;
import com.ocr.image.scan.ScanImage;

public class FileReadAll extends Comperator {

	static char alphabet = 'A';
	static int rw = 0;
	static int a[][] = new int[8][8];
	static int count = 0;
	static int m = 65;
	static char m1;
	static int myimage[][] = new int[8][8];

	public void readfile(int arry[][], int nwidth, int nheight, int xpos,
			int ypos) {

		FileReadAll fra = new FileReadAll();

		ScanImage si = new ScanImage();
		// si.makearray(h);
		try {
			String dbUrl = "jdbc:mysql://localhost:3306/ocr";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con1 = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/OCR", "root", "root");
			Statement st2 = con1.createStatement();
			for (m = 65; m <= 122; m++, count = 0) {
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						// String sql= "SELECT * FROM ocr WHERE Character="+m+";
						String r = "b" + j;
						ResultSet rs = st2
								.executeQuery("SELECT * FROM ocr WHERE ASCII = '"
										+ m + "'and number = '" + i + "'");
						while (rs.next()) {
							a[i][j] = rs.getInt(r);
							// System.out.println("retreiving "+r+"from row:"+i+"value:"+a[i][j]);
							// System.out.println("Storing in a["+i+"]["+j+"]");

						}
					}
				}
				m1 = (char) m;

				System.out.println("For char   " + m1);
				mymethod(arry, a, m1);
				for (int j = 0; j < 8; j++) {
					for (int k = 0; k < 8; k++) {

						System.out.print("   " + a[j][k]);
					}
					System.out.println("\n");
				}
				System.out.println("no of bits matched : " + count);

				if (m == 90) {
					m = 96;
				}

			}
			System.out.println("8*8 array representation of image");
			for (int j = 0; j < 8; j++) {
				for (int k = 0; k < 8; k++) {
					System.out.print(" " + arry[j][k]);
				}
				System.out.println();
			}

			maxmatch(nwidth, nheight, xpos, ypos);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
