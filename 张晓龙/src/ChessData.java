
public class ChessData {
	public int[][] chess,chess_1,chess_2;
	public int b_or_w=1;
	public int num_of_b=0;
	public int num_of_w=0;
	//0--blank	1--white	2--black	9--white_or_black_ok
	{
		chess=new int[8][8];
		chess_1=new int[8][8];
		chess_2=new int[8][8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				chess[i][j]=0;
			}
		}
			chess[3][3]=1;
			chess[3][4]=2;
			chess[4][4]=1;
			chess[4][3]=2;
			/*chess[0][0]=1;
			chess[1][0]=1;
			chess[2][0]=1;
			chess[3][0]=1;
			chess[4][0]=2;
			chess[5][0]=0;
			chess[3][1]=1;
			chess[3][2]=1;
			chess[3][3]=1;
			chess[3][4]=1;
			chess[4][1]=1;
			chess[4][3]=1;
			chess[0][1]=0;
			chess[1][1]=1;
			chess[2][1]=2;
			chess[5][1]=2;
			chess[1][2]=2;
			chess[2][2]=2;
			chess[4][2]=2;
			chess[5][2]=2;
			chess[1][3]=2;
			chess[2][3]=2;
			chess[5][3]=2;
			chess[1][4]=2;
			chess[2][4]=2;
			chess[4][4]=2;
			chess[5][4]=2;
			chess[1][5]=2;
			chess[2][5]=2;
			chess[3][5]=2;
			chess[4][5]=2;
			chess[5][5]=2;
			chess[6][5]=2;
			chess[1][6]=2;
			chess[2][6]=2;*/
			/*chess[3][2]=2;
			chess[4][2]=2;
			chess[5][2]=2;
			chess[3][3]=1;
			chess[4][3]=1;
			chess[5][3]=1;*/
		
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				chess_1[i][j]=1;
			}
		}
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				chess_2[i][j]=2;
			}
		}
	}
	
	public int[][] confirm_you(int[][] chess , int a , int b){
		int sum=0;
		int tem=0;
		int[][] chess2= new int[8][8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				chess2[i][j]=chess[i][j];
			}
		}
		if(b_or_w==1){
			for(int i=b+1;i<8;i++){
				sum+=chess[a][i];
				if(chess[a][i]==1){
					tem=i;
					break;
				}
			}
			if(tem-b-1==0){
				return chess;
			}else{
				if(sum!=((tem-b-1)*2+1)){
					return chess;
				}else{
					for(int j=b;j<tem;j++){
						chess2[a][j]=1;
					}
					return chess2;
				}
			}
			
		}
		else{
			for(int i=b+1;i<8;i++){
				sum+=chess[a][i];
				if(chess[a][i]==2){
					tem=i;
					break;
				}
			}
			if(tem-b-1==0){
				return chess;
			}else{
				if(sum!=((tem-b-1)*1+2)){
					return chess;
				}else{
					for(int j=b;j<tem;j++){
						chess2[a][j]=2;
					}
					return chess2;
				}
			}
			
		}
	}
	
	public int[][] confirm_zuo(int[][] chess , int a , int b){
		int sum=0;
		int tem=0;
		int[][] chess2= new int[8][8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				chess2[i][j]=chess[i][j];
			}
		}
		if(b_or_w==1){
			for(int i=b-1;i>=0;i--){
				sum+=chess[a][i];
				if(chess[a][i]==1){
					tem=i;
					break;
				}
			}
			if(b-tem-1==0){
				return chess;
			}else{
				if(sum!=((b-tem-1)*2+1)){
					return chess;
				}else{
					for(int j=b;j>=tem;j--){
						chess2[a][j]=1;
					}
					return chess2;
				}
			}
			
		}
		else{
			for(int i=b-1;i>=0;i--){
				sum+=chess[a][i];
				if(chess[a][i]==2){
					tem=i;
					break;
				}
			}
			if(b-tem-1==0){
				return chess;
			}else{
				if(sum!=((b-tem-1)*1+2)){
					return chess;
				}else{
					for(int j=b;j>=tem;j--){
						chess2[a][j]=2;
					}
					return chess2;
				}
			}
			
		}
	}
	
	public int[][] confirm_shang(int[][] chess , int a , int b){
		int sum=0;
		int tem=0;
		int[][] chess2= new int[8][8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				chess2[i][j]=chess[i][j];
			}
		}
		if(b_or_w==1){
			for(int i=a-1;i>=0;i--){
				sum+=chess[i][b];
				if(chess[i][b]==1){
					tem=i;
					break;
				}
			}
			if(a-tem-1==0){
				return chess;
			}else{
				if(sum!=((a-tem-1)*2+1)){
					return chess;
				}else{
					for(int j=a;j>=tem;j--){
						chess2[j][b]=1;
					}
					return chess2;
				}
			}
			
		}
		else{
			for(int i=a-1;i>=0;i--){
				sum+=chess[i][b];
				if(chess[i][b]==2){
					tem=i;
					break;
				}
			}
			if(a-tem-1==0){
				return chess;
			}else{
				if(sum!=((a-tem-1)*1+2)){
					return chess;
				}else{
					for(int j=a;j>=tem;j--){
						chess2[j][b]=2;
					}
					return chess2;
				}
			}
			
		}
	}
	
	
	public int[][] confirm_xia(int[][] chess , int a , int b){
		int sum=0;
		int tem=0;
		int[][] chess2= new int[8][8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				chess2[i][j]=chess[i][j];
			}
		}
		if(b_or_w==1){
			for(int i=a+1;i<8;i++){
				sum+=chess[i][b];
				if(chess[i][b]==1){
					tem=i;
					break;
				}
			}
			if(tem-a-1==0){
				return chess;
			}else{
				if(sum!=((tem-a-1)*2+1)){
					return chess;
				}else{
					for(int j=a;j<tem;j++){
						chess2[j][b]=1;
					}
					return chess2;
				}
			}
			
		}
		else{
			for(int i=a+1;i<8;i++){
				sum+=chess[i][b];
				if(chess[i][b]==2){
					tem=i;
					break;
				}
			}
			if(tem-a-1==0){
				return chess;
			}else{
				if(sum!=((tem-a-1)*1+2)){
					return chess;
				}else{
					for(int j=a;j<tem;j++){
						chess2[j][b]=2;
					}
					return chess2;
				}
			}
			
		}
	}
	
	
	public int[][] confirm_zuoshang(int[][] chess , int a , int b){
		int sum=0;
		int tem=0;
		int c=0;
		int[][] chess2= new int[8][8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				chess2[i][j]=chess[i][j];
			}
		}
		if(b_or_w==1){
			if(a>b){
				c=b;
			}else{
				c=a;
			}
			for(int i=1;i<=c;i++){
				sum+=chess[a-i][b-i];
				if(chess[a-i][b-i]==1){
					tem=i;
					break;
				}
			}
			if(tem-1==0){
				return chess;
			}else{
				if(sum!=((tem-1)*2+1)){
					return chess;
				}else{
					for(int j=0;j<tem;j++){
						chess2[a-j][b-j]=1;
					}
					return chess2;
				}
			}
			
		}
		else{
			if(a>b){
				c=b;
			}else{
				c=a;
			}
			for(int i=1;i<=c;i++){
				sum+=chess[a-i][b-i];
				if(chess[a-i][b-i]==2){
					tem=i;
					break;
				}
			}
			if(tem-1==0){
				return chess;
			}else{
				if(sum!=((tem-1)*1+2)){
					return chess;
				}else{
					for(int j=0;j<tem;j++){
						chess2[a-j][b-j]=2;
					}
					return chess2;
				}
			}
			
		}
	}
	
	public int[][] confirm_youxia(int[][] chess , int a , int b){
		int sum=0;
		int tem=0;
		int c=0;
		int[][] chess2= new int[8][8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				chess2[i][j]=chess[i][j];
			}
		}
		if(b_or_w==1){
			if((8-a)<(8-b)){
				c=8-a;
			}else{
				c=8-b;
			}
			for(int i=1;i<c;i++){
				sum+=chess[a+i][b+i];
				if(chess[a+i][b+i]==1){
					tem=i;
					break;
				}
			}
			if(tem-1==0){
				return chess;
			}else{
				if(sum!=((tem-1)*2+1)){
					return chess;
				}else{
					for(int j=0;j<tem;j++){
						chess2[a+j][b+j]=1;
					}
					return chess2;
				}
			}
			
		}
		else{
			if((8-a)<(8-b)){
				c=8-a;
			}else{
				c=8-b;
			}
			for(int i=1;i<c;i++){
				sum+=chess[a+i][b+i];
				if(chess[a+i][b+i]==2){
					tem=i;
					break;
				}
			}
			if(tem-1==0){
				return chess;
			}else{
				if(sum!=((tem-1)*1+2)){
					return chess;
				}else{
					for(int j=0;j<tem;j++){
						chess2[a+j][b+j]=2;
					}
					return chess2;
				}
			}
			
		}
	}
	
	public int[][] confirm_youshang(int[][] chess , int a , int b){
		int sum=0;
		int tem=0;
		int c=0;
		int[][] chess2= new int[8][8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				chess2[i][j]=chess[i][j];
			}
		}
		if(b_or_w==1){
			if(a>(8-b)){
				c=8-b;
			}else{
				c=a;
			}
			for(int i=1;i<c;i++){
				sum+=chess[a-i][b+i];
				if(chess[a-i][b+i]==1){
					tem=i;
					break;
				}
			}
			if(tem-1==0){
				return chess;
			}else{
				if(sum!=((tem-1)*2+1)){
					return chess;
				}else{
					for(int j=0;j<tem;j++){
						chess2[a-j][b+j]=1;
					}
					return chess2;
				}
			}
			
		}
		else{
			if(a>(8-b)){
				c=8-b;
			}else{
				c=a;
			}
			for(int i=1;i<c;i++){
				sum+=chess[a-i][b+i];
				if(chess[a-i][b+i]==2){
					tem=i;
					break;
				}
			}
			if(tem-1==0){
				return chess;
			}else{
				if(sum!=((tem-1)*1+2)){
					return chess;
				}else{
					for(int j=0;j<tem;j++){
						chess2[a-j][b+j]=2;
					}
					return chess2;
				}
			}
			
		}
	}
	
	
	public int[][] confirm_zuoxia(int[][] chess , int a , int b){
		int sum=0;
		int tem=0;
		int c=0;
		int[][] chess2= new int[8][8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				chess2[i][j]=chess[i][j];
			}
		}
		if(b_or_w==1){
			if((8-a)<b){
				c=8-a;
			}else{
				c=b;
			}
			for(int i=1;i<c;i++){
				sum+=chess[a+i][b-i];
				if(chess[a+i][b-i]==1){
					tem=i;
					break;
				}
			}
			if(tem-1==0){
				return chess;
			}else{
				if(sum!=((tem-1)*2+1)){
					return chess;
				}else{
					for(int j=0;j<tem;j++){
						chess2[a+j][b-j]=1;
					}
					return chess2;
				}
			}
			
		}
		else{
			if((8-a)<b){
				c=8-a;
			}else{
				c=b;
			}
			for(int i=1;i<c;i++){
				sum+=chess[a+i][b-i];
				if(chess[a+i][b-i]==2){
					tem=i;
					break;
				}
			}
			if(tem-1==0){
				return chess;
			}else{
				if(sum!=((tem-1)*1+2)){
					return chess;
				}else{
					for(int j=0;j<tem;j++){
						chess2[a+j][b-j]=2;
					}
					return chess2;
				}
			}
			
		}
	}
	
	
	
	public int[][] confirm(int[][] chess , int a , int b){
		int[][] chess2= new int[8][8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				chess2[i][j]=chess[i][j];
				if(chess2[i][j]==9){
					chess2[i][j]=0;
				}
			}
		}
		if (chess[a][b]==1){
			return this.chess_1;
		}else{
			if(chess[a][b]==2){
				return this.chess_2;
			}else{
				chess2=this.confirm_shang(chess2, a, b);
				chess2=this.confirm_xia(chess2, a, b);
				chess2=this.confirm_zuo(chess2, a, b);
				chess2=this.confirm_you(chess2, a, b);
				chess2=this.confirm_zuoshang(chess2, a, b);
				chess2=this.confirm_youshang(chess2, a, b);
				chess2=this.confirm_zuoxia(chess2, a, b);
				chess2=this.confirm_youxia(chess2, a, b);
				return chess2;
			}
		}
		
	}
	
	
	public Boolean compare(int[][] chess2){
		int tem=0;
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(this.chess[i][j]==chess2[i][j]){
					tem++;
				}
			}
		}
		if(tem==64){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean compare2(int[][] chess2,int[][] chess3){
		int tem=0;
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(chess2[i][j]==chess3[i][j]){
					tem++;
				}
			}
		}
		if(tem==64){
			return true;
		}else{
			return false;
		}
	}
	
	public int[][] check(int[][] chess){
		if(this.b_or_w==1){
			this.b_or_w=2;
		}else{
			this.b_or_w=1;
		}
		
		int[][] chess2= new int[8][8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(chess[i][j]==9){
					chess[i][j]=0;
				}
				chess2[i][j]=chess[i][j];
			}
		}
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(chess2[i][j]==0||chess2[i][j]==9){
					chess2=this.confirm(chess2, i, j);
				}
			}
		}
		if(this.b_or_w==1){
			this.b_or_w=2;
		}else{
			this.b_or_w=1;
		}
		return chess2;
	}
	
	public int[][] check2(int[][] chess){
		if(this.b_or_w==1){
			this.b_or_w=2;
		}else{
			this.b_or_w=1;
		}
		
		int[][] chess2= new int[8][8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(chess[i][j]==9){
					chess[i][j]=0;
				}
				chess2[i][j]=chess[i][j];
			}
		}
		int[][] chess4= new int[8][8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(chess[i][j]==9){
					chess[i][j]=0;
				}
				chess4[i][j]=chess[i][j];
			}
		}
		int[][] chess3= new int[8][8];
		
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(chess2[i][j]==0){
					chess3=this.confirm(chess2, i, j);
					if(this.compare2(chess2, chess3)){
						chess4[i][j]=0;
					}else{
						chess4[i][j]=9;
					}
				}
			}
		}
		if(this.b_or_w==1){
			this.b_or_w=2;
		}else{
			this.b_or_w=1;
		}
		return chess4;
	}
	
	public void checkback(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(this.chess[i][j]==9){
					this.chess[i][j]=0;
				}
			}
		}
	}
	
	public int confirmwin(){
		int nums_of_blank=0;
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(this.chess[i][j]==9||this.chess[i][j]==0){
					nums_of_blank++;
				}
			}
		}
		return nums_of_blank;
	}
	
	public void count(){
		int tem1=0;
		int tem2=0;
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(this.chess[i][j]==2){
					tem1++;
				}
				if(this.chess[i][j]==1){
					tem2++;
				}
			}
		}
		this.num_of_b=tem1;
		this.num_of_w=tem2;
	}
	
	
	
	public void show(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				System.out.print(this.chess[i][j]+"\t");
			}
			System.out.println();
		}
	}
	
	public void show(int[][] chess){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				System.out.print(chess[i][j]+"\t");
			}
			System.out.println();
		}
	}
	
	public void reset(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				chess[i][j]=0;
			}
		}
			chess[3][3]=1;
			chess[3][4]=2;
			chess[4][4]=1;
			chess[4][3]=2;
			b_or_w=1;
	}
	
	public String toString(){
		String tem=new String("");
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				tem=tem+(chess[i][j]+"\t");
			}
			tem=tem+("\n");
		}
		return tem;
	}
}
