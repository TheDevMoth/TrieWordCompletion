import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class TrieCompletion {
    public static void main(String[] args) {
        //prepair the map and trie
        Trie trie = new Trie();

        System.out.println("Setting up");
        long loadTime = System.currentTimeMillis();
        try(ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("Tried.bin"))){
            trie = (Trie) inFile.readObject();
        }
        catch(ClassNotFoundException cnfe){
            System.out.println(cnfe);
        }
        catch(FileNotFoundException fnfe){
            System.out.println(fnfe);
        }
        catch(IOException e){
            System.out.println(e);
        }
        loadTime = System.currentTimeMillis()-loadTime;
        System.out.println("Loading took is "+loadTime+" ms");

        //User input part
        Scanner scanner = new Scanner(System.in);
        String choice = "0";
        int suggNum = 3;

        System.out.println("Write the word you would like me to complete, or choose one of the following\n"
                    + "1) List all words that begin with a prefix\n"
                    + "2) Size of the trie\n"
                    + "3) Change number of suggestions\n"
                    + "4) Quit\n");

        while(!choice.equals("4")){
            choice = scanner.next();
            String word;
            switch(choice){
                case("1"):
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
                case("2"):
                    System.out.println("The size of the trie = "+trie.size()+"\n");
                break;
                case("3"):
                    System.out.print("Enter the number of suggestions you'd like: ");
                    suggNum = Integer.parseInt(scanner.next());
                    System.out.println("Done. the new number of suggestions is "+suggNum+"\n");
                break;
                case("4"):
                    scanner.close();
                    System.out.println("Goodbye");
                break;
                default:
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
}

