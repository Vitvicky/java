package Delete;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class StudentDelete extends JFrame {
	JLabel j = new JLabel("请输入您要删除的学生学号:");
	JTextField j1 = new JTextField();
	
	String url = "jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=utf-8";
	String drv = "com.mysql.jdbc.Driver";
	String username = "root";
	String password = "12345";
	Connection con;
	ResultSet rs;
	JPanel a = new JPanel();

	public StudentDelete() {
		setTitle("删除学生");
		setPreferredSize(new Dimension(450, 200));
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

		ImageIcon q = new ImageIcon("image/tou.gif");
		w.setIcon(q);
		w.setBounds(5,5,50,50);

		a.add(j);
		a.add(j1);
		j.setBounds(80, 20, 180, 30);
		j1.setBounds(100, 80, 150, 30);
		listener l = new listener();

		JButton j2 = new JButton("确定");
		j2.setBounds(320, 50, 80, 20);
		a.add(j2);
		j2.addActionListener(l);
		JButton j3 = new JButton("取消");
		j3.setBounds(320, 100, 80, 20);
		a.add(j3);
		j3.addActionListener(l);
		// z.setBounds(30,200,150,50);
		a.add(w);
		JLabel j1 = new JLabel();
		ImageIcon h = new ImageIcon("image/cloud.jpg");
		j1.setIcon(h);
		j1.setBounds(0, 0, 500, 300);
		 a.add(j1);
		// add(z);
	}
	class listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("确定")){
				String number=j1.getText();
				String q1="select * from student where StudentNumber="+"'"+number+"'";
				String sql="delete from student where StudentNumber="+"'"+number+"'";
				try{
					Class.forName(drv).newInstance();
					con = DriverManager.getConnection(url, username, password);
					PreparedStatement ps=con.prepareStatement(q1);
					rs=ps.executeQuery();
					if(!rs.next()){
						JOptionPane.showConfirmDialog(null,"不存在这个学生！请重新输入！","警告对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
					}
					else{
					PreparedStatement ps1=con.prepareStatement(sql);
					ps1.executeUpdate();
					con.close(); // 关闭连接
					ps.close();
					rs.close();
					JOptionPane.showConfirmDialog(null,"该学生资料删除成功!","提示对话框", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
		}
				}catch(Exception e1){
			e1.printStackTrace();
		}
		}
	}	
	}	
	public static void main(String[] args) {
		//new StudentDelete();

	}

}
