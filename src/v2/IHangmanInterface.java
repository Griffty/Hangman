package v2;

import java.util.HashSet;

public interface IHangmanInterface {
    /**
     * Shows a short scene with introduction in attached interface
     * @param num amount of letters in the word
     */
    void start(int num);

    /**
     *  Makes user enter a letter in attached interface. It also automaticly check if the input is valid; if it is singe, english only letter
     * @return lowercase, single, english only letter
     */
    char getGameInput();

    /**
     * Shows all relative info on attached interface about where user currently is
     * @param word word that user should guess
     * @param usedLetters hashset of letters that user enter previously
     * @param stage how many attempts left
     * @param reply program reply to last user input; {@link Hangman#checkInput(String, HashSet, char)}
     */
    void showCurrentProgress(String word, HashSet<Character> usedLetters, int stage, int reply);

    /**
     * Shows final result of the round on attached interface
     * @param result 1 - user won; 0 - user lost
     * @param word word that user should guess
     */
    void showResult(int result, String word);

    /**
     * Ask user to play again
     * @return true if user agreed, otherwise false
     */
    boolean playAgain();
}
