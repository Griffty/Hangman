package v2.TypeWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TypeWriter extends Thread{
    /**
     * Time multipliers for wait time during typewriter effect for special symbols
     */
    private static final HashMap<Character, Float> punctuationSpeedMultiplayer = new HashMap<>() {
        {
            put(' ', 0.7f);
            put(',', 2f);
            put('.', 5f);
            put('!', 3.5f);
            put('?', 3.5f);
            put(';', 2f);
        }
    };
    private static int basicSpeed = 60;

    /**
     * Sets basic speed of typewriter when created with {@link #startTypingAnimationOnCurrentThread(String)} or {@link #startTypingAnimationOnDifferentThread(String)}
     * @param basicSpeed speed to write with
     */
    public static void setBasicSpeed(int basicSpeed) {
        TypeWriter.basicSpeed = basicSpeed;
    }

    private static final List<TypeWriter> runningWriters = new ArrayList<>();

    /**
     * Starts new typewriter effect on different thread with a basic speed
     * @see #startTypingAnimationOnDifferentThread(String, int)
     */
    public static TypeWriterResult startTypingAnimationOnDifferentThread(String string){
        return startTypingAnimationOnDifferentThread(string, basicSpeed);
    }

    /**
     * Creates new typewriter that print provided string. Runs on separate daemon thread. All currently running typewriters are saved in {@link #runningWriters}
     * @param string string to write with effect
     * @param speed the speed to write with
     * @return {@link TypeWriterResult} object with info about current state of this specific typewriter effect
     */
    public static TypeWriterResult startTypingAnimationOnDifferentThread(String string, int speed){
        TypeWriterResult result = new TypeWriterResult();
        TypeWriter writer = new TypeWriter(result, string, speed);
        writer.setDaemon(true);
        writer.start();
        runningWriters.add(writer);
        return result;
    }

    /**
     * Starts new typewriter effect on current thread
     * @see #startTypingAnimationOnCurrentThread(String, int)
     */
    public static TypeWriterResult startTypingAnimationOnCurrentThread(String string){
        return startTypingAnimationOnCurrentThread(string, basicSpeed);
    }

    /**
     * Creates new typewriter that print provided string. Blocks and runs on the same thread it was called. All currently running typewriters are saved in {@link #runningWriters}
     * @param string string to write with effect
     * @param speed the speed to write with
     * @return {@link TypeWriterResult} object with info about current state of this specific typewriter effect
     */
    public static TypeWriterResult startTypingAnimationOnCurrentThread(String string, int speed){
        TypeWriterResult result = new TypeWriterResult();
        TypeWriter writer = new TypeWriter(result, string, speed);
        writer.run();
        runningWriters.add(writer);
        return result;
    }

    public static List<TypeWriter> getRunningWriters() {
        return runningWriters;
    }

    private final int speed;
    private final String string;
    private final TypeWriterResult result;
    private TypeWriter(TypeWriterResult typeWriterResult, String s, int speed){
        result = typeWriterResult;
        string = s;
        this.speed = speed;
    }


    /**
     * Runs new typewriter effect. To get current state check returned {@link TypeWriterResult}
     */
    @Override
    public void run() {
        for (int i = 0; i < string.length(); i++){
            char c = string.charAt(i);
            System.out.print(c);
            int speedM = 1;
            if (punctuationSpeedMultiplayer.containsKey(c)){
                speedM *= punctuationSpeedMultiplayer.get(c);
            }
            try {
                sleep((long) 1000 / speed * speedM);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println();
        result.makeFinished();
        runningWriters.remove(this);
    }
}
