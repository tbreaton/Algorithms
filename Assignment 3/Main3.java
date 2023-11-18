import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

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

        File graphsOne = new File("graphs1.txt");
        Graph g = new Graph();
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addVertex(5);
        g.addVertex(6);
        g.addVertex(7);
        g.addEdge(1, 2);
        g.addEdge(1, 5);
        g.addEdge(1, 6);
        g.addEdge(2, 3);
        g.addEdge(2, 5);
        g.addEdge(2, 6);
        g.addEdge(3, 4);
        g.addEdge(4, 5);
        g.addEdge(5, 6);
        g.addEdge(5, 7);
        g.addEdge(6, 7);

        g.printGraph();
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

class Graph {
    ArrayList<GraphNode> graph;
    int v;

    class GraphNode {

        private int vertexID = 0;
        private boolean processed;
        private ArrayList<Integer> neighbors;

        public GraphNode(int newID) {
            vertexID = newID;
            processed = false;
            neighbors = new ArrayList<Integer>();
        }

    }

    Graph() {
        graph = new ArrayList<GraphNode>();
    }

    void addVertex(int vertexID) {
        GraphNode vertex = new GraphNode(vertexID);
        addToGraph(vertex);
    }

    void addToGraph(GraphNode newVertex) {
        graph.add(newVertex);
    }

    GraphNode getVertexByID(int ID) {
        for (int i = 0; i < graph.size(); i++) {
            if (graph.get(i).vertexID == ID) {
                return graph.get(i);
            }
        }
        return null;
    }

    GraphNode getByID(int ID) {
        GraphNode result = getVertexByID(ID);
        return result;
    }

    void addEdge(int source, int dest) {
        GraphNode tempSource = getVertexByID(source);
        GraphNode tempDest = getVertexByID(dest);

        tempSource.neighbors.add(dest);
        tempDest.neighbors.add(source);
    }

    void printGraph() {
        for (int i = 0; i < graph.size(); i++) {
            GraphNode tempVertex = graph.get(i);
            System.out.print("Vertex " + tempVertex.vertexID + " has neighbors: ");
            for (int k = 0; k < tempVertex.neighbors.size(); k++) {
                System.out.print(tempVertex.neighbors.get(k) + ", ");
            }
            System.out.println();
        }
    }
}

/*
 * void fileToGraph(File file) throws FileNotFoundException {
 * Scanner scan = new Scanner(file);
 * 
 * int i = 0;
 * 
 * while (scan.hasNextLine()) {
 * String data = scan.nextLine();
 * String delims = "[ ]+";
 * String[] tempString = data.split(delims);
 * if (tempString[0].compareTo("new") == 0 || tempString[0].compareTo("--") ==
 * 0) {
 * 
 * }
 * }
 * }
 */
