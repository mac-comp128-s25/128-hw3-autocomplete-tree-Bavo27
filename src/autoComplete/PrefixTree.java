package autoComplete;

import java.util.ArrayList;
import java.util.Map;

/**
 * A prefix tree used for autocompletion. The root of the tree just stores links to child nodes (up to 26, one per letter).
 * Each child node represents a letter. A path from a root's child node down to a node where isWord is true represents the sequence
 * of characters in a word.
 */
public class PrefixTree {
    private TreeNode root; 

    // Number of words contained in the tree
    private int size;

    public PrefixTree(){
        root = new TreeNode();
    }

    /**
     * Adds the word to the tree where each letter in sequence is added as a node
     * If the word, is already in the tree, then this has no effect.
     * @param word
     */
    public void add(String word){
        TreeNode current = root;
        for (int i = 0; i < word.length(); i++) {
            if (!current.children.containsKey(word.charAt(i))) {
                TreeNode child = new TreeNode();
                current.children.put(word.charAt(i), child);
                current = current.children.get(word.charAt(i));
            } else {
                current = current.children.get(word.charAt(i));
            }
            if (i == word.length() - 1) {
                if (current.isWord == true) {
                    size = size;
                } else {
                    current.isWord = true;
                    size++;
                }
            }
        }
    }

    /**
     * Checks whether the word has been added to the tree
     * @param word
     * @return true if contained in the tree.
     */
    public boolean contains(String word) {
        TreeNode current = root;
        for (int i = 0; i < word.length(); i++) {
            if (current.children.containsKey(word.charAt(i))) {
                current = current.children.get(word.charAt(i));
            }
            if (i == word.length() - 1 && current.isWord) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the words in the tree that start with prefix (including prefix if it is a word itself).
     * The order of the list can be arbitrary.
     * @param prefix
     * @return list of words with prefix
     */
    public ArrayList<String> getWordsForPrefix(String prefix){
        ArrayList<String> wordList = new ArrayList<>();
        TreeNode current = root;
        for (int i = 0; i < prefix.length(); i++) {
            if (current.children.containsKey(prefix.charAt(i))){
                current = current.children.get(prefix.charAt(i));
            } else {
                return wordList;
            }
        }
        getWords(current, prefix, wordList);
        return wordList;
    }

    /**
     * Checks if the prefix is a word and then recurses through the children of each node until it gets to null. While recursing if it gets to a word
     * it adds it to the wordList.
     * @param current
     * @param prefix
     * @param wordList
     */
    public void getWords(TreeNode current, String prefix, ArrayList<String> wordList) {
        if (current.isWord) {
            wordList.add(prefix);
        }
        for (Character ch : current.children.keySet()) {
            getWords(current.children.get(ch), prefix + ch, wordList);
        }
    }

    /**
     * @return the number of words in the tree
     */
    public int size(){
        return size;
    }
    
}
