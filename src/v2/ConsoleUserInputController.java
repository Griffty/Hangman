package v2;

import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConsoleUserInputController {
    private static ConsoleUserInputController instance;
    public static ConsoleUserInputController getInstance() {
        if (instance == null){
            instance = new ConsoleUserInputController();
        }
        return instance;
    }
    private  Scanner userInput;

    /**
     * Volatile version of Array List to prevent caching values in other threads
     */
    private final CopyOnWriteArrayList<String> inputBuffer;

    /**
     * Creates new object with new empty {@link #inputBuffer} and starts new {@link Thread} with {@link #userInput} System.in scanner object running on it.
     */
    private ConsoleUserInputController(){
        inputBuffer = new CopyOnWriteArrayList<>();
        new Thread(() -> {
            userInput = new Scanner(System.in);
            while (true){
                inputBuffer.add(userInput.nextLine());
            }
        }).start();
    }

    /**
     * Blocks the thread where was called and wait for first new user input in console
     * @return first new input after method was called
     */
    public String waitForNextInput(){
        if (inputBuffer.isEmpty()){
            while (inputBuffer.isEmpty())
            {
            }
            return inputBuffer.get(0);
        }
        int bufferSize = inputBuffer.size();
        while (bufferSize == inputBuffer.size())
        {
        }
        return inputBuffer.get(inputBuffer.size()-1);
    }

    /**
     * Returns last input
     * @return if buffer is not empty last input, otherwise empty String
     */
    public String getLastInput() {
        if (inputBuffer.isEmpty()){
            return "";
        }
        return inputBuffer.get(inputBuffer.size()-1);
    }

    /**
     * Returns {@link #inputBuffer} size
     * @return {@link #inputBuffer} size
     */
    public int getInputBufferSize() {
        return inputBuffer.size();
    }
}
