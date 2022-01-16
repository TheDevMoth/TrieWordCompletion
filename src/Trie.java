import java.io.Serializable;
import java.util.LinkedList;

public class Trie implements Serializable{
    private int size; 
    private TrieNode head; //an empty node to store the map of first letters


    public Trie(){
        size = 0;
        head = new TrieNode(' '); 
    }
    public Trie(String s, int freq){
        this();
        insert(s, freq);
    }
    public Trie(String[] s, int[] freq){
        this();
        insert(s, freq);
    }

    public boolean isEmpty(){
        return head.children.isEmpty();
    }
    
    public void clear(){
        head.children.clear();
        size=0;
    }
    
    public int size(){
        return size;
    }
    
    public void insert(String[] s, int[] f){
        if(s.length > f.length)
            throw new IllegalArgumentException("The string array is longer than the frequencies array");
        for (int i = 0; i < s.length; i++)
            insert(s[i], f[i]);
    }

    public void insert(String s, int f){
        TrieNode node = head;
        //For each letter in s
        for (int i = 0; i<s.length(); i++) {
            //if the key exists in the map go to the map of the child
            if(node.children.containsKey(s.charAt(i)))
                node=node.children.get(s.charAt(i));
            else{ //add it then go to it
                node.children.put(s.charAt(i), new TrieNode(s.charAt(i)));
                node=node.children.get(s.charAt(i));
                size++;
            }
        }
        node.freq = f; //marks as end node
    }

    private TrieNode follow(String s){
        TrieNode node = head;
        //Navigates the tree following the given string 
        for (int i = 0; i < s.length(); i++) {
            if(node.children.containsKey(s.charAt(i)))
                node=node.children.get(s.charAt(i));
            else
                //null if the character is not in the tree
                return null;
        }
        //returns the node containing the last character
        return node;
    }

    
    public boolean contains(String s){        
        //check if the node corresponding to the last character is an end of a word (has a null child)
        TrieNode node = follow(s);
        if(node==null)
            return false;
        return node.isEndNode();
    }
    

    public boolean isPrefix(String p){
        return follow(p)!=null;
    }

    public String[] allWordsPrefix(String p){
        TrieNode node = follow(p); //get the node corresponding to the last char
        if(node==null){
            return new String[0];
        } else {
            LinkedList<String> out = new LinkedList<>();
            allWordsPrefix(node, p, out);
            return out.toArray(new String[out.size()]);
        }

    }

    private void allWordsPrefix(TrieNode node, String p, LinkedList<String> out){
        //iterate through the values of the children
        for (TrieNode subnode : node.children.values()) {
            //if the child is an ending point add the string so far to the list
            if(subnode.isEndNode())
                out.add(p+subnode.el);
            else
                allWordsPrefix(subnode,p+subnode.el,out);
        }
    }

    public String[] completeSuggestion(String p, int suggestNum){
        TrieNode node = follow(p); //get the node corresponding to the last char
        if(node==null){
            return new String[0];
        } else {
            String[] words = new String[suggestNum];
            int[] freqs = new int[suggestNum];
            completeSuggestion(node, p, words, freqs);
            return words;
        }
    }
    
    private void completeSuggestion(TrieNode node, String p, String[] words, int[] freqs){
        //iterate through the values of the children
        for (TrieNode subnode : node.children.values()) {
            //if the child is an ending point add the string so far to the list
            if(subnode.isEndNode()){
                String s = p+subnode.el;
                int freq = subnode.freq;
                for (int i = 0; i < words.length; i++) {
                    if(freq > freqs[i]){
                        int itmp = freqs[i];
                        String stmp = words[i];
                        freqs[i] = freq;
                        words[i] = s;
                        freq = itmp;
                        s = stmp;
                    }
                }
            }
            completeSuggestion(subnode,p+subnode.el,words, freqs);
        }
    }
    
    public void delete(String s){
        if(!contains(s))
            return;
        
        TrieNode prevWordEnd=head, tracker = head;
        Character key = s.charAt(0); //key of the top node to be deleted
        //follow the string through the tree, saving the last position with more than one node
        for(int i=0;i<s.length();i++){
            if(tracker.children.size() > 1 || tracker.isEndNode()){
                prevWordEnd = tracker;
                key = s.charAt(i);
            }
            tracker=tracker.children.get(s.charAt(i));
        }
        if(tracker.children.size() > 0) //the branch of tracker continues into a different word
            tracker.freq = -1;
        //there are other words using the subtree delete the ending point
        // else if(prevWordEnd.children.get(key).children.size()>1)
        //     prevWordEnd.children.get(key).children.remove(null);
        else { //otherwise delete the subtree
            size -= nodesInSubtree(prevWordEnd.children.get(key));
            prevWordEnd.children.remove(key);
        }
    }

    private int nodesInSubtree(TrieNode node){
        int count = 1; //counting the node itself
        //iterate through the values of the children
        for (TrieNode subnode : node.children.values())
            count += nodesInSubtree(subnode);
        return count;
    }
}