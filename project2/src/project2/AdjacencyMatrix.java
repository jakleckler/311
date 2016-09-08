package project2;

public class AdjacencyMatrix {
	int k;
	int[][] a;

	public AdjacencyMatrix(int k) {
		this.k = k;
		a = new int[k][k];
		for (int n = 1; n < a.length; n++) {
			for (int m = 1; m < a.length; m++) {
				if (n == m) {
					a[n][m] = 0;
				} else {
					// there is no edge when the number is this high
					a[n][m] = 1500;
				}
			}
		}
	}

	public void update(int i, int j, int t) {
		a[i][j] = t;
	}

	public void floyd() {
		int i, j, k, through_k;
		for (k = 1; k < a.length; k++) {
			for (i = 1; i < a.length; i++) {
				for (j = 1; j < a.length; j++) {
					through_k = a[i][k] + a[k][j];
					if (through_k < a[i][j]) {
						a[i][j] = through_k;
					}

				}
			}
		}
		processMatrix();
	}

	public void processMatrix() {
		boolean in = false, out = false;
		boolean[] disjoint = new boolean[a.length];
		int[] totalTime = new int[a.length];
		int minTime = 1500;
		int startNode = 0;
		int time;

		//printMatrix();
		for (int i = 1; i < a.length; i++) {
			for (int j = 1; j < a.length; j++) {
				if (a[i][j] == 1500) {
					disjoint[i] = true;
				} else {
					totalTime[i] += a[i][j];
				}
			}
		}
		for (int l = 1; l < a.length; l++) {
			if (!disjoint[l]) {
				if (totalTime[l] < minTime) {
					startNode = l;
					minTime = totalTime[l];
				}
			}
		}
		time = findMax(startNode);
		System.out.println(startNode + " " + time);
	}

	public int findMax(int i) {
		int max = 0;
		for (int j = 1; j < a.length; j++) {
			if (a[i][j] == 1500) {
				// this is not an edge
			} else {
				if (max < a[i][j]) {
					max = a[i][j];
				}
			}
		}
		return max;
	}

	public void printMatrix() {
		System.out.println("Matrix: ");
		System.out.println(printdashes(a.length * 2 + 2));
		for (int n = 0; n < a.length; n++) {
			System.out.print("|" + n + "|");
			for (int m = 1; m < a.length; m++) {
				if (n == 0) {

					System.out.print(m + "|");

				} else {
					System.out.print(a[n][m] + "|");
				}
			}
			System.out.println();
			System.out.println(printdashes(a.length * 2 + 2));
		}
		System.out.println();
	}

	public String printdashes(int n) {
		String dashes = "";
		for (int i = 0; i < n; i++) {
			dashes += "-";
		}
		return dashes;
	}

}
