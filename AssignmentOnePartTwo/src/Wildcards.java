import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Wildcards {
    public static boolean bruteForce(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        int i = 0;
        while (i <= n - m) {
            int j = 0;
            int matchIndex = -1;
            while (j < m) {
                char textChar = text.charAt(i + j);
                char patternChar = pattern.charAt(j);
                if (patternChar != '?' && patternChar != '*' && patternChar != textChar) {
                    break;
                }
                if (patternChar == '*') {
                    matchIndex = j;
                }
                j++;
            }
            if (j == m) {
                return true;
            }
            if (matchIndex != -1) {
                char nextPatternChar = pattern.charAt(matchIndex + 1);
                int k = i + matchIndex;
                while (k <= n - m + 1) {
                    if (bruteForce(text.substring(k), pattern.substring(matchIndex + 1))) {
                        return true;
                    }
                    if (k == n - 1 || (nextPatternChar != '?' && nextPatternChar != '*' && nextPatternChar != text.charAt(k + 1))) {
                        break;
                    }
                    k++;
                }
            }
            i++;
        }
        return false;
    }

    public static boolean sunday(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        int i = 0;
        while (i <= n - m) {
            int j = 0;
            while (j < m) {
                char textChar = text.charAt(i + j);
                char patternChar = pattern.charAt(j);
                if (patternChar != '?' && patternChar != '*' && patternChar != textChar) {
                    break;
                }
                if (patternChar == '*') {
                    if (j == m - 1) {
                        return true;
                    }
                    char nextPatternChar = pattern.charAt(j + 1);
                    int k = i + j;
                    while (k <= n - m + 1) {
                        if (sunday(text.substring(k), pattern.substring(j + 1))) {
                            return true;
                        }
                        if (k == n - 1 || (nextPatternChar != '?' && nextPatternChar != '*' && nextPatternChar != text.charAt(k + 1))) {
                            break;
                        }
                        k++;
                    }
                    break;
                }

                j++;
            }
            if (j == m) {
                return true;
            }
            int shift = 1;
            if (i + m < n) {
                char nextChar = text.charAt(i + m);
                int index = pattern.indexOf(nextChar);
                if (index != -1 && index < m - 1) {
                    shift = m - index - 1;
                }
            }
            i += shift;
        }
        return false;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String text = "";
        File file = new File("text.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            text += scanner.nextLine();
        }

        String pattern1 = "a*or?thm";
        String pattern2 = "ma?k";
        String pattern3 = "De*line";
        String pattern4 = "hel*o";

        System.out.println("Brute Force:");
        System.out.println(pattern1 + ": " + bruteForce(text, pattern1));
        System.out.println(pattern2 + ": " + bruteForce(text, pattern2));
        System.out.println(pattern3 + ": " + bruteForce(text, pattern3));
        System.out.println(pattern4 + ": " + bruteForce(text, pattern4));
        System.out.println();
        System.out.println("Sunday:");
        System.out.println(pattern1 + ": " + sunday(text, pattern1));
        System.out.println(pattern2 + ": " + sunday(text, pattern2));
        System.out.println(pattern3 + ": " + sunday(text, pattern3));
        System.out.println(pattern4 + ": " + sunday(text, pattern4));
    }
}
