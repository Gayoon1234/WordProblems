package utils;

import dictionary.Dictionary;
import java.util.*;

public class PerformanceTester {
    
    public static void runQuickPerformanceTest() {
        System.out.println("Running performance test for isAWord...");
        Dictionary dict = Dictionary.getInstance();
        dict.printPerformanceStats();
        
        // Test performance with sample words
        String[] testWords = {"hello", "world", "test", "xyzabc", "supercalifragilisticexpialidocious"};
        
        // Warm up JVM
        System.out.println("\nWarming up JVM...");
        for (int i = 0; i < 1000; i++) {
            dict.isAWord("test");
            dict.isAWordOriginal("test");
        }
        
        System.out.println("\nTesting optimized isAWord (1000 iterations):");
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            for (String word : testWords) {
                dict.isAWord(word);
            }
        }
        long endTime = System.nanoTime();
        double optimizedTime = (endTime - startTime) / 1_000_000.0;
        System.out.println("Optimized lookup time: " + optimizedTime + " ms");
        
        System.out.println("\nTesting original isAWord (1000 iterations):");
        startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            for (String word : testWords) {
                dict.isAWordOriginal(word);
            }
        }
        endTime = System.nanoTime();
        double originalTime = (endTime - startTime) / 1_000_000.0;
        System.out.println("Original lookup time: " + originalTime + " ms");
        
        System.out.println("\nPerformance comparison:");
        if (optimizedTime < originalTime) {
            double improvement = ((originalTime - optimizedTime) / originalTime) * 100;
            System.out.println("✅ Optimized version is " + String.format("%.1f", improvement) + "% faster");
        } else {
            double slowdown = ((optimizedTime - originalTime) / originalTime) * 100;
            System.out.println("❌ Optimized version is " + String.format("%.1f", slowdown) + "% slower");
        }
        
        // Test with realistic word permutations scenario
        System.out.println("\nTesting realistic scenario (word permutations):");
        String testWord = "hello";
        System.out.println("Testing subwords for: " + testWord);
        
        startTime = System.nanoTime();
        int subWordCount = WordUtils.getSubWordCount(testWord);
        endTime = System.nanoTime();
        System.out.println("Subword count: " + subWordCount + " (took " + (endTime - startTime) / 1_000_000.0 + " ms)");
        
        // Show some actual subwords found
        System.out.println("Sample subwords found:");
        Set<String> subWords = WordUtils.generateSubWords(testWord);
        int count = 0;
        for (String subWord : subWords) {
            if (count < 10) { // Show first 10 subwords
                System.out.println("  - " + subWord);
                count++;
            } else {
                System.out.println("  ... and " + (subWords.size() - 10) + " more");
                break;
            }
        }
        
        // Test with a longer word to see the real performance impact
        System.out.println("\nTesting with longer word (demonstrates exponential complexity):");
        String longWord = "programming";
        System.out.println("Testing subwords for: " + longWord);
        
        startTime = System.nanoTime();
        subWordCount = WordUtils.getSubWordCount(longWord);
        endTime = System.nanoTime();
        System.out.println("Subword count: " + subWordCount + " (took " + (endTime - startTime) / 1_000_000.0 + " ms)");
        
        System.out.println("\nNote: The real performance improvement will be seen when processing");
        System.out.println("the entire dictionary, not individual word lookups.");
    }
    
    public static void runLongPerformanceTest() {
        System.out.println("Running 10-minute comprehensive performance test...");
        System.out.println("This test will provide statistically significant results.");
        System.out.println("Press Ctrl+C to stop early if needed.\n");
        
        Dictionary dict = Dictionary.getInstance();
        dict.printPerformanceStats();
        
        // Generate a large set of test words for variety
        String[] testWords = generateTestWords();
        System.out.println("Generated " + testWords.length + " test words for comprehensive testing.");
        
        // Warm up JVM thoroughly
        System.out.println("\nWarming up JVM (this may take a few seconds)...");
        warmUpJVM(dict, testWords);
        
        // Test parameters
        final long TEST_DURATION_MS = 10 * 60 * 1000; // 10 minutes
        final long startTime = System.currentTimeMillis();
        final long endTime = startTime + TEST_DURATION_MS;
        
        // Performance counters
        long optimizedLookups = 0;
        long originalLookups = 0;
        long optimizedTotalTime = 0;
        long originalTotalTime = 0;
        
        // Progress tracking
        long lastProgressTime = startTime;
        final long PROGRESS_INTERVAL = 30 * 1000; // 30 seconds
        
        System.out.println("\nStarting 10-minute performance test...");
        System.out.println("Progress updates every 30 seconds.\n");
        
        try {
            while (System.currentTimeMillis() < endTime) {
                // Test optimized version
                long testStart = System.nanoTime();
                for (String word : testWords) {
                    dict.isAWord(word);
                    optimizedLookups++;
                }
                long testEnd = System.nanoTime();
                optimizedTotalTime += (testEnd - testStart);
                
                // Test original version
                testStart = System.nanoTime();
                for (String word : testWords) {
                    dict.isAWordOriginal(word);
                    originalLookups++;
                }
                testEnd = System.nanoTime();
                originalTotalTime += (testEnd - testStart);
                
                // Progress update
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastProgressTime >= PROGRESS_INTERVAL) {
                    long elapsedMinutes = (currentTime - startTime) / 60000;
                    long remainingMinutes = (endTime - currentTime) / 60000;
                    
                    System.out.printf("Progress: %d minutes elapsed, %d minutes remaining\n", 
                                    elapsedMinutes, remainingMinutes);
                    System.out.printf("Optimized: %d lookups, Original: %d lookups\n", 
                                    optimizedLookups, originalLookups);
                    
                    lastProgressTime = currentTime;
                }
            }
        } catch (Exception e) {
            System.out.println("Test interrupted: " + e.getMessage());
        }
        
        // Calculate final results
        long totalTestTime = System.currentTimeMillis() - startTime;
        double optimizedAvgTime = (optimizedTotalTime / 1_000_000.0) / optimizedLookups;
        double originalAvgTime = (originalTotalTime / 1_000_000.0) / originalLookups;
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMPREHENSIVE PERFORMANCE TEST RESULTS");
        System.out.println("=".repeat(60));
        System.out.printf("Total test time: %.1f minutes\n", totalTestTime / 60000.0);
        System.out.printf("Total optimized lookups: %,d\n", optimizedLookups);
        System.out.printf("Total original lookups: %,d\n", originalLookups);
        System.out.printf("Average optimized lookup time: %.6f ms\n", optimizedAvgTime);
        System.out.printf("Average original lookup time: %.6f ms\n", originalAvgTime);
        
        if (optimizedAvgTime < originalAvgTime) {
            double improvement = ((originalAvgTime - optimizedAvgTime) / originalAvgTime) * 100;
            System.out.printf("✅ Optimized version is %.2f%% faster\n", improvement);
        } else {
            double slowdown = ((optimizedAvgTime - originalAvgTime) / originalAvgTime) * 100;
            System.out.printf("❌ Optimized version is %.2f%% slower\n", slowdown);
        }
        
        System.out.printf("Throughput improvement: %.2fx\n", originalAvgTime / optimizedAvgTime);
        System.out.println("=".repeat(60));
    }
    
    private static String[] generateTestWords() {
        // Generate a diverse set of test words
        List<String> words = new ArrayList<>();
        
        // Common English words
        String[] commonWords = {"hello", "world", "test", "programming", "algorithm", "computer", "science", "data", "structure"};
        words.addAll(Arrays.asList(commonWords));
        
        // Random length words
        Random random = new Random(42); // Fixed seed for reproducibility
        for (int i = 0; i < 100; i++) {
            int length = 3 + random.nextInt(12); // 3-15 characters
            StringBuilder word = new StringBuilder();
            for (int j = 0; j < length; j++) {
                word.append((char) ('a' + random.nextInt(26)));
            }
            words.add(word.toString());
        }
        
        // Edge cases
        words.add("a");
        words.add("ab");
        words.add("abc");
        words.add("supercalifragilisticexpialidocious");
        words.add("pneumonoultramicroscopicsilicovolcanoconiosiss");
        
        return words.toArray(new String[0]);
    }
    
    private static void warmUpJVM(Dictionary dict, String[] testWords) {
        // Thorough JVM warm-up to eliminate startup effects
        for (int i = 0; i < 10000; i++) {
            for (String word : testWords) {
                dict.isAWord(word);
                dict.isAWordOriginal(word);
            }
            
            // Force garbage collection occasionally during warm-up
            if (i % 1000 == 0) {
                System.gc();
            }
        }
        System.out.println("JVM warm-up completed.");
    }
}
