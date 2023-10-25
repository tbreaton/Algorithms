import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class Main2{

    public static void main(String[] args) throws FileNotFoundException{
        File magicItemsFile = new File("magicitems.txt");
        String[] magicItemsArray = fileToArray(magicItemsFile);
        String[] randomItems = randomItems(magicItemsArray);

        // Prints out random items to check code
        for (int i = 0; i < randomItems.length; i++) {
          System.out.println(randomItems[i]);
          }
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

    public static final Random rand = new Random();

    // Creates an array of 42 random items
    public static String[] randomItems(String[] arr){
        String[] items = new String[42];
        int i = 0;

        // Generates a random number that corresponds to a item
        // That item is added to the list if it hasn't already been selected
        while(i<42){
            int n = rand.nextInt(arr.length - 1);
            int[] randomNumberTracker = new int[42];
            items[i] = arr[n];
            
            // Loops through an array of all the generated numbers to ensure that
            // no number is used twice
            randomNumberTracker[i] = n;
            for (int k=0; k<randomNumberTracker.length; k++){
                if (randomNumberTracker[k] != n){
                    i++;
                    break;
                }
            }
        }
        return items;
    }
}