import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author XXX
 *
 */
public class MainFrame {
	private JButton jbutton;
	private JFrame jframe;
	private JPanel jpanel;
	
	public MainFrame(){
		jframe = new JFrame();
		jbutton = new JButton(){
			private static final long serialVersionUID = 32323L;
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon img = new ImageIcon(MainFrame.class.getResource("\\image\\œ£Õ˚.jpg"));
				img.paintIcon(this, g, 0, 0);
			}
		};
		
		jpanel = new JPanel();
		init();
	}
	
	private void init(){
		jframe.setTitle("Õº∆¨∞¥≈•≤‚ ‘");
		jframe.add(jpanel);
		jpanel.setLayout(new FlowLayout());
		jbutton.setOpaque(true);
		jbutton.setBorderPainted(true);
		jbutton.setPreferredSize(new Dimension(154, 154));
		jpanel.add(jbutton);
	}
	
	public void showMe(){
		jframe.setSize(300, 200);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainFrame().showMe();
	}
}