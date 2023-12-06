package v2;

import java.util.HashSet;

public class Hangman {
    private final IHangmanInterface gameInterface;

    /**
     * Creates a new instance of the game with a specified IHangmanGameInterface interface for interaction with user.
     * @see IHangmanInterface
     */
    public Hangman(IHangmanInterface gameInterface){
        this.gameInterface = gameInterface;
    }

    /**
     * Start game sequence
     */
    public void start() {
        String word = WordGenerator.createRandom();
        gameInterface.start(word.length());
        newRound(word);
        while (gameInterface.playAgain()){
            newRound();
        }

        System.exit(69);
    }

    /**
     * Starts new round with randomly picked word
     */
    public void newRound() {
        String word = WordGenerator.createRandom();
        showCurrentProgress(word, new HashSet<>(), 0, -1);
        newRound(word);
    }

    /**
     * Starts new round with already defined word
     * @param word word that player will try to guess
     */
    public void newRound(String word) {
        HashSet<Character> usedLetters = new HashSet<>();
        int stage = 0;
        while (stage < 9 ) {
            char input = getInput();
            int reply = checkInput(word, usedLetters, input);
            if (reply == -1) {
                stage++;
            }
            if (userVictory(usedLetters, word)){
                showResult(1, word);
                return;
            }
            showCurrentProgress(word, usedLetters, stage, reply);
            }
        showResult(2, word);
    }

    /**
     * Checks if user have already won by checking if user already entered all letters that are in the word
     * @param word word that user should guess
     * @param usedLetters hashset of letters that user enter previously
     * @return true if user already won, otherwise false
     */
    private boolean userVictory(HashSet<Character> usedLetters, String word) {
        for(int i = 0; i < word.length(); i++){
            if (!usedLetters.contains(word.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if input is valid; if it is, it decides if it is correct or not
     * @param input char to check
     * @param word word that user should guess
     * @param usedLetters hashset of letters that user enter previously
     * @return if word does not contain input letter - -1; if this letter was already used - 0 and if user made a correct guess - 1
     */
    private int checkInput(String word, HashSet<Character> usedLetters, char input) {
        if (usedLetters.contains(input)){
            return 0;
        }
        usedLetters.add(input);
        if (word.contains(input+"")){
            return 1;
        }
        return -1;
    }

    /**
     * Interface call to request new letter guess from the user
     * @return char that user entered
     */
    private char getInput(){
        return (gameInterface.getGameInput()+"").toLowerCase().charAt(0);
    }

    /**
     * Interface call to show all relative info about where user currently is
     * @param word word that user should guess
     * @param usedLetters hashset of letters that user enter previously
     * @param stage how many attempts left
     * @param inputStatus program reply to last user input; {@link #checkInput(String, HashSet, char)}
     */
    private void showCurrentProgress(String word, HashSet<Character> usedLetters, int stage, int inputStatus){
        gameInterface.showCurrentProgress(word, usedLetters, stage, inputStatus);
    }

    /**
     * Interface call to show final result of the round
     * @param result 1 - user won; 0 - user lost
     * @param word word that user should guess
     */
    private void showResult(int result, String word) {
        gameInterface.showResult(result, word);
    }

    public static void main(String[] args) {
        Hangman hangman = new Hangman(new ConsoleHangmanInterface());
        hangman.start();
    }
}