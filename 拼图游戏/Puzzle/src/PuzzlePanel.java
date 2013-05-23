import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.*;



public class PuzzlePanel extends JPanel implements MouseListener{
	private MyIcon[] myicon=new MyIcon[9];
	private MyIcon blankplace;
	
	public PuzzlePanel(){
		this.setLayout(null);
		init();
	}

	private void init() {
		Icon icon=null;
		int num=0;
		MyIcon cell;
		//ʵ����myicons��ȷ��λ�ú�ͼƬ��
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				num=i*3+j;
				icon=new ImageIcon("src\\pic\\"+(num+1)+".jpg");
				cell=new MyIcon(icon,num);
				cell.setLocation(j*cell.ICONWIDTH,i*cell.ICONWIDTH);
				myicon[num]=cell;
			}
		}
		//��ӵ������
		for(int i=0;i<myicon.length;i++){
			this.add(myicon[i]);
		}
		
	}
	//��Ӽ�����������ͼƬ��˳��
	public void random(){
		Random random=new Random();
		int m,n,x,y;
		if(blankplace==null){
			blankplace=myicon[myicon.length-1];
			for(int i=0;i<myicon.length;i++){//
				if(i!=myicon.length){//���հ�ͼƬ����Ӽ�������
					myicon[i].addMouseListener(this);
				}
			}
		}
		for(int i=0;i<myicon.length;i++){
			//�漴����������ť��������λ�ã�ѭ��9�Ρ�
			 m=random.nextInt(myicon.length);
			 n=random.nextInt(myicon.length);
			 x=myicon[m].getX();
			 y=myicon[m].getY();
			 myicon[m].setLocation(myicon[n].getX(),myicon[n].getY());
			 myicon[n].setLocation(x,y);
		}
	}


	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void mousePressed(MouseEvent e) {
		MyIcon icons=(MyIcon) e.getSource();
		int x=blankplace.getX();
		int y=blankplace.getY();
		//����
		if(x-icons.getX()==icons.ICONWIDTH&&y==icons.getY()){
			icons.move(MyIcon.Direction.RIGHT);
			blankplace.move(MyIcon.Direction.LEFT);
		}
		//����
		else if(x-icons.getX()==-icons.ICONWIDTH&&y==icons.getY()){
			icons.move(MyIcon.Direction.LEFT);
			blankplace.move(MyIcon.Direction.RIGHT);
		}
		//����
		else if(y-icons.getY()==icons.ICONWIDTH&&x==icons.getX()){
			icons.move(MyIcon.Direction.DOWN);
			blankplace.move(MyIcon.Direction.UP);
		}
		//����
		else if(y-icons.getY()==-icons.ICONWIDTH&&x==icons.getX()){
			icons.move(MyIcon.Direction.UP);
			blankplace.move(MyIcon.Direction.DOWN);
		}
		//
		if(isSuccess()){
			int i=JOptionPane.showConfirmDialog(this, "�ɹ�������һ�֣�","ƴͼ�ɹ�!",JOptionPane.YES_NO_OPTION);
			if(i==JOptionPane.YES_OPTION){
				random();
			}
		}
		
		
	}

	//�Ƚ�ÿ��ͼƬ��λ���Ƿ����������ͬ��
	private boolean isSuccess() {
		for(int i=0;i<myicon.length;i++){
			int x=myicon[i].getX();
			int y=myicon[i].getY();
			if(y*3/myicon[i].ICONWIDTH+x/myicon[i].ICONWIDTH!=myicon[i].getPlace())//
				return false;
		}
		return true;
	}

	public void mouseReleased(MouseEvent arg0) {
	
		
	}

}
