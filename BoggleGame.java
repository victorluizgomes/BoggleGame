import java.util.ArrayList;
import java.util.Scanner;

/**
 * The program plays a console based game of Boggle where user type in words
 * found in the the displayed BoggleTray of BoggleGame.
 *
 * 	Skeleton Code by: Rick Mercer
 *	Authors: Victor Gomes and Michelle Monteith
 *
 */

public class BoggleGame {
	public static void main(String[] args) {
		Boggle game = new Boggle();
		System.out.println("Play one game of Boggle:");
		System.out.println(game.getBoggleTrayAsString());
		System.out.println("Enter words or ZZ to quit:");
		Scanner scan = new Scanner(System.in);
		scan.reset();
		ArrayList<String> str = new ArrayList<String>();
		

		while (true) {
			String word = scan.next();
			if (word.toLowerCase().equals("zz")) {
				break;
			}
			str.add(word);
		}
		for (int i = 0; i < str.size(); i++) {
			game.addGuess(str.get(i));
		}

		System.out.println();
		System.out.println("Your score: " + game.getScore());
		System.out.println("Words you found: ");
		for (int i = 0; i < game.getWordsFound().size(); i++) {
			if (i % 10 == 0 && i != 0) {
				System.out.println();
			}
			System.out.print(game.getWordsFound().get(i) + " ");
		}
		
		
		System.out.println();
		System.out.println("Incorrect Words: ");
		for (int i = 0; i < game.getWordsIncorrect().size(); i++) {
			if (i % 10 == 0 && i != 0) {
				System.out.println();
			}
			System.out.print(game.getWordsIncorrect().get(i) + " ");
		}
		
		
		System.out.println();
		System.out.println("You could have found " + game.getWordsNotGuessed().size() + " more words");
		System.out.println("The computer found all of your words plus these: ");
		for (int i = 0; i < game.getWordsNotGuessed().size(); i++) {
			if (i % 10 == 0) {
				System.out.println();
			}
			System.out.print(game.getWordsNotGuessed().get(i) + " ");
		}
		
		scan.close();
	}
}
