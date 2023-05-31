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
                return true; // Match found
            }
        }
        return false; // No match found
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
                    // Calculate the shift for the next character in text
                    int shift = calculateShift(text, i + m, patternChar);
                    i += shift;
                    break;
                }
            }
            if (j == m) {
                return true; // Match found
            }
        }
        return false; // No match found
    }

    private static int calculateShift(String text, int index, char target) {
        int n = text.length();
        if (index >= n) {
            return 1; // Shift by 1 if target character is not found
        }

        char nextChar = text.charAt(index);
        if (nextChar == target) {
            return 1; // Shift by 1 if target character is found
        } else if (nextChar == '?' || nextChar == '*') {
            return 1 + calculateShift(text, index + 1, target); // Recursively calculate the shift
        } else {
            return calculateShift(text, index + 1, target); // Recursively calculate the shift
        }
    }

    public static void main(String[] args) {
        String text = "Hel?o World!";
        String pattern = "He*?o Wo*";

        boolean bruteForceMatch = bruteForce(text, pattern);
        boolean sundayMatch = sunday(text, pattern);

        System.out.println("Brute-force match: " + bruteForceMatch);
        System.out.println("Sunday match: " + sundayMatch);
    }
}
