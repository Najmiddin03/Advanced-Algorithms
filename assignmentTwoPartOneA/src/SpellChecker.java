import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SpellChecker {
    private final List<String> dictionaryList;
    private final HashSet<String> dictionaryHashSet;
    private final HashMap<String, Boolean> dictionaryHashMap;

    public SpellChecker() {
        dictionaryList = new ArrayList<>();
        dictionaryHashSet = new HashSet<>();
        dictionaryHashMap = new HashMap<>();
    }

    public void buildDictionary(String dictionaryFilePath) throws FileNotFoundException {
        File file = new File(dictionaryFilePath);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String word = scanner.nextLine().trim().toLowerCase();
            dictionaryList.add(word);
            dictionaryHashSet.add(word);
            dictionaryHashMap.put(word, true);
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

    public List<String> treeSetSpellCheck(File file) throws FileNotFoundException {
        List<String> misspelledWords = new ArrayList<>();
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String word = scanner.next().trim().toLowerCase();
            if (!dictionaryHashSet.contains(word)) {
                misspelledWords.add(word);
            }
        }

        scanner.close();
        return misspelledWords;
    }

    public List<String> hashMapSpellCheck(File file) throws FileNotFoundException {
        List<String> misspelledWords = new ArrayList<>();
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String word = scanner.next().trim().toLowerCase();
            if (!dictionaryHashMap.containsKey(word)) {
                misspelledWords.add(word);
            }
        }

        scanner.close();
        return misspelledWords;
    }

    public static void main(String[] args) throws FileNotFoundException {
        final int testTimes = 10;
        SpellChecker spellChecker = new SpellChecker();

        // Build the dictionary
        try {
            spellChecker.buildDictionary("dictionary.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary file not found.");
            return;
        }
        System.out.println("Size:  Linear  TreeSet  HashMap");


        long startTime, endTime, duration = 0;
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
                System.out.print("  " + duration);
            } else {
                System.out.print("\t\t\t");
            }
            duration = 0;

            // TreeSet approach
            for (int j = 0; j < testTimes; j++) {
                startTime = System.nanoTime();
                spellChecker.treeSetSpellCheck(file);
                endTime = System.nanoTime();
                duration += endTime - startTime;
            }
            System.out.print("  " + duration / testTimes);
            duration = 0;

            // HashMap approach
            for (int j = 0; j < testTimes; j++) {
                startTime = System.nanoTime();
                spellChecker.hashMapSpellCheck(file);
                endTime = System.nanoTime();
                duration += endTime - startTime;
            }
            System.out.print("  " + duration / testTimes + "\n");
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