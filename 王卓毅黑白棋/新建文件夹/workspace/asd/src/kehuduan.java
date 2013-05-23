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
              // 利用由socket提供的输出流向服务器发送信息
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
     * 客户端线程对接收到的信息进行处理的函数
     */
    public void acceptMessage(String recMessage) {
        if (recMessage.startsWith("/userlist ")) {
            // 如果接收到的信息以"/userlist "开头，将其后的用户名提取出来，添加到
            // 输入信息Panel左边的用户列表中。
        	
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
            // 如果接收到的信息以"/userlist "开头，将其后的用户名提取出来，添加到
            // 输入信息Panel左边的用户列表中。
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
        // 如果如果接收到的信息以"/yourname "开头,将用户名显示在客户端对话框标题栏。
        else if (recMessage.startsWith("/yourname ")) {
            //之所以是10因为“/yourname ”正好10个字符，后面的是用户名。。
            chessClientName = recMessage.substring(10);
            setTitle("网络黑白棋客户端 " + "当前用户名:" + chessClientName);
        }
        else if(recMessage.startsWith("/huiqi")){
        	for(int t=0;t<8;t++) for(int s=0;s<8;s++) zhu.scr.qi[t][s]=zhu.scr.y[t][s];zhu.scr.repaint();
        }
        // 如果如果接收到的信息以"/reject"开头，在状态栏显示拒绝加入游戏。
        else if (recMessage.equals("/reject")) {
            try {
                ch.chatLineArea.append("不能加入游戏");
                //更新按钮状态
                
            } catch (Exception ef) {
                ch.chatLineArea.append("chessclient.chesspad.chessSocket.close无法关闭");
            }
         
        }
        // 如果如果接收到的信息以"/peer"开头,则记下对方的名字，然后进入等待状态
        else if (recMessage.startsWith("/peer ")) { 
       chessPeerName=recMessage.substring(6);
         //zhu.wanjia2.xingming.setText(  zhu.wanjia2.xingming.getText().substring(0,3)+recMessage.substring(6));
            if (isServer) {//黑子先行
                chessColor = 1;
                //zhu.wanjia1.yanse.setText(zhu.wanjia1.yanse.getText()+"黑");
                //zhu.wanjia2.yanse.setText(zhu.wanjia2.yanse.getText()+"白");
                isMouseEnabled = true;
                zhu.chat.chatarea.append("请黑棋下子\n");
            } else if (isClient) {
                chessColor = -1;
                //zhu.wanjia2.yanse.setText(zhu.wanjia2.yanse.getText()+"白");
                //zhu.wanjia1.yanse.setText(zhu.wanjia1.yanse.getText()+"黑");
                zhu.chat.chatarea.append("已加入游戏，等待对方下子...\n");
            }

        }else if(recMessage.startsWith("/continue")){
        	 if (isServer) {
        	zhu.chat.chatarea.append("白子无位可落子，请黑子落子\n");
        	isMouseEnabled=true;
        	 }else if (isClient) {
        		 zhu.chat.chatarea.append("黑子无位可落子，请白子落子\n");
             	isMouseEnabled=true; 
        	 }
       }else if (recMessage.equals("/youwin")) {
            isOnChess = false;
            zhu.scr.chessvictory(0);
            isMouseEnabled = false;
        } else if (recMessage.equals("/OK")) {
            zhu.chat.chatarea.append("创建游戏成功，等待别人加入...\n");
        } else if (recMessage.equals("/error")) {
             ch.chatLineArea.append("传输错误：请退出程序，重新加入 \n");
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
        else {//普通聊天
            ch.chatLineArea.append(recMessage + "\n");//zhu.chat.chatarea.append
            ch.chatLineArea .setCaretPosition( ch.chatLineArea .getText().length()); //  zhu.chat.chatarea
            if(isOnChess) zhu.chat.chatarea.append(recMessage + "\n");
        }
    }
	public void actionPerformed(ActionEvent e) {
	     ////////////////////////////////////////////////////////////
        // 如果点击的是“创建游戏”按钮，设定用户状态、按钮状态，然后与服务器通讯。
	     if (e.getSource() == trol.creatbutton) {
            try {
                // 未建立连接时的操作。
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
                ch.chatLineArea.append("chesspad.connectServer无法连接 \n" + ec);
            }

        }
	     else if (e.getSource() == trol.joinbutton) {
            // 得到选择的用户
	    	
            String selectedUser = use.userList.getSelectedItem();
            if (selectedUser == null|| selectedUser.equals(chessClientName)) {// || selectedUser.startsWith("[inchess]") 
            
            	 ch.chatLineArea.append("必须先选定一个有效用户\n");
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
                 catch (Exception ee) {//加入游戏 失败。。
                   
                    isOnChess = false;
                    isClient = false;
                    
                    ch.chatLineArea.append("chesspad.connectServer无法连接 \n" + ee);}}}
    else if (e.getSource() == trol.exitbutton) {
            System.exit(0);}}}