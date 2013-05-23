import java.io.IOException;
import java.util.StringTokenizer;
public class qipanThread extends Thread{
	Qi qipan;
	public qipanThread(Qi qipan){
		this.qipan=qipan;
	}
	/*发送消息*/
	public void send(String fasong){
		try{
			qipan.outDate.writeUTF(fasong);//通过对战面板的DataOutputStream对象的writeUTF方法将信息写给对方
			
		}
		catch(Exception e){
			System.out.println("qipanThread.send:"+e);
		}
	}
	/*接受消息*/
	public void accept(String jieshou){
		//若接受的消息以"/fight"开头，将里面的坐标和颜色提出来
		if(jieshou.startsWith("/fight")){
			StringTokenizer user=new StringTokenizer(jieshou," ");
			String chessToken;
			String []chessopt={"-1","-1","0"};
			int chessoptnum=0;
			while(user.hasMoreTokens()){
				chessToken=(String)user.nextToken(" ");
				if(chessoptnum>=1&&chessoptnum<=3){
					chessopt[chessoptnum-1]=chessToken;
				}
				chessoptnum++;
			}
			//将己方的走棋信息如棋子摆放的位置，棋子的颜色等参数，使用netchesspaint函数
			//使对方客户端也能看到己方的落子位置
			qipan.netchesspaint(Integer.parseInt(chessopt[0]),Integer.parseInt(chessopt[1]),Integer.parseInt(chessopt[2]));
			
		}
	}
	public void run(){
		String message="";
		try{
			while(true){//线程体通过DateoutPutStream流将发过来的信息读取出来
				message=qipan.inDate.readUTF();
				accept(message);
				
			}
		}catch(IOException e){
			
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
