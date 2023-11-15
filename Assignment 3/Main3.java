import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main3{
    
    private static final int LINES_IN_MAGIC_ITEMS = 666;

    public static void main(String[] args) throws FileNotFoundException{
        File magicItemsFile = new File("magicitems.txt");
        String[] magicItemsArray = fileToArray(magicItemsFile);
    }

    public static String[] fileToArray(File file) throws FileNotFoundException {

        Scanner scan = new Scanner(file);

        // Creates the array to store the magic itmes in
        String[] itemsArray = new String[666];
        int i = 0;

        // Goes through the whole text file and adds each line to the array
        while (scan.hasNextLine()) {
            String data = scan.nextLine();
            itemsArray[i] = data;
            i++;
        }
        scan.close();
        return itemsArray;
    }
}