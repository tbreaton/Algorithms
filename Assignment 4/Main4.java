import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main4 {
    private static Object adjacencymatrix;
    public static final int MAX_VALUE = 999;


    public static void main(String[] args) throws FileNotFoundException {
        File graphs2 = new File("graphs2.txt");
        ArrayList<Integer> numVerticiesArray = fileToVerticesArray(graphs2);

        
        int source = 1;
        int lineNumber = 0;
        ArrayList<int[][]> arrayOfMatrix = new ArrayList<int[][]>();

        for (int i = 0; i < numVerticiesArray.size(); i++) {
            int numberofvertices = numVerticiesArray.get(i);

            lineNumber = verticesToMatrix(graphs2, numberofvertices, arrayOfMatrix, lineNumber);
            lineNumber++;
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
            bellmanford.BellmanFordEvaluation(source, adjacencymatrix, i);
        }

        fractionalKnapsack();

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

    public static ArrayList<Integer> fileToSpiceInfo(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        ArrayList<Integer> knapsackWeights = new ArrayList<Integer>();

        while (scan.hasNextLine()) {
            String data = scan.nextLine();
            String delim1 = ";";
            String delim2 = "[ ]+";
            String[] tempString1 = data.split(delim1);
            String[] tempString2 = tempString1[0].split(delim2);
            String tempString3 = tempString2[0];
            if (tempString3.compareTo("") == 0) {

            } else if (tempString3.compareTo("--") == 0) {

            } else if (tempString3.compareTo("spice") == 0) {
                Spice tempSpice = new Spice(tempString2[3]);
                tempString2 = tempString1[1].split(delim2);
                tempSpice.setValue(Float.parseFloat(tempString2[3]));
                tempString2 = tempString1[2].split(delim2);
                tempSpice.setQuantity(Integer.parseInt(tempString2[3]));
            } else if (tempString3.compareTo("knapsack") == 0) {
                knapsackWeights.add(Integer.parseInt(tempString2[3]));
            }
        }
        return knapsackWeights;
    }

    public static void fractionalKnapsack() throws FileNotFoundException{
        int i, j = 0, max_qty, m, n;
        float sum = 0, max;
        Scanner sc = new Scanner(System.in);
        int array[][] = new int[2][20];
        File spiceFile = new File("spice.txt");
        Spice spice = new Spice("ignore");

        ArrayList<Integer> knapsackWeights = fileToSpiceInfo(spiceFile);
        spice = spice.getSpiceByPosition(0);
        n = spice.getSpiceListSize();

        for (int k = 1; k < n; k++) {
            array[0][k] = spice.getSpiceByPosition(k).getQuantity();
        }
        for (int k = 1; k < n; k++) {
            array[1][k] = (int) spice.getSpiceByPosition(k).getValue();
        }

        for (int k = 0; k < knapsackWeights.size(); k++) {
            int[][] tempArray = new int[2][20];
            for (int p=0; p<array.length; p++){
                for (int o=0; o<array[0].length; o++){
                    tempArray[p][o] = array[p][o];
                }
            }
            max_qty = knapsackWeights.get(k);
            sum = 0;
            int spiceCounter = n-1;

            m = max_qty;

            ArrayList<Integer> knapsackContents = new ArrayList<Integer>();
            ArrayList<Spice> spiceTracker = new ArrayList<Spice>();

            while (m >= 0) {
                max = 0;
                for (i = 0; i < n; i++) {
                    if (((float) tempArray[1][i]) / ((float) tempArray[0][i]) > max) {
                        max = ((float) tempArray[1][i]) / ((float) tempArray[0][i]);
                        j = i;
                    }
                }
                if (tempArray[0][j] > m) {
                    knapsackContents.add(m);
                    spiceTracker.add(spice.getSpiceByPosition(j));
                    sum += m * max;
                    m = -1;
                } else {
                    knapsackContents.add(tempArray[0][j]);
                    spiceTracker.add(spice.getSpiceByPosition(j));
                    m -= tempArray[0][j];
                    sum += (float) tempArray[1][j];
                    tempArray[1][j] = 0;
                }
            }
            System.out.print("Knapsack of capacity " + knapsackWeights.get(k) + " is worth " + (int) sum +
                             " quatloos and contains ");
                             int l=0;
            for (int o = 0; o < knapsackContents.size(); o++) {
                
                System.out.print(knapsackContents.get(o) + " scoops of ");
                if (o < knapsackContents.size() - 1) {
                    System.out.print(spiceTracker.get(l).getName() + " ");
                    l++;
                } else {
                    System.out.println(spiceTracker.get(l).getName());
                    l++;
                }
            }
            
        }
    }
}

class BellmanFord {
    private int distances[];
    private int path[];
    private int numberofvertices;
    final int MAX_VALUE = 999;

    public BellmanFord(int numberofvertices) {
        this.numberofvertices = numberofvertices;
        distances = new int[numberofvertices + 1];
        path = new int[numberofvertices + 1];
    }

    public void BellmanFordEvaluation(int source, int adjacencymatrix[][], int graphNum) {
        for (int node = 1; node <= numberofvertices; node++) {
            distances[node] = MAX_VALUE;
        }

        Arrays.fill(path, -1);

        distances[source] = 0;
        for (int node = 1; node <= numberofvertices - 1; node++) {
            for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++) {
                for (int destinationnode = 1; destinationnode <= numberofvertices; destinationnode++) {
                    if (adjacencymatrix[sourcenode][destinationnode] != MAX_VALUE) {
                        if (distances[destinationnode] > distances[sourcenode]
                                + adjacencymatrix[sourcenode][destinationnode]) {
                            distances[destinationnode] = distances[sourcenode]
                                    + adjacencymatrix[sourcenode][destinationnode];
                            path[destinationnode] = sourcenode;
                        }
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

        System.out.println("Output for graph " + graphNum + ":");
        for (int vertex = 2; vertex <= numberofvertices; vertex++) {
            System.out.print(source + " --> " + vertex + " cost is " + distances[vertex] + "; path is ");
            printPath(path, vertex);
            System.out.println();
        }
    }

    void printPath(int[] path, int i) {
        if (path[i] == -1) {
            System.out.print(i);
            return;
        }
        printPath(path, path[i]);
        System.out.print(" --> " + i);
    }
}

class Spice {
    private int quantity;
    private float value;
    private String name;

    private static ArrayList<Spice> spiceList = new ArrayList<Spice>();

    public Spice(String name) {
        this.name = name;
        this.value = 0;
        this.quantity = 0;
        addSpice(this);
    }

    public void addSpice(Spice newSpice) {
        spiceList.add(newSpice);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public float getValue() {
        return this.value;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public Spice getSpiceByName(String name) {
        for (int i = 0; i < spiceList.size(); i++) {
            if (spiceList.get(i).getName().compareTo(name) == 0) {
                return spiceList.get(i);
            }
        }
        return null;
    }

    public Spice getSpiceByPosition(int n) {
        return spiceList.get(n);
    }

    public int getSpiceListSize() {
        return spiceList.size();
    }
}