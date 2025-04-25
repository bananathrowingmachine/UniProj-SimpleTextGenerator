package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class for 3 argument inputs, creating a single hashMap for the seed word which allows us to significantly speed up the process instead of using WordCount2D
 *
 * @author bananathrowingmachine and [redacted]
 * @version April 13th, 2024
 */
public class WordCount extends WordCountSkeleton{
    private HashMap<String, Integer> wordAssociation;

    /**
     * Constuctor for 3 input arguments which initialize the hashmap and begin counting words
     * @param file, the txt file to read from
     * @param seed, the seed word
     */
    public WordCount(File file, String seed) {
        wordAssociation = new HashMap<String, Integer>();
        countWords(file, cleanup(seed));
    }

    /**
     * Loop through each word and stop when the root word appears and add the next word to hashMap or update its count in the hashmap
     * @param file, the txt file to read from
     * @param seed, the seed word
     */
    private void countWords(File file, String seed) {
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()){
                String cleanWord = cleanup(scanner.next());
                if (cleanWord.equals(seed) && scanner.hasNext()){
                    String nextWord = cleanup(scanner.next());
                    if (!wordAssociation.containsKey(nextWord))
                        wordAssociation.put(nextWord,1);
                    else
                        wordAssociation.put(nextWord, wordAssociation.get(nextWord) + 1);
                }
            }
            scanner.close();
        } catch (NoSuchElementException e) {
            wordAssociation = null;
        } catch (FileNotFoundException e) { //intellij forced us to put this here
            throw new IllegalArgumentException();
        }
    }

    /**
     * Will generate the text that most often occurs after the word
     * @param k - the amount of words to generate
     */
    public void generateText(int k){
        if (wordAssociation == null) {
            System.out.print("");
            return;
        }
        ArrayList<String> words = new ArrayList<>(wordAssociation.keySet());
        Collections.sort(words, (String a, String b) -> wordAssociation.get(b) - wordAssociation.get(a));
        for (int index = 0; index < k && index < words.size(); index++) {
            if (!words.get(index).isEmpty()) {
                if (index != 0)
                    System.out.print(" ");
                System.out.print(words.get(index));
            }
        }
    }
}
