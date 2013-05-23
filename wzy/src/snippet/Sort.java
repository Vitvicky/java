package snippet;
import java.util.*;
public class Sort {
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int a[]=new int[5];
		Scanner scan=new Scanner(System.in);
		for(int i=0;i<5;i++){
			a[i]=scan.nextInt();
		}
		Arrays.sort(a);
		for (int i = 0; i < 5; i++) {
			System.out.println(a[i]);
			
		}

	}

}