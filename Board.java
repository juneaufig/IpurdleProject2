import java.util.ArrayList;

public class Board {
    private char[] word;
    private int guesses;
    private int maxGuesses;
    private ArrayList<String> guesslist = new ArrayList<String>();
    private ArrayList<Clue> clues = new ArrayList<Clue>();

    /**
     * Creates a empty Board where the game Ipurdle is going to run.
     * 
     * @param wordSize size of words in this game.
     * @param maxGuesses maximum amount of guesses allowed.
     * @requires {@code wordSize} and {@code maxGuesses} to be greater or equal to 1.
     */
    public Board(int wordSize, int maxGuesses) {
        if(wordSize>=1 && maxGuesses >= 1){
            this.word = new char[wordSize];
            this.guesses = 0;
            this.maxGuesses = maxGuesses;
        }else if(wordSize<1 || maxGuesses < 1){
            System.out.println("Word size and max Guesses must be greater or equal to 1.");
        }
    }

    /**
     * @return size of the word to guess.
     */
    public int wordLength() {
        return word.length;
    }

    /**
     * @return the maximum amount of guesses allowed.
     */
    public int maxGuesses() {
        return this.maxGuesses;
    }

    /**
     * @return number of guesses.
     */
    public int guesses() {
        return guesses = guesslist.size();
    }

    /**
     * Stores the inputed {@code guess} and {@code clue} in their respective array lists.
     * @param guess word input by player in this round of game.
     * @param clue symbolic representation of characters correct, incorrect, or in wrong position in word.
     */
    public void insertGuessAndClue(String guess, Clue clue) {
        guesslist.add(guess);
        clues.add(clue);
    }
    /**
     * Creates a graphical representation of the game being run showing the {@code guess} on the left and the matching {@code clue}.
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("+-").append("-".repeat(wordLength()*2+3)).append("-+\n");
        for(int i = 0 ; i < guesses(); i++){
            str.append("| ").append(guesslist.get(i)).append(" | ").append(clues.get(i).toString()).append(" |\n");
            str.append("+-").append("-".repeat(wordLength()*2+3)).append("-+\n");
        }
        return str.toString();
    }
}
