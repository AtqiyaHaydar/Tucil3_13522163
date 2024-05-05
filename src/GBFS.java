import java.util.*;

public class GBFS {
    static class SearchResult {
        ArrayList<String> path;
        int nodeVisited;

        SearchResult(ArrayList<String> path, int nodeVisited) {
            this.path = path;
            this.nodeVisited = nodeVisited;
        }
    }

    public static SearchResult GBFS(String startWord, String endWord, Set<String> dictionary) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.heuristic));
        Set<String> visited = new HashSet<>();

        pq.offer(new Node(startWord, 0));

        int nodeVisited = 0;
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            visited.add(current.word);
            nodeVisited++;

            if (current.word.equals(endWord)) {
                return new SearchResult(current.path, nodeVisited);
            }

            List<String> neighbors = getNeighbors(current.word, endWord, dictionary);
            for (String neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    int heuristic = calculateHeuristic(neighbor, endWord);
                    pq.offer(new Node(neighbor, heuristic, current.path));
                }
            }
        }

        return null;
    }

    private static List<String> getNeighbors(String word, String endWord, Set<String> dictionary) {
        List<String> neighbors = new ArrayList<>();
        char[] wordChars = word.toCharArray();

        for (int i = 0; i < word.length(); i++) {
            char originalChar = wordChars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != originalChar) {
                    wordChars[i] = c;
                    String newWord = new String(wordChars);
                    if (dictionary.contains(newWord)) {
                        neighbors.add(newWord);
                    }
                }
            }
            wordChars[i] = originalChar;
        }

        return neighbors;
    }

    private static int calculateHeuristic(String word, String endWord) {
        // Menggunakan jumlah karakter yang berbeda antara word dan endWord sebagai heuristik
        int heuristic = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != endWord.charAt(i)) {
                heuristic++;
            }
        }
        return heuristic;
    }

    static class Node {
        String word;
        int heuristic;
        ArrayList<String> path;

        Node(String word, int heuristic) {
            this.word = word;
            this.heuristic = heuristic;
            this.path = new ArrayList<>(Arrays.asList(word));
        }

        Node(String word, int heuristic, ArrayList<String> path) {
            this.word = word;
            this.heuristic = heuristic;
            this.path = new ArrayList<>(path);
            this.path.add(word);
        }
    }
}