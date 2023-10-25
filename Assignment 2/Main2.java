import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class Main2 {

    public static void main(String[] args) throws FileNotFoundException {
        File magicItemsFile = new File("magicitems.txt");
        String[] magicItemsArray = fileToArray(magicItemsFile);
        String[] randomItems = randomItems(magicItemsArray);
        quickSort(magicItemsArray, 0, magicItemsArray.length - 1);

        // Prints out magicItemsArray to double check sorting works
        for (int i = 0; i < magicItemsArray.length; i++) {
            System.out.println(magicItemsArray[i]);
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
    public static String[] randomItems(String[] arr) {
        String[] items = new String[42];
        int i = 0;

        // Generates a random number that corresponds to a item
        // That item is added to the list if it hasn't already been selected
        while (i < 42) {
            int n = rand.nextInt(arr.length - 1);
            int[] randomNumberTracker = new int[42];
            items[i] = arr[n];

            // Loops through an array of all the generated numbers to ensure that
            // no number is used twice
            randomNumberTracker[i] = n;
            for (int k = 0; k < randomNumberTracker.length; k++) {
                if (randomNumberTracker[k] != n) {
                    i++;
                    break;
                }
            }
        }
        return items;
    }

    public static void quickSort(String[] arr, int lower, int higher) {
        int i = lower;
        int j = higher;
        String pivot = arr[i + (j - i) / 2];
        String temp = "";

        while (i <= j) {

            // Compares the strings disregarding capitalization
            while ((arr[i].toLowerCase()).compareTo(pivot.toLowerCase()) < 0) {
                i++;
            }

            while ((arr[j].toLowerCase()).compareTo(pivot.toLowerCase()) > 0) {
                j--;
            }

            // If the lower index is still less than the higher index, then the values of
            // said
            // indexes are swapped
            if (i <= j) {
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }

            // Calls the quickSort function recursivley
            if (lower < j) {
                quickSort(arr, lower, j);
            }
            if (i < higher) {
                quickSort(arr, i, higher);
            }
        }
    }
}