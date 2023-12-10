public class Board {
    private char[] word;
    private int maxGuesses;
    private int guesses;
    private String guess;
    private Clue clue;

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
        return this.guesses;
    }

    /**
     * @param guess
     * @param clue
     */
    public void insertGuessAndClue(String guess, Clue clue) {
        this.guess = guess;
        this.clue = clue;
        this.guesses += 1;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("+-").append("-".repeat(wordLength()*2+3)).append("-+\n");
        str.append("| ").append(guess).append(" | ").append(clue.toString()).append(" |\n");
        str.append("+-").append("-".repeat(wordLength()*2 + 3)).append("-+\n");

        return str.toString();
    }
}
