public class Clue {

    private LetterStatus[] letterStatus;
    private int length;
    private int order;
    private int clueInt;
    public static void main(String[] args) {
        
    }

    /**
     * @param elements
     */
    public Clue(LetterStatus[] elements) {
        this.letterStatus = elements;
        this.clueInt = intClueFromEnum(elements);
    }

    /**
     * @param orderNumber
     * @param wordSize
     */
    public Clue(int orderNumber, int wordSize) {
        this.order = orderNumber;
        this.length = wordSize;
        this.clueInt = intClueFromOrderAndSize(orderNumber, wordSize);
    }

    /**
     * @return
     */
    public int length() {
        return (Integer.toString(this.clueInt)).length();
    }

    /**
     * @return
     */
    public int orderNumber() {
        return this.clueInt - minClue(this.length());
    }

    /**
     * @return
     */
    public LetterStatus[] letterStatus() {
        return this.letterStatus;
    } 

    /**
     * @return
     */
    public boolean isMax() {
        return isMaxClue(this.clueInt, this.length());
    }

    public String toString() {
        return Integer.toString(clueInt);
    }

    private int intClueFromEnum(LetterStatus[] elements) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < elements.length; i++) {
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
        return minClue(wordSize) + orderNumber;
    }

    private int minClue(int size){
        int clue = 0;
        for(int i= 0; i < size; i ++){
           clue = (clue * 10) + 1;
        }
        return clue;
    }

    private boolean isMaxClue(int clue, int size){
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

}
