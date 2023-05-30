public class StringMatcher {
    // Brute-force algorithm with wildcard support
    public static boolean bruteForceMatch(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();

        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (pattern.charAt(j) != '?' && text.charAt(i + j) != pattern.charAt(j))
                    break;
            }
            if (j == m)
                return true;
        }
        return false;
    }

    // Sunday algorithm with wildcard support
    public static boolean sundayMatch(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        int[] shiftTable = createShiftTable(pattern);

        int i = 0;
        while (i <= n - m) {
            int j = 0;
            while (j < m && (pattern.charAt(j) == '?' || text.charAt(i + j) == pattern.charAt(j))) {
                j++;
            }

            if (j == m)
                return true;

            if (i + m < n) {
                char nextChar = text.charAt(i + m);
                int shift = shiftTable[nextChar];
                i += m - shift;
            } else {
                break;
            }
        }
        return false;
    }

    // Create shift table for Sunday algorithm
    private static int[] createShiftTable(String pattern) {
        int[] shiftTable = new int[256];
        int m = pattern.length();
        for (int i = 0; i < 256; i++) {
            shiftTable[i] = m + 1; // default shift
        }
        for (int i = 0; i < m; i++) {
            char c = pattern.charAt(i);
            shiftTable[c] = m - i; // rightmost occurrence shift
        }
        return shiftTable;
    }

    public static void main(String[] args) {
        String text = "Hello, world!";
        String pattern = "Hello";

        boolean bruteForceResult = bruteForceMatch(text, pattern);
        boolean sundayResult = sundayMatch(text, pattern);

        System.out.println("Brute-force match: " + bruteForceResult);
        System.out.println("Sunday match: " + sundayResult);
    }
}
