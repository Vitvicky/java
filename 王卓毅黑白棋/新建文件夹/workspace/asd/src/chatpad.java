

import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

public class chatpad extends JPanel implements MouseListener{
public TextArea chatarea;
public TextField input=new TextField("点击回车发送消息",20);
JLabel chatlabel=new JLabel("输入发送消息：");
JButton queding;
gameduan zh;
	public chatpad(gameduan zhu){
		this.zh=zhu;
		//this.setBackground(new Color(71,180,174));
		setLayout(new BorderLayout());
		chatarea=new TextArea("",3,10,TextArea.SCROLLBARS_VERTICAL_ONLY); //chatarea.setLineWrap(true);
		chatarea.setCaretPosition(chatarea.getText().length());chatarea.requestFocus();
		chatarea.setEditable(false);
		add(chatarea,BorderLayout.CENTER);
		Container con=new JPanel(new FlowLayout());
		add(con,BorderLayout.SOUTH);
		queding=new JButton("确定");queding.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(input.getText().equals("")||input.getText().equals("点击回车发送消息"))  return;
				else
				{zh.padpad.sendMessage(input.getText());
					chatarea.append(input.getText());
				chatarea.setCaretPosition(chatarea.getText().length());
				input.setText("");}
			}
		});
		con.add(chatlabel);con.add(input);input.addMouseListener(this);con.add(queding);
	}
	public static void main(String[] args) {
		

	}
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void mousePressed(MouseEvent arg0) {
		if(input.getText().equals("点击回车发送消息"))
	input.setText("");
		
	}
	
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
