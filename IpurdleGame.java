import java.util.Arrays;
import java.util.Scanner;

public class IpurdleGame {
    private String[] words;
    private boolean[] canBeWord;
    private int wordSize;
    private int maxGuesses;
    private int guessesPlayed;
    private int lastClueInt;
    private boolean hasPlayedARound = false;
    private StringBuilder stringGame;

    private String[] wordsList = {"JAVA", "LOOP", "EXIT", "TRUE", "LONG", "THIS", "BREAK", "WHILE", "GRADE", "PUPIL", "FIELD", "BASIC", "ABORT", "ABOVE", "FALSE", "FLOAT", "SHORT", "CLASS", "FINAL", "STATIC", "METHOD", "STRING", "RETURN", "RANDOM", "EQUALS", "OBJECT", "FUNCTION", "VARIABLE", "INTEGER", "SCANNER",};


    public static void main(String[] args) {

    }

    /**
     * @param wordSize
     * @param maxGuesses
     */
    public IpurdleGame(int wordSize, int maxGuesses) {
        this.wordSize = wordSize;
        this.maxGuesses = maxGuesses;
        this.words = findWordsWithList(wordsList);
        this.canBeWord = new boolean[words.length];
        Arrays.fill(canBeWord, true);
        this.guessesPlayed = 0;
    }

    /**
     * @return
     */
    public int wordLength() {
        return wordSize;
    }

    /**
     * @return
     */
    public int maxGuesses() {
        return maxGuesses;
    }

    /**
     * @return
     */
    public int guesses() {
        return guessesPlayed;
    }

