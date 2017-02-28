
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

// Authors: Victor Gomes and Michelle Monteith

public class Boggle {

	// Instance variables
	private BoggleTray boggleTray;
	private ArrayList<String> wordsGuessed;
	private ArrayList<String> wordsThatCount;
	private ArrayList<String> allTheWords;
	private ArrayList<String> notCountWords;
	private ArrayList<String> notGuessedWords;
	private Scanner boggleWords;

	// Initialize a new game.
	public Boggle() {
		boggleTray = new BoggleTray();
		wordsGuessed = new ArrayList<String>();
		wordsThatCount = new ArrayList<String>();
		allTheWords = new ArrayList<String>();
		notCountWords = new ArrayList<String>();
		notGuessedWords = new ArrayList<String>();
		boggleWords = null;
		listOfAllWords();

	}

	// Allow testers to set the BoggleTray to a known one (not random).
	public void setBoggleTray(BoggleTray dt) {
		boggleTray = dt;
	}

	// Return the BoggleTray object as a textual representation as a String
	public String getBoggleTrayAsString() {
		return boggleTray.toString();
	}

	// reads from the 80000 words given (BoggleWords.txt) and puts it in a list
	private void listOfAllWords() {
		try {
			boggleWords = new Scanner(new File("BoggleWords"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (boggleWords.hasNextLine()) {
			allTheWords.add(boggleWords.nextLine().trim());
		}

	}

	// Record one word (a string with no whitespace) as one of the users'
	// guesses.
	public void addGuess(String oneGuess) {
		oneGuess = oneGuess.toLowerCase().trim();
		wordsGuessed.add(oneGuess);
	}

	// Return a list of all words the user entered that count for the score. The
	// found words must be in their natural ordering.
	public List<String> getWordsFound() {
		for (int i = 0; i < wordsGuessed.size(); i++) {
			if (boggleTray.foundInBoggleTray(wordsGuessed.get(i))) {
				if (allTheWords.contains(wordsGuessed.get(i))) {
					if (!wordsThatCount.contains(wordsGuessed.get(i))) {
						wordsThatCount.add(wordsGuessed.get(i));
					}
				}
			}
		}
		Collections.sort(wordsThatCount);
		return wordsThatCount;
	}

	// Return a list of all words the user entered that do not count for the
	// score in their natural order. 
	public List<String> getWordsIncorrect() {
		for (int i = 0; i < wordsGuessed.size(); i++) {
			if (!boggleTray.foundInBoggleTray(wordsGuessed.get(i))) {
				if (!notCountWords.contains(wordsGuessed.get(i))) {
					notCountWords.add(wordsGuessed.get(i));
				}
			} else if (!allTheWords.contains(wordsGuessed.get(i))) {
				if (!notCountWords.contains(wordsGuessed.get(i))) {
					notCountWords.add(wordsGuessed.get(i));
				}
			}
		}
		Collections.sort(notCountWords);
		return notCountWords;
	}

	// All words the user could have guessed but didn't in their natural order.
	public List<String> getWordsNotGuessed() {
		for (int i = 0; i < allTheWords.size(); i++) {
			boolean yeah = boggleTray.foundInBoggleTray(allTheWords.get(i));
			if (yeah) {
				if (!wordsGuessed.contains(allTheWords.get(i))) {
					if(!notGuessedWords.contains(allTheWords.get(i)))
						notGuessedWords.add(allTheWords.get(i));
				}
			}
		}
		return notGuessedWords;
	}

	// Return the correct score.
	public int getScore() {
		int score = 0;
		getWordsFound();
		for (int i = 0; i < wordsThatCount.size(); i++) {
			String str = wordsThatCount.get(i);
			if (str.length() == 3 || str.length() == 4) {
				score = score + 1;
			} else if (str.length() == 5) {
				score = score + 2;
			} else if (str.length() == 6) {
				score = score + 3;
			} else if (str.length() == 7) {
				score = score + 5;
			} else if (str.length() >= 8) {
				score = score + 11;
			}
		}
		return score;
	}
}
