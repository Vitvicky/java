import java.io.IOException;
import java.util.StringTokenizer;
public class qipanThread extends Thread{
	Qi qipan;
	public qipanThread(Qi qipan){
		this.qipan=qipan;
	}
	/*������Ϣ*/
	public void send(String fasong){
		try{
			qipan.outDate.writeUTF(fasong);//ͨ����ս����DataOutputStream�����writeUTF��������Ϣд���Է�
			
		}
		catch(Exception e){
			System.out.println("qipanThread.send:"+e);
		}
	}
	/*������Ϣ*/
	public void accept(String jieshou){
		//�����ܵ���Ϣ��"/fight"��ͷ����������������ɫ�����
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
			//��������������Ϣ�����Ӱڷŵ�λ�ã����ӵ���ɫ�Ȳ�����ʹ��netchesspaint����
			//ʹ�Է��ͻ���Ҳ�ܿ�������������λ��
			qipan.netchesspaint(Integer.parseInt(chessopt[0]),Integer.parseInt(chessopt[1]),Integer.parseInt(chessopt[2]));
			
		}
	}
	public void run(){
		String message="";
		try{
			while(true){//�߳���ͨ��DateoutPutStream��������������Ϣ��ȡ����
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
