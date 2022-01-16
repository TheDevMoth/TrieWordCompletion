import java.io.Serializable;
import java.util.HashMap;

class TrieNode implements Serializable{
    Character el;
    int freq;
    HashMap<Character,TrieNode> children;

    TrieNode(Character ch, int freq){
        this.el = ch;
        this.freq = freq;
        children = new HashMap<>();
    }

    TrieNode(Character ch){
        this(ch, -1);
    }

    boolean isEndNode(){
        return freq != -1;
    }
}
