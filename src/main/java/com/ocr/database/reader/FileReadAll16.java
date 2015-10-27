package com.ocr.database.reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.ocr.comparator.comperator16;

public class FileReadAll16 extends comperator16 {

	static char alphabet = 'A';
	static int rw = 0;
	static int a[][] = new int[16][16];
	static int count = 0;
	static int m;
	static char m1;
	static int myimage[][] = new int[16][16];

	public void readfile16(int arry[][]) {

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				System.out.print(" " + arry[i][j]);
			}
			System.out.println();
		}

		try {
			String dbUrl = "jdbc:mysql://localhost:3306/OCR";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con1 = DriverManager
					.getConnection(dbUrl, "root", "root");
			Statement st2 = con1.createStatement();
			for (m = 65; m <= 122; m++, count = 0) {
				for (int i = 0; i < 16; i++) {
					for (int j = 0; j < 16; j++) {
						// String sql= "SELECT * FROM ocr WHERE Character="+m+";
						String r = "b" + j;
						ResultSet rs = st2
								.executeQuery("SELECT * FROM ocr16 WHERE ASCII = '"
										+ m + "'and row = '" + i + "'");
						while (rs.next()) {
							a[i][j] = rs.getInt(r);
							// System.out.println("retreiving "+r+"from row:"+i+"value:"+a[i][j]);
							// System.out.println("Storing in a["+i+"]["+j+"]");

						}
					}
				}
				m1 = (char) m;

				// System.out.println("For char   " + m1);
				mymethod16(arry, a, m1);
				// for (int j = 0; j < 8; j++) {
				// for (int k = 0; k < 8; k++) {
				//
				// System.out.print("   "+a[j][k]);
				// }
				// System.out.println("\n");
				// }
				// System.out.println("no of bits matched : " + count);

				if (m == 90) {
					m = 96;
				}

			}
			System.out.println("16*16 array representation of image");
			for (int j = 0; j < 16; j++) {
				for (int k = 0; k < 16; k++) {
					System.out.print(" " + arry[j][k]);
				}
				System.out.println();
			}

			System.out.println("array printed from scan image");
			for (int j = 0; j < 16; j++) {
				for (int k = 0; k < 16; k++) {
					System.out.print(" " + arry[j][k]);
				}
				System.out.println();
			}

			maxmatch16();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
