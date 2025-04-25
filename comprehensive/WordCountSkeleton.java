package comprehensive;

/**
 * Skeleton class for the WordCount classes to make life slightly in TextGenerator.java and for making all the words valid
 *
 * @author bananathrowingmachine and [redacted]
 * @version April 17, 2024
 */
public abstract class WordCountSkeleton {

    /**
     * Turns all letters to lowercase, and removes all characters after illegal characters
     * @param word - the word to be cleaned
     * @return the cleaned word
     */
    public static String cleanup(String word){
        StringBuilder builder = new StringBuilder();
        word = word.toLowerCase();
        for (char c : word.toCharArray()) {
            if (!isLetterOrNumber(c)) {
                break;
            }
            builder.append(c);
        }
        return builder.toString();
    }

    /**
     * Determines if a character
     * @param c - character to verify
     * @return boolean
     */
    private static boolean isLetterOrNumber(char c) {
        return (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '_';
    }

    /**
     * Simplifies TextGenerator.java by allowing both solutions to call the same method
     * @param k - the amount of words to generate
     */
    public abstract void generateText(int k);
}
