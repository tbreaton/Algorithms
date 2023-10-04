import java.io.File;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Main{
    
    public static void main(String[] args) throws FileNotFoundException{
        File magicItemsFile = new File("/Users/Tom/Documents/Github/Algorithms/magicitems.txt");
        String[] magicItemsArray = fileToArray(magicItemsFile);
        /*for (int i=0; i<magicItemsArray.length; i++){
            System.out.println(magicItemsArray[i]);
        }*/
        String[] palindromes = findPalindrome(magicItemsArray);
        /*for(int i=0; i<palindromes.length; i++){
            System.out.println(palindromes[i]);
        }*/
    }

    public static String[] fileToArray(File file) throws FileNotFoundException{
        
        Scanner scan = new Scanner(file);

        String[] itemsArray = new String[666];
        int i=0;
        while(scan.hasNextLine()){
            String data = scan.nextLine();
            data = data.toLowerCase();
            itemsArray[i] = data;
            i++;
        }
        scan.close();
        return itemsArray;
    }

    public static String[] findPalindrome(String[] arr){
        String[] palindromes = new String[12];
        int palindromesCounter = 0;

        Queue<String> queue = new LinkedList<>();
        Stack<String> stack = new Stack<>();
        
        for (int i=0; i<1; i++){
            boolean isPalindrome = false;
            for (int j=0; j<arr[i].length(); j++){
                if (arr[i].charAt(j) != ' '){
                    String temp = Character.toString(arr[i].charAt(j));
                    //System.out.println(temp);
                    queue.add(temp);
                    stack.push(temp);
                }
            }
            int queueLength = queue.size();
            for (int l=0; l<queueLength; l++){
                        System.out.println(queue.remove());
                        //System.out.println(stack.pop());
                    }
            for (int k=0; k<queueLength; k++){
                String tempForQueue = queue.remove();
                String tempForStack = stack.pop();
                if (tempForQueue == tempForStack){
                    isPalindrome = true;
                }else{
                    isPalindrome = false;
                    break;
                }
            }
            if (isPalindrome){
                palindromes[palindromesCounter] = arr[i];
                palindromesCounter++;
            }
        }
        return palindromes;
    }
}
