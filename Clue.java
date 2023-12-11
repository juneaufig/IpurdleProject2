public class Clue {


    //ATTRIBUTES
    private LetterStatus[] elements;
    private int wordSize;
    private int orderNumber;
    private int clueInt;



    
    public static void main(String[] args) {
//        LetterStatus[] statusTest = new LetterStatus[] {LetterStatus.INEXISTENT, LetterStatus.CORRECT_POS, LetterStatus.WRONG_POS, LetterStatus.INEXISTENT, LetterStatus.INEXISTENT};
//        Clue clueTest = new Clue(statusTest);
        Clue clueTest2 = new Clue(7, 5);
    }

    /**
     * @param elements
     */
    public Clue(LetterStatus[] elements) {
        this.elements = elements;
        this.wordSize = elements.length;
        this.clueInt = intClueFromEnum(elements);
        this.orderNumber = getOrderNumber();
    }

    /**
     * @param orderNumber
     * @param wordSize
     */
    public Clue(int orderNumber, int wordSize) {
        this.orderNumber = orderNumber;
        this.wordSize = wordSize;
        this.clueInt = intClueFromOrderAndSize(orderNumber, wordSize);
        this.elements = statusFromClueInt(clueInt);
    }

    /**
     * @return
     */
    public int length() {
        return wordSize;
    }

    /**
     * @return
     */
    public int orderNumber() {
        return getOrderNumber();
    }

     public int getOrderNumber() {
         int clueStart = clueInt;
         int counter = 1;
         while (clueStart != minClue(wordSize)) {
             clueStart = previousClue(clueStart, wordSize);
             counter++;
         }
         return counter;
     }

    public int getOrderNumberWithClueInt(int clueInt) {
        int clueStart = clueInt;
        int counter = 1;
        while (clueStart != minClue(wordSize)) {
            clueStart = previousClue(clueStart, wordSize);
            counter++;
        }
        return counter;
    }

    /**
     * @return
     */
    public LetterStatus[] letterStatus() {
        return statusFromClueInt(clueInt);
    } 

    /**
     * @return
     */
    public boolean isMax() {
        return isMaxClue(clueInt, wordSize);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] == LetterStatus.INEXISTENT) {
                sb.append("_");
            }
            else if (elements[i] == LetterStatus.WRONG_POS) {
                sb.append("o");
            }
            else if (elements[i] == LetterStatus.CORRECT_POS) {
                sb.append("*");
            }
        }
        return sb.toString();
    }

    public int getClueInt() {
        return clueInt;
    }

    private int intClueFromEnum(LetterStatus[] elements) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordSize; i++) {
            if (elements[i] == LetterStatus.INEXISTENT) {
                sb.append("1");
            }
            else if (elements[i] == LetterStatus.WRONG_POS) {
                sb.append("2");
            }
            else if (elements[i] == LetterStatus.CORRECT_POS) {
                sb.append("3");
            }
        }
        return Integer.parseInt(sb.toString());
    }

    private int intClueFromOrderAndSize(int orderNumber, int wordSize) {
        int resultClue = minClue(wordSize);
        for (int i = 1; i < orderNumber; i++) {
            resultClue = nextClue(resultClue, wordSize);
        }
        return resultClue;
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

    private boolean isMaxClue(int clue, int size) {
        boolean maxClue = true;
        int tst = 0;
        for(int i = 0; i < size; i++){
            tst = clue % 10;
            clue /= 10;
            if(tst != 3){
                maxClue = false;
            }
        }return maxClue;
    }

    private LetterStatus[] statusFromClueInt(int clueInt) {
        String clue = Integer.toString(clueInt);
        LetterStatus[] result = new LetterStatus[wordSize];
        for (int i = 0; i < wordSize; i++) {
            if (clue.charAt(i) == '1') {
                result[i] = LetterStatus.INEXISTENT;
            }
            else if (clue.charAt(i) == '2') {
                result[i] = LetterStatus.WRONG_POS;
            }
            else if (clue.charAt(i) == '3') {
                result[i] = LetterStatus.CORRECT_POS;
            }
        }
        return result;
    }
}
