package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class for 4 argument inputs, creating a 2D hashMap and generating the needed text for all and one inputs
 *
 * @version  April 19th, 2024
 * @author bananathrowingmachine and [redacted]
 */
public class WordCount2D extends WordCountSkeleton{
    private HashMap<String, HashMap<String, Integer>> wordAssociation;
    private boolean fourth;
    private String seed;

    /**
     * Constructor class to initialize the arguments and call the countWords;
     * @param file, the txt file passed
     * @param seed, the word to begin generating text
     * @param fourth, the fourth argument indicating whether all or one was called, with all being true, one being false
     */
    public WordCount2D(File file, String seed, boolean fourth) {
        wordAssociation = new HashMap<>();
        this.fourth = fourth;
        this.seed = cleanup(seed);
        countWords(file);
    }

    /**
     * Creates a 2D hashMap with a word being a key having another hashmap as its value which stores all words that
     * come after it and how many times they do
     * @param file, the txt file to get the words from
     */
    private void countWords(File file) {
        try {
            Scanner scanner = new Scanner(file);
            String cleanWord = cleanup(scanner.next());
            while (scanner.hasNext()){
                String nextWord = cleanup(scanner.next());
                if (!wordAssociation.containsKey(cleanWord)) {
                    HashMap<String, Integer> hashMap = new HashMap<>();
                    hashMap.put(nextWord, 1);
                    wordAssociation.put(cleanWord, hashMap);
                } else {
                    HashMap<String, Integer> hashMap = wordAssociation.get(cleanWord);
                    if (hashMap.containsKey(nextWord)) {
                        hashMap.put(nextWord, hashMap.get(nextWord) + 1);
                        wordAssociation.put(cleanWord, hashMap);
                    } else {
                        hashMap.put(nextWord, 1);
                    }
                    wordAssociation.put(cleanWord, hashMap);
                }
                cleanWord = nextWord;

            }
            if (!wordAssociation.containsKey(cleanWord))
                wordAssociation.put(cleanWord, new HashMap<String, Integer>());
            scanner.close();
        } catch (FileNotFoundException e) { //intellij forced us to put this here
            throw new RuntimeException(e);
        }
    }

    /**
     * Driver method to decide which text to generate and to check if the hashMap was created successfully
     * @param k - the amount of words to generate
     */
    public void generateText(int k){
        if (wordAssociation == null)
            return;
        if (fourth)
            generateAll(k);
        else
            generateOne(k);
    }

    /**
     * Generates a text where the next word is the word with the greatest count
     * @param k
     */
    public void generateOne(int k) {
        HashMap<String, String> finalWordAssociation = new HashMap<>();
        String newSeed = seed;
        System.out.print(seed);
        for(int i = 1; i < k; i++) {
            String largestWord;
            if(finalWordAssociation.containsKey(newSeed)) {
                largestWord = finalWordAssociation.get(newSeed);
            }
              else {
                largestWord = findMaxHash(newSeed);
                finalWordAssociation.put(newSeed, largestWord);
            }
            System.out.print(" " + largestWord);
            newSeed = largestWord;
        }
    }

    /**
     * Finds the word which comes after the given word the most in the txt file
     * @param word, the word which is used as the first word
     * @return the word with the greatest count in the hashmap
     */
    public String findMaxHash(String word) {
        int largestValue = 0;
        String largestWord = "";
        for (Map.Entry<String, Integer> entry : wordAssociation.get(word).entrySet()) {
            if (!entry.getKey().isEmpty()) {
                if (entry.getValue() > largestValue) {
                    largestValue = entry.getValue();
                    largestWord = entry.getKey();
                } else if (entry.getValue() == largestValue && entry.getKey().compareTo(largestWord) < 0)
                    largestWord = entry.getKey();
            }
        }
        if (largestWord.isEmpty())
            largestWord = seed;
        return largestWord;
    }

    /**
     * Generates a text where the next word is picked with the count probabilities
     * @param k - the number of words to generate
     */
    public void generateAll(int k) {
        HashMap<String, Integer> finalWordAssociation = new HashMap<>();
        Random random = new Random();
        String newSeed = seed;
        System.out.print(seed);

        for(int i = 1; i < k; i++) {
            int totalAppearence = 0;
            if(finalWordAssociation.containsKey(newSeed)) {
                totalAppearence = finalWordAssociation.get(newSeed);
            } else {
                for (Map.Entry<String, Integer> entry : wordAssociation.get(newSeed).entrySet())
                    if (!entry.getKey().isEmpty())
                        totalAppearence += entry.getValue();
                finalWordAssociation.put(newSeed, totalAppearence);
            }
            String randomWord = findRandomWord(newSeed, random, totalAppearence);
            System.out.print(" " + randomWord);
            newSeed = randomWord;
        }
    }

    /**
     * Method used to pick a word based on the probability of it appearing in the text
     * @param word, the root word which has associaitions
     * @param random, a random object
     * @param totalAppearence, the total amount of times the seed word appeared
     * @return the randomly picked word
     */
    public String findRandomWord(String word, Random random, int totalAppearence){
        if (totalAppearence != 0) {
            int randomNum = random.nextInt(totalAppearence) + 1;
            int currNum = 0;
            for (Map.Entry<String, Integer> entry : wordAssociation.get(word).entrySet()) {
                if (!entry.getKey().isEmpty()) {
                    currNum += entry.getValue();
                    if (randomNum <= currNum)
                        return entry.getKey();
                }
            }
        }
        return seed;
    }

}

