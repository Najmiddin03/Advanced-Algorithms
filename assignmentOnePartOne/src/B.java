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
        long start, end, sunday, gusfield, kmp, rabin;

        // Sunday vs. Gusfield Z
        // Test Sunday
        start = System.nanoTime();
        PatternMatchingAlgorithms.sunday(text, pattern);
        end = System.nanoTime();
        sunday = end - start;

        // Test Gusfield
        start = System.nanoTime();
        PatternMatchingAlgorithms.gusfieldZ(text, pattern);
        end = System.nanoTime();
        gusfield = end - start;

        System.out.println("Sunday is " + gusfield / sunday + " times as fast as GusfieldZ");


        // KMP vs. Rabin-Karp
        // Test KMP
        start = System.nanoTime();
        PatternMatchingAlgorithms.kmp(text, pattern);
        end = System.nanoTime();
        kmp = end - start;

        // Test Rabin-Karp
        start = System.nanoTime();
        PatternMatchingAlgorithms.rabinKarp(text, pattern);
        end = System.nanoTime();
        rabin = end - start;

        System.out.println(rabin+" "+kmp);
        System.out.println("KMP is " + (double)rabin / kmp + " times as fast as Rabin-Karp");

    }
}
