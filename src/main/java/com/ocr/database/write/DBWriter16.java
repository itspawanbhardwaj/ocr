package com.ocr.database.write;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * 
 * @author pawan
 */
public class DBWriter16 {
	String dbUrl = "jdbc:mysql://localhost/OCR";

	public void dbwriter16(int[][] myimage, char m, int j) throws Exception {
		int a = m;

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection con1 = DriverManager.getConnection(dbUrl, "root", "root");
		Statement st2 = con1.createStatement();
		st2.executeUpdate("insert into ocr16 values('" + m + "','" + a + "','"
				+ j + "','" + myimage[j][0] + "','" + myimage[j][1] + "','"
				+ myimage[j][2] + "','" + myimage[j][3] + "','" + myimage[j][4]
				+ "','" + myimage[j][5] + "','" + myimage[j][6] + "','"
				+ myimage[j][7] + "','" + myimage[j][8] + "','" + myimage[j][9]
				+ "','" + myimage[j][10] + "','" + myimage[j][11] + "','"
				+ myimage[j][12] + "','" + myimage[j][13] + "','"
				+ myimage[j][14] + "','" + myimage[j][15] + "')");

	}

}
