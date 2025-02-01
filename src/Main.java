import dictionary.Dictionary;
import java.util.*;

import static utils.WordUtils.getSubWordCount;
import static utils.WordUtils.printSubWords;

public class Main {
    public static void main(String[] args) {
        Iterator<String> words = Dictionary.getInstance().getIterableDictionary();

        Map<Integer, Set<String>> subWordCounter = new HashMap<>();

        int i = 0, max = 0;
        while (words.hasNext()){

            String word = words.next();

            if(word.length() < 4) continue;
            int subCount = getSubWordCount(word);

            if(max < subCount) {
                max = subCount;
                System.out.println("Current max: " + max);
                System.out.println("Searches :" + i);
                System.out.println("Word: " + word);
            }

            subWordCounter.computeIfAbsent(subCount, k -> new HashSet<>());
            subWordCounter.get(subCount).add(word);

            ++i;
        }

        for(int idx: subWordCounter.keySet()){
            System.out.println(idx + ", " + subWordCounter.get(idx));
        }
    }

}