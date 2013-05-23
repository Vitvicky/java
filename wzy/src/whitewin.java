import java.awt.*;

import javax.swing.*;
public class whitewin extends JFrame{

	public whitewin(){
		setTitle("°×ÆåÓ®À²!!!");
		setPreferredSize(new Dimension(200, 300));
		setBounds(100, 100, 200, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		setLayout(null);
		setLocation(
				this.getToolkit().getScreenSize().width/2-this.getWidth()/2,
				this.getToolkit().getScreenSize().height/2-this.getHeight()/2);	
		JLabel w=new JLabel();
		JLabel z=new JLabel("¹§Ï²°×Æå»ñµÃÊ¤Àû!!!");
		ImageIcon q=new ImageIcon("»¶ºô1.gif");
		w.setIcon(q);
		w.setBounds(30,30,150,150);
		z.setBounds(30,200,150,50);
		add(w);
		add(z);
	}
	

}
