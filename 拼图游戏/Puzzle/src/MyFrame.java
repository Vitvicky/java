import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MyFrame extends JFrame {
	//
    private final int WIDTH=358;
    private final int HEIGHT=414;
    private JPanel panel;
	public static void main(String[] args) {
	      new MyFrame().setVisible(true);
	}
	
	public MyFrame(){
		this.setSize(WIDTH,HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel=new JPanel();
	    panel.setLayout(new BorderLayout());
		this.getContentPane().add(panel);
		
		JPanel control=new JPanel();
	    control.setBackground(Color.BLUE);
		panel.add(control,BorderLayout.NORTH);
		
		
		final PuzzlePanel puzzle=new PuzzlePanel();//final 可使puzzle在内部类中调用。
		panel.add(puzzle,BorderLayout.CENTER);
		
		JButton button=new JButton("开始");
		control.add(button);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			     puzzle.random();//打乱按钮位置。
				
			}
			
		});
		
		
	
		
	}

}
