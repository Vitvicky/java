package LoginMain;
import java.sql.*;
public class DB {

	String username="root";
	String password="12345";
	private String drv = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/wzy?useUnicode=true&characterEncoding=utf-8";
	Connection con;
	ResultSet rs;
	PreparedStatement ps;
	
	public DB(){
		con=null;
		rs=null;
		ps=null;
		//�������ݿ�
		try{
			Class.forName(drv);
			con= DriverManager.getConnection(url,username,password);	
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public boolean insert(String a){//����
		try{
			ps = con.prepareStatement(a);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public boolean select(String a){//����
		try{
			ps = con.prepareStatement(a);
			rs = ps.executeQuery();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			rs = null;
			return false;
		}
		
	}
	
	
}
