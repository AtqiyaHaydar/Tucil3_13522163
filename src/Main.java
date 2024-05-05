import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Set<String> dictionary = loadDictionary("dictionary.txt");

    System.out.println("Word Ladder!");

    String startWord, endWord;
    boolean hasMatchingLetter;
    do {
      System.out.print("Enter the start word: ");
      startWord = scanner.nextLine();

      System.out.print("Enter the end word: ");
      endWord = scanner.nextLine();

      if (startWord.length() != endWord.length()) {
        System.out.println("The words must be the same length!");
      }

      if (!dictionary.contains(startWord.toLowerCase()) || !dictionary.contains(endWord.toLowerCase())) {
        System.out.println("Start word or end word must be an english word!");
      }

      hasMatchingLetter = false;
      for (int i = 0; i < startWord.length(); i++) {
        if (startWord.charAt(i) == endWord.charAt(i)) {
          hasMatchingLetter = true;
          break;
        }
      }

      if (!hasMatchingLetter) {
        System.out.println("Start word and end word must have at least one matching letter!");
      }
    } while (startWord.length() != endWord.length() || !dictionary.contains(startWord.toLowerCase()) || !dictionary.contains(endWord.toLowerCase()) || !hasMatchingLetter);

    System.out.println("Choose a Solving Algorithm: ");
    System.out.println("1. Uniform Cost Search");
    System.out.println("2. Greedy Best First Search");
    System.out.println("3. A* Search");

    Integer choice;
    do {
        System.out.print("Choice: ");
        choice = Integer.parseInt(scanner.nextLine());
    } while (choice != 1 && choice != 2 && choice != 3);

    if (choice.equals(1)) {
      System.out.println("Solving with Uniform Cost Search");

    }
    else if (choice.equals(2)) {
      System.out.println("Solving with Greedy Best First Search");

    }
    else if (choice.equals(3)) {
      System.out.println("Solving with A* Search");

    }


    scanner.close();
  }

  private static Set<String> loadDictionary(String filename) {
    Set<String> dictionary = new HashSet<>();
    try {
        Scanner fileScanner = new Scanner(new File(filename));
        while (fileScanner.hasNextLine()) {
          dictionary.add(fileScanner.nextLine().trim().toLowerCase());
        }
        fileScanner.close();
      } catch (FileNotFoundException e) {
        System.out.println("Dictionary file not found: " + e.getMessage());
      }
    return dictionary;
  }
}
