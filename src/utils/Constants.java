package utils;

public class Constants {
    public static final String USAGE_MESSAGE = """
        Usage: java Main <option> [word]

        Options:
          - subwords   : Find words that can be formed using the letters of the given word.
                         If no word is provided, prints valid permutations.
          - violations : Identify words that violate the 'i' before 'e' except after 'c' rule.
          - perftest   : Run quick performance test for the optimized isAWord method.
          - longperf   : Run comprehensive 10-minute performance test for statistically significant results.

        Examples:
          java Program subwords apple
          java Program violations
          java Program perftest
          java Program longperf
        """;
    
    public static final String SUBWORDS_WARNING = """
        Warning: As you have not provided a specific word, the program will run for each word in the dictionary.
        This is take some time. Press 'y' to continue, or any other key to cancel.
        """;
    
    public static final String INVALID_ARGUMENT_MESSAGE = "Invalid argument. Use 'subwords' to print valid permutations, 'violations' to print violations, 'perftest' for quick performance testing, or 'longperf' for comprehensive performance testing.";
    public static final String EXITED_MESSAGE = "Exited";
}
