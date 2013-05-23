package Check;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;


public class StudentCheck extends JFrame{
	JLabel j=new JLabel("请选择您要查找的学生内容:");
	JTextField j1=new JTextField();
	String url = "jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=utf-8";
    String drv = "com.mysql.jdbc.Driver";
    String username="root";
	String password="12345";
	Connection con;
	ResultSet rs;
	JPanel a=new JPanel();
	JComboBox r = new JComboBox();
	public StudentCheck(){
		setTitle("查找学生");
		setPreferredSize(new Dimension(500, 300));
		setBounds(100, 100, 500, 300);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		setLayout(null);
		a.setBounds(0,0,500,300);
		a.setLayout(null);
		add(a);
		setLocation(
				this.getToolkit().getScreenSize().width/2-this.getWidth()/2,
				this.getToolkit().getScreenSize().height/2-this.getHeight()/2);	
		
		JLabel w=new JLabel();
		
		ImageIcon q=new ImageIcon("image/欢呼1.gif");
		w.setIcon(q);
		w.setBounds(10,10,120,150);
		
		a.add(j);
		a.add(j1);
		j.setBounds(150, 20, 180, 30);
		j1.setBounds(150,90,150,25);
		listener l=new listener();
		
		JButton j2=new JButton("确定");
		j2.setBounds(380,50,80,20);
		a.add(j2);
		j2.addActionListener(l);
		JButton j3=new JButton("取消");
		j3.setBounds(380,100,80,20);
		a.add(j3);
		j3.addActionListener(l);
		//z.setBounds(30,200,150,50);
		a.add(w);
		JLabel j1=new JLabel();
		j1.setBounds(0, 0, 500, 300);
		r.addItem("学生编号");
		r.addItem("姓名");
		r.addItem("学号");
		r.setBounds(150, 60, 180, 20);
		a.add(r);
	}
	class listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String in = (String) r.getSelectedItem();
			String what=new String(j1.getText());
			if(e.getActionCommand().equals("确定")){
				try {
					Class.forName(drv).newInstance();
					con = DriverManager.getConnection(url, username, password);
				} catch (Exception m) {
					m.printStackTrace();
				}
				//System.out.println(in);
				if (in.equals("学生编号")) {
					try {
						String sql="select * from student where Number="+"'"+what+"'";
						PreparedStatement ps = con.prepareStatement(sql);
						rs = ps.executeQuery();
						if(!rs.next()){
							JOptionPane.showConfirmDialog(null,"不存在这个学生！","警告对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
						}
						else { // 遍历查询结果
							
							System.out.println(rs.getString("Name")+" "+rs.getString("Sex"));
							JLabel  result=new JLabel("查询结果:");
							a.add(result);
							result.setBounds(10,160,80,30);
							JLabel shuxing=new JLabel("编号       姓名         性别          年龄                   院系                              学号");
							a.add(shuxing);
							shuxing.setBounds(5,200,480,20);
							JLabel mm=new JLabel(rs.getInt("Number")+"           "+rs.getString("Name")
									+"           "+rs.getString("Sex")+"           "+rs.getInt("Age")
									+"           "+rs.getString("DepartName")+"           "+
									rs.getInt("StudentNumber"));
							//JLabel mm=new JLabel("kkkk");
							a.add(mm);
							mm.setBounds(10,230,480,40);
						}
						
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
				}
				if (in.equals("姓名")) {
					try {
						String sql="select * from student where Name="+"'"+what+"'";
						PreparedStatement ps = con.prepareStatement(sql);
						rs = ps.executeQuery();
						if(!rs.next()){
							JOptionPane.showConfirmDialog(null,"不存在这个学生课！","警告对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
						}
						else { 
							//System.out.println(rs.getString("Name")+" "+rs.getString("Sex"));
							JLabel  result=new JLabel("查询结果:");
							a.add(result);
							result.setBounds(10,160,80,30);
							JLabel shuxing=new JLabel("编号       姓名         性别          年龄                   院系                              学号");
							a.add(shuxing);
							shuxing.setBounds(5,200,480,20);
							JLabel mm=new JLabel(rs.getInt("Number")+"           "+rs.getString("Name")
									+"           "+rs.getString("Sex")+"           "+rs.getInt("Age")
									+"           "+rs.getString("DepartName")+"           "+
									rs.getInt("StudentNumber"));
							//JLabel mm=new JLabel("kkkk");
							a.add(mm);
							mm.setBounds(10,230,480,40);
							}
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
				}
				if (in.equals("学号")) {
					try {
						String sql ="select * from student where StudentNumber="+"'"+what+"'";
						PreparedStatement ps = con.prepareStatement(sql);
						rs = ps.executeQuery();
						if(!rs.next()){
							JOptionPane.showConfirmDialog(null,"不存在这个学生课！","警告对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
						}
						else{ // 遍历查询结果
							
							//System.out.println(rs.getString("Name")+" "+rs.getString("Sex"));
							JLabel  result=new JLabel("查询结果:");
							a.add(result);
							result.setBounds(10,160,80,30);
							JLabel shuxing=new JLabel("编号       姓名         性别          年龄                   院系                              学号");
							a.add(shuxing);
							shuxing.setBounds(5,200,480,20);
							JLabel mm=new JLabel(rs.getInt("Number")+"           "+rs.getString("Name")
									+"           "+rs.getString("Sex")+"           "+rs.getInt("Age")
									+"           "+rs.getString("DepartName")+"           "+
									rs.getInt("StudentNumber"));
							//JLabel mm=new JLabel("kkkk");
							a.add(mm);
							mm.setBounds(10,230,480,40);
						}
						
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
				}
				}
if(e.getActionCommand().equals("取消")){
				
			}
		
	}
	}
	public static void main(String []args){
		//new StudentCheck();
	}

}
