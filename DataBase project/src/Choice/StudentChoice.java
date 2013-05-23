package Choice;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class StudentChoice extends JFrame {
	JLabel j = new JLabel("请输入您的学生学号:");
	JLabel jj = new JLabel("请选择您要选择的课程:");
	
	String url = "jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=utf-8";
	String drv = "com.mysql.jdbc.Driver";
	String username = "root";
	String password = "12345";
	Connection con;
	ResultSet rs;
	JPanel a = new JPanel();
	JComboBox r = new JComboBox();
	JTextField j1 = new JTextField();
	//JTextField j11 = new JTextField();
	public StudentChoice() {
		setTitle("学生选课");
		setPreferredSize(new Dimension(500, 300));
		setBounds(100, 100, 500, 300);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		setLayout(null);
		a.setBounds(0, 0, 500, 300);
		a.setLayout(null);
		add(a);
		setLocation(this.getToolkit().getScreenSize().width / 2
				- this.getWidth() / 2, this.getToolkit().getScreenSize().height
				/ 2 - this.getHeight() / 2);

		JLabel w = new JLabel();

		ImageIcon q = new ImageIcon("image/panda.gif");
		w.setIcon(q);
		w.setBounds(10, 40, 120, 160);

		a.add(j);
		j.setBounds(150, 30, 180, 30);
		listener l = new listener();

		JButton j2 = new JButton("确定");
		j2.setBounds(380, 50, 80, 20);
		a.add(j2);
		j2.addActionListener(l);
		JButton j3 = new JButton("取消");
		j3.setBounds(380, 140, 80, 20);
		a.add(j3);
		j3.addActionListener(l);
		// z.setBounds(30,200,150,50);
		a.add(w);
		r.addItem("高等数学");
		r.addItem("Java");
		r.addItem("数据结构");
		r.addItem("数据库");
		r.setBounds(160, 120, 180, 20);
		a.add(r);
		j1.setBounds(160, 60, 120, 25);
		a.add(j1);
		jj.setBounds(150, 90, 150, 25);
		a.add(jj);
		
	}

	class listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String shuxing = (String) r.getSelectedItem();
			String number=new String(j1.getText());
			
			String q1="select * from student where StudentNumber="+"'"+number+"'";
			if (e.getActionCommand().equals("确定")) {
				
				try {
					Class.forName(drv).newInstance();
					con = DriverManager.getConnection(url, username, password);
					PreparedStatement ps1 = con.prepareStatement(q1);
					rs=ps1.executeQuery();
					if(!rs.next()){
						JOptionPane.showConfirmDialog(null,"不存在这个学生！","警告对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
					}
					else{
					String sql = "insert into xuanke (CourseName,StudentNumber) values (?,?)";
					PreparedStatement ps=con.prepareStatement(sql);
					ps.setString(1,shuxing);
				    ps.setString(2,number);
				    //ps.executeUpdate();
				    if (rs != null)
					{   rs.close();
						ps1.close();
						//c.close();
						JOptionPane.showConfirmDialog(null,"该学生选课成功!","提示对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
					}
				} 
				}catch (Exception m) {
					m.printStackTrace();
				}
				
			}
			if (e.getActionCommand().equals("取消")) {
				dispose();
			}
		}
	}

	public static void main(String[] args) {
		//new StudentChoice();
	}

}

