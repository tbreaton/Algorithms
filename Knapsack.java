import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

class Fractional_Knapsack {
    public static void main(String args[]) throws IOException {
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
            max_qty = knapsackWeights.get(k);
            sum = 0;
            int spiceCounter = n-1;

            m = max_qty;

            ArrayList<Integer> knapsackContents = new ArrayList<Integer>();
            ArrayList<Spice> spiceTracker = new ArrayList<Spice>();

            while (m >= 0) {
                max = 0;
                for (i = 0; i < n; i++) {
                    if (((float) array[1][i]) / ((float) array[0][i]) > max) {
                        max = ((float) array[1][i]) / ((float) array[0][i]);
                        j = i;
                    }
                }
                if (array[0][j] > m) {
                    knapsackContents.add(m);
                    spiceTracker.add(spice.getSpiceByPosition(spiceCounter));
                    spiceCounter--;
                    sum += m * max;
                    m = -1;
                } else {
                    knapsackContents.add(array[0][j]);
                    spiceTracker.add(spice.getSpiceByPosition(spiceCounter));
                    spiceCounter--;
                    m -= array[0][j];
                    sum += (float) array[1][j];
                    array[1][j] = 0;
                }
            }
            System.out.print("Knapsack of capacity " + knapsackWeights.get(k) + " is worth " + (int) sum +
                             " quatloos and contains ");
            for (int o = 0; o < knapsackContents.size(); o++) {
                int l=0;
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