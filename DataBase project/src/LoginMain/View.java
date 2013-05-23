package LoginMain;
import java.sql.*;
import java.awt.*;

import javax.swing.*;

//import java.awt.event.*; 
public class View extends JPanel {

	private String user = "root"; // 数据库用户
	private String password = "12345"; // 数据库用户密码
	String url = "jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=utf-8";
	private JTable table;

	public View() {
		super(); // 调用父类构造函数
		String[] columnNames = { "编号", "姓名", "性别", "年龄", "院系", "学号","选择课程" }; // 列名
		Object[][] rowData = new Object[20][7]; // 表格数据
		setBackground(new Color(60, 70, 221));

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, password);

			String sqlStr = "select * from student"; // 查询语句
			PreparedStatement ps = con.prepareStatement(sqlStr); // 获取
																	// PreparedStatement
																	// 对象
			ResultSet rs = ps.executeQuery(); // 执行查询

			int count = 0;
			while (rs.next()) { // 遍历查询结果
				rowData[count][0] = rs.getInt("Number"); // 初始化数组内容

				rowData[count][1] = rs.getString("Name");
				rowData[count][2] = rs.getString("Sex");
				//
				rowData[count][3] = rs.getInt("Age");
				rowData[count][4] = rs.getString("DepartName");
				rowData[count][5] = rs.getInt("StudentNumber");
				rowData[count][6] = rs.getInt("Course");
				count++;
			}
			con.close(); // 关闭连接
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		table = new JTable(rowData, columnNames); // 实例化表格
		table.getColumn("编号").setMaxWidth(50);
		table.getColumn("姓名").setMaxWidth(50);
		table.getColumn("性别").setMaxWidth(50);
		table.getColumn("年龄").setMaxWidth(50);
		table.getColumn("院系").setMaxWidth(150);
		table.getColumn("学号").setMaxWidth(100);
		table.getColumn("选择课程").setMaxWidth(50);
		add(new JScrollPane(table), BorderLayout.CENTER); // 增加组 件
		// setSize(470, 440); //设置窗口尺寸
		setBounds(0, 0, 500, 450);
		setVisible(true); // 设置窗口可视
		
	}

}
