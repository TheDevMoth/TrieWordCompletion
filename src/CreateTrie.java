import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;

public class CreateTrie {
    public static void main(String[] args) {
        Trie trie = new Trie();
        long timenano = System.nanoTime();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("gutenberg-all-lowercase-words-with-counts.txt")))) {
            try {
                int dictSize = 152480;
                for (int i = 1; i <= dictSize; i++) {
                    System.out.println(i+"/"+dictSize);
                    String[] line = reader.readLine().split(" ");
                    trie.insert(line[line.length-1], Integer.parseInt(line[line.length-2]));
                }
            } catch (Exception e) {
                System.out.println("out of loop\n"+e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        timenano = System.nanoTime()-timenano;
        System.out.println("time: "+timenano/1000000+" milliseconds");
        System.out.println(trie.size());

        try(ObjectOutputStream write= new ObjectOutputStream (new FileOutputStream("Tried.bin"))){
            write.writeObject(trie);
        }
        catch(NotSerializableException nse){
            System.out.println(nse);
        }
        catch(IOException eio){
            System.out.println(eio);
        }
    }

}
