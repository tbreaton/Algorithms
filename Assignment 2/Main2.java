import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class Main2 {

    private static final int HASH_TABLE_SIZE = 250;
    private static final int LINES_IN_FILE = 666;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) throws FileNotFoundException {
        File magicItemsFile = new File("magicitems.txt");
        String[] magicItemsArray = fileToArray(magicItemsFile);
        String[] randomItems = randomItems(magicItemsArray);
        quickSort(magicItemsArray, 0, magicItemsArray.length - 1);
        int linearSeachComparisons = linearSearch(magicItemsArray, randomItems);
        System.out.println("Linear search comparisons: " + linearSeachComparisons);
        int binarySearchComparisons = binarySearch(magicItemsArray, randomItems);
        System.out.println("Binary search comparisons: " + binarySearchComparisons);

        int[] hashValues = new int[LINES_IN_FILE];
        int hashCode = 0;
        for (int i = 0; i < LINES_IN_FILE; i++) {
            // System.out.print(i);
            // System.out.print(". " + magicItemsArray[i] + " - ");
            hashCode = makeHashCode(magicItemsArray[i]);
            // System.out.format("%03d%n", hashCode);
            hashValues[i] = hashCode;
        }
        int[] bucketCount = analyzeHashValues(hashValues, magicItemsArray);
        ArrayList<String[]> hashes = new ArrayList<String[]>();
        hashes = fillHashes(magicItemsArray, bucketCount, hashes);
        double hashingComparisons = retrieveItems(hashes, magicItemsArray, randomItems);
        System.out.println("Hashing retrieval comparisons: " + df.format(hashingComparisons));
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

    public static int linearSearch(String[] arr, String[] items) {
        // Keeps track of the number of comparisons for each
        int[] numComparisons = new int[42];

        // Loops through the magicItemsArray for each of the
        // randomly selected items
        for (int i = 0; i < items.length; i++) {
            int comparisons = 0;
            for (int k = 0; k < arr.length; k++) {
                comparisons++;
                if (arr[k].compareTo(items[i]) == 0) {
                    // Adds number of comparisons to the Array for each iteration
                    numComparisons[i] = comparisons;
                    break;
                }
            }
        }

        // Loops through array and adds up the total number of iterations
        int sum = 0;
        for (int i = 0; i < numComparisons.length; i++) {
            sum = sum + numComparisons[i];
        }

        // Takes the average of the number of comparisons for all 42 items
        int avg = sum / 42;

        return avg;
    }

    public static int binarySearch(String[] arr, String[] items) {
        int[] numComparisons = new int[42];

        // Loops through every randomItem and counts how many comparisons
        // are needed to find each item during binary search
        for (int i = 0; i < items.length; i++) {
            int comparisons = 0;
            int high = arr.length - 1;
            int low = 0;

            // Loops through the magicItems array and changes the upper and/or lower
            // bound of the search until the item is found
            while (high >= low) {
                int arrItem = (low + high) / 2;
                if (arr[arrItem].compareTo(items[i]) > 0) {
                    high = arrItem - 1;
                    comparisons++;
                } else if (arr[arrItem].compareTo(items[i]) < 0) {
                    low = arrItem + 1;
                    comparisons++;
                } else {
                    // Adds the total number of comparisons to an array
                    numComparisons[i] = comparisons;
                    break;
                }
            }
        }

        // Takes all of the total comparisons and finds and returns the average of all
        // 42 searches
        int sum = 0;
        for (int i = 0; i < numComparisons.length; i++) {
            sum = sum + numComparisons[i];
        }

        int avg = sum / 42;

        return avg;
    }

    private static int makeHashCode(String str) {
        str = str.toUpperCase();
        int length = str.length();
        int letterTotal = 0;
        // Iterate over all letters in the string, totalling their ASCII values.
        for (int i = 0; i < length; i++) {
            char thisLetter = str.charAt(i);
            int thisValue = (int) thisLetter;
            letterTotal = letterTotal + thisValue;

        }

        // Scale letterTotal to fit in HASH_TABLE_SIZE.
        int hashCode = (letterTotal * 1) % HASH_TABLE_SIZE; // % is the "mod" operator

        return hashCode;
    }

    // Took the code from the website provided and removed the code that I didn't
    // need
    // I need the bucketCount array so I could populate the ArrayList with the
    // proper items at the proper hash values
    private static int[] analyzeHashValues(int[] hashValues, String[] arr) {

        // Sort the hash values.
        Arrays.sort(hashValues);

        int asteriskCount = 0;
        int[] bucketCount = new int[HASH_TABLE_SIZE];
        int totalCount = 0;
        int arrayIndex = 0;

        for (int i = 0; i < HASH_TABLE_SIZE; i++) {
            asteriskCount = 0;
            while ((arrayIndex < LINES_IN_FILE) && (hashValues[arrayIndex] == i)) {
                asteriskCount = asteriskCount + 1;
                arrayIndex = arrayIndex + 1;
            }
            bucketCount[i] = asteriskCount;
            totalCount = totalCount + asteriskCount;
        }

        return bucketCount;
    }

    public static ArrayList<String[]> fillHashes(String[] magicArr, int[] buckCount, ArrayList<String[]> hashes) {
        // Loops through the buckCount array (which has how many values are chained at
        // each hash value)
        for (int i = 0; i < HASH_TABLE_SIZE; i++) {
            String[] temp = new String[buckCount[i]];
            int tempCounter = 0;
            // Loops through the magicArr (magicItemsArray) and checks if each item's hash
            // value is equal to the current has value. If it is, then the item is added to
            // a temporary String Array
            for (int k = 0; k < LINES_IN_FILE; k++) {
                int tempHashCode = makeHashCode(magicArr[k]);
                if (tempHashCode == i) {
                    temp[tempCounter] = magicArr[k];
                    tempCounter++;
                }
            }
            // Once all the items are added to the current hash value array that array is
            // stored in the hashes ArrayList
            hashes.add(temp);
        }
        return hashes;
    }

    public static double retrieveItems(ArrayList<String[]> hashes, String[] magicArr, String[] randItems) {
        int[] numComparisons = new int[42];
        int comparisons = 0;

        // Loops through the randItems (randomItems) Array and for each item checks its
        // gets the hash value. We also rerieve the proper array for the corresponding
        // hash value from the ArrayList
        for (int i = 0; i < randItems.length; i++) {
            int tempHashCode = makeHashCode(randItems[i]);
            String[] tempFromHashes = hashes.get(tempHashCode);
            comparisons = 1;

            // Loops through the Array for the hash value of the current random item and
            // compares each string in the array to see which one is the same as the random
            // item
            for (int k = 0; k < tempFromHashes.length; k++) {
                comparisons++;
                if (randItems[i].compareTo(tempFromHashes[k]) == 0) {
                    numComparisons[i] = comparisons;
                    break;
                }
            }
        }

        // Takes the average number of comparisons that it takes to find/retrieve the
        // item
        int sum = 0;
        for (int i = 0; i < numComparisons.length; i++) {
            sum = sum + numComparisons[i];
        }

        double avg = sum / 42.00;

        return avg;
    }
}