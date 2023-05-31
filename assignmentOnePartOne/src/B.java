import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class B {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("text.txt");
        String text = "";
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            text += scanner.nextLine();
        }
        String pattern = "algorithm";
        long start, end, sunday = 0, gusfield = 0, kmp = 0, rabin = 0;

        for (int i = 0; i < 1000; i++) {

            // Test Sunday
            start = System.nanoTime();
            PatternMatchingAlgorithms.sunday(text, pattern);
            end = System.nanoTime();
            sunday += end - start;

            // Test Gusfield
            start = System.nanoTime();
            PatternMatchingAlgorithms.gusfieldZ(text, pattern);
            end = System.nanoTime();
            gusfield += end - start;

            // Test KMP
            start = System.nanoTime();
            PatternMatchingAlgorithms.kmp(text, pattern);
            end = System.nanoTime();
            kmp += end - start;

            // Test Rabin-Karp
            start = System.nanoTime();
            PatternMatchingAlgorithms.rabinKarp(text, pattern);
            end = System.nanoTime();
            rabin += end - start;
        }
        sunday /= 1000;
        gusfield /= 1000;
        rabin /= 1000;
        kmp /= 1000;
        // Sunday vs. Gusfield Z
        System.out.println("Sunday is " + (double) gusfield / sunday + " times as fast as GusfieldZ");
        // KMP vs. Rabin-Karp
        System.out.println("KMP is " + (double) rabin / kmp + " times as fast as Rabin-Karp");
        // Rabin-Karp vs. Sunday
        System.out.println("Rabin-Karp is " + (double) sunday / rabin + " times as fast as Sunday");
    }
}