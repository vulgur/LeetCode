import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Sudoku {
	private List<Cell> cellList;

	private int count = 0;
	private Set<Cell> mpCellSet;
	private Stack<Cell> mpStack;
	private Stack<List<Cell>> cellsStack;

	public void solveSudoku(char[][] board) {
		// Start typing your Java Sudoku below
		// DO NOT write main() function

		mpCellSet = new HashSet<Cell>();
		initCells(board);

		boolean countFlag = true;
		while (countFlag) {
			int temp = count;
			List<Cell> confirmedCells = getConfirmedCells();
			for (Cell cell : confirmedCells) {
				cell.reduceBlockPossible();
				cell.reduceColumnPossible();
				cell.reduceRowPossible();
			}
			if (count != temp) {
				countFlag = true;
			} else {
				countFlag = false;
			}
		}

		printResult(cellList);
		System.out.println("^^^^^^^^^Start to guess^^^^^^^^");
		System.out.println();

		guess();

		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				board[x][y] = getCellValue(x, y);
			}
		}
	}

	private void guess() {
		Cell mpCell = getMinPossibleCell(cellList);
		mpStack = new Stack<Sudoku.Cell>();
		cellsStack = new Stack<List<Cell>>();
		mpCellSet.add(mpCell);
		// push the mpCell and cellList into stack
		mpStack.push(mpCell);
		cellsStack.push(cellList);
		while (count < 81) {
			// guess the possibles of the mpCell in order
			int possibles = mpCell.possibleList.size();
			System.out.println("Possibles:" + mpCell.possibleList + mpCell);
			if (possibles <= mpCell.imposibleList.size()) {
				System.out.println("NO POSSIBLES!!!");
				mpCell.imposibleList.clear();
				// pop out the previous one
				mpCell = mpStack.pop();
				cellList = cellsStack.pop();
				System.out.println("%%%%%% Stack Pop" + mpCell + " value = "
						+ mpCell.value + " size: " + mpStack.size());
				// add the wrong number to the
				// impossible list
				mpCell.imposibleList.add(mpCell.value);
//				mpCell.removePossible(mpCell.value);
				mpCell.value = '.';
				count--;
				continue;
			}
			
			for (int i = 0; i < possibles; i++) {
				char possibleValue = mpCell.possibleList.get(i);
				if (mpCell.imposibleList.contains(possibleValue)) {
					continue;
				}

				if (mpCell.isValid(possibleValue)) {
					System.out.println("^^^^^^VALID Possible Value:" + possibleValue + mpCell);
					mpCell.value = possibleValue;
					count++;
					mpStack.push(mpCell);
					cellsStack.push(cellList);
					System.out.println("Stack Push" + mpCell + " with value: " + possibleValue
							+ " size: " + mpStack.size());
					
					// get another mpCell and guess it
					mpCell = getAnotherMinPossibleCell(mpCell);
					break;
				} else {
					System.out.println("#######NOT VALID Possible Value:" + possibleValue + mpCell);
					mpCell.imposibleList.add(possibleValue);

					if (i == possibles - 1 || possibles == mpCell.imposibleList.size()) {
						// tried all possibles, so must
						// the previous one is filled
						// with wrong number, clear the
						// impossible list
						System.out.println("@@@@@@@Tried all possibles" + mpCell);
						mpCell.imposibleList.clear();
						// pop out the previous one
						mpCell = mpStack.pop();
						cellList = cellsStack.pop();
						System.out.println("%%%%%% Stack Pop" + mpCell + " value = "
								+ mpCell.value + " size: " + mpStack.size());
						// add the wrong number to the
						// impossible list
						mpCell.imposibleList.add(mpCell.value);
//						mpCell.removePossible(mpCell.value);
						mpCell.value = '.';
						count--;
						break;
					}
				}
			}
			// if the possible value is not valid, back to previous
			// state, guess the next possible
			// if the possible value is valid, set the value to the
			// cell, push the mpCell and cellList into stack,
			// get the next mpCell and guess it
			
			System.out.println("COUNT = " + count);
		}
		 
	}

	private Cell getAnotherMinPossibleCell(Cell cell) {
		boolean flag = false;
		Cell another = null;
		cellList.remove(cell);
		another = getMinPossibleCell(cellList);
		System.out.println("Another mpCell" + another);
		cellList.add(cell);
		return another;
	}

	private void printResult(List<Cell> cells) {
		for (Cell cell : cells) {
			System.out.print(cell.value + " ");
			if (cell.y == 8) {
				System.out.println();
			}
		}
		System.out.println();
	}

	public char getCellValue(int x, int y) {
		for (Cell cell : cellList) {
			if (cell.x == x && cell.y == y) {
				return cell.value;
			}
		}
		return 0;
	}

	public List<Cell> getConfirmedCells() {
		List<Cell> confirmedList = new ArrayList<Sudoku.Cell>();
		for (Cell cell : cellList) {
			if (cell.value != '.') {
				confirmedList.add(cell);
			}
		}
		return confirmedList;
	}

	public Cell getMinPossibleCell(List<Cell> cells) {
		int minPossibles = 9;
		int size = 0;
		int index = -1;
		for (int i = 0; i < cells.size(); i++) {
			Cell cell = cells.get(i);

			if (cell.possibleList == null || cell.value != '.') {
				continue;
			}
			size = cell.possibleList.size();
			if (size < minPossibles) {
				minPossibles = size;
				index = i;
			}
		}
		if (index == -1) {
			return null;
		}
		return cells.get(index);
	}

	public List<Cell> getRow(int x) {
		List<Cell> row = new ArrayList<Sudoku.Cell>();
		for (Cell cell : cellList) {
			if (row.size() == 9) {
				break;
			}
			if (cell.x == x) {
				row.add(cell);
			}
		}
		return row;
	}

	public List<Cell> getColumn(int y) {
		List<Cell> column = new ArrayList<Sudoku.Cell>();
		for (Cell cell : cellList) {
			if (column.size() == 9) {
				break;
			}
			if (cell.y == y) {
				column.add(cell);
			}
		}
		return column;
	}

	public List<Cell> getBlock(int x, int y) {
		List<Cell> block = new ArrayList<Sudoku.Cell>();
		int bno = getBlockNo(x, y);
		for (Cell cell : cellList) {
			if (block.size() == 8) {
				break;
			}
			if (cell.blockNo == bno && (cell.x != x && cell.y != y)) {
				block.add(cell);
			}
		}
		return block;
	}

	private int getBlockNo(int x, int y) {
		if (0 <= x && x <= 2 && 0 <= y && y <= 2)
			return 0;
		else if (0 <= x && x <= 2 && 3 <= y && y <= 5)
			return 1;
		else if (0 <= x && x <= 2 && 6 <= y && y <= 8)
			return 2;
		else if (3 <= x && x <= 5 && 0 <= y && y <= 2)
			return 3;
		else if (3 <= x && x <= 5 && 3 <= y && y <= 5)
			return 4;
		else if (3 <= x && x <= 5 && 6 <= y && y <= 8)
			return 5;
		else if (6 <= x && x <= 8 && 0 <= y && y <= 2)
			return 6;
		else if (6 <= x && x <= 8 && 3 <= y && y <= 5)
			return 7;
		else if (6 <= x && x <= 8 && 6 <= y && y <= 8)
			return 8;
		else
			return -1;

	}

	private void initCells(char[][] board) {
		cellList = new ArrayList<Sudoku.Cell>();
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				int bno = getBlockNo(x, y);
				Cell cell = new Cell(x, y, bno);
				if (board[x][y] != '.') {
					cell.value = board[x][y];
					cell.possibleList = null;
					cell.imposibleList = null;
					count++;
				}
				cellList.add(cell);
			}
		}
	}

	private class Cell {
		public int x = -1;
		public int y = -1;
		public char value = '.';
		public int blockNo = -1;
		public List<Character> possibleList;
		public List<Character> imposibleList;

		public Cell(int x, int y, int blockNo) {
			this.x = x;
			this.y = y;
			this.blockNo = blockNo;
			initPossibleList();
			imposibleList = new ArrayList<Character>();
		}

		private void initPossibleList() {
			this.possibleList = new ArrayList<Character>();
			char value = '1';
			for (int i = 1; i <= 9; i++) {
				possibleList.add(new Character(value));
				value++;
			}
		}

		private void addPossible(char val) {
			this.possibleList.add(new Character(val));
		}

		private boolean removePossible(char val) {
			if (possibleList == null) {
				return true;
			}
			if (this.possibleList.remove((Character) val)) {
				System.out.println("Cell: [" + x + ", " + y + "]  removed possible:" + val);
			}
			if (possibleList.size() == 1) {
				char tmp = possibleList.get(0);
				if (checkBlock(tmp) && checkColumn(tmp) && checkRow(tmp)) {
					this.value = possibleList.get(0);
					possibleList = null;
					count++;
					System.out.println();
					System.out.println("CONFIRMED CELL[" + x + "," + y + "] value=" + this.value);
					return true;
				} else {
					System.err.println("WRONG NUMBER, Cannot confirm");
					return false;
				}
			}
			if (possibleList.size() == 0) {
				System.err.println("!!!WRONG NUMBER!!!");
				return false;
			} else {
				return true;
			}
		}

		private boolean check(List<Cell> cells, char value) {
			for (Cell cell : cells) {
				if (cell.value == value) {
					return false;
				}
			}
			return true;
		}

		private boolean checkRow(char value) {
			List<Cell> rowCells = getRowCells();
			return check(rowCells, value);
		}

		private boolean checkColumn(char value) {
			List<Cell> colunmCells = getColumnCells();
			return check(colunmCells, value);
		}

		private boolean checkBlock(char value) {
			List<Cell> blockCells = getBlockCells();
			return check(blockCells, value);
		}

		private boolean reduceRowPossible() {
			List<Cell> row = getRowCells();
			return reducePossibles(row, value);
		}

		private boolean reduceColumnPossible() {
			List<Cell> column = getColumnCells();
			return reducePossibles(column, value);
		}

		private boolean reduceBlockPossible() {
			List<Cell> block = getBlockCells();
			return reducePossibles(block, value);
		}

		private boolean reducePossibles(List<Cell> list, char value) {
			boolean flag = false;
			for (Cell cell : list) {
				if (cell.possibleList == null) {
					continue;
				}

				flag = cell.removePossible(value);
				if (flag == false) {
					return false;
				}
			}
			return true;
		}

		private boolean isValid(char value) {
			return this.checkBlock(value) && this.checkColumn(value) && this.checkRow(value);
		}

		private List<Cell> getRowCells() {
			return getRow(x);
		}

		private List<Cell> getColumnCells() {
			return getColumn(y);
		}

		private List<Cell> getBlockCells() {
			return getBlock(x, y);
		}

		public String toString() {
			return " --- Cell [" + x + "," + y + "]";
		}
	}
}
