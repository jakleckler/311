public class HexagonMap {
	private int n; // max number of steps
	private int[][][] moves;
	private String[][][] hexagon;
	private final static int MAX_INT = 1000000;

	public boolean debug = false;

	public HexagonMap(int n) {
		this.n = n;
		// create an array value for each hexagon, which stores it's neighbors
		// in an array. 0 is above neighbor, continuing in a clockwise direction
		// storing neighbor location in String 'ls'.
		hexagon = new String[n / 2 + 1][n / 2 * 6][6];
		show("layers: " + n / 2 + 1);
		show("size: " + n / 2 * 6);
		moves = new int[n / 2 + 1][50][14];
		for (int i = 0; i < n / 2 + 1; i++) {
			for (int k = 0; k < 14; k++) {
				if (i == 0) {
					moves[i][0][k] = MAX_INT;
				}
				for (int j = 0; j < 6 * i; j++) {

					if (k < i) {
						moves[i][j][k] = 0;
					} else 
						moves[i][j][k] = MAX_INT;
				}
			}
		}
		connectMap();
	}

	public int calculatePaths(int n) {

		return countMoves(0, 0, n);

	}

	public int countMoves(int l, int i, int n) {

		if (n == 0 && l == 0 && i == 0) {
			moves[0][0][0] = 0;
			return 1;
		}
		if (n == 1 && l == 1) {
			moves[l][i][n] = 1;
			return 1;
		}
		if (n <= 0 && l != 0) {
			return 0;
		}
		if (n < l) {
			return 0;
		}
		if (moves[l][i][n] == MAX_INT) {
			moves[l][i][n] = 0;
			if (n >= l) {
				System.out.println("Looking at: (" + l + "," + i + "," + n + ")");
				for (int c = 0; c < 6; c++) {
					String location = hexagon[l][i][c];
					int x = Integer.parseInt(location.substring(0, 1));
					int y = Integer.parseInt(location.substring(1));
					System.out.println("--neighbor " + c + ": (" + x + "," + y + ")");

					moves[l][i][n] += countMoves(x, y, n - 1);
					//System.out.println("steps: " + (n-1) + " moves: " + moves[x][y][n - 1]);
				}
				System.out.println("----Giving us (" + l + "," + i + ", " + n + ") = "  + moves[l][i][n]);
			}
		}

		return moves[l][i][n];

	}

	public void connectMap() {

		int i = 0;
		show("hexagon size: " + hexagon.length);
		show("hexagon[0] size: " + hexagon[0].length);
		show("hexagon[0][0] size: " + hexagon[0][0].length);
		// l is the layer for the map, 0 is the start node, 1 is 1 step away and
		// so on
		for (int l = 0; l < hexagon.length; l++) {
			if (l == 0) {
				// s is each side of the hexagon
				for (int s = 0; s < 6; s++) {
					hexagon[0][0][s] = Integer.toString(l + 1) + Integer.toString(s);
				}
			} else {
				i = 0;
				for (int r = 0; r < 12; r++) {
					addConnection(r, l, i++);
					if (r % 2 == 0) {
						r++;
						for (int m = 0; m < (l - 1); m++) {
							addConnection(r, l, i++);
						}
					}
				}

			}
		}
	}

	public void addConnection(int r, int l, int i) {

		show("r :" + r);
		show("l :" + l);
		show("i :" + i);

		switch (r) {
		// top point of hexagon table
		case 0:

			if (l < 7) {
				hexagon[l][i][0] = Integer.toString(l + 1) + Integer.toString(0);
				hexagon[l][i][1] = Integer.toString(l + 1) + Integer.toString(1);
				hexagon[l][i][5] = Integer.toString(l + 1) + Integer.toString(((l + 1) * 6) - 1);

			}
			hexagon[l][i][4] = Integer.toString(l) + Integer.toString((l * 6) - 1);
			hexagon[l][i][3] = Integer.toString(l - 1) + Integer.toString(0);
			hexagon[l][i][2] = Integer.toString(l) + Integer.toString(i + 1);

			break;
		// left down slope
		case 1:
			if (l < 7) {
				hexagon[l][i][0] = Integer.toString(l + 1) + Integer.toString(i);
				hexagon[l][i][1] = Integer.toString(l + 1) + Integer.toString(i + 1);
			}
			hexagon[l][i][2] = Integer.toString(i) + Integer.toString((l * 6) - 1);
			hexagon[l][i][3] = Integer.toString(l - 1) + Integer.toString(i);
			hexagon[l][i][4] = Integer.toString(l - 1) + Integer.toString(i - 1);
			hexagon[l][i][5] = Integer.toString(l) + Integer.toString(i - 1);
			break;

		// top right point
		case 2:
			if (l < 7) {
				hexagon[l][i][0] = Integer.toString(l + 1) + Integer.toString(i);
				hexagon[l][i][1] = Integer.toString(l + 1) + Integer.toString(i + 1);
				hexagon[l][i][2] = Integer.toString(l + 1) + Integer.toString(i + 2);
			}
			hexagon[l][i][3] = Integer.toString(l) + Integer.toString(i + 1);
			hexagon[l][i][4] = Integer.toString(l - 1) + Integer.toString(i - 1);
			hexagon[l][i][5] = Integer.toString(l) + Integer.toString(i - 1);
			break;

		// downward cycle
		case 3:
			hexagon[l][i][0] = Integer.toString(l) + Integer.toString(i - 1);
			if (l < 7) {
				hexagon[l][i][1] = Integer.toString(l + 1) + Integer.toString(i + 1);
				hexagon[l][i][2] = Integer.toString(l + 1) + Integer.toString(i + 2);

			}
			hexagon[l][i][3] = Integer.toString(l) + Integer.toString(i + 1);
			hexagon[l][i][4] = Integer.toString(l - 1) + Integer.toString(i - 1);
			hexagon[l][i][5] = Integer.toString(l - 1) + Integer.toString(i - 2);
			break;
		// bottom right point
		case 4:
			hexagon[l][i][0] = Integer.toString(l) + Integer.toString(i - 1);
			if (l < 7) {
				hexagon[l][i][1] = Integer.toString(l + 1) + Integer.toString(i + 1);
				hexagon[l][i][2] = Integer.toString(l + 1) + Integer.toString(i + 2);
				hexagon[l][i][3] = Integer.toString(l) + Integer.toString(i + 3);
			}

			hexagon[l][i][4] = Integer.toString(l) + Integer.toString(i + 1);
			hexagon[l][i][5] = Integer.toString(l - 1) + Integer.toString(i - 2);
			break;
		// right down slope
		case 5:
			hexagon[l][i][0] = Integer.toString(l - 1) + Integer.toString(i - 3);
			hexagon[l][i][1] = Integer.toString(l) + Integer.toString(i - 1);

			if (l < 7) {
				hexagon[l][i][2] = Integer.toString(l + 1) + Integer.toString(i + 2);
				hexagon[l][i][3] = Integer.toString(l + 1) + Integer.toString(i + 3);

			}
			hexagon[l][i][4] = Integer.toString(l) + Integer.toString(i + 1);
			hexagon[l][i][5] = Integer.toString(l - 1) + Integer.toString(i - 2);
			break;
		// botton point
		case 6:
			hexagon[l][i][0] = Integer.toString(l - 1) + Integer.toString(i - 3);
			hexagon[l][i][1] = Integer.toString(l) + Integer.toString(i - 1);

			if (l < 7) {
				hexagon[l][i][2] = Integer.toString(l + 1) + Integer.toString(i + 2);
				hexagon[l][i][3] = Integer.toString(l + 1) + Integer.toString(i + 3);
				hexagon[l][i][4] = Integer.toString(l + 1) + Integer.toString(i + 4);

			}

			hexagon[l][i][5] = Integer.toString(l) + Integer.toString(i + 1);
			break;

		// left up slope
		case 7:
			hexagon[l][i][0] = Integer.toString(l - 1) + Integer.toString(i - 3);
			hexagon[l][i][1] = Integer.toString(l - 1) + Integer.toString(i - 4);
			hexagon[l][i][2] = Integer.toString(l) + Integer.toString(i - 1);
			if (l < 7) {

				hexagon[l][i][3] = Integer.toString(l + 1) + Integer.toString(i + 3);
				hexagon[l][i][4] = Integer.toString(l + 1) + Integer.toString(i + 4);

			}

			hexagon[l][i][5] = Integer.toString(l) + Integer.toString(i + 1);
			break;

		// bottom left point
		case 8:
			hexagon[l][i][0] = Integer.toString(l) + Integer.toString(i + 1);
			hexagon[l][i][1] = Integer.toString(l - 1) + Integer.toString(i - 4);
			hexagon[l][i][2] = Integer.toString(l) + Integer.toString(i - 1);
			if (l < 7) {

				hexagon[l][i][3] = Integer.toString(l + 1) + Integer.toString(i + 3);
				hexagon[l][i][4] = Integer.toString(l + 1) + Integer.toString(i + 4);
				hexagon[l][i][5] = Integer.toString(l + 1) + Integer.toString(i + 5);
			}

			break;

		// left up side
		case 9:
			hexagon[l][i][0] = Integer.toString(l) + Integer.toString(i + 1);
			hexagon[l][i][1] = Integer.toString(l - 1) + Integer.toString(i - 4);
			hexagon[l][i][2] = Integer.toString(l - 1) + Integer.toString(i - 5);
			hexagon[l][i][3] = Integer.toString(l + 1) + Integer.toString(i + 3);
			if (l < 7) {

				hexagon[l][i][4] = Integer.toString(l + 1) + Integer.toString(i + 4);
				hexagon[l][i][5] = Integer.toString(l + 1) + Integer.toString(i + 5);
			}

			break;

		// left top point
		case 10:

			hexagon[l][i][1] = Integer.toString(l) + Integer.toString(i + 1);
			hexagon[l][i][2] = Integer.toString(l - 1) + Integer.toString(i - 5);
			hexagon[l][i][3] = Integer.toString(l) + Integer.toString(i - 1);
			if (l < 7) {

				hexagon[l][i][0] = Integer.toString(l + 1) + Integer.toString(i + 6);
				hexagon[l][i][4] = Integer.toString(l + 1) + Integer.toString(i + 4);
				hexagon[l][i][5] = Integer.toString(l + 1) + Integer.toString(i + 5);
			}

			break;

		// left up slope
		case 11:
			hexagon[l][i][1] = Integer.toString(l) + Integer.toString((i + 1) % (l * 6));
			hexagon[l][i][2] = Integer.toString(l - 1) + Integer.toString((i - 5) % ((l - 1) * 6));
			hexagon[l][i][3] = Integer.toString(l - 1) + Integer.toString(i - 6);
			hexagon[l][i][4] = Integer.toString(l) + Integer.toString(i - 1);

			if (l < 7) {

				hexagon[l][i][0] = Integer.toString(l + 1) + Integer.toString(i + 6);

				hexagon[l][i][5] = Integer.toString(l + 1) + Integer.toString(i + 5);
			}

			break;

		}
	}

	public void show(String print) {
		if (debug) {
			System.out.println(print);
		}
	}
}
