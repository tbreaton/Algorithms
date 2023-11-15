import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main3 {

    private static final int LINES_IN_MAGIC_ITEMS = 666;

    public static void main(String[] args) throws FileNotFoundException {
        File magicItemsFile = new File("magicitems.txt");
        String[] magicItemsArray = fileToArray(magicItemsFile);
        BST_class bst = new BST_class();

        for (int i = 0; i < LINES_IN_MAGIC_ITEMS; i++) {
            bst.insert(magicItemsArray[i]);
        }

        bst.inOrder();
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

class BST_class {
    class Node {
        String key;
        Node left, right;

        public Node(String item) {
            key = item;
            left = null;
            right = null;
        }
    }

    // Root node for BST
    Node root;

    // BST constructor, makes empty tree
    BST_class() {
        root = null;
    }

    void insert(String key) {
        root = BST_Insert(root, key);
    }

    Node BST_Insert(Node root, String key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }

        if (key.compareToIgnoreCase(root.key) < 0) {
            root.left = BST_Insert(root.left, key);
        } else {
            root.right = BST_Insert(root.right, key);
        }
        return root;
    }

    void inOrder() {
        inOrderTraversal(root);
    }

    void inOrderTraversal(Node root) {
        if (root != null) {
            inOrderTraversal(root.left);
            System.out.print(root.key + ", ");
            inOrderTraversal(root.right);
        }
    }
}