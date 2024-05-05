import java.util.*;

public class UCS {
    static class Node {
        String word;
        ArrayList<String> path;
        int cost;

        Node(String word, ArrayList<String> path, int cost) {
            this.word = word;
            this.path = new ArrayList<>(path);
            this.path.add(word);
            this.cost = cost;
        }
    }

    static class SearchResult {
        ArrayList<String> path;
        int nodeVisited;

        SearchResult(ArrayList<String> path, int nodeVisited) {
            this.path = path;
            this.nodeVisited = nodeVisited;
        }
    }

    public static SearchResult UniformCostSearch(String startWord, String endWord, Set<String> dictionary) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.cost));
        Set<String> visited = new HashSet<>();
        int nodeVisited = 0;

        pq.offer(new Node(startWord, new ArrayList<>(), 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            visited.add(current.word);

            if (current.word.equals(endWord)) {
                return new SearchResult(current.path, nodeVisited);
            }

            List<String> neighbors = getNeighbors(current.word, endWord, dictionary);
            // System.out.println("Neighbors dari " + current.word + " Adalah " + neighbors);
            for (String neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    int newCost = current.cost + 1;
                    pq.offer(new Node(neighbor, current.path, newCost));
                }
            }

            nodeVisited++;
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
}
