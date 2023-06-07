import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SpellChecker {
    private final List<String> dictionaryList;
    String[] bbst;
    private final Trie dictionaryTrie;
    private final Set<String> dictionarySet;

    public SpellChecker() {
        dictionaryList = new ArrayList<>();
        dictionaryTrie = new Trie();
        dictionarySet = new HashSet<>();
    }

    public void buildDictionary(String dictionaryFilePath) throws FileNotFoundException {
        File file = new File(dictionaryFilePath);
        bbst = new String[size(file) + 1];
        Scanner scanner = new Scanner(file);
        int i = 0;
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine().trim().toLowerCase();
            dictionaryList.add(word);
            bbst[i] = word;
            i++;
            dictionaryTrie.insert(word);
            dictionarySet.add(word);
        }

        scanner.close();
    }

    public List<String> linearListSpellCheck(File file) throws FileNotFoundException {
        List<String> misspelledWords = new ArrayList<>();
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String word = scanner.next().trim().toLowerCase();
            if (!dictionaryList.contains(word)) {
                misspelledWords.add(word);
            }
        }

        scanner.close();
        return misspelledWords;
    }

    public List<String> binarySearchSpellCheck(File file) throws FileNotFoundException {
        List<String> misspelledWords = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String word = scanner.next().trim().toLowerCase();
            if (binarySearch(bbst, word) == -1) {
                misspelledWords.add(word);
            }
        }

        scanner.close();
        return misspelledWords;
    }

    public static int binarySearch(String[] arr, String target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int comparison = target.compareTo(arr[mid]);
            if (comparison == 0) {
                return mid;
            }

            if (comparison < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1; // Target not found
    }

    public List<String> trieSpellCheck(File file) throws FileNotFoundException {
        List<String> misspelledWords = new ArrayList<>();
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String word = scanner.next().trim().toLowerCase();
            if (!dictionaryTrie.search(word)) {
                misspelledWords.add(word);
            }
        }

        scanner.close();
        return misspelledWords;
    }

    public List<String> hashSetSpellCheck(File file) throws FileNotFoundException {
        List<String> misspelledWords = new ArrayList<>();
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String word = scanner.next().trim().toLowerCase();
            if (!dictionarySet.contains(word)) {
                misspelledWords.add(word);
            }
        }

        scanner.close();
        return misspelledWords;
    }

    public static void main(String[] args) throws FileNotFoundException {
        final int testTimes = 100;
        SpellChecker spellChecker = new SpellChecker();
        System.out.println("Main");
        // Build the dictionary
        try {
            spellChecker.buildDictionary("dictionary.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary file not found.");
            return;
        }
        System.out.println("Size:\tLinear\tBBST\tTrie\tHashSet");

        long startTime, endTime, duration;
        String[] textFilePath = {"small.txt", "medium.txt", "large.txt", "gigantic.txt"};

        for (int i = 0; i < textFilePath.length; i++) {
            //Build file
            File file = new File(textFilePath[i]);
            //Calculate size
            int size = SpellChecker.size(file);
            System.out.print(size);

            if (i < 2) {
                // Linear list approach
                startTime = System.nanoTime();
                spellChecker.linearListSpellCheck(file);
                endTime = System.nanoTime();
                duration = endTime - startTime;
                System.out.print("\t" + duration);
            } else {
                System.out.print("\t\t\t");
            }
            duration = 0;

            // BBST approach
            for (int j = 0; j < testTimes; j++) {
                startTime = System.nanoTime();
                spellChecker.binarySearchSpellCheck(file);
                endTime = System.nanoTime();
                duration += endTime - startTime;
            }
            System.out.print("\t" + duration / testTimes);
            duration = 0;

            // Trie approach
            for (int j = 0; j < testTimes; j++) {
                startTime = System.nanoTime();
                spellChecker.trieSpellCheck(file);
                endTime = System.nanoTime();
                duration += endTime - startTime;
            }
            System.out.print("\t" + duration / testTimes);
            duration = 0;

            // HashSet approach
            for (int j = 0; j < testTimes; j++) {
                startTime = System.nanoTime();
                spellChecker.hashSetSpellCheck(file);
                endTime = System.nanoTime();
                duration += endTime - startTime;
            }
            System.out.print("\t" + duration / testTimes + "\n");
        }
    }

    private static int size(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        int size = 0;
        while (scanner.hasNext()) {
            scanner.next();
            size++;
        }
        scanner.close();
        return size;
    }
}