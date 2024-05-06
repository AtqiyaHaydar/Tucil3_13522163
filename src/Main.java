import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

public class Main {
  static class SearchResult {
    ArrayList<String> path;
    int nodeVisited;

    SearchResult(ArrayList<String> path, int nodeVisited) {
        this.path = path;
        this.nodeVisited = nodeVisited;
    }
  }
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Set<String> dictionary = loadDictionary("dictionary.txt");

    System.out.println("Welcome to Word Ladder Game!");

    Integer choice;
    do {
      System.out.println("Choose a Solving Algorithm: ");
      System.out.println("1. Uniform Cost Search");
      System.out.println("2. Greedy Best First Search");
      System.out.println("3. A* Search");
      System.out.println("4. Exit");

      do {
        System.out.print("Choice: ");
        choice = Integer.parseInt(scanner.nextLine());

        if (choice != 1 && choice != 2 && choice != 3 && choice != 4) {
          System.out.println("Invalid choice!");
        }
      } while (choice != 1 && choice != 2 && choice != 3 && choice != 4);

      if (choice.equals(4)) {
        System.out.println("Goodbye!");
        break;
      }

      String startWord, endWord;
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
      } while (startWord.length() != endWord.length() || !dictionary.contains(startWord.toLowerCase()) || !dictionary.contains(endWord.toLowerCase()));

      Integer startTime = (int) System.currentTimeMillis();

      SearchResult result = null;
      UCS.SearchResult UCSresult = null;
      GBFS.SearchResult GBFSresult = null;
      AStar.SearchResult AStarresult = null;
      if (choice.equals(1)) {
        System.out.println("Solving with Uniform Cost Search");
        UCSresult = UCS.UniformCostSearch(startWord, endWord, dictionary);
  
        if (UCSresult != null) result = new SearchResult(UCSresult.path, UCSresult.nodeVisited);
      }
      else if (choice.equals(2)) {
        System.out.println("Solving with Greedy Best First Search");
        GBFSresult = GBFS.GreedyBestFirstSearch(startWord, endWord, dictionary);
  
        if (GBFSresult != null) result = new SearchResult(GBFSresult.path, GBFSresult.nodeVisited);
      }
      else if (choice.equals(3)) {
        System.out.println("Solving with A* Search");
        AStarresult = AStar.AStarSearch(startWord, endWord, dictionary);
  
        if (AStarresult != null) result = new SearchResult(AStarresult.path, AStarresult.nodeVisited);
      }
  
      Integer endTime = (int) System.currentTimeMillis();
      Integer elapsedTime = endTime - startTime;

      if (result != null) {
        ArrayList<String> path = result.path;
        int nodeVisited = result.nodeVisited;
        for (String word: path) {
          System.out.println(word);
        }
        System.out.println("Number of nodes visited: " + nodeVisited);
        System.out.println("Elapsed time: " + elapsedTime + "ms");
      } else {
        System.out.println(startWord +" is either not in the dictionary or cannot be transformed into any other word.");
      }

      System.out.println("\nNew Game? (1: Yes, 2: No)");
      Integer newGame;
      do {
        System.out.print("Choice: ");
        newGame = Integer.parseInt(scanner.nextLine());

        if (newGame != 1 && newGame != 2) {
          System.out.println("Invalid choice!");
        }
      } while (newGame != 1 && newGame != 2);

      if (newGame.equals(2)) {
        System.out.println("Goodbye!");
        break;
      }
      else if (newGame.equals(1)) {
        System.out.println("\n << ========== New Game ========== >>");
      }

    } while (choice == 1 || choice == 2 || choice == 3);

    scanner.close();
  }

  // Function to load dictionary from file
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
