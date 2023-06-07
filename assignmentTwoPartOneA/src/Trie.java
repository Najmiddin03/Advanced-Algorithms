import java.util.HashMap;
import java.util.Map;

class Trie {
    private boolean isLeaf;
    private final Map<Character, Trie> children;

    Trie() {
        isLeaf = false;
        children = new HashMap<>();
    }

    public void insert(String key) {
        Trie curr = this;
        for (char c : key.toCharArray()) {
            curr.children.putIfAbsent(c, new Trie());
            curr = curr.children.get(c);
        }
        curr.isLeaf = true;
    }

    public boolean search(String key) {
        Trie curr = this;
        for (char c : key.toCharArray()) {
            curr = curr.children.get(c);
            if (curr == null) {
                return false;
            }
        }
        return curr.isLeaf;
    }
}