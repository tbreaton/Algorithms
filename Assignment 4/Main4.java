import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main4 {
    private static Object adjacencymatrix;

    public static void main(String[] args) throws FileNotFoundException {
        File graphs2 = new File("graphs2.txt");
        ArrayList<Integer> numVerticiesArray = fileToVerticesArray(graphs2);

        final int MAX_VALUE = 999;
        int source = 1;
        int lineNumber = 0;
        ArrayList<int[][]> arrayOfMatrix = new ArrayList<int[][]>();

        for (int i = 0; i < numVerticiesArray.size(); i++) {
            int numberofvertices = numVerticiesArray.get(i);

            lineNumber = verticesToMatrix(graphs2, numberofvertices, arrayOfMatrix, lineNumber);
            lineNumber++;
            System.out.println("Line number" + lineNumber);
        }

        for (int i = 0; i < arrayOfMatrix.size(); i++) {

            int[][] adjacencymatrix = arrayOfMatrix.get(i);
            int numberofvertices = numVerticiesArray.get(i);

            for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++) {
                for (int destinationnode = 1; destinationnode <= numberofvertices; destinationnode++) {
                    if (sourcenode == destinationnode) {
                        adjacencymatrix[sourcenode][destinationnode] = 0;
                        continue;
                    }
                    if (adjacencymatrix[sourcenode][destinationnode] == 0) {
                        adjacencymatrix[sourcenode][destinationnode] = MAX_VALUE;
                    }
                }
            }

            BellmanFord bellmanford = new BellmanFord(numberofvertices);
            bellmanford.BellmanFordEvaluation(source, adjacencymatrix);
        }
    }

    public static ArrayList<Integer> fileToVerticesArray(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        int numVertices = 0;
        ArrayList<Integer> numVerticesArray = new ArrayList<Integer>();

        // Scans each line looking for a specific string
        while (scan.hasNextLine()) {
            String data = scan.nextLine();
            String delims = "[ ]+";
            String[] tempString = data.split(delims);
            // There is a new line so the graph is done being created, it can be added to
            // the ArrayList
            if (tempString[0].compareTo("") == 0) {
                numVerticesArray.add(numVertices);
                numVertices = 0;
                // Creates the new graph
            } else if (tempString[0].compareTo("new") == 0) {
                
            } else if (tempString[0].compareTo("--") == 0) {
                // Adds the vertexes to the graph
            } else if (tempString[1].compareTo("vertex") == 0) {
                numVertices++;
                // Adds the edges to the graph
            } else if (tempString[1].compareTo("edge") == 0) {

            }
        }
        numVerticesArray.add(numVertices);
        scan.close();
        return numVerticesArray;
    }

    public static int verticesToMatrix(File file, int numVertices, ArrayList<int[][]> arrayOfMatrix, int lineNumber)
            throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        int adjacencymatrix[][] = new int[numVertices + 1][numVertices + 1];

        // Scans each line looking for a specific string
        for (int i = 0; i < lineNumber; i++) {
            if (scan.hasNextLine()) {
                scan.nextLine();
            }
        }
        while (scan.hasNextLine()) {
            String data = scan.nextLine();
            String delims = "[ ]+";
            String[] tempString = data.split(delims);
            // There is a new line so the graph is done being created, it can be added to
            // the ArrayList
            if (tempString[0].compareTo("") == 0) {
                arrayOfMatrix.add(adjacencymatrix);
                return lineNumber;
                // Creates the new graph
            } else if (tempString[0].compareTo("new") == 0) {

            } else if (tempString[0].compareTo("--") == 0) {
                // Adds the vertexes to the graph
            } else if (tempString[1].compareTo("vertex") == 0) {
                // Adds the edges to the graph
            } else if (tempString[1].compareTo("edge") == 0) {
                adjacencymatrix[Integer.parseInt(tempString[2])][Integer.parseInt(tempString[4])] = Integer
                        .parseInt(tempString[5]);
            }
            lineNumber++;
        }
        // Adds the final graph from the file to the ArrayList
        arrayOfMatrix.add(adjacencymatrix);
        scan.close();
        return lineNumber;
    }

}

class BellmanFord {
    private int distances[];
    private int numberofvertices;
    final int MAX_VALUE = 999;

    public BellmanFord(int numberofvertices) {
        this.numberofvertices = numberofvertices;
        distances = new int[numberofvertices + 1];
    }

    public void BellmanFordEvaluation(int source, int adjacencymatrix[][]) {
        for (int node = 1; node <= numberofvertices; node++) {
            distances[node] = MAX_VALUE;
        }

        distances[source] = 0;
        for (int node = 1; node <= numberofvertices - 1; node++) {
            for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++) {
                for (int destinationnode = 1; destinationnode <= numberofvertices; destinationnode++) {
                    if (adjacencymatrix[sourcenode][destinationnode] != MAX_VALUE) {
                        if (distances[destinationnode] > distances[sourcenode]
                                + adjacencymatrix[sourcenode][destinationnode])
                            distances[destinationnode] = distances[sourcenode]
                                    + adjacencymatrix[sourcenode][destinationnode];
                    }
                }
            }
        }

        for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++) {
            for (int destinationnode = 1; destinationnode <= numberofvertices; destinationnode++) {
                if (adjacencymatrix[sourcenode][destinationnode] != MAX_VALUE) {
                    if (distances[destinationnode] > distances[sourcenode]
                            + adjacencymatrix[sourcenode][destinationnode])
                        System.out.println("The Graph contains negative egde cycle");
                }
            }
        }

        for (int vertex = 1; vertex <= numberofvertices; vertex++) {
            System.out.println("distance of source  " + source + " to "
                    + vertex + " is " + distances[vertex]);
        }
    }
}