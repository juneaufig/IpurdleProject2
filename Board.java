import java.util.ArrayList;

public class Board {
    private char[] word;
    private int guesses;
    private int maxGuesses;
    private ArrayList<String> guesslist = new ArrayList<String>();
    private ArrayList<Clue> clues = new ArrayList<Clue>();

    /**
     * @param wordSize
     * @param maxGuesses
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
     * @return
     */
    public int wordLength() {
        return word.length;
    }

    /**
     * @return
     */
    public int maxGuesses() {
        return this.maxGuesses;
    }

    /**
     * @return
     */
    public int guesses() {
        return guesses = guesslist.size();
    }

    /**
     * @param guess
     * @param clue
     */
    public void insertGuessAndClue(String guess, Clue clue) {
        guesslist.add(guess);
        clues.add(clue);
    }

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
