package com.ocr.database.write;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.ocr.ocr.Initial;

/**
 * 
 * @author pawan
 */
public class DBWriter extends Initial {
	String dbUrl = "jdbc:mysql://localhost/OCR";

	public void dbwriter(int[][] myimage, char m, int j) throws Exception {
		int a = m;

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection con1 = DriverManager.getConnection(dbUrl, "root", "root");
		Statement st2 = con1.createStatement();
		st2.executeUpdate("insert into ocr values('" + m + "','" + a + "','"
				+ j + "','" + myimage[j][0] + "','" + myimage[j][1] + "','"
				+ myimage[j][2] + "','" + myimage[j][3] + "','" + myimage[j][4]
				+ "','" + myimage[j][5] + "','" + myimage[j][6] + "','"
				+ myimage[j][7] + "')");

	}

}
