package dictionary;


public enum DictionarySource {
    SCRABBLE("dictionary.txt"),
    INFOCHIMPS_ALPHA("words.txt"),
    INFOCHIMPS_ALL("words_alpha.txt");

    private final String fileName;

    DictionarySource(String filePath) {
        this.fileName = filePath;
    }

    public String getFileName() {
        return fileName;
    }
}
