
public class MainApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		char[][] board = new char[9][9];
		String[] values = { "...2...63","3....54.1","..1..398.",".......9.","...538...",".3.......",".263..5..","5.37....8","47...1..."};
		for (int x = 0; x < 9; x++) {
			String string = values[x];
			for (int y = 0; y < 9; y++) {
				board[x][y] = string.charAt(y);
				System.out.print(board[x][y]+" ");
			}
			System.out.println();
		}

		
		Sudoku solver = new Sudoku();
		solver.solveSudoku(board);
		
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				System.out.print(board[x][y]+" ");
			}
			System.out.println();
		}
	}
}
