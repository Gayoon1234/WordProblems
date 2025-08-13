package problems;

import dictionary.Dictionary;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class IBeforeEExceptAfterC {

    public static Set<String> getViolatingRules() {
        Set<String> words = new TreeSet<>();
        Iterator<String> dictionary = Dictionary.getInstance().getIterableDictionary();

        while (dictionary.hasNext()){
            String currentWord = dictionary.next();
            if(currentWord.matches(".*[^C]EI.*") | currentWord.matches(".*CIE.*")){
                words.add(currentWord);
            }
        }
        return words;
    }

    public static void printViolations(){
        Set<String> violatingRules = getViolatingRules();
        for(String s: violatingRules){
            System.out.println(s);
        }
    }
}
