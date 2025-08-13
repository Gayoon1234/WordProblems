package dictionary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Word lists from:
// https://github.com/dwyl/english-words?tab=readme-ov-file
// https://github.com/redbo/scrabble/blob/master/dictionary.txt

public class Dictionary {

    private static Dictionary dictionaryClass;
    private Set<String> dictionary;
    private DictionarySource dictionarySource = DictionarySource.SCRABBLE; //Can change
    
    // Performance optimizations - use HashSet for O(1) lookups
    private Set<String> lowercaseDictionary;
    private int minWordLength = Integer.MAX_VALUE;
    private int maxWordLength = 0;

    private Dictionary() {
       readDictionary();
    }

    private void readDictionary(){
        try (Stream<String> lines = Files.lines(Paths.get("./src/dictionary/resources/" + dictionarySource.getFileName()))) {
            dictionary = lines.collect(Collectors.toSet());
            
            // Build optimized lookup structures
            buildOptimizedStructures();
        } catch (IOException ignored){}
    }
    
    private void buildOptimizedStructures() {
        // Use HashSet for O(1) lookups
        lowercaseDictionary = new HashSet<>(dictionary.size());
        
        for (String word : dictionary) {
            lowercaseDictionary.add(word.toLowerCase());
            
            // Track word length bounds for quick rejection
            int length = word.length();
            if (length < minWordLength) minWordLength = length;
            if (length > maxWordLength) maxWordLength = length;
        }
    }

    public void switchDictionarySource(DictionarySource dsrc)  {
        dictionarySource = dsrc;
        dictionaryClass.readDictionary();
    }

    public static synchronized Dictionary getInstance(){
        if(dictionaryClass == null){
            dictionaryClass = new Dictionary();
        }
        return dictionaryClass;
    }

    public boolean isAWord(String word){
        if (word == null || word.isEmpty()) return false;
        
        // Quick length check - reject words outside dictionary bounds
        int length = word.length();
        if (length < minWordLength || length > maxWordLength) return false;
        
        // Direct lookup in lowercase dictionary - should be faster than original
        return lowercaseDictionary.contains(word.toLowerCase());
    }
    
    // Original method for backward compatibility
    public boolean isAWordOriginal(String word){
        return dictionary.contains(word.toUpperCase());
    }

    public Iterator<String> getIterableDictionary(){
        return dictionary.iterator();
    }

    public int getWordCount(){
        return dictionary.size();
    }
    
    // Performance statistics
    public void printPerformanceStats() {
        System.out.println("Dictionary loaded: " + dictionary.size() + " words");
        System.out.println("Word length range: " + minWordLength + " to " + maxWordLength);
        System.out.println("Optimized dictionary size: " + lowercaseDictionary.size());
    }
}
