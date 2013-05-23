package Register;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class TeacherRegister extends JFrame implements ActionListener{
	JLabel name = new JLabel("姓名:");
	JTextField nametxt = new JTextField();
	JLabel age = new JLabel("年龄:");
	JTextField nichengtxt = new JTextField();
	JLabel xuehao = new JLabel("教师号:");
	JTextField q = new JTextField();
	JLabel xingbie = new JLabel("性别");
	ButtonGroup w = new ButtonGroup();
	JRadioButton e1 = new JRadioButton("男", false);
	JRadioButton e2 = new JRadioButton("女", false);
	//JLabel subject = new JLabel("院系");
	//JComboBox r = new JComboBox();
	
	String url = "jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=utf-8";
    String drv = "com.mysql.jdbc.Driver";
    String username="root";
	String password="12345";
	Connection c;
	ResultSet rs;
	//String sql = "select * from info";

	public TeacherRegister() {
		setTitle("新老师录入");
		setPreferredSize(new Dimension(300, 300));
		setBounds(100, 100, 300,300);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		setLocation(
				this.getToolkit().getScreenSize().width/2-this.getWidth()/2,
				this.getToolkit().getScreenSize().height/2-this.getHeight()/2);
		

		JPanel a = new JPanel();
		a.setBounds(0,0,500,700);
		a.setLayout(null);
		add(a);
		
		//姓名的输入
		name.setBounds(10, 80, 60, 25);
		a.add(name);
		nametxt.setBounds(80, 80, 140, 22);
		a.add(nametxt);
		//昵称的输入
		age.setBounds(10, 110, 90, 25);
		a.add(age);
		nichengtxt.setBounds(80, 110, 140, 22);
		a.add(nichengtxt);
		//密码的输入
		xuehao.setBounds(10, 140, 60, 25);
		a.add(xuehao);
		q.setBounds(80, 140, 140, 22);
		a.add(q);
		//性别选择
		xingbie.setBounds(10, 170, 60, 25);
		a.add(xingbie);
		
		e1.setBounds(80, 170, 50, 25);
		e2.setBounds(140, 170, 50, 25);
		w.add(e1);
		w.add(e2);
		a.add(e1);
		a.add(e2);
		//专业选择
		JLabel picture = new JLabel();//贴入图片
		picture.setBounds(60, 10, 50, 64);
		a.add(picture);
		ImageIcon t = new ImageIcon("image/一.gif");
		picture.setIcon(t);
		JButton a1=new JButton("提交");//提交按钮
		a1.setBounds(60,210,70,22);
		a1.addActionListener(this);
		a.add(a1);
		JButton a2=new JButton("重置");
		a2.setBounds(160,210,70,22);
		a.add(a2);
		JLabel j1=new JLabel();
		ImageIcon h = new ImageIcon("image/mm.jpg");
		j1.setIcon(h);
		j1.setBounds(0, 0, 300, 300);
		a.add(j1);
		}
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent arg0){
		int result=JOptionPane.showConfirmDialog(this,"你确定提交吗?","询问对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
		if(result==JOptionPane.OK_OPTION){
			String name = new String(nametxt.getText());
			//String age = new String(nichengtxt.getText());
			String xuehao=new String(q.getText());
			//String sex = new String(e1.isSelected()?e1.getText():e2.getText());
			
			String sql="insert into teacher(TeacherName,TeacherNumber) values (?,?)";
			String q1="select * from teacher where TeacherNumber="+"'"+xuehao+"'";
			try {
				Class.forName(drv);
				c= DriverManager.getConnection(url,username,password);	
				PreparedStatement ps0=c.prepareStatement(q1);
				rs=ps0.executeQuery();
				if(rs.next()){
					JOptionPane.showConfirmDialog(null,"这名教师已经注册！","警告对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
				}
				else{
					PreparedStatement ps=c.prepareStatement(sql);
					ps.setString(1, name);
	     		    ps.setString(2,xuehao);
				    ps.executeUpdate();
				    if (rs != null)
				{   rs.close();
					ps.close();
					c.close();
					JOptionPane.showConfirmDialog(null,"该学生添加成功!","提示对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
				}
				}	
				
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
			dispose();
			
		}
		//else{dispose();
		}
		
		public static void main(String args[]){
			//new TeacherRegister();
		}
		}
		

	
	