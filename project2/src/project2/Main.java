package project2;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int k = 0, i = 0, j = 0, c = 0, t = 0;
		//System.out.println("File: ");
		Scanner in = new Scanner(System.in);
//		String data = input.next();
//		input.close();
//		Scanner in = new Scanner(data);
		while (in.hasNext()) {
			k = in.nextInt();
			if (k == 0) {
				in.close();
				System.exit(0);
			} else {
				AdjacencyMatrix matrix = new AdjacencyMatrix(k+1);
				for (i = 1; i <= k; i++) {
					c = in.nextInt();
					for (int m = 0; m < c; m++) {
						j = in.nextInt();
						t = in.nextInt();
						matrix.update(i, j, t);
						
					}
				}
				matrix.floyd();
			}
		}
	}

}
