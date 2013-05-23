import java.awt.*;

import javax.swing.*;
public class blackwin extends JFrame{

	public blackwin(){
		setTitle("ºÚÆåÓ®À²!!!");
		setPreferredSize(new Dimension(220, 300));
		setBounds(100, 100, 220, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		setLayout(null);
		setLocation(
				this.getToolkit().getScreenSize().width/2-this.getWidth()/2,
				this.getToolkit().getScreenSize().height/2-this.getHeight()/2);	
		JLabel w=new JLabel();
		JLabel z=new JLabel("¹§Ï²ºÚÆå»ñµÃÊ¤Àû!!!");
		ImageIcon q=new ImageIcon("»¶ºô2.gif");
		w.setIcon(q);
		w.setBounds(20,20,200,200);
		z.setBounds(30,200,150,50);
		add(w);
		add(z);
	}
	

}
