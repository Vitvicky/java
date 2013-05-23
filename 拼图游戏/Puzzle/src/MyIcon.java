import java.awt.Rectangle;

import javax.swing.*;

public class MyIcon extends JButton{
  public static final int ICONWIDTH=117;
  private int place;//标记按钮序号
 
  public enum Direction{//枚举类，定义四个方向。
	  LEFT,UP,RIGHT,DOWN
  };
  
  public MyIcon(Icon icon,int place){
	 
	  this.place=place;
	  this.setIcon(icon);
	  this.setSize(ICONWIDTH,ICONWIDTH);
  }
  public void move(Direction dir){
	  Rectangle rec=this.getBounds();//得到外接四边形
	  switch(dir){
	  case LEFT:
		  this.setLocation(rec.x-this.ICONWIDTH,rec.y);
		  break;
	  case UP:
		  this.setLocation(rec.x,rec.y-this.ICONWIDTH);
		  break;
	  case RIGHT:
		  this.setLocation(rec.x+this.ICONWIDTH,rec.y);
		  break;
	  case DOWN:
		  this.setLocation(rec.x,rec.y+this.ICONWIDTH);
		  break;	  
	  }
  }
  
  public int getX(){
	  return this.getBounds().x;
  }
  public int getY(){
	  return this.getBounds().y;
  }
  public int getPlace(){
	  return place;
  }
}
