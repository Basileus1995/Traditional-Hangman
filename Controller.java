package hangman;
//importing Scanner class to get user's input
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * Controller class handles the user interface and implements the gaming console.
 * <p>
 * It is responsible for-
 * <br>Initiating the game and making the user aware of the rule
 * <br>Display the word pattern to the user
 * <br>Keep track of the guesses entered by the user.
 *
 */
public class Controller extends Dictionary{

	/**
	 * Variable that holds the guesses that were incorrect 
	 */
	private Set<Character> incorrectGuesses;
	
	/**
	 * Constructor for Controller class, initializes incorrectGuesses set
	 */
	public Controller() {
		
		incorrectGuesses = new TreeSet<Character>();
	}
	/**
	 * validateInput method returns true if the String contains only alphabets, or
	 * else return false.
	 * @param input The User provided input to the program
	 * @return Boolean Value
	 */
	protected boolean validateInput(String input) {

		if(input.length() > 1) {return false;}
		else {
			if(input.matches("[a-zA-Z]+")) {return true;}
			else {return false;}
		}
	}

	/**
	 * Returns the number of unique characters in the word presented to the user.
	 * @param input The word to be given to the user
	 * @return Integer Value
	 */
	int countUniqueCharacters(String input) {
		return (int) input.chars()
				.distinct()
				.count();
	}

	/**
	 * <p>
	 * <br> returns 0 if letter entered is not in the word (counts as try),
	 * <br> returns 1 if letter were entered 1st time (counts as try),
	 * <br> returns 2 if already guessed letter was REentered,
	 * <br> returns 3 if all letters were guessed 
	 * @param word The word given to the user
	 * @param enteredLetters The array that holds the correct guesses
	 * @return Integer Value
	 */
	private int enterLetter(String word, char[] enteredLetters){


		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		if (!printWord(word, enteredLetters))
		{return 3;}
		//Get the user input
		System.out.print("Guess a letter in word >> ");
		String input = scan.nextLine();	
		//Validate the user input
		while(! this.validateInput(input)) {
			System.out.println("Invalid Input! Enter a VALID Alphabet Character");
			input = scan.nextLine();
			input = input.trim();
			
		}
		char userInput = input.toLowerCase().charAt(0);
		int emptyPosition = this.emptyPositionsAvailable(enteredLetters);
		if (presentInEnteredLetters(userInput , enteredLetters)) {
			System.out.println("\n" + userInput  + " is already in the word");
			System.out.println("\n===========================================================================\n");

			return 2;
		}
		else if (word.contains(String.valueOf(userInput ))) {
			enteredLetters[emptyPosition] = userInput ;
			System.out.println("\n===========================================================================\n");

			return 1;
		}
		else {
			this.incorrectGuesses.add(userInput);
			System.out.println("\n" + userInput  + " is not in the word");
			System.out.println("\n"+"Incorrect Guesses: "+ this.incorrectGuesses);
			System.out.println("\n===========================================================================\n");
            
			return 0;
		}
		
		

	}

	/**
	 * Find first empty position in array of entered letters
	 * @param enteredLetters The array that holds the correct guesses
	 * @return Integer Value
	 */
	int emptyPositionsAvailable(char[] enteredLetters) {
		try{
			int index = 0;
			while (enteredLetters[index] != '\u0000') index++;
			return index;
		}
		catch (final ArrayIndexOutOfBoundsException e){
			return -1;
		}	
	}


	/**
	 * Print word with asterisks for hidden letters, returns true if
	 * asterisks were printed, otherwise return false.
	 * @param word The word given to the user
	 * @param enteredLetters The array that holds the correct guesses
	 * @return Boolean Value
	 */
	private boolean printWord(String word, char[] enteredLetters) {
		// Iterate through all letters in word
		boolean asteriskPrinted = false;
		for (int i = 0; i < word.length(); i++) {
			char letter = word.charAt(i);
			// Check if letter already have been entered by user before
			if (this.presentInEnteredLetters(letter, enteredLetters))
				System.out.print(letter+" "); // If yes - print it
			else {
				System.out.print("_ ");
				asteriskPrinted = true;
			}
		}

		System.out.println("\n");
		return asteriskPrinted;
	}

	/**
	 * Check if letter is in enteredLetters array, returns true
	 * if the letter is in the array, else return false. 
	 * @param letter The current guess made by the user
	 * @param enteredLetters The array that holds the correct guesses
	 * @return Boolean Value
	 */
	boolean presentInEnteredLetters(char letter, char[] enteredLetters) {
		return new String(enteredLetters).contains(String.valueOf(letter));
	}

	/**
	 * infinitely iterate through cycle as long as enterLetter returns true
	 * if enterLetter returns false that means user guessed all the letters
	 * in the word e. g. no asterisks were printed by printWord.
	 * @param word The word to be given to the user.
	 */
	protected void playGame(String word) {
		char[] enteredLetters = new char[word.length()];
		int triesCount = 0;
		boolean wordIsGuessed = false;

		while(!wordIsGuessed) {
			switch (enterLetter(word, enteredLetters)) {
			case 0:
				triesCount++;
				break;
			case 1:
				triesCount++;
				break;
			case 2:
				break;
			case 3:
				wordIsGuessed = true;
				break;
			}
		}
		System.out.println("You Won");
		System.out.println("\nThe word is " + "\""+ word + "\"" +
				"\n\nYou Guessed Incorrectly " + (triesCount-this.countUniqueCharacters(word)) +
				" time(s)");
		System.out.println("\n=======================================================================\n");

	}

	/**
	 * Main method of the program that enables the gaming console.
	 * @param args Default Argument to the main function
	 */
	public static void main(String args[]) {

		Scanner readChoice = new Scanner(System.in);
		Controller gameControl = new Controller();
		//Open and Read the file
		gameControl.openAndReadFile("src/sample_words.txt");
		//Get the valid list of words
		gameControl.validWordsList();
		String choice;
		while(true) {
			//Get word form the list
			String word = gameControl.selectWordFromList();
			System.out.println("\n=============================== THE HANGMAN ===============================");
			System.out.println("\n============================= MIND THE RULES  =============================\n");
			System.out.println("1. Only Single character guesses are accepted as input");
			System.out.println("2. Only Alphabets are accepted as input");
			System.out.println("3. Avoid entering a wrong choice as input twice");
			System.out.println("\n============================ !!BEGIN THE GAME!! ===========================\n");
			gameControl.playGame(word);
			System.out.println("Do you want to play the game again, Type Y/y for Yes >>");
			choice =  readChoice.next().toLowerCase();
			if(choice.equals("y")) {continue;}
			else {break;}
		}
		readChoice.close();
	}


}