import java.sql.*;

public class TestSQL {
	public static void main(String args[]) {
		

		try {
			String dbUrl = "jdbc:odbc:sd";
			String sql = "select * from info";
			
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection c = DriverManager.getConnection(dbUrl);
			Statement s = c.createStatement();
			//s.executeUpdate("insert into info values(1,22,'asd','��׿��','shen')");
			//s.executeUpdate("insert into info values(3,24,'aaa','���ֺ�','wlh')");
			s.executeUpdate("delete from info where ID=4");
			s.executeUpdate("delete from info where ID=5");
			s.executeUpdate("delete from info where ID=6");
			ResultSet set = s.executeQuery(sql);
			while (set.next()) 
			{
				System.out.println(set.getInt("ID")+","+set.getInt("�˺�")+","+set.getString("����")+","+set.getString("�û�")+","+set.getString("�ǳ�"));
				
			}
			c.close();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
