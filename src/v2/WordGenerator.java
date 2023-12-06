package v2;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class WordGenerator {
    private static final String filename = "english-nouns.txt";

    /**
     * Picks random word from the {@link #filename} file
     * @return random english only, lowercase word
     */
    public static String createRandom() {
        ArrayList<String> lines = new ArrayList<>();
        InputStream is = WordGenerator.class.getResourceAsStream(filename);
        if (is == null){
            throw new RuntimeException("Cannot get the word");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines.get(new Random().nextInt(lines.size()));
    }
}
