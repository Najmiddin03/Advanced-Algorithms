import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatternMatchingAlgorithms {

    // Brute Force algorithm
    public static List<Integer> bruteForce(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();

        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) break;
            }
            if (j == m) {
                matches.add(i);
            }
        }

        return matches;
    }

    // Sunday algorithm
    public static List<Integer> sunday(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();
        int[] shift = new int[256];

        for (int i = 0; i < shift.length; i++) {
            shift[i] = m + 1;
        }

        for (int i = 0; i < m; i++) {
            shift[pattern.charAt(i)] = m - i;
        }

        int i = 0;
        while (i <= n - m) {
            int j;
            for (j = 0; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) break;
            }
            if (j == m) {
                matches.add(i);
            }
            if (i + m >= n) break;
            i += shift[text.charAt(i + m)];
        }

        return matches;
    }

    // Knuth-Morris-Pratt (KMP) algorithm
    public static List<Integer> kmp(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();
        int[] lps = computeLPS(pattern);

        int i = 0; // index for text
        int j = 0; // index for pattern
        while (i < n) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }
            if (j == m) {
                matches.add(i - j);
                j = lps[j - 1];
            } else if (i < n && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) j = lps[j - 1];
                else i++;
            }
        }

        return matches;
    }

    private static int[] computeLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int len = 0;
        int i = 1;

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    // Rabin-Karp algorithm
    public static List<Integer> rabinKarp(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();
        int d = 256; // Number of characters in the input alphabet
        int q = 101; // A prime number

        int h = 1;
        for (int i = 0; i < m - 1; i++) {
            h = (h * d) % q;
        }

        int p = 0; // Hash value for pattern
        int t = 0; // Hash value for text
        for (int i = 0; i < m && i < n; i++) {
            p = (d * p + pattern.charAt(i)) % q;
            t = (d * t + text.charAt(i)) % q;
        }

        for (int i = 0; i <= n - m; i++) {
            if (p == t) {
                int j;
                for (j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) break;
                }
                if (j == m) {
                    matches.add(i);
                }
            }

            if (i < n - m) {
                t = (d * (t - text.charAt(i) * h) + text.charAt(i + m)) % q;
                if (t < 0) t = t + q;
            }
        }

        return matches;
    }

    // Gusfield Z algorithm
    public static List<Integer> gusfieldZ(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        String concat = pattern + "$" + text;
        int n = concat.length();
        int m = pattern.length();
        int[] z = computeZ(concat);

        for (int i = m + 1; i < n; i++) {
            if (z[i] == m) {
                matches.add(i - m - 1);
            }
        }

        return matches;
    }

    private static int[] computeZ(String str) {
        int n = str.length();
        int[] z = new int[n];
        int left = 0;
        int right = 0;

        for (int i = 1; i < n; i++) {
            if (i > right) {
                left = right = i;
                while (right < n && str.charAt(right - left) == str.charAt(right)) {
                    right++;
                }
                z[i] = right - left;
                right--;
            } else {
                int k = i - left;
                if (z[k] < right - i + 1) {
                    z[i] = z[k];
                } else {
                    left = i;
                    while (right < n && str.charAt(right - left) == str.charAt(right)) {
                        right++;
                    }
                    z[i] = right - left;
                    right--;
                }
            }
        }

        return z;
    }

    public static void main(String[] args) throws IOException {
        // Test the pattern matching algorithms
        String text1 = "";
        String text2 = "This is short version to test the occurances of the test";
        String pattern = "test";
        FileReader fr = new FileReader("text.txt");
        int val = fr.read();
        while (val != -1) {
            text1 += (char) val;
            val = fr.read();
        }
        fr.close();

        // Calculate the runtimes for each algorithm on text1
        long startTime, endTime, elapsedTime;

        // Brute Force on text1
        startTime = System.nanoTime();
        bruteForce(text1, pattern);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        System.out.println("Text1 (Brute Force) - Runtime (ns): " + elapsedTime);

        // Sunday on text1
        startTime = System.nanoTime();
        sunday(text1, pattern);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        System.out.println("Text1 (Sunday) - Runtime (ns): " + elapsedTime);

        // KMP on text1
        startTime = System.nanoTime();
        kmp(text1, pattern);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        System.out.println("Text1 (KMP) - Runtime (ns): " + elapsedTime);

        // Rabin-Karp on text1
        startTime = System.nanoTime();
        rabinKarp(text1, pattern);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        System.out.println("Text1 (Rabin-Karp) - Runtime (ns): " + elapsedTime);

        // Gusfield Z on text1
        startTime = System.nanoTime();
        gusfieldZ(text1, pattern);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        System.out.println("Text1 (Gusfield Z) - Runtime (ns): " + elapsedTime);

        System.out.println();

        // Calculate the runtimes for each algorithm on text2

        // Brute Force on text2
        startTime = System.nanoTime();
        bruteForce(text2, pattern);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        System.out.println("Text2 (Brute Force) - Runtime (ns): " + elapsedTime);

        // Sunday on text2
        startTime = System.nanoTime();
        sunday(text2, pattern);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        System.out.println("Text2 (Sunday) - Runtime (ns): " + elapsedTime);

        // KMP on text2
        startTime = System.nanoTime();
        kmp(text2, pattern);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        System.out.println("Text2 (KMP) - Runtime (ns): " + elapsedTime);

        // Rabin-Karp on text2
        startTime = System.nanoTime();
        rabinKarp(text2, pattern);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        System.out.println("Text2 (Rabin-Karp) - Runtime (ns): " + elapsedTime);

        // Gusfield Z on text2
        startTime = System.nanoTime();
        gusfieldZ(text2, pattern);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        System.out.println("Text2 (Gusfield Z) - Runtime (ns): " + elapsedTime);
    }
}
