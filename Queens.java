//Kristina Gocheva, fn 81117, group 5
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Queens {

	static int[] cols;
	public static final int MAX_MOVES = 5000;

	static ArrayList<Integer> columnCandidates = new ArrayList<Integer>();
	static ArrayList<Integer> rowCandidates = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		
		//initialize(Integer.parseInt(args[0]));
		initialize(new Scanner(System.in).nextInt());
		solve();
		print();
	}

	private static void initialize(int size) {
		cols = new int[size];
		for (int j = 0; j < cols.length; j++) {
			cols[j] = ThreadLocalRandom.current().nextInt(0, size);
		}

	}

	static void solve() {

		for (int moves = 0; moves < MAX_MOVES; moves++) {
			int maxConflicts = 0;
			columnCandidates.clear();
			for (int i = 0; i < cols.length; i++) {
				int conflicts = conflicts(cols[i], i);
				if (conflicts == maxConflicts) {
					columnCandidates.add(i);
				} else if (conflicts > maxConflicts) {
					maxConflicts = conflicts;
					columnCandidates.clear();
					columnCandidates.add(i);
				}
			}

			if (goal(maxConflicts)) {
				return;
			}
			int worstQueenColumn = columnCandidates.get(ThreadLocalRandom.current().nextInt(0, columnCandidates.size()));
			minimizeConflicts(worstQueenColumn);
		}
	}

	private static void print() {
		for (int i = 0; i < cols.length; i++) {
			System.out.print(cols[i] + " ");
		}
		System.out.println();
		char matrix[][] = new char[cols.length][];
		for (int i = 0; i < cols.length; i++) {
			matrix[i] = new char[cols.length];
			for (int j = 0; j < cols.length; j++) {
				if (i == cols[j]) {
					matrix[i][j] = '*';
				} else {
					matrix[i][j] = '_';
				}
				System.out.print(matrix[i][j]);
			}
			System.out.println();

		}
	}

	static int conflicts(int row, int col) {
		int count = 0;
		for (int i = 0; i < cols.length; i++) {
			if (i == col)
				continue;
			int r = cols[i];
			if (r == row || Math.abs(r - row) == Math.abs(i - col))
				count++;
		}
		return count;
	}

	static boolean goal(int numberConflicts) {
		return numberConflicts == 0;
	}
	
	static void minimizeConflicts(int moveQueenColumn) {
		int minConflicts = cols.length;
		rowCandidates.clear();
		for (int i = 0; i < cols.length; i++) {
			int conflicts = conflicts(i, moveQueenColumn);
			if (conflicts == minConflicts) {
				rowCandidates.add(i);
			} else if (conflicts < minConflicts) {
				minConflicts = conflicts;
				rowCandidates.clear();
				rowCandidates.add(i);
			}
		}
		if (!rowCandidates.isEmpty()) {
			cols[moveQueenColumn] = rowCandidates.get(ThreadLocalRandom.current().nextInt(0, rowCandidates.size()));
		}
	}
}
