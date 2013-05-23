import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;
public class kehuduan extends JFrame implements ActionListener, Runnable{
public info user;
public kongzhi trol;
public userpad use;  
public boolean  isOnChess = false,isClient = false,isServer=false,isMouseEnabled=false;
public int chessColor=1;
public String chessClientName="",chessPeerName="";
public gameduan zhu;
public Socket socket;
public chat ch;
public DataInputStream in;
public DataOutputStream out;
	public kehuduan(Socket socket,DataInputStream in,DataOutputStream out) throws Exception{
		setSize(500,500);
		this.socket=socket;this.in=in;this.out=out;
		user=new info();
		trol=new kongzhi();trol.creatbutton.addActionListener(this);trol.exitbutton.addActionListener(this);trol.joinbutton.addActionListener(this);
		use=new userpad();
		trol.setBackground(new Color(71,180,174));
		setLayout(new BorderLayout());
	ch=new chat();ch.setBackground(new Color(71,180,174));
	add(ch,BorderLayout.SOUTH);
		add(trol,BorderLayout.NORTH);
		add(user,BorderLayout.WEST);
		add(use,BorderLayout.EAST);
		setVisible(true);
		setLocation(getToolkit().getScreenSize().width/2-getWidth()/2,
				getToolkit().getScreenSize().height/2-getHeight()/2);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	     zhu= new gameduan(this);zhu.setVisible(false);
		Thread thread=new Thread(this);
		thread.start();
	}
	  public void sendMessage(String sndMessage) {
          try {
              // ������socket�ṩ��������������������Ϣ
              out.writeUTF(sndMessage);
          } catch (Exception ea) {
              System.out.println("chessThread.sendMessage:" + ea);
          }
      }
		public void run() {
			  String message = "";//System.out.println("44444444");
		        try {      
		            while (true) {	
		                message = in.readUTF();
		                acceptMessage(message);
		            }
		        } catch (IOException es) {}		       		        
		}
    /**
     * �ͻ����̶߳Խ��յ�����Ϣ���д���ĺ���
     */
    public void acceptMessage(String recMessage) {
        if (recMessage.startsWith("/userlist ")) {
            // ������յ�����Ϣ��"/userlist "��ͷ���������û�����ȡ��������ӵ�
            // ������ϢPanel��ߵ��û��б��С�
        	
            StringTokenizer userToken = new StringTokenizer(recMessage, " ");
            int userNumber = 0;
            
            use.userList.removeAll();
    
            while (userToken.hasMoreTokens()) {
                String user = (String) userToken.nextToken(" ");
                if (userNumber > 0 ) {//&& !user.startsWith("[inchess]")
                   use.userList.add(user);    
               
                }
                userNumber++;
            }
         
        }
        else if (recMessage.startsWith("/uselist ")) {
            // ������յ�����Ϣ��"/userlist "��ͷ���������û�����ȡ��������ӵ�
            // ������ϢPanel��ߵ��û��б��С�
            StringTokenizer userToken = new StringTokenizer(recMessage, " ");
            int userNumber = 0;
            
            use.userList1.removeAll();
      
            while (userToken.hasMoreTokens()) {
                String user = (String) userToken.nextToken(" ");
                if (userNumber > 0 ) {//&& !user.startsWith("[inchess]")
                   use.userList1.add(user);
                
                }
                userNumber++;
            }
          
        }
        // ���������յ�����Ϣ��"/yourname "��ͷ,���û�����ʾ�ڿͻ��˶Ի����������
        else if (recMessage.startsWith("/yourname ")) {
            //֮������10��Ϊ��/yourname ������10���ַ�����������û�������
            chessClientName = recMessage.substring(10);
            setTitle("����ڰ���ͻ��� " + "��ǰ�û���:" + chessClientName);
        }
        else if(recMessage.startsWith("/huiqi")){
        	for(int t=0;t<8;t++) for(int s=0;s<8;s++) zhu.scr.qi[t][s]=zhu.scr.y[t][s];zhu.scr.repaint();
        }
        // ���������յ�����Ϣ��"/reject"��ͷ����״̬����ʾ�ܾ�������Ϸ��
        else if (recMessage.equals("/reject")) {
            try {
                ch.chatLineArea.append("���ܼ�����Ϸ");
                //���°�ť״̬
                
            } catch (Exception ef) {
                ch.chatLineArea.append("chessclient.chesspad.chessSocket.close�޷��ر�");
            }
         
        }
        // ���������յ�����Ϣ��"/peer"��ͷ,����¶Է������֣�Ȼ�����ȴ�״̬
        else if (recMessage.startsWith("/peer ")) { 
       chessPeerName=recMessage.substring(6);
         //zhu.wanjia2.xingming.setText(  zhu.wanjia2.xingming.getText().substring(0,3)+recMessage.substring(6));
            if (isServer) {//��������
                chessColor = 1;
                //zhu.wanjia1.yanse.setText(zhu.wanjia1.yanse.getText()+"��");
                //zhu.wanjia2.yanse.setText(zhu.wanjia2.yanse.getText()+"��");
                isMouseEnabled = true;
                zhu.chat.chatarea.append("���������\n");
            } else if (isClient) {
                chessColor = -1;
                //zhu.wanjia2.yanse.setText(zhu.wanjia2.yanse.getText()+"��");
                //zhu.wanjia1.yanse.setText(zhu.wanjia1.yanse.getText()+"��");
                zhu.chat.chatarea.append("�Ѽ�����Ϸ���ȴ��Է�����...\n");
            }

        }else if(recMessage.startsWith("/continue")){
        	 if (isServer) {
        	zhu.chat.chatarea.append("������λ�����ӣ����������\n");
        	isMouseEnabled=true;
        	 }else if (isClient) {
        		 zhu.chat.chatarea.append("������λ�����ӣ����������\n");
             	isMouseEnabled=true; 
        	 }
       }else if (recMessage.equals("/youwin")) {
            isOnChess = false;
            zhu.scr.chessvictory(0);
            isMouseEnabled = false;
        } else if (recMessage.equals("/OK")) {
            zhu.chat.chatarea.append("������Ϸ�ɹ����ȴ����˼���...\n");
        } else if (recMessage.equals("/error")) {
             ch.chatLineArea.append("����������˳��������¼��� \n");
        } else if(recMessage.startsWith("/chess")){
			StringTokenizer usertoken=new StringTokenizer(recMessage," ");
			String chesstoken;
			String[]chessopt={"-1","1","0"};
			int chessoptnum=0;
			while(usertoken.hasMoreTokens()){
				chesstoken=(String)usertoken.nextToken(" ");
				if(chessoptnum>=1&&chessoptnum<=3){
					chessopt[chessoptnum-1]=chesstoken;
				}chessoptnum++;
			}
			zhu.scr.netchesspaint(Integer.parseInt(chessopt[0]),Integer.parseInt(chessopt[1]),Integer.parseInt(chessopt[2]));
		}
        else {//��ͨ����
            ch.chatLineArea.append(recMessage + "\n");//zhu.chat.chatarea.append
            ch.chatLineArea .setCaretPosition( ch.chatLineArea .getText().length()); //  zhu.chat.chatarea
            if(isOnChess) zhu.chat.chatarea.append(recMessage + "\n");
        }
    }
	public void actionPerformed(ActionEvent e) {
	     ////////////////////////////////////////////////////////////
        // ���������ǡ�������Ϸ����ť���趨�û�״̬����ť״̬��Ȼ���������ͨѶ��
	     if (e.getSource() == trol.creatbutton) {
            try {
                // δ��������ʱ�Ĳ�����
                        isOnChess = true;
                        isServer = true;
                   
                       //zhu.wanjia1.xingming.setText(user.xingming.getText());
                       //zhu.wanjia1.dengji.setText(user.dengji.getText());
                      // this.setVisible(false);
                       
                       zhu.chat.chatarea.setText(ch.chatLineArea.getText());
                 zhu.setVisible(true);
                 if(chessClientName.startsWith("[inchess]"))
                 out.writeUTF("/creatgame " + chessClientName);
                 else     out.writeUTF("/creatgame " +"[inchess]" + chessClientName);
                }
             catch (Exception ec) {
               
                isOnChess = false;
                isServer = false;
            
                ec.printStackTrace();
                ch.chatLineArea.append("chesspad.connectServer�޷����� \n" + ec);
            }

        }
	     else if (e.getSource() == trol.joinbutton) {
            // �õ�ѡ����û�
	    	
            String selectedUser = use.userList.getSelectedItem();
            if (selectedUser == null|| selectedUser.equals(chessClientName)) {// || selectedUser.startsWith("[inchess]") 
            
            	 ch.chatLineArea.append("������ѡ��һ����Ч�û�\n");
            } else {
            	chessPeerName= use.userList.getSelectedItem();
                try{ isOnChess = true;
                        isClient = true;
                        sendMessage("/joingame " + use.userList.getSelectedItem() + " " + chessClientName);
   
                        //zhu.wanjia1.xingming.setText(zhu.wanjia1.xingming.getText()+chessPeerName);
                        //zhu.wanjia1.dengji.setText(zhu.wanjia1.dengji.getText());
                        //zhu.wanjia2.xingming.setText(zhu.wanjia2.xingming.getText()+chessClientName);
                        //zhu.wanjia2.dengji.setText(zhu.wanjia2.dengji.getText());
                        zhu.chat.chatarea.setText(ch.chatLineArea.getText());
                        zhu.setVisible(true);  
                        }
                 catch (Exception ee) {//������Ϸ ʧ�ܡ���
                   
                    isOnChess = false;
                    isClient = false;
                    
                    ch.chatLineArea.append("chesspad.connectServer�޷����� \n" + ee);}}}
    else if (e.getSource() == trol.exitbutton) {
            System.exit(0);}}}