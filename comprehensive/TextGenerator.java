package comprehensive;

import java.io.File;

/**
 * Launches the text generator
 *
 * @version April 13th, 2024
 * @author bananathrowingmachine and [redacted]
 */
public class TextGenerator {
    public static void main(String[] args) {
        WordCountSkeleton userGen;
        if(args.length == 3)
            userGen = new WordCount(new File(args[0]), args[1]);
        else
            userGen = new WordCount2D(new File(args[0]), args[1], args[3].equals("all"));
        userGen.generateText(Integer.parseInt(args[2]));
    }
}