    /**
     * @param guess
     * @return
     */
    public boolean isValid(String guess) {
        boolean rightSize = (guess.length() == wordSize);
        boolean inDictionary = false;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(guess)) {
                inDictionary = true;
            }
        }
        return rightSize && inDictionary;
    }
    /**
     * @return
     */
    public boolean isOver() {

        return (hasPlayedARound && (guessesPlayed == maxGuesses)) || (hasPlayedARound && isMaxClue(lastClueInt, wordSize));
    }

    /**
     * @param guess
     * @return
     */
    public Clue playGuess(String guess) {

        Clue result;
        int resultClueIntForGuess = betterClueForGuess(guess);
        for (int i = 0; i < words.length; i++) {
            if (clueForGuessAndWord(guess, words[i]).getClueInt() != resultClueIntForGuess) {
                canBeWord[i] = false;
            }
        }

        int resultWordSize = String.valueOf(resultClueIntForGuess).length();
        int resultClueInt = resultClueIntForGuess;
        int resultOrder = getOrderNumberWithClueInt(resultClueInt);
        lastClueInt = resultClueInt;
        result = new Clue(resultOrder, resultWordSize);

        if (!hasPlayedARound) {
            stringGame = new StringBuilder("Ipurdle with words of " + wordSize + " letters.\n Remaining guesses: " + (maxGuesses - guessesPlayed) + "\n");
        }
        stringGame.append(lineSeparator());
        stringGame.append("| " + guess.toUpperCase() + " | " + result + " |" + "\n");

        guessesPlayed++;
        hasPlayedARound = true;


        return result;
    }

    public String toString() {
        stringGame.append(lineSeparator());
        return stringGame.toString();
    }

    public int betterClueForGuess(String guess) {
        int clueStart = minClue(guess.length());
        int currentMaxWords = howManyWordsWithClue(clueStart, guess);
        int clueMax = clueStart;
        while (!isMaxClue(clueStart, guess.length())) {
            int secondClue = nextClue(clueStart, guess.length());
            int num1 = howManyWordsWithClue(clueStart, guess);
            int num2 = howManyWordsWithClue(secondClue, guess);
            if (num1 > num2 && num1 > currentMaxWords) {
                currentMaxWords = num1;
                clueMax = clueStart;
            } else if (num2 > num1 && num2 > currentMaxWords)  {
                currentMaxWords = num2;
                clueMax = secondClue;
            }
            clueStart = nextClue(clueStart, guess.length());
        }
        return clueMax;
    }

    public int howManyWordsWithClue(int clueInt, String guess) {
        int counterHowMany = 0;
        boolean[] canBeWord2 = new boolean[words.length];
        for (int i = 0; i < words.length; i++) {
            canBeWord2[i]= canBeWord[i];
        }

        checkThrees(canBeWord2, guess, clueInt);
        checkTwos(canBeWord2, guess, clueInt);
        checkOnes(canBeWord2, guess, clueInt);
        for (int i = 0; i < words.length; i++) {
            if (canBeWord2[i] == true) {
                counterHowMany++;
            }
        }
        return counterHowMany;
    }

    /**
     * Checks letters from {@code dict} words to see if they are valid according to {@code clue} and {@code guess}.
     *
     * @param dict dictionary to check words from.
     * @param guess guess to check dictionary words against.
     * @param clue clue to check letters in position with 1.
     */
    private void checkOnes(boolean[] dict, String guess, int clue) {
        for (int i = 0; i < guess.length(); i++) {
            if ((Integer.toString(clue).charAt(i))=='1') {
                for (int l = 0; l < dict.length; l++) {
                    for (int h = 0; h < guess.length(); h++) {
                        if (checkThisLetter(guess.charAt(i), words[l], h)) {
                            dict[l] = false;
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks letters from {@code dict} words to see if they are valid according to {@code clue} and {@code guess}.
     *
     * @param dict dictionary to check words from.
     * @param guess guess to check dictionary words against.
     * @param clue clue to check letters in position with 2.
     */
    private void checkTwos(boolean[] dict, String guess, int clue) {
        for (int i = 0; i < guess.length(); i++) {
            if ((Integer.toString(clue).charAt(i))=='2') {
                for (int k = 0; k < dict.length; k++) {
                    if (guess.charAt(i)==(words[k]).charAt(i)) {
                        dict[k] = false;
                    }
                }
                for (int j = 0; j < dict.length; j++) {
                    boolean hasLetter = false;
                    for (int p = 0; p < guess.length(); p++) {
                        if (checkThisLetter(guess.charAt(i), words[j], p)) {
                            hasLetter = true;
                        }
                    }
                    if (!hasLetter) {
                        dict[j] = false;
                    }
                }
            }
        }
    }

    /**
     * Checks letters from {@code dict} words to see if they are valid according to {@code clue} and {@code guess}.
     *
     * @param dict dictionary to check words from.
     * @param guess guess to check dictionary words against.
     * @param clue clue to check letters in position with 3.
     */
    private void checkThrees(boolean[] dict, String guess, int clue) {
        for (int i = 0; i < guess.length(); i++) {
            if ((Integer.toString(clue).charAt(i))=='3') {
                for (int j = 0; j < dict.length; j++) {
                    if (!checkThisLetter(guess.charAt(i), words[j], i)) {
                        dict[j] = false;
                    }
                }
            }
        }
    }

    /**
     * Checks if a certain character input is in a chosen string in a specific position.
     *
     * @param c character being checked against word.
     * @param wordToCheck word being checked for presence of {@code c}.
     * @param pos position in which {@code c} must be in in the word.
     * @return true if {@code c} is in {@code wordToCheck} in {@code pos}.
     * @requires {@code pos} must be less than length of {@code wordToCheck}.
     */
    private static boolean checkThisLetter(char c, String wordToCheck, int pos) {
        boolean letterInWord;
        if (wordToCheck.charAt(pos)==c) {
            letterInWord = true;
        } else {
            letterInWord = false;
        }
        return letterInWord;
    }


    private int nextClue(int clue, int size) {
        boolean carry = false;
        clue++;
        clue -= minClue(size);
        for (int pos = 1; pos <= size; pos++) {
            carry = (clue % Math.pow(10, pos)) >= (3 * Math.pow(10, pos-1));
            if (carry) {
                clue += (9 * Math.pow(10, pos-1));
                clue -= (clue % Math.pow(10, pos));
            }
        }
        clue += minClue(size);
        return clue;
    }

    private boolean isMaxClue(int clue, int size) {
        boolean maxClue = true;
        int tst = 0;
        for (int i = 0; i < size; i++) {
            tst = clue % 10;
            clue /= 10;
            if (tst != 3) {
                maxClue = false;
            }
        }return maxClue;
    }

    private String[] findWords() {
        Scanner scanDictionary = new Scanner("Dicionario.txt");
        Scanner scanForWords = new Scanner("Dicionario.txt");
        int counterWordsOfSize = 0;
        while (scanDictionary.hasNextLine()) {

            if (scanDictionary.nextLine().length() == wordSize) {
                counterWordsOfSize++;
            }
        }
        int i = 0;
        String[] wordsOfSize = new String[counterWordsOfSize];
        while (scanForWords.hasNextLine()){
            String word = scanForWords.nextLine();
            if (word.length() == wordSize) {
                wordsOfSize[i] = word;
            }
            i++;
        }
        return wordsOfSize;
    }

    private String[] findWordsWithList(String[] list) {
        int counterWordsOfSize = 0;
        for (int j = 0; j < list.length; j++) {
            if (list[j].length() == wordSize) {
                counterWordsOfSize++;
            }
        }
        int i = 0;
        String[] wordsOfSize = new String[counterWordsOfSize];
        for (int k = 0; k < list.length; k++) {
            if (list[k].length() == wordSize) {
                wordsOfSize[i] = list[k];
                i++;
            }
        }
        return wordsOfSize;
    }

    private String lineSeparator() {
        StringBuilder lineSeparator = new StringBuilder("+");
        for (int i = 0; i < 2*wordSize+5; i++) {
            lineSeparator.append("-");
        }
        lineSeparator.append("+");
        lineSeparator.append("\n");
        return lineSeparator.toString();
    }

    private int getOrderNumberWithClueInt(int clueInt) {
        int clueStart = clueInt;
        int counter = 1;
        while (clueStart != minClue(wordSize)) {
            clueStart = previousClue(clueStart, wordSize);
            counter++;
        }
        return counter;
    }

    private int previousClue(int clue, int size) {
        int clueCheck = clue;
        clue--;
        clue = clue - minClue(size) + (int) Math.pow(10, size);
        String clueString = Integer.toString(clue);
        for (int pos = 1; pos <= wordSize; pos++) {
            if (clueString.charAt(wordSize+1-pos)=='9') {
                clue = clue - (int) (7 * Math.pow(10, pos-1));
            }
        }
        clue += minClue(size) - (int) Math.pow(10, size);
        return clue;
    }

    private int minClue(int size){
        int clue = 0;
        for(int i= 0; i < size; i ++){
            clue = (clue * 10) + 1;
        }
        return clue;
    }

    /**
     * @param guess
     * @param word
     * @return
     */
    private Clue clueForGuessAndWord(String guess, String word) {
        StringBuilder notSamePos = new StringBuilder();
        StringBuilder clue = new StringBuilder();
        clue.append(Integer.toString(minClue(word.length())));
        Clue result;
        for (int i = 0; i < wordSize; i++) {
            if (Character.compare(word.charAt(i),guess.charAt(i)) == 0) {
                clue.deleteCharAt(i);
                clue.insert(i, 3);
            } else {
                notSamePos.append(word.charAt(i));
            }
        }
        for(int j = 0; j < word.length(); j++) {
            for (int k = 0; k < notSamePos.length(); k++) {
                if (guess.charAt(j) == notSamePos.charAt(k)) {
                    clue.deleteCharAt(j);
                    clue.insert(j, 2);
                    notSamePos.deleteCharAt(k);
                }
            }
        }
        int resultWordSize = clue.toString().length();
        int resultClueInt = Integer.parseInt(clue.toString());
        int resultOrder = getOrderNumberWithClueInt(resultClueInt);
        result = new Clue(resultOrder, resultWordSize);
        return result;
    }
}
