import java.awt.*;

import javax.swing.*;
class LabelDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		JFrame frame=new JFrame("Label Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon penguin=new ImageIcon("panda.gif");
		JLabel label1,label2,label3;
		
		label1=new JLabel("panda Left",penguin,SwingConstants.CENTER);
		
		label2=new JLabel("panda Right",penguin,SwingConstants.CENTER);
		label2.setHorizontalTextPosition(SwingConstants.LEFT);
		label2.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		label3=new JLabel("panda  Above",penguin,SwingConstants.CENTER);
		label3.setHorizontalTextPosition(SwingConstants.CENTER);
		label3.setVerticalTextPosition(SwingConstants.BOTTOM);
		JPanel a=new JPanel();
		a.setBackground(Color.red);
		a.setPreferredSize(new Dimension(200,250));
		a.add(label1);
		a.add(label2);
		a.add(label3);
		
		frame.getContentPane().add(a);
		frame.pack();
		frame.setVisible(true);
		
		
		
		
		
		
		


	}

	
	
}
	