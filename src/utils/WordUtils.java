package utils;

import dictionary.Dictionary;
import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class WordUtils {

    public static void printSubWords(String word){
        for(String w : generateSubWords(word)) {
            System.out.println(w);
        }
    }

    public static int getSubWordCount(String word){
        return generateSubWords(word).size();
    }

    private static Set<String> generateSubWords(String word) {
        Dictionary dictionary = Dictionary.getInstance();

        StringBuilder letters = new StringBuilder(word);
        Set<Character> vowels = Set.of('A', 'E', 'I', 'O', 'U', 'Y');
        Set<String> uniqueWords = new HashSet<>();

        for (int i = 3; i <= letters.length(); i++) {

            // iter uses combinations to generate every arrangement of the letters
            Iterator<int[]> iter = CombinatoricsUtils.combinationsIterator(letters.length(), i);
            while (iter.hasNext()) {
                int[] indices = iter.next();
                StringBuilder combination = new StringBuilder();
                boolean hasVowel = false;

                for (int index : indices) {
                    char letter = letters.charAt(index);
                    combination.append(letter);
                    if (vowels.contains(letter)) hasVowel = true;
                }

                // it's probably not a word if there are no vowels/y
                if (!hasVowel) continue;

                String combinationStr = combination.toString();

                // prevents creating the same word with different letters
                if (uniqueWords.contains(combinationStr)) continue;

                // Checks against dictionary
                if (dictionary.isAWord(combinationStr)) {
                    uniqueWords.add(combinationStr);
                }
            }
        }
        return uniqueWords;
    }
}
