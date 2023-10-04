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
        String[] palindromes = findPalindrome(magicItemsArray);

        //Loops through the palindromes array and displays them
        for(int i=0; i<palindromes.length; i++){
            System.out.println(palindromes[i]);
        }
    }

    public static String[] fileToArray(File file) throws FileNotFoundException{
        
        Scanner scan = new Scanner(file);

        //Creates the array to store the magic itmes in
        String[] itemsArray = new String[666];
        int i=0;

        //Goes through the whole text file and adds each line to the array while converting them to lower case
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
        
        //Loops through the array inputed and returns the 12 palindromes
        for (int i=0; i<arr.length; i++){
            boolean isPalindrome = false;

            //Adds each character to a queue and stack
            for (int j=0; j<arr[i].length(); j++){
                if (arr[i].charAt(j) != ' '){
                    String temp = Character.toString(arr[i].charAt(j));
                    queue.add(temp);
                    stack.push(temp);
                }
            }
            int queueLength = queue.size();
            //Loops through the queue and stack chcecking if the string is a palindrome
            for (int k=0; k<queueLength; k++){
                String tempForQueue = queue.poll();
                String tempForStack = stack.pop();
                if (tempForQueue.compareTo(tempForStack) == 0){
                    //If all the letters match up, then a palindrome is found
                    isPalindrome = true;
                }else{
                    //If the letters don't match up, the loop is broken and the queue and stack are cleared for another string to be entered
                    isPalindrome = false;
                    queue.clear();
                    stack.clear();
                    break;
                }
            }
            //Adds the palindromes to an array that is returned to the main function
            if (isPalindrome){
                palindromes[palindromesCounter] = arr[i];
                palindromesCounter++;
            }
        }
        return palindromes;
    }
}
