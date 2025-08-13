package utils;

import dictionary.Dictionary;
import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.*;

public class WordUtils {
    private static final Dictionary DICTIONARY = Dictionary.getInstance();
    private static final Set<Character> VOWELS = Set.of('A', 'E', 'I', 'O', 'U', 'Y');
    private static final int MIN_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH_FOR_COMBINATIONS = 15; // Avoid exponential explosion
    
    // Cache for word combinations to avoid recalculating
    private static final Map<String, Integer> WORD_COUNT_CACHE = new HashMap<>();

    public static void printSubWords(String word){
        for(String w : generateSubWords(word)) {
            System.out.println(w);
        }
    }

    public static int getSubWordCount(String word){
        return WORD_COUNT_CACHE.computeIfAbsent(word, k -> calculateSubWordCount(k));
    }

    public static void clearCache() {
        WORD_COUNT_CACHE.clear();
    }

    private static int calculateSubWordCount(String word) {
        if (word.length() < MIN_WORD_LENGTH) return 0;
        
        // For very long words, the number of combinations becomes astronomical
        // Use a heuristic approach instead
        if (word.length() > MAX_WORD_LENGTH_FOR_COMBINATIONS) {
            return estimateSubWordCount(word);
        }
        
        Set<String> uniqueWords = new HashSet<>();
        char[] letters = word.toCharArray();
        
        // Pre-calculate vowel positions for efficiency
        boolean[] hasVowelAtPosition = new boolean[letters.length];
        for (int i = 0; i < letters.length; i++) {
            hasVowelAtPosition[i] = VOWELS.contains(letters[i]);
        }

        for (int length = MIN_WORD_LENGTH; length <= letters.length; length++) {
            Iterator<int[]> iter = CombinatoricsUtils.combinationsIterator(letters.length, length);
            while (iter.hasNext()) {
                int[] indices = iter.next();
                
                // Quick vowel check using pre-calculated positions
                boolean hasVowel = false;
                for (int index : indices) {
                    if (hasVowelAtPosition[index]) {
                        hasVowel = true;
                        break;
                    }
                }
                if (!hasVowel) continue;

                // Build combination more efficiently
                char[] combination = new char[length];
                for (int i = 0; i < length; i++) {
                    combination[i] = letters[indices[i]];
                }
                
                String combinationStr = new String(combination);
                
                // Check dictionary only if not already found
                if (!uniqueWords.contains(combinationStr) && DICTIONARY.isAWord(combinationStr)) {
                    uniqueWords.add(combinationStr);
                }
            }
        }
        return uniqueWords.size();
    }

    private static int estimateSubWordCount(String word) {
        // For very long words, estimate based on character frequency and common patterns
        // This avoids the exponential complexity of generating all combinations
        int vowelCount = 0;
        int consonantCount = 0;
        
        for (char c : word.toCharArray()) {
            if (VOWELS.contains(c)) {
                vowelCount++;
            } else {
                consonantCount++;
            }
        }
        
        // Estimate based on typical English word patterns
        // Most valid subwords will be 3-8 characters long
        int estimatedCount = Math.min(vowelCount * 2, consonantCount * 3);
        return Math.max(estimatedCount, 10); // Minimum reasonable estimate
    }

    private static Set<String> generateSubWords(String word) {
        if (word.length() < MIN_WORD_LENGTH) return new HashSet<>();
        
        Set<String> uniqueWords = new HashSet<>();
        char[] letters = word.toCharArray();
        
        // Pre-calculate vowel positions
        boolean[] hasVowelAtPosition = new boolean[letters.length];
        for (int i = 0; i < letters.length; i++) {
            hasVowelAtPosition[i] = VOWELS.contains(letters[i]);
        }

        for (int length = MIN_WORD_LENGTH; length <= letters.length; length++) {
            Iterator<int[]> iter = CombinatoricsUtils.combinationsIterator(letters.length, length);
            while (iter.hasNext()) {
                int[] indices = iter.next();
                
                // Quick vowel check
                boolean hasVowel = false;
                for (int index : indices) {
                    if (hasVowelAtPosition[index]) {
                        hasVowel = true;
                        break;
                    }
                }
                if (!hasVowel) continue;

                // Build combination efficiently
                char[] combination = new char[length];
                for (int i = 0; i < length; i++) {
                    combination[i] = letters[indices[i]];
                }
                
                String combinationStr = new String(combination);
                
                if (!uniqueWords.contains(combinationStr) && DICTIONARY.isAWord(combinationStr)) {
                    uniqueWords.add(combinationStr);
                }
            }
        }
        return uniqueWords;
    }
}
