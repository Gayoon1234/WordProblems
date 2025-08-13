import java.util.Scanner;

import static problems.IBeforeEExceptAfterC.printViolations;
import static problems.ValidWordPermutations.printValidPermutations;
import static utils.Constants.*;
import static utils.WordUtils.printSubWords;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(USAGE_MESSAGE);
            return;
        }

        processCommand(args);
    }

    private static void processCommand(String[] args) {
        switch (args[0].toLowerCase()) {
            case "subwords" -> handleSubwords(args);
            case "violations" -> printViolations();
            default -> System.out.println(INVALID_ARGUMENT_MESSAGE);
        }
    }

    private static void handleSubwords(String[] args) {
        if (args.length > 1) {
            printSubWords(args[1]);
        } else {
            handleSubwordsWithoutWord();
        }
    }

    private static void handleSubwordsWithoutWord() {
        System.out.println(SUBWORDS_WARNING);
        if (getUserConfirmation()) {
            printValidPermutations();
        } else {
            System.out.println(EXITED_MESSAGE);
        }
    }

    private static boolean getUserConfirmation() {
        return new Scanner(System.in).nextLine().trim().toLowerCase().equals("y");
    }
}