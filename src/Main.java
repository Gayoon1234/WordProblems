import java.util.*;

import static problems.IBeforeEExceptAfterC.getViolatingRules;
import static problems.ValidWordPermutations.getValidWords;
import static utils.WordUtils.printSubWords;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("""
                Usage: java Main <option> [word]

                Options:
                  - subwords   : Find words that can be formed using the letters of the given word.
                                 If no word is provided, prints valid permutations.
                  - violations : Identify words that violate the 'i' before 'e' except after 'c' rule.

                Examples:
                  java Program subwords apple
                  java Program violations
                """);
            return;
        }

        switch (args[0].toLowerCase()) {
            case "subwords":
                if(args.length > 1){
                    printSubWords(args[1]);
                } else {
                    System.out.println("""
                        Warning: As you have not provided a specific word, the program will run for each word in the dictionary.
                        This is take some time. Press 'y' to continue, or any other key to cancel.
                        """);
                    Scanner scanner = new Scanner(System.in);
                    String input = scanner.nextLine().trim().toLowerCase();
                    if (input.equals("y")) {
                        printValidPermutations();
                    } else {
                        System.out.println("Exited");
                    }
                }
                break;
            case "violations":
                printViolations();
                break;
            default:
                System.out.println("Invalid argument. Use 'subwords' to print valid permutations or 'violations' to print violations.");
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