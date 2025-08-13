package utils;

public class Constants {
    public static final String USAGE_MESSAGE = """
        Usage: java Main <option> [word]

        Options:
          - subwords   : Find words that can be formed using the letters of the given word.
                         If no word is provided, prints valid permutations.
          - violations : Identify words that violate the 'i' before 'e' except after 'c' rule.

        Examples:
          java Program subwords apple
          java Program violations
        """;
    
    public static final String SUBWORDS_WARNING = """
        Warning: As you have not provided a specific word, the program will run for each word in the dictionary.
        This is take some time. Press 'y' to continue, or any other key to cancel.
        """;
    
    public static final String INVALID_ARGUMENT_MESSAGE = "Invalid argument. Use 'subwords' to print valid permutations or 'violations' to print violations.";
    public static final String EXITED_MESSAGE = "Exited";
}
