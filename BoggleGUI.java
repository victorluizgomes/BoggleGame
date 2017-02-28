
// GUI components and interfaces you will need
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * An event driven graphical user interface for the Boggle game using the design
 * of BoggleTray and Boggle classes during Rick Mercer's CSc classes at the UofA
 * 
 * Skeleton Code by: Rick Mercer
 * Author: Michelle Monteith
 */
@SuppressWarnings("serial")
public class BoggleGUI extends JFrame {

	public static void main(String[] args) {
		// This main method allows us to can run this class as a Java
		// Application
		BoggleGUI theView = new BoggleGUI();
		theView.setVisible(true);
	}

	// START PRIVATE LISTENER CLASSES
	/*
	 * Each time a button is clicked, create a new game with a randomly
	 * generated DiceTray and show it. The JTextArea for the user input must be
	 * set to empty.
	 * 
	 */
	private class NewGame implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			userInputArea.getText();
			startNewGame();
		}
	}

	/*
	 * Each time a button is clicked, show a JOptionPane.showMessageDialog with
	 * the results as shown in the spec.
	 * 
	 * When the dialog box is closed, a new game beings with an empty JTextArea.
	 */
	private class EndGame implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			ArrayList<String> str = new ArrayList<String>();

			String guesses = userInputArea.getText();
			Scanner scan = new Scanner(guesses);

			while (scan.hasNext()) {

				String word = scan.next();
				str.add(word);
			}

			for (int i = 0; i < str.size(); i++) {
				game.addGuess(str.get(i));
			}
			
			JOptionPane.showMessageDialog(null, endResults());
			scan.close();
			startNewGame();
		}

		private String endResults() {

			String res = "";
			
			res += "Your score: " + game.getScore() + "\n" + "Words you found: " + "\n" + "\n" + game.getWordsFound()
					+ "\n" + "\n" + "Incorrect words: " + "\n" + "\n" + game.getWordsIncorrect() + "\n" + "\n"
					+ "You could have found " + game.getWordsNotGuessed().size() + " more words." + "\n" + "\n"
					+ "The computer found all of your words plus these you could have: " + "\n";

			for (int i = 0; i < game.getWordsNotGuessed().size(); i++) {
				if (i % 10 == 0) {
					res += "\n";
				}
				res += game.getWordsNotGuessed().get(i) + " ";
			}
			return res;
		}
	}
	// END PRIVATE LISTENER CLASSES

	private Boggle game;
	private NewGame newGame;
	private EndGame endGame;

	// GUI components
	private JTextArea diceTrayArea;
	private JButton newGameButton = new JButton("New Game");
	private JButton endButton = new JButton("End game");
	private JTextArea userInputArea = new JTextArea(10, 25);

	public BoggleGUI() {
		setUpModel();
		layoutWindow();
		setupListeners();
		startNewGame();

		// Listeners
		newGame = new NewGame();
		endGame = new EndGame();

		newGameButton.addActionListener(newGame);
		endButton.addActionListener(endGame);
	}

	private void startNewGame() {

		game = new Boggle();
		diceTrayArea.setText(game.getBoggleTrayAsString());
		userInputArea.setText(null);
	}

	private void setupListeners() {

	}

	private void setUpModel() {
		game = new Boggle();
	}


	private void layoutWindow() {
		// Set up the JFrame
		this.setSize(500, 270);
		this.setResizable(false);
		setLocation(10, 10);
		setTitle("Boggle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		// Layout the dice tray area
		diceTrayArea = new JTextArea();
		diceTrayArea.setEditable(false);
		diceTrayArea.setBackground(Color.pink);
		diceTrayArea.setFont(new Font("Courier", Font.BOLD, 24));

		// Set size and location.
		diceTrayArea.setSize(210, 220);
		diceTrayArea.setLocation(10, 10);
		add(diceTrayArea);

		// Declare and setSize and setLocation of a JLabel.
		JLabel enter = new JLabel("Enter your words below");
		enter.setSize(500, 30);
		enter.setLocation(260, 25);
		add(enter);

		// setSize and setLocation of the user input area.
		userInputArea.setSize(200, 150);
		userInputArea.setLocation(260, 50);
		userInputArea.setLineWrap(true);
		add(userInputArea);

		// setSize and setLocation of the newGameButton
		newGameButton.setSize(100, 20);
		newGameButton.setLocation(260, 210);
		add(newGameButton);

		// setSize and setLocation of the endButton
		endButton.setSize(100, 20);
		endButton.setLocation(360, 210);
		add(endButton);

	}
}
