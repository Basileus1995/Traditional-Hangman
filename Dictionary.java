package hangman;

// Import the java.io for I/O operations
import java.io.*;
// For importing ArrayList
import java.util.ArrayList;
// Import Random for random picking of a word.
import java.util.Random;

/**
 * Dictionary Class is responsible for the following tasks:
 * <p>
 * <br> Open the file 'words.txt' and read the data.
 * <br> Clean the words list according to the restrictions imposed.
 * <br> Select a word at random from the words list.
 */
public class Dictionary {

	/**
	 * Array List to hold the words from the file.
	 */
	private ArrayList <String> listOfWords = new ArrayList<String>();
    
	/**
	 * Constructor for the class that initializes the ArrayList for holding the words
	 */
	public Dictionary() {
		
		listOfWords = new ArrayList<String>();
	}
	/**
	 * Sets the argument provided to be the new listOfWords.
	 * @param words- Array List of words 
	 */
	public void setListOfWords( ArrayList <String> words) {

		this.listOfWords = words;
	}

	/**
	 * Gets the listOfWords array
	 * @return ArrayList of words
	 */
	public ArrayList<String> getListOfWords(){

		return this.listOfWords;
	}

	/**
	 * Method to open and read the words from the text file, 'words.txt'.
	 * @param pathToFile the path of file on the disk
	 */
	public void openAndReadFile(String pathToFile) {

		//Container to hold the words
		ArrayList<String> words = new ArrayList<String>();
		// Open operation
		try {
			FileReader reader = new FileReader(pathToFile);
			BufferedReader bufferedReader = new BufferedReader(reader);

			//Read Operation
			String line= bufferedReader.readLine();
			words.add(line);

			while (line != null) {

				line= bufferedReader.readLine();
				words.add(line);

			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Removing null elements in the list
		words.removeIf(item -> item == null || "".equals(item));
		this.setListOfWords(words);
	}

	/**
	 * Method to check is a word is contains any Upper Case Character or No
	 * @param word String input to the function
	 * @return boolean value
	 */
	private boolean containsUpperCase(String word) {
		String wordLowerCase = word.toLowerCase(); 
		if(word.equals(wordLowerCase)) {return false;}
		else {return true;}

	}

	/**
	 * Method returns true if a digits or digits are present in the String.
	 * @param word String input to the function
	 * @return boolean value
	 */
	private boolean containsNumber(String word) {
		int count = 0;
		for(int i = 0; i < 10;i++) {
			if(word.contains( Integer.toString(i))) 
			{count ++;}
		}

		if(count > 0 ) {return true;}
		else {return false;}
	}

	/**
	 * Method returns true if any of the special characters, ['.\s -]
	 * @param word String input to the function
	 * @return boolean value
	 */
	private boolean containsSpecialSymbol(String word) {
		if(word.contains("-")|| word.contains("'")||word.contains(" ") || word.contains(".")) {
			return true;
		}
		else {return false;}
	}

	/**
	 * Method to extract the words that conform to a certain set of rules and update
	 * the current words list to contain only valid words. 
	 */
	public void validWordsList() {

		// Variable to hold the valid words
		ArrayList<String> validWordsList = new ArrayList<String>();

		for(int i =0; i< this.getListOfWords().size();i++) {
			String word = this.getListOfWords().get(i).trim();
			// Checks if the word contains either upper case letter(s), digit(s), or a set of special characters['.- \s]
			if(this.containsUpperCase(word)|| this.containsNumber(word) || this.containsSpecialSymbol(word))	{
				continue;
			}
			else {
				validWordsList.add(word);
			}
		}

		this.setListOfWords(validWordsList); 
	}

	/**
	 * Method to randomly select a word from the 'listOfWords'
	 * @return String value to be used by the controller
	 */
	public String selectWordFromList() {

		Random randNum = new Random();
		int num = randNum.nextInt(this.getListOfWords().size());
		String word = this.getListOfWords().get(num);
		
		return word;
	}
	
}

