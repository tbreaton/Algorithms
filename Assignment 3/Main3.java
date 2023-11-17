import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main3 {

    private static final int LINES_IN_MAGIC_ITEMS = 666;
    private static final int LINES_IN_ITEMS_TO_FIND = 42;

    public static void main(String[] args) throws FileNotFoundException {
        File magicItemsFile = new File("magicitems.txt");
        String[] magicItemsArray = fileToArray(magicItemsFile);
        BST_class bst = new BST_class();
        String path = "";
        for (int i = 0; i < LINES_IN_MAGIC_ITEMS; i++) {
            bst.insert(magicItemsArray[i], path);
        }
        bst.inOrder();

        File magicItemsToFindFile = new File("magicitems-find-in-bst.txt");
        String[] magicItemsToFindArray = fileToArray(magicItemsToFindFile);

        int[] searchComparisons = new int[42];

        for (int i = 0; i < LINES_IN_ITEMS_TO_FIND; i++) {
            searchComparisons = bst.search(magicItemsToFindArray[i], path, searchComparisons, i);
        }

        int sum = 0;
        for (int i = 0; i < LINES_IN_ITEMS_TO_FIND; i++) {
            sum = sum + searchComparisons[i];
        }

        int avg = sum / 42;
        System.out.println("Average comparisons for BST search: " + avg);

    }

    public static String[] fileToArray(File file) throws FileNotFoundException {
        int iteration = 1;

        Scanner scan = new Scanner(file);

        // Creates the array to store the magic itmes in
        String[] itemsArray = new String[666];
        String[] itemsToFindArray = new String[42];
        int i = 0;

        // Goes through the whole text file and adds each line to the array
        while (scan.hasNextLine()) {
            String data = scan.nextLine();
            if (iteration == 1) {
                itemsArray[i] = data;
            } else if (iteration == 2) {
                itemsToFindArray[i] = data;
            }

            i++;
        }
        scan.close();
        iteration++;
        return itemsArray;
    }

}

class BST_class {
    class Node {
        String key;
        Node left, right;
        String path;

        public Node(String item) {
            key = item;
            left = null;
            right = null;
            path = "";
        }
    }

    // Root node for BST
    Node root;

    // BST constructor, makes empty tree
    BST_class() {
        root = null;
    }

    void insert(String key, String path) {
        path = "";
        root = BST_Insert(root, key, path);
    }

    Node BST_Insert(Node root, String key, String path) {
        if (root == null) {
            root = new Node(key);
            System.out.println(key + ": path - " + path);
            return root;
        }

        if (key.compareToIgnoreCase(root.key) < 0) {
            path = path + "L,";
            root.left = BST_Insert(root.left, key, path);
        } else {
            path = path + "R,";
            root.right = BST_Insert(root.right, key, path);
        }
        return root;
    }

    void inOrder() {
        inOrderTraversal(root);
    }

    void inOrderTraversal(Node root) {
        if (root != null) {
            inOrderTraversal(root.left);
            // System.out.print(root.key + ", ");
            inOrderTraversal(root.right);
        }
    }

    int[] search(String key, String path, int[] numComparisons, int placeInArray) {
        Node rootToChange;
        int comparisons = 0;
        path = "";
        rootToChange = search_BST(root, key, path, comparisons, numComparisons, placeInArray);
        return numComparisons;
    }

    Node search_BST(Node root, String key, String path, int comparisons, int[] numComparisons, int placeInArray) {
        if (root == null || root.key.compareTo(key) == 0) {
            System.out.println(key + ": Path - " + path + ": Comparisons - " + comparisons);
            numComparisons[placeInArray] = comparisons;
            return root;
        }
        if (root.key.compareTo(key) > 0) {
            path = path + "L,";
            comparisons++;
            return search_BST(root.left, key, path, comparisons, numComparisons, placeInArray);
        } else {
            path = path + "R,";
            comparisons++;
            return search_BST(root.right, key, path, comparisons, numComparisons, placeInArray);
        }

    }
}