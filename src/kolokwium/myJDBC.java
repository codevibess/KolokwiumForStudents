package kolokwium;


import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.Properties;

public class myJDBC {
private java.sql.Connection connect =null;
private java.sql.Statement statement = null;
private PreparedStatement preparedStatement = null;
private ResultSet resultSet = null;
private java.sql.Statement s = null;


public void connectDataBase() throws Exception{
	try {
		System.out.println("Sprawdzanie sterownika:");
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
		} catch (Exception e) {
			System.out.println("Blad przy ladowaniu sterownika bazy!");
			
		
		}
		
		/************************************************************************/		
		String baza = "jdbc:mysql://" + "127.0.0.1" + "/" + "nowabaza";
		
		/************************************************************************/	
		
		try {
			connect =  DriverManager.getConnection(baza, "root", "");
		} catch (SQLException e) {
			System.out.println("Blad przy po³¹czeniu z baz¹ danych!");
			System.exit(1);
		}
		/************************************************************************/		
		try {
			 connect.createStatement();
		} catch (SQLException e) {
			System.out.println("Blad createStatement! " + e.getMessage() + ": " + e.getErrorCode());
			System.exit(3);
		}
		
		statement =  connect.createStatement();
	      // Result set get the result of the SQL query
//		resultSet = statement
//		          .executeQuery("Select * from ksiazki_;");
//	     
//		getDataSql(resultSet);
		
		
	      
	    } catch (Exception e) {
	      throw e;
	    } 
	
	
}
public void readDataSql() throws Exception{
	connectDataBase();
	resultSet = statement
	          .executeQuery("Select * from clients;");
   
	getDataSql(resultSet);
	close();
}

public static void getDataSql(ResultSet r) {

	
	System.out.println("Pobieranie danych z wykorzystaniem nazw kolumn");
	try {
		ResultSetMetaData rsmd = r.getMetaData();
		int numcols = rsmd.getColumnCount();
		// Tytul tabeli z etykietami kolumn zestawow wynikow
		for (int i = 1; i <= numcols; i++) {
			System.out.print(rsmd.getColumnLabel(i) + "\t|\t");
		}
		System.out
		.print("\n____________________________________________________________________________\n");
		while (r.next()) {
			int size = r.getMetaData().getColumnCount();
			for(int i = 1; i <= size; i++){
				switch(r.getMetaData().getColumnTypeName(i)){
				case "INT":
					System.out.print(r.getInt(r.getMetaData().getColumnName(i)) + "\t|\t");
					break;
				case "DATE":
					System.out.print(r.getDate(r.getMetaData().getColumnName(i)) );
					break;
				case "VARCHAR":
					System.out.print(r.getString(r.getMetaData().getColumnName(i)) + "\t|\t");
					break;
				default:
					System.out.print(r.getMetaData().getColumnTypeName(i));
				}
			}
			System.out.println();
		}
	} catch (SQLException e) {
		System.out.println("Bl¹d odczytu z bazy! " + e.getMessage() + ": " + e.getErrorCode());
	}
}

public void setDataSql(String s, int i) throws Exception{
	connectDataBase();

 statement.executeUpdate("INSERT INTO clients VALUES(" + s +"," +  i +");");
 close();
}


private void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }

}
