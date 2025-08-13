package problems;

import dictionary.Dictionary;

import java.util.*;

import static utils.WordUtils.getSubWordCount;

public class ValidWordPermutations {

    // execution time: 72315 ms (before optimization)
    public static Map<Integer, Set<String>> getValidWords(){
        Map<Integer, Set<String>> subWordCounter = new TreeMap<>(); // Use TreeMap for sorted output
        Iterator<String> words = Dictionary.getInstance().getIterableDictionary();
        
        int processedCount = 0;
        int totalWords = 0;
        
        // First pass: count total words and filter by length
        List<String> validWords = new ArrayList<>();
        while (words.hasNext()) {
            String word = words.next();
            if (word.length() >= 4) {
                validWords.add(word);
                totalWords++;
            }
        }
        
        System.out.println("Processing " + totalWords + " words...");
        
        // Process filtered words
        for (String word : validWords) {
            processedCount++;
            if (processedCount % 1000 == 0) {
                System.out.println("Processed " + processedCount + "/" + totalWords + " words...");
            }
            
            int subCount = getSubWordCount(word);
            subWordCounter.computeIfAbsent(subCount, k -> new TreeSet<>()).add(word); // Use TreeSet for sorted output
        }
        
        System.out.println("Completed processing " + processedCount + " words.");
        return subWordCounter;
    }

    public static void printValidPermutations(){
        Map<Integer, Set<String>> subWordCounter = getValidWords();
        for(Map.Entry<Integer, Set<String>> entry : subWordCounter.entrySet()){
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }
}
