package problems;

import dictionary.Dictionary;

import java.util.*;

import static utils.WordUtils.getSubWordCount;

public class ValidWordPermutations {

    // execution time: 72315 ms
    public static Map<Integer, Set<String>> getValidWords(){
        Map<Integer, Set<String>> subWordCounter = new HashMap<>();

        Iterator<String> words = Dictionary.getInstance().getIterableDictionary();
        while (words.hasNext()){
            String word = words.next();

            if(word.length() < 4) continue;
            int subCount = getSubWordCount(word);

            subWordCounter.computeIfAbsent(subCount, k -> new HashSet<>());
            subWordCounter.get(subCount).add(word);
        }

        return subWordCounter;
    }
}
