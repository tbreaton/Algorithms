import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main4 {
    public static void main(String[] args) throws FileNotFoundException {
        File graphs2 = new File("graphs2.txt");

    }

    public static ArrayList<Graph> fileToGraphs(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);

        // Creates a new graph and an Arraylist to store the graph in
        Graph g = new Graph();
        ArrayList<Graph> graphArrayList = new ArrayList<Graph>();

        // Scans each line looking for a specific string
        while (scan.hasNextLine()) {
            String data = scan.nextLine();
            String delims = "[ ]+";
            String[] tempString = data.split(delims);
            // There is a new line so the graph is done being created, it can be added to
            // the ArrayList
            if (tempString[0].compareTo("") == 0) {
                graphArrayList.add(g);
                // Creates the new graph
            } else if (tempString[0].compareTo("new") == 0) {
                g = new Graph();
            } else if (tempString[0].compareTo("--") == 0) {
                // Adds the vertexes to the graph
            } else if (tempString[1].compareTo("vertex") == 0) {
                g.addVertex(Integer.parseInt(tempString[2]));
                // Adds the edges to the graph
            } else if (tempString[1].compareTo("edge") == 0) {
                g.addEdge(Integer.parseInt(tempString[2]), Integer.parseInt(tempString[4]));
            }
        }
        // Adds the final graph from the file to the ArrayList
        graphArrayList.add(g);
        scan.close();
        return graphArrayList;
    }

}

class DirectedGraph {

    class Vertex {
        private int vertexID;
        private boolean processed;
        private ArrayList<Integer> neighbors;

        public Vertex(int newID) {
            vertexID = newID;
            processed = false;
        }
    }

    class Edge {
        int source;
        int dest;
        int weight;

        public Edge(int source, int dest, int weight) {
            this.source = source;
            this.dest = dest;
            this.weight = weight;
        }
    }

    class Graph {
        LinkedList<Edge>[] adjacencyList;
        ArrayList<Vertex> graph;

        // Initializes a new graph
        Graph() {
            graph = new ArrayList<Vertex>();
        }

        // Allows to add a vertex with a given ID
        void addVertex(int vertexID) {
            Vertex vertex = new Vertex(vertexID);
            addToGraph(vertex);
        }

        // Adds a vertex to the graph
        void addToGraph(Vertex newVertex) {
            graph.add(newVertex);
        }

        void addEdge(int source, int dest, int weight) {
            Edge edge = new Edge(source, dest, weight);
            adjacencyList[source].addFirst(edge);
        }
    }
}