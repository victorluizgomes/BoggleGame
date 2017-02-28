import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Model the tray of dice in the game Boggle. A DiceTray can be constructed with
 * a 4x4 array of characters for testing. A 2nd constructor with no arguments
 * simulates the shaking of 16 dice and selection of one of the 6 sides.
 * 
 *	Skeleton Code by: Rick Mercer
 	Authors: Victor Gomes and Michelle Monteith
 */

public class BoggleTray {

	/**
	 * Construct a tray of dice by simulating the actual Boggle "roll" of
	 * the 16 Boggle dice.
	 * 
	 * @param newBoard
	 *            The 2D array of characters used in testing
	 */

	public BoggleTray() {
		listOfRandom = new ArrayList<Character>();
		board = new char[4][4];
		setUpDices();
		setUpRandomBoard();
	}

	// Set up an array of arrays with 16 dices and the 6 letters given on the
	// spec
	private void setUpDices() {
		char[] dice1 = { 'L', 'R', 'Y', 'T', 'T', 'E' };
		char[] dice2 = { 'A', 'N', 'A', 'E', 'E', 'G' };
		char[] dice3 = { 'A', 'F', 'P', 'K', 'F', 'S' };
		char[] dice4 = { 'Y', 'L', 'D', 'E', 'V', 'R' };
		char[] dice5 = { 'V', 'T', 'H', 'R', 'W', 'E' };
		char[] dice6 = { 'I', 'D', 'S', 'Y', 'T', 'T' };
		char[] dice7 = { 'X', 'L', 'D', 'E', 'R', 'I' };
		char[] dice8 = { 'Z', 'N', 'R', 'N', 'H', 'L' };
		char[] dice9 = { 'E', 'G', 'H', 'W', 'N', 'E' };
		char[] dice10 = { 'O', 'A', 'T', 'T', 'O', 'W' };
		char[] dice11 = { 'H', 'C', 'P', 'O', 'A', 'S' };
		char[] dice12 = { 'N', 'M', 'I', 'H', 'U', 'Q' };
		char[] dice13 = { 'S', 'E', 'O', 'T', 'I', 'S' };
		char[] dice14 = { 'M', 'T', 'O', 'I', 'C', 'U' };
		char[] dice15 = { 'E', 'N', 'S', 'I', 'E', 'U' };
		char[] dice16 = { 'O', 'B', 'B', 'A', 'O', 'J' };
		dices = new char[][] { dice1, dice2, dice3, dice4, dice5, dice6, dice7, dice8, dice9, dice10, dice11, dice12,
				dice13, dice14, dice15, dice16 };
	}

	// Makes a random Boggle tray with the array of dices
	private void setUpRandomBoard() {
		Random rand = new Random();

		for (int i = 0; i < 16; i++) {
			char cha = dices[i][rand.nextInt(6)];
			listOfRandom.add(cha);
		}

		Collections.shuffle(listOfRandom);

		int index = 0;
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				board[r][c] = listOfRandom.get(index);
				index++;
			}
		}
	}

	private ArrayList<Character> listOfRandom;
	private char[][] dices;
	private char[][] path;
	private char[][] board;
	public static final char TRIED = '@';
	public static final char PART_OF_WORD = '!';
	private String attempt;
	public static final int SIZE = 4;
	public static final int NUMBER_SIDES = 6;

	/**
	 * Construct a tray of dice using a hard coded 2D array of chars.
	 * Used for testing.
	 *
	 * @param newBoard
	 *            The 2D array of characters used in testing
	 */
	public BoggleTray(char[][] newBoard) {
		board = newBoard;
	}

	/**
	 * Provide a textual version of this BoggleTray
	 */
	@Override
	public String toString() {
		String result = "\n";
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				if (board[r][c] == 'Q')
					result += " Qu";
				else
					result += "  " + board[r][c];
			}
			result += " \n\n";
		}
		return result;
	}

	/**
	 * Return true if search is word that can found on the board following the
	 * rules of Boggle
	 * 
	 * @param str
	 *            A word that may be in the board by connecting consecutive
	 *            letters
	 * 
	 */
	public boolean foundInBoggleTray(String str) {
		if (str.length() < 3)
			return false;
		str = str.toUpperCase().trim();
		attempt = str;
		boolean found = false;
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++)
				if (board[r][c] == str.charAt(0)) {
					initializeTriedLocations();
					
					found = recursiveSearch(r, c);
					if (found) {
						return true;
					}
				}
		}
		return false;
	}

	// Keep a 2nd 2D array to remember the characters that have been tried
	private void initializeTriedLocations() {
		path = new char[SIZE][SIZE];
		for (int r = 0; r < SIZE; r++)
			for (int c = 0; c < SIZE; c++)
				path[r][c] = '.';
	}

	// Check eight possible directions.
	//
	// "Qu" is treated as "Q"
	//
	public boolean recursiveSearch(int row, int column) {
		return halp(row, column, attempt, 0);
	}

	// Helper method for recursive search, with more arguments
	public boolean halp(int r, int c, String str, int index) {
		boolean found = false;
		if (index >= str.length()) {
			return true;
		}
		if ((r < 0 || r >= SIZE) || (c < 0 || c >= SIZE)) {
			return false;
		}
		if (path[r][c] == PART_OF_WORD || path[r][c] == TRIED) {
			return false;
		}
		if (board[r][c] == str.charAt(index)) {
			path[r][c] = PART_OF_WORD;
			if (board[r][c] == 'Q') {
				index++;
			}
			for (int i = r - 1; i <= r + 1; i++) {
				for (int j = c - 1; j <= c + 1; j++) {
					if (!found) {
						found = halp(i, j, str, index + 1);
					}
				}
			}
			if (!found) {
				path[r][c] = TRIED;
			}
		}
		return found;
	}
}
