package com.yorer.persely;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DbConnector {
	private final String JDBC_CONNECT = "jdbc:sqlite:resources/persely.db";
	private final String JDBC_CLASS = "org.sqlite.JDBC";

	private Connection connection;
	private Statement statement;

	private Connection getConnection() {
		try {
			Class.forName(JDBC_CLASS);
			Connection c = DriverManager.getConnection(JDBC_CONNECT);
			return c;
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
		}
		return null;
	}

	public void createTable() {
		try {
			String sql = "CREATE TABLE IF NOT EXISTS `Persely` (" + 
					"`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
					"`alap_osszeg`	INTEGER NOT NULL," + 
					"`nov_rata`	INTEGER NOT NULL, " +
					"`arfolyam` VARCHAR(10), " +
					"`current_date` DATE DEFAULT current_date, " +
					"`start_date` DATE DEFAULT '2016-01-01' NOT NULL, " +
					"`end_date` DATE DEFAULT '2016-12-31' NOT NULL, " +
					"`day` DATE," + 
					"`paid` INTEGER DEFAULT '0' NOT NULL);";
			
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Database create table error: " + e.getMessage());
		}
	}

	public void addToDB(PerselyAdatok items) {
		try{
			String sql = "INSERT INTO Persely (alap_osszeg, nov_rata) VALUES (" + 
						items.getAlapOsszeg() + ", " + 
						items.getNovRata() + ")";
			
			statement.executeUpdate(sql);
			setDay();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Database add to database error: " + e.getMessage());
		}
	}

	public void updateDB(PerselyAdatok item) {
		try{
			String sql = "UPDATE Persely set `alap_osszeg` = " + item.getAlapOsszeg();
			statement.executeUpdate(sql);
			sql = "UPDATE Persely set `nov_rata` = " + item.getNovRata();
			statement.executeUpdate(sql);
			sql = "UPDATE Persely set `arfolyam` = '" + item.getArfolyam() + "'";
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Database update error: " + e.getMessage());
		}
	}

	public void deleteFromDB(PerselyAdatok item) {
		try{
			String sql = "DELETE FROM Persely where `id` = " + item.getId();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Database delete error: " + e.getMessage());
		}
	}
	
	public void setDay(){
		try {
			String sql = "update persely set `day` = case cast(strftime('%w', `current_date`) as integer)" +
					"when 0 then 'Vasárnap'" +
					"when 1 then 'Hétfõ'" +
					"when 2 then 'Kedd'" +
					"when 3 then 'Szerda'" +
					"when 4 then 'Csütörtök'" +
					"when 5 then 'Péntek'" +
					"else 'Szombat' end;";
			
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Database set day error: " + e.getMessage());
		}
	}
	
	public boolean open() {
		try {
			connection = getConnection();
			statement = connection.createStatement();
			System.out.println("Database connection successfully opened.");
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Database open error: " + e.getMessage());
		}
		return true;
	}

	public void close() {
		try {
			statement.close();
			connection.close();
			System.out.println("Database successfully closed.");
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Database close error: " + e.getMessage());
		}
	}
	
	public void getDatabaseValues(PerselyAdatok adatok){
		String sql = "select * from Persely;";
		try {
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				adatok.setAlapOsszeg(rs.getInt("alap_osszeg"));
				adatok.setNovRata(rs.getInt("nov_rata"));
				adatok.setArfolyam(rs.getString("arfolyam"));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Database get data error: " + e.getMessage());
		}
	}
}
