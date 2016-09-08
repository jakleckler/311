import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int h;
		Scanner in = new Scanner(System.in);
		triangle t;
		ArrayList<triangle> tlist = new ArrayList<triangle>();
		while (in.hasNext()) {
			h = in.nextInt();
			
				
				t = new triangle(h);
				for (int j = 0; j < h; j++) {
					for (int k = 0; k < j + 1; k++) {
						t.addElement(j, k, in.nextInt());
					}
				}
				tlist.add(t);
//				t.init();
//				t.bestPath();
			
		}
		for (int i = 0; i <tlist.size();i++) {
			tlist.get(i).init();
			tlist.get(i).bestPath();
		}
		in.close();
		System.exit(0);

	}

}
