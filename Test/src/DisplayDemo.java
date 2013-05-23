import java.sql.*; 
import java.awt.*;
import javax.swing.*; 
import javax.swing.table.*; 
//import java.awt.event.*; 
public class DisplayDemo extends JPanel  { 

private String user="root"; // 数据库用户 
private String password="12345"; // 数据库用户密码 
String url = "jdbc:mysql://localhost:3306/wzy?useUnicode=true&characterEncoding=utf-8";
private JTable table;
 private JButton ok,canel; 
public DisplayDemo(){ 
super(); //调用父类构造函数 
String[] columnNames={"ID","用户名","密码"}; //列名 
Object[][] rowData=new Object[5][4]; //表格数据 
ok=new JButton("确定"); 
canel=new JButton("取消"); 

try 
{ 
Class.forName("com.mysql.jdbc.Driver"); 
Connection con = DriverManager.getConnection(url, user, password);

String sqlStr="select * from info"; //查询语句 
PreparedStatement ps=con.prepareStatement(sqlStr); //获取 PreparedStatement 对象 
ResultSet rs=ps.executeQuery(); //执行查询 
String name,sex,email; //查询结果 
int age; 
int count=0; 
while (rs.next()){ //遍历查询结果 
rowData[count][0]=rs.getString("id"); //初始化数组内容 
//rowData[count][1]=Integer.toString(rs.getInt("age")); 
rowData[count][1]=rs.getString("username");
rowData[count][2]=rs.getString("password"); 
count++;
} con.close(); //关闭连接
} catch(Exception ex)
{ ex.printStackTrace(); }
//输出出错信息
//Container container=getContentPane(); //获取窗口容器 
//container.setLayout(null); 
add(ok);
add(canel); 
ok.setBounds(10,120,70,20);
canel.setBounds(100,120,70,20); 
table=new JTable(rowData,columnNames); //实例化表格 
//table.getColumn("用户名").setMaxWidth(25); //设置行宽 
add(new JScrollPane(table),BorderLayout.CENTER); //增加组 件
setSize(505, 505); //设置窗口尺寸 
setVisible(true); //设置窗口可视 
//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
}//关闭窗口时退出程 序 } 
public static void main(String[] args)
{ new DisplayDemo(); }
}

