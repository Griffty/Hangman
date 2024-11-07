package com.github.griffty.v2;

import com.github.griffty.v2.typewritter.TypeWriter;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleHangmanInterface implements IHangmanInterface {
    private final ConsoleUserInputController inputController = ConsoleUserInputController.getInstance();

    @Override
    public void start(int num){
        System.out.println("""
                ██░ ██  ▄▄▄       ███▄    █   ▄████  ███▄ ▄███▓ ▄▄▄       ███▄    █\s
                ▓██░ ██▒▒████▄     ██ ▀█   █  ██▒ ▀█▒▓██▒▀█▀ ██▒▒████▄     ██ ▀█   █\s
                ▒██▀▀██░▒██  ▀█▄  ▓██  ▀█ ██▒▒██░▄▄▄░▓██    ▓██░▒██  ▀█▄  ▓██  ▀█ ██▒
                ░▓█ ░██ ░██▄▄▄▄██ ▓██▒  ▐▌██▒░▓█  ██▓▒██    ▒██ ░██▄▄▄▄██ ▓██▒  ▐▌██▒
                ░▓█▒░██▓ ▓█   ▓██▒▒██░   ▓██░░▒▓███▀▒▒██▒   ░██▒ ▓█   ▓██▒▒██░   ▓██░
                 ▒ ░░▒░▒ ▒▒   ▓▒█░░ ▒░   ▒ ▒  ░▒   ▒ ░ ▒░   ░  ░ ▒▒   ▓▒█░░ ▒░   ▒ ▒\s
                 ▒ ░▒░ ░  ▒   ▒▒ ░░ ░░   ░ ▒░  ░   ░ ░  ░      ░  ▒   ▒▒ ░░ ░░   ░ ▒░
                 ░  ░░ ░  ░   ▒      ░   ░ ░ ░ ░   ░ ░      ░     ░   ▒      ░   ░ ░\s
                 ░  ░  ░      ░  ░         ░       ░        ░         ░  ░         ░\s
                                                                                    \s""");
        TypeWriter.startTypingAnimationOnCurrentThread("\n\n[Narrator] Greetings dear Player. Do you want to play one cool game?");
        waitForInput();
        TypeWriter.startTypingAnimationOnCurrentThread("[Narrator] Also, we got you friend here, but don't worry he will definitely enjoy it. \n[Narrator] Say hi to him.");
        printStage(-1);
        waitForInput();
        TypeWriter.startTypingAnimationOnCurrentThread("[Narrator] So, lets begin." +
                "\n[Narrator] I chose some word and your task is to guess it by checking single letters, but be careful or your friend will feel all your mistakes." +
                "\n[Narrator] So your word has " + num + " letters in it and now it looks like this -" +  " _".repeat(num) + ".");
        TypeWriter.startTypingAnimationOnCurrentThread("[Narrator] Now enter your first guess: ");
    }

    /**
     * Gives user some time to read the dialog before going forward by entering anything
     */
    private void waitForInput() {
        TypeWriter.startTypingAnimationOnCurrentThread("\n*Enter to continue*", 300);
        inputController.waitForNextInput();
    }

    @Override
    public char getGameInput() {
        Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
        boolean again = true;
        String s = "";
        while (again){
            again = false;
            System.out.print("-> ");
            s = inputController.waitForNextInput();
            if (s.length() > 1){
                TypeWriter.startTypingAnimationOnCurrentThread("[Narrator] Don't make me angry, just enter one damn letter.");
                again = true;
                continue;
            }
            Matcher matcher = pattern.matcher(s);
            if (!matcher.matches()){
                TypeWriter.startTypingAnimationOnCurrentThread("[Narrator] Don't piss me off, I accept only basic latin symbols.");
                again = true;
            }
        }
        return s.charAt(0);
    }
    @Override
    public void showCurrentProgress(String word, HashSet<Character> usedLetters, int stage, int reply) {
        StringBuilder sb = new StringBuilder();
        if (reply == 0){
            TypeWriter.startTypingAnimationOnCurrentThread("[Narrator] You already tried this letter, don't make such dumb mistakes, try again.");
            return;
        }
        if (reply == 1){
            TypeWriter.startTypingAnimationOnCurrentThread("[Narrator] What a miracle, you got this one.\n");
        }else{
            printStage(stage);
            waitForInput();
        }
        if (stage != 9) {
            for (int i = 0; i < word.length(); i++) {
                if (usedLetters.contains(word.charAt(i))) {
                    sb.append(word.charAt(i));
                } else {
                    sb.append("_");
                }
                sb.append(" ");
            }
            TypeWriter.startTypingAnimationOnCurrentThread("[Narrator] Currently your word looks like this - " + sb +
                    "\n[Narrator] And you already used these letters - " + usedLetters +
                    "\n[Narrator] Make your next choice wisely!");
        }
    }

    @Override
    public void showResult(int result, String word) {
        if (result == 1){
            TypeWriter.startTypingAnimationOnCurrentThread("[Narrator] So your word was - " + word + "." +
                    "\n[Narrator] Well, your luck saved your friend today, but it won't be like that forever!" +
                    "\n[Narrator] Next time I will choose the word, you will never guess!");
            waitForInput();
            System.out.println("""
                    ▓██   ██▓ ▒█████   █    ██     █     █░ ▒█████   ███▄    █\s
                     ▒██  ██▒▒██▒  ██▒ ██  ▓██▒   ▓█░ █ ░█░▒██▒  ██▒ ██ ▀█   █\s
                      ▒██ ██░▒██░  ██▒▓██  ▒██░   ▒█░ █ ░█ ▒██░  ██▒▓██  ▀█ ██▒
                      ░ ▐██▓░▒██   ██░▓▓█  ░██░   ░█░ █ ░█ ▒██   ██░▓██▒  ▐▌██▒
                      ░ ██▒▓░░ ████▓▒░▒▒█████▓    ░░██▒██▓ ░ ████▓▒░▒██░   ▓██░
                       ██▒▒▒ ░ ▒░▒░▒░ ░▒▓▒ ▒ ▒    ░ ▓░▒ ▒  ░ ▒░▒░▒░ ░ ▒░   ▒ ▒\s
                     ▓██ ░▒░   ░ ▒ ▒░ ░░▒░ ░ ░      ▒ ░ ░    ░ ▒ ▒░ ░ ░░   ░ ▒░
                     ▒ ▒ ░░  ░ ░ ░ ▒   ░░░ ░ ░      ░   ░  ░ ░ ░ ▒     ░   ░ ░\s
                     ░ ░         ░ ░     ░            ░        ░ ░           ░\s
                     ░ ░                                                      \s""");
        }else{

            TypeWriter.startTypingAnimationOnCurrentThread("[Narrator] You didn't guess such an easy word - " + word + "." +
                    "\n[Narrator] AHAHAAH, you did this to your friend. You did this...");
            waitForInput();
            System.out.println("""
                    ▓██   ██▓ ▒█████   █    ██     ██▓     ▒█████    ██████ ▄▄▄█████▓
                     ▒██  ██▒▒██▒  ██▒ ██  ▓██▒   ▓██▒    ▒██▒  ██▒▒██    ▒ ▓  ██▒ ▓▒
                      ▒██ ██░▒██░  ██▒▓██  ▒██░   ▒██░    ▒██░  ██▒░ ▓██▄   ▒ ▓██░ ▒░
                      ░ ▐██▓░▒██   ██░▓▓█  ░██░   ▒██░    ▒██   ██░  ▒   ██▒░ ▓██▓ ░\s
                      ░ ██▒▓░░ ████▓▒░▒▒█████▓    ░██████▒░ ████▓▒░▒██████▒▒  ▒██▒ ░\s
                       ██▒▒▒ ░ ▒░▒░▒░ ░▒▓▒ ▒ ▒    ░ ▒░▓  ░░ ▒░▒░▒░ ▒ ▒▓▒ ▒ ░  ▒ ░░  \s
                     ▓██ ░▒░   ░ ▒ ▒░ ░░▒░ ░ ░    ░ ░ ▒  ░  ░ ▒ ▒░ ░ ░▒  ░ ░    ░   \s
                     ▒ ▒ ░░  ░ ░ ░ ▒   ░░░ ░ ░      ░ ░   ░ ░ ░ ▒  ░  ░  ░    ░     \s
                     ░ ░         ░ ░     ░            ░  ░    ░ ░        ░          \s
                     ░ ░                                                            \s""");
        }
    }

    @Override
    public boolean playAgain() {
        TypeWriter.startTypingAnimationOnCurrentThread("Do you want to play again?");
        System.out.print("->");
        String s = inputController.waitForNextInput();

        return s.equals("y") || s.equals("Yes") || s.equals("Y") || s.equals("yes") || s.equals("ye") || s.equals("ok");
    }

    /**
     * Shows current stage of hanging with attached comment from narrator
     * @param stage - what stage of hanging to display
     */
    public void printStage(int stage){
        switch (stage){
            case -1 -> TypeWriter.startTypingAnimationOnCurrentThread("""
                                O / \s
                               /| \s
                               / \\
                    """);
            case 1 -> TypeWriter.startTypingAnimationOnCurrentThread("""
                    \s
                          
                          
                          
                          
                          |
                    =========
                    
                    [Narrator] Hmmm, what this thing can be?""");
            case 2 -> TypeWriter.startTypingAnimationOnCurrentThread("""
                    \s
                          |
                          |
                          |
                          |
                          |
                    =========
                    
                    [Narrator] Oh, now I see, make more mistakes and you will also get it. AHAHAHAHA""");
            case 3 -> TypeWriter.startTypingAnimationOnCurrentThread("""
                      +---+
                      |   |
                          |
                          |
                          |
                          |
                    =========
                    
                    [Narrator] Oh yee, such a fun game.""");
            case 4 -> TypeWriter.startTypingAnimationOnCurrentThread("""
                      +---+
                      |   |
                      O   |
                          |
                          |
                          |
                    =========
                    
                    [Narrator] Is this your friend? YES, IT IS!""");
            case 5 -> TypeWriter.startTypingAnimationOnCurrentThread("""
                      +---+
                      |   |
                      O   |
                      |   |
                          |
                          |
                    =========
                    
                    [Narrator] I can hear his screams. Yes, his screams of fear!""");
            case 6 -> TypeWriter.startTypingAnimationOnCurrentThread("""
                      +---+
                      |   |
                      O   |
                     /|   |
                          |
                          |
                    =========
                    
                    [Narrator] His last seconds in this world are getting closer and closer.""");
            case 7 -> TypeWriter.startTypingAnimationOnCurrentThread("""
                      +---+
                      |   |
                      O   |
                     /|\\  |
                          |
                          |
                    =========
                    
                    [Narrator] He is one leg in the grave.""");
            case 8 -> TypeWriter.startTypingAnimationOnCurrentThread("""
                      +---+
                      |   |
                      O   |
                     /|\\  |
                     /    |
                          |
                    =========
                    
                    [Narrator] YES, make one more mistake and he is done!!!""");
            case 9 -> TypeWriter.startTypingAnimationOnCurrentThread("""
                      +---+
                      |   |
                      O   |
                     /|\\  |
                     / \\  |
                          |
                    =========""");
        }
    }
}
