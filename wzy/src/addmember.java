import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.sql.*;

public class addmember extends JFrame {
	JLabel j1 = new JLabel();
	JLabel j2 = new JLabel();
	JTextField j3 = new JTextField();
	JTextField j4 = new JTextField();
	JButton j5 = new JButton("ok");
	String dbUrl = "jdbc:odbc:sd";
	String sql = "select * from info";
	
	public addmember() {
		try {
			jb();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jb() throws Exception {
		getContentPane().setLayout(null);
		// j1.setBorder(BorderFactory.createEtchedBorder());
		j1.setBounds(new Rectangle(27, 23, 339, 257));
		// j1.setText("用户");
		j2.setBounds(new Rectangle(34, 129, 35, 24));
		// j2.setText("密码");
		j3.setBounds(new Rectangle(78, 62, 118, 21));
		//j5.addActionListener(this);
		this.getContentPane().add(j2);
		j5.setBounds(new Rectangle(100, 233, 103, 25));
		j4.setBounds(new Rectangle(78, 134, 272, 26));
		this.getContentPane().add(j1);
		this.getContentPane().add(j3);
		this.getContentPane().add(j4);
		this.getContentPane().add(j5);
		j5.addActionListener(new actionAdapter());
	}

	/**
	 * @param
	 */
	public static void main(String[] args) {
		addmember m = new addmember();
		m.setSize(new Dimension(400, 320));
		m.setVisible(true);
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	class actionAdapter implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("ok")) {
				String s1 = j3.getText();
				String s2 = j4.getText();
				//int mima = Integer.parseInt(s1);
				//boolean m = false;
				try {
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					Connection c = DriverManager.getConnection(dbUrl);
					Statement s = c.createStatement();
					//ResultSet set=s.executeQuery("select*from info");
					//while(set.next()){										
						s.executeUpdate("INSERT INTO info(用户名,密码) VALUES('"+s1+"','"+s2+"')");
					//}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				/*if (true) {
					JOptionPane.showMessageDialog(this, "注册成功");
				} else {
					JOptionPane.showMessageDialog(this, "注册失败", "错误",
							JOptionPane.ERROR_MESSAGE);
				}*/
				// 清空内容
				j3.setText("");
				j4.setText("");
			}

		}
	}

}