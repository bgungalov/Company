import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;

 public class ExportDatabase extends sqliteConnection {
	 
	 public ExportDatabase() {
		 try {
	    PrintWriter pw= new PrintWriter(new File("/Users/bisergungalov/EmployeeInfo.csv"));
	    StringBuilder sb=new StringBuilder();
	 
	 
	    Connection connection=null;
	    connection = sqliteConnection.dbConnector();
	    ResultSet rs=null;
	 
	    String query="select * from EmployeeInfo.db";
	    PreparedStatement ps=conn.prepareStatement(query);
	    rs=ps.executeQuery();
	 
	    while(rs.next()){
	     sb.append(rs.getString("EID"));
	     sb.append(","); 
	     sb.append(rs.getString("Name"));
	     sb.append(","); 
	     sb.append(rs.getString("Surname"));
	     sb.append(","); 
	     sb.append(rs.getString("Username"));
	     sb.append("\r\n");
	    }
	 
	    pw.write(sb.toString());
	    pw.close();
	    System.out.println("finished");
	 
	   } catch (Exception e) {
	    // TODO: handle exception
	   } 
}
}