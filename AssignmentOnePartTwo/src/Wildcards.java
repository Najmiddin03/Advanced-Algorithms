import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Wildcards {
    public static boolean bruteForce(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();

        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                char textChar = text.charAt(i + j);
                char patternChar = pattern.charAt(j);
                if (patternChar != '?' && patternChar != '*' && patternChar != textChar) {
                    break;
                }
            }
            if (j == m) {
                return true;
            }
        }
        return false;
    }

    public static boolean sunday(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        int i = 0;

        while (i <= n - m) {
            int j;
            for (j = 0; j < m; j++) {
                char textChar = text.charAt(i + j);
                char patternChar = pattern.charAt(j);
                if (patternChar != '?' && patternChar != '*' && patternChar != textChar) {
                    int shift = calculateShift(text, i + m, patternChar);
                    i += shift;
                    break;
                }
            }
            if (j == m) {
                return true;
            }
        }
        return false;
    }

    private static int calculateShift(String text, int index, char target) {
        int n = text.length();
        if (index >= n) {
            return 1;
        }

        char nextChar = text.charAt(index);
        if (nextChar == target) {
            return 1;
        } else if (nextChar == '?' || nextChar == '*') {
            return 1 + calculateShift(text, index + 1, target);
        } else {
            return calculateShift(text, index + 1, target);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        String text = "";
        File file = new File("text.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            text += scanner.nextLine();
        }
        String pattern1 = "algo*?thm";
        String pattern2 = "m*?k";
        String pattern3 = "De*?line";
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
