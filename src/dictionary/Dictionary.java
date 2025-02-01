package dictionary;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Word lists from:
// https://github.com/dwyl/english-words?tab=readme-ov-file
// https://github.com/redbo/scrabble/blob/master/dictionary.txt

public class Dictionary {

    private static Dictionary dictionaryClass;
    private final Set<String> dictionary;

    private Dictionary() throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get("./src/dictionary/resources/dictionary.txt"))) {
            dictionary = lines.collect(Collectors.toSet());
        }
    }

    public static synchronized Dictionary getInstance(){
        if(dictionaryClass == null){
            try {
                dictionaryClass = new Dictionary();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return dictionaryClass;
    }

    public boolean isAWord(String word){
        return dictionary.contains(word.toUpperCase());
    }

    public Iterator<String> getIterableDictionary(){
        return dictionary.iterator();
    }

    public int getWordCount(){
        return dictionary.size();
    }

}
