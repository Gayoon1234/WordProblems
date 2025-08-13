package dictionary;

import java.util.BitSet;

/**
 * Bloom Filter implementation for fast rejection of non-words.
 * Provides O(k) lookup time where k is the number of hash functions.
 * May have false positives but never false negatives.
 */
public class BloomFilter {
    private final BitSet bitSet;
    private final int size;
    private final int hashCount;
    private final int bitSetSize;
    
    public BloomFilter(int expectedElements, double falsePositiveRate) {
        this.size = expectedElements;
        this.bitSetSize = calculateOptimalBitSetSize(expectedElements, falsePositiveRate);
        this.hashCount = calculateOptimalHashCount(expectedElements, bitSetSize);
        this.bitSet = new BitSet(bitSetSize);
    }
    
    public void add(String element) {
        if (element == null) return;
        
        for (int i = 0; i < hashCount; i++) {
            int hash = hash(element, i);
            bitSet.set(Math.abs(hash % bitSetSize));
        }
    }
    
    public boolean mightContain(String element) {
        if (element == null) return false;
        
        for (int i = 0; i < hashCount; i++) {
            int hash = hash(element, i);
            if (!bitSet.get(Math.abs(hash % bitSetSize))) {
                return false;
            }
        }
        return true;
    }
    
    private int hash(String element, int seed) {
        int hash = seed;
        for (char c : element.toCharArray()) {
            hash = 31 * hash + c;
        }
        return hash;
    }
    
    private int calculateOptimalBitSetSize(int expectedElements, double falsePositiveRate) {
        return (int) Math.ceil(-expectedElements * Math.log(falsePositiveRate) / (Math.log(2) * Math.log(2)));
    }
    
    private int calculateOptimalHashCount(int expectedElements, int bitSetSize) {
        return (int) Math.ceil((bitSetSize / (double) expectedElements) * Math.log(2));
    }
    
    public int getSize() {
        return bitSetSize;
    }
    
    public int getHashCount() {
        return hashCount;
    }
}
