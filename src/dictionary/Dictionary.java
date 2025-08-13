package dictionary;

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
    private Set<String> dictionary;
    private DictionarySource dictionarySource = DictionarySource.SCRABBLE; //Can change

    private Dictionary() {
       readDictionary();
    }

    private void readDictionary(){
        try (Stream<String> lines = Files.lines(Paths.get("./src/dictionary/resources/" + dictionarySource.getFileName()))) {
            dictionary = lines.collect(Collectors.toSet());
        } catch (IOException ignored){}
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
        return dictionary.contains(word.toUpperCase());
    }

    public Iterator<String> getIterableDictionary(){
        return dictionary.iterator();
    }

    public int getWordCount(){
        return dictionary.size();
    }

}
