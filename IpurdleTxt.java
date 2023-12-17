import java.util.Scanner;

public class IpurdleTxt {
    /**
     * /**
     * Plays a game of IPurdle, a Intro Programming version of Wordle!
     * 
     * @author Dienis Garkavenko 61818 and J. Juneau Wilson 61795
     * 
     * @param args first argument is length of words, second argument is number of attempts.
     */
    public static void main(String[] args){
        Scanner scr = new Scanner(System.in);
        int wordSize = 5;
        int maxAttempts = 6;
        IpurdleGame ipd = new IpurdleGame(wordSize, maxAttempts);
        Board board = new Board(wordSize, maxAttempts);
        welcomeMessage(wordSize, maxAttempts);
        do{
            String guess = askForGuess(ipd, scr);
            Clue clue = ipd.playGuess(guess);
            board.insertGuessAndClue(guess, clue);
            System.out.print(board.toString());
            if(clue.isMax()){
                System.out.println("Parabéns, enconstraste a palavra secreta.");
            }
        }while(!ipd.isOver());

    }
    /**
     * Asks player to input word, repeats until word is valid.
     * 
     * @param ipd Instance of IpurdleGame.
     * @param scr Scanner where player inputs words from.
     * @return {@code guess} that is valid. 
     */
    public static String askForGuess(IpurdleGame ipd, Scanner scr) {
        boolean validWord = false;
        String guess = "";
        do {
            System.out.print("Palavra a jogar ?");
            guess = scr.nextLine();
            guess = guess.toUpperCase();

            if (guess.equals("QUIT")) {
                //END
                break;
            } else if (!ipd.isValid(guess)) {
                System.out.println("Palavra invalida.");
            } else {
                validWord = true;
            }
        } while (!validWord);
        return guess;
    }
    /**
     * Writes welcome message for player.
     * 
     * @param wordSize size of words in this game.
     * @param maxGuesses maximum amount of guesses allowed.
     */
    public static void welcomeMessage(int wordSize, int maxAttempts) {
        System.out.println("");
        System.out.println("");
        System.out.println("Bem vindo ao jogo Ipurdle de Juneau Wilson e Dienis Garkavenko!");
        System.out.println("Neste jogo as palavras tem tamanho " + wordSize + ". O dicionario tem apenas palavras em ingles relacionadas com IP.");
        System.out.println("");
        System.out.println("Se quiseres sair do jogo, digite QUIT");
        System.out.println("");
        System.out.println("Tens " + maxAttempts + " tentativas para adivinhar a palavra. Boa sorte!");
        System.out.println("");
        System.out.println("");
    }
}
