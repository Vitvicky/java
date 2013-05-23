import java.awt.*;

import javax.swing.*;
public class fail extends JFrame{
	JLabel j1=new JLabel("±§Ç¸£¬µÇÂ½Ê§°Ü");
	JLabel j2=new JLabel();
	public fail(){
		setTitle("µÇÂ½Ê§°Ü");
		setPreferredSize(new Dimension(200, 200));
		setBounds(100, 100, 200, 200);
		setLayout(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		j1.setBounds(10, 10, 100, 25);
		ImageIcon t = new ImageIcon("Ç¿ÐÐ¹Ø±Õ.gif");
		j2.setIcon(t);
		j2.setBounds(10, 40, 140, 120);
		add(j1);
		add(j2);
	}
	
}
