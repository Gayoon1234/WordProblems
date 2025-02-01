import java.util.*;

import static problems.IBeforeEExceptAfterC.getViolatingRules;
import static problems.ValidWordPermutations.getValidWords;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("""
                    Please provide an argument: \
                    
                      - 'subwords': See how many words can be formed using the letters of each word\
                    
                      - 'violations': See which word violated 'i' before 'e' except after 'c'""");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "subwords":
                printValidPermutations();
                break;
            case "violations":
                printViolations();
                break;
            default:
                System.out.println("Invalid argument. Use 'valid' to print valid permutations or 'violations' to print violations.");
        }
    }

    private static void printValidPermutations(){
        Map<Integer, Set<String>> subWordCounter = getValidWords();

        for(int i: subWordCounter.keySet()){
            System.out.println(i + ", " + subWordCounter.get(i));
        }
    }

    private static void printViolations(){
        Set<String> violatingRules = getViolatingRules();
        for(String s: violatingRules){
            System.out.println(s);
        }
    }
}