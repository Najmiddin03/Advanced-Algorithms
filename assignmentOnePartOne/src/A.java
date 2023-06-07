import java.util.Random;

public class A {
    public static void main(String[] args) {
        final int times = 1000;
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";
        String pattern = "pattern matching algorithms";
        Random rand = new Random();
        System.out.println("Size:\tBrute\tSunday\tKMP\tRabin\tGusfield");
        for (int i = 1000; i <= 100000; i += 1000) {

            String text = "";
            System.out.print(i);
            for (int j = 0; j < i; j++) {
                text += alpha.charAt(rand.nextInt(alpha.length()));
            }
            long startTime, endTime;
            long brute = 0;
            long sunday = 0;
            long kmp = 0;
            long rabin = 0;
            long gusfield = 0;

            for (int j = 0; j < times; j++) {
                // Brute Force
                startTime = System.nanoTime();
                PatternMatchingAlgorithms.bruteForce(text, pattern);
                endTime = System.nanoTime();
                brute += endTime - startTime;

                // Sunday
                startTime = System.nanoTime();
                PatternMatchingAlgorithms.sunday(text, pattern);
                endTime = System.nanoTime();
                sunday += endTime - startTime;

                // KMP
                startTime = System.nanoTime();
                PatternMatchingAlgorithms.kmp(text, pattern);
                endTime = System.nanoTime();
                kmp += endTime - startTime;

                // Rabin-Karp
                startTime = System.nanoTime();
                PatternMatchingAlgorithms.rabinKarp(text, pattern);
                endTime = System.nanoTime();
                rabin += endTime - startTime;

                // Gusfield Z
                startTime = System.nanoTime();
                PatternMatchingAlgorithms.gusfieldZ(text, pattern);
                endTime = System.nanoTime();
                gusfield += endTime - startTime;
            }
            brute /= times;
            System.out.print("\t" + brute);
            sunday /= times;
            System.out.print("\t" + sunday);
            kmp /= times;
            System.out.print("\t" + kmp);
            rabin /= times;
            System.out.print("\t" + rabin);
            gusfield /= times;
            System.out.print("\t" + gusfield + "\n");
        }
    }
}
