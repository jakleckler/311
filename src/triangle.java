
public class triangle {
	private int height;
	private int[][] triangle;
	private int[][] sum;
	private int[] path;
	
	public triangle(int h) {
		height = h;
		triangle = new int[h][h];
		sum = new int[h][h];
		path = new int[h];
	}
	
	public void addElement(int x, int y, int n) {
		triangle[x][y] = n;
	}
	
	public void init() {
		sum[0][0] = triangle[0][0];
	}
	
	public void bestPath() {
		int largestSum = findSum(0,0);
		int largestRow = findSum(0,0);
		path[0] = triangle[0][0];
		int next;
		int lrx;
		int lry;
		for (int r = 1; r < height; r++) {
			largestRow = 0;
			lrx = r;
			lry = 0;
			for (int c = 0; c <= r; c++) {
				next = findSum(r, c);
				if (next > largestRow) {
					largestRow = next;
					lrx = r;
					lry = c;
				}
			}
			largestSum = largestRow;
			path[r] = triangle[lrx][lry];
			
			
		}
		System.out.println("Max is " + largestSum);
		System.out.print(path[0]);
		for (int i = 1; i < path.length; i++) {
			System.out.print("-->" + path[i]);
		}
		System.out.println();
	}
	
	public int findSum(int r, int c) {
		if (r < 0 || c < 0) {
			return 0;
		}
		if (sum[r][c] == 0) {
			sum[r][c] = Math.max((findSum(r-1, c-1) + triangle[r][c]), (findSum(r-1, c) + triangle[r][c]));
		}
		
		return sum[r][c];
	}
}
