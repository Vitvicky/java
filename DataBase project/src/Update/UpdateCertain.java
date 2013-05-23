package Update;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class UpdateCertain extends JFrame {
	JLabel j = new JLabel("请选择您要更新的选项:");
	//JTextField j1 = new JTextField();
	JPanel a = new JPanel();

	public UpdateCertain() {
		setTitle("更新选项");
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

		ImageIcon q = new ImageIcon("image/欢呼2.gif");
		w.setIcon(q);
		w.setBounds(10, 10, 176, 220);

		a.add(j);
		
		j.setBounds(200, 100, 180, 30);
		
		listener l = new listener();

		JButton j2 = new JButton("学生");
		j2.setBounds(380, 30, 80, 20);
		a.add(j2);
		j2.addActionListener(l);
		JButton j3 = new JButton("教师");
		j3.setBounds(380, 190, 80, 20);
		a.add(j3);
		j3.addActionListener(l);
		JButton j4 = new JButton("课程");
		j4.setBounds(380, 110, 80, 20);
		a.add(j4);
		j4.addActionListener(l);
		// z.setBounds(30,200,150,50);
		a.add(w);
		JLabel j1 = new JLabel();
		ImageIcon h = new ImageIcon("image/cloud.jpg");
		j1.setIcon(h);
		j1.setBounds(0, 0, 500, 300);
		// a.add(j1);
		// add(z);
	}

	class listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("学生")) {
				new StudentUpdate();
				dispose();
			}
			if (e.getActionCommand().equals("课程")) {
				new CourseUpdate();
				dispose();
			}
			if(e.getActionCommand().equals("教师")){
				//new TeacherUpdate();
				dispose();
			}
		}
	}

	public static void main(String[] args) {
		//UpdateCertain q=new UpdateCertain();
	}

}
