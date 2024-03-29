import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TrieCompletion {
    public static void main(String[] args) {
        //prepair the map and trie
        Trie trie = CreateTrie("gutenberg-all-lowercase-words-with-counts.txt", 152480);

        //User input part
        Scanner scanner = new Scanner(System.in);
        String choice = "0";
        int suggNum = 3;

        System.out.println("Write the word you would like me to complete, or choose one of the following\n"
                    + "1) List all words that begin with a prefix\n"
                    + "2) Size of the trie\n"
                    + "3) Change number of suggestions\n"
                    + "4) Quit\n");
        
        //Loop for user input:
        while(!choice.equals("4")){ //exit when input = 4
            choice = scanner.next();
            String word;
            switch(choice){
                case("1"): //List all words that begin with a prefix
                    System.out.print("Enter the prefix: ");
                    word = scanner.next().toLowerCase();
                    String[] words = trie.allWordsPrefix(word);
                    if(words.length != 0){
                        System.out.println("The words that start with "+word+" are:");
                        for (String string : words) {
                            System.out.print(string+ "\t");
                        }
                    } else {
                        System.out.println("There are no words that start with "+word);
                    }
                    System.out.println("\n");
                break;

                case("2"): //Number of nodes in the trie structure
                    System.out.println("The size of the trie = "+trie.size()+"\n");
                break;

                case("3"): //Change number of suggestions for completion
                    System.out.print("Enter the number of suggestions you'd like: ");
                    suggNum = Integer.parseInt(scanner.next());
                    System.out.println("Done. the new number of suggestions is "+suggNum+"\n");
                break;

                case("4"): //prepare for exit 
                    scanner.close();
                    System.out.println("Goodbye");
                break;

                default: //Complete word
                    choice = choice.toLowerCase();
                    long timenano = System.nanoTime();
                    words = trie.completeSuggestion(choice, suggNum);
                    timenano = System.nanoTime()-timenano;

                    if(words.length != 0){
                        //System.out.println("The words that start with "+choice+" are:");
                        for (String string : words) {
                            System.out.print(string+ "\t");
                        }
                    } else {
                        System.out.println("There are no words that start with "+choice);
                    }
                    System.out.println("\nThe search took "+timenano+" nanoseconds\n");
                break;
            }
        }
    }
    
    static Trie CreateTrie(String fileName, int dictSize){
        Trie trie = new Trie();
        //long timenano = System.nanoTime();

        // Open the file using a buffered reader because it is too long for a regular scanner
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) { 
            //Insert each word from the dictionary into the trie
            try { 
                for (int i = 1; i <= dictSize; i++) {
                    String[] line = reader.readLine().split(" ");
                    trie.insert(line[line.length-1], Integer.parseInt(line[line.length-2])); //some lines have spaces before the frequency score
                }
            } catch (Exception e) {
                System.out.println("out of loop\n"+e);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //timenano = System.nanoTime()-timenano;
        //System.out.println("time: "+timenano/1000000+" milliseconds");
        //System.out.println(trie.size());
        return trie;
    }
}

