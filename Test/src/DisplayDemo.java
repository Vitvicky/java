import java.sql.*; 
import java.awt.*;
import javax.swing.*; 
import javax.swing.table.*; 
//import java.awt.event.*; 
public class DisplayDemo extends JPanel  { 

private String user="root"; // ���ݿ��û� 
private String password="12345"; // ���ݿ��û����� 
String url = "jdbc:mysql://localhost:3306/wzy?useUnicode=true&characterEncoding=utf-8";
private JTable table;
 private JButton ok,canel; 
public DisplayDemo(){ 
super(); //���ø��๹�캯�� 
String[] columnNames={"ID","�û���","����"}; //���� 
Object[][] rowData=new Object[5][4]; //������� 
ok=new JButton("ȷ��"); 
canel=new JButton("ȡ��"); 

try 
{ 
Class.forName("com.mysql.jdbc.Driver"); 
Connection con = DriverManager.getConnection(url, user, password);

String sqlStr="select * from info"; //��ѯ��� 
PreparedStatement ps=con.prepareStatement(sqlStr); //��ȡ PreparedStatement ���� 
ResultSet rs=ps.executeQuery(); //ִ�в�ѯ 
String name,sex,email; //��ѯ��� 
int age; 
int count=0; 
while (rs.next()){ //������ѯ��� 
rowData[count][0]=rs.getString("id"); //��ʼ���������� 
//rowData[count][1]=Integer.toString(rs.getInt("age")); 
rowData[count][1]=rs.getString("username");
rowData[count][2]=rs.getString("password"); 
count++;
} con.close(); //�ر�����
} catch(Exception ex)
{ ex.printStackTrace(); }
//���������Ϣ
//Container container=getContentPane(); //��ȡ�������� 
//container.setLayout(null); 
add(ok);
add(canel); 
ok.setBounds(10,120,70,20);
canel.setBounds(100,120,70,20); 
table=new JTable(rowData,columnNames); //ʵ������� 
//table.getColumn("�û���").setMaxWidth(25); //�����п� 
add(new JScrollPane(table),BorderLayout.CENTER); //������ ��
setSize(505, 505); //���ô��ڳߴ� 
setVisible(true); //���ô��ڿ��� 
//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
}//�رմ���ʱ�˳��� �� } 
public static void main(String[] args)
{ new DisplayDemo(); }
}

