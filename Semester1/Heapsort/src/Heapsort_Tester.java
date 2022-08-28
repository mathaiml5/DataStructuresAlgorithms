/*  The heapsort tester class produces an interactive menu of options and performs the testing of the various functionalities based on user's input
 *  user input.
 *  The program asks the user for a choice of numeric or string input and tests the heapsort algorithm for any multiway (d-ary) heap:
 *  For example specifying d=2 creates a binary heap and specifying d=3 builds and sorts a list of items using a ternary heap.
 *  The user can either input a string of characters which they wish to use to create the heap (option #2) OR
 *  it can generate random list of numbers with a user specified range (option #1)
 *  The program creates maximum order Priority Queue and sorts them in-place using d-ary heapsort.
 *  The program prints the output for each step of heapify and each sink step in the heapsort algorithm
 *  There is also horizontal dendrogram style visualization of the final n-ary heap sorted tree as well as the array contents
 *  during each step (Note: the array contents are preceded by 2 numbers N and k where N=element that is swapped and k is the node
 *  from which sink is currently taking place)
 *  @author: Vishak Srikanth
 *  @version: 11/01/2021
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

/**
 * Main heapsort tester class
 */
public class Heapsort_Tester {

    //main method that runs the interactive tester
    public static void main(String[] args) throws IOException {

        System.out.println("************************************************************************************************************************");
        System.out.println("********************************************* d-Ary HeapSort Tester ****************************************************");
        System.out.println("************************************************************************************************************************");
        System.out.println("This program tests the heapsort algorithm for any multiway (d-ary) heap. For example specifying d=2 creates a binary heap and specifying");
        System.out.println("3 builds and sorts a list of items using a ternary heap. The user can either input a string of characters which they wish to use");
        System.out.println("to create the heap OR it can generate random list of numbers with a user specified range. The program creates maximum order Priority Queue and ");
        System.out.println("sorts them in-place using heapsort. The program prints the output for each step of heapify and each sink step in the heapsort algorithm");
        System.out.println("We also print a horizontal dendrogram style visualization of the final of the n-ary tree as well as the array contents during during each step.");
        System.out.println("************************************************************************************************************************");
        System.out.println("Input types Menu:");
        System.out.println("[1] Random list of integers in a range");
        System.out.println("[2] User specified string of characters");
        System.out.println("Enter the input type you will use.");
        String infixStr;
        boolean invalidMenuChoice = true;
        int menuChoice = -1;
        //get menu choices
        do {
            System.out.println("(Enter 1 or 2 for the input type) > ");
            Scanner inp = new Scanner(System.in);
            try {
                infixStr = inp.nextLine();

                //Check if valid string: only options 1 or 2 are allowed
                menuChoice = Integer.parseInt(infixStr);
                if (menuChoice == 1 || menuChoice == 2) {
                    invalidMenuChoice = false;
                    break;
                }
                //print error message and prompt user to re-enter a valid infix expression if the expression is invalid
                else {
                    System.out.println("Invalid input. Please enter a valid menu choice which contains only valid options (1 or 2)");
                    invalidMenuChoice = true;
                }
            }
            catch(NumberFormatException e){
                System.out.println("Invalid input. Please enter a valid menu choice which contains only valid options (1 or 2)");
                invalidMenuChoice = true;
            }



        } while (invalidMenuChoice);
        //Get the heap tree order type (n-ary)
        int n_ary = 2;
        boolean invalidNAryChoice = true;
        do {
            System.out.println("(Enter the heap's tree order (n-ary) or multiway parameter (e.g. 2= binary, 3 = ternary) allowed up to 20-way trees)> ");
            Scanner inp = new Scanner(System.in);

            infixStr = inp.nextLine();

            //Check if valid string: only options 1 or 2 are allowed
            n_ary = Integer.parseInt(infixStr);
            if (n_ary <= 20 && n_ary >=2) {
                invalidNAryChoice = false;
                break;
            }


            //print error message and prompt user to re-enter a valid infix expression if the expression is invalid
            else {
                System.out.println("Invalid input. Please enter a valid tree order in the range 2 - 20" );
                invalidNAryChoice = true;
            }


        } while (invalidNAryChoice);



        //If doing a numeric simulation generate a random list of numbers in the range specified
        if (menuChoice == 1) {
            int sample_size = 100;
            int max_bound = 100;
            boolean invalidNumInputs = true;
            do {



                System.out.println("Enter 2 numbers separated by spaces for (A) how many numbers in the list (upto 200) and (b) how big you need the integers to be (upto 500) > ");

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                String str = br.readLine();
                String[] nums = str.split("\\s+"); //returns an array of strings split based on the parameter

                sample_size = Integer.parseInt(nums[0]);
                max_bound = Integer.parseInt(nums[1]);

                if (sample_size <= 0 || sample_size > 200) {
                    System.out.println("Please enter a sample size which is an integer within specified limits (1-200)!");
                    invalidNumInputs = true;
                } else if (max_bound <= 0 || max_bound > 500) {
                    System.out.println("Please enter an upper bound which is an integer within specified limits (1-500)!");
                    invalidNumInputs = true;
                } else {
                    invalidNumInputs = false;
                }



            } while (invalidNumInputs);

            // generate N random number between 0 to max_bound
            final int finalMax_bound = max_bound;
            int[] randomIntsArray = IntStream.generate(() -> new Random().nextInt(finalMax_bound)).limit(sample_size).toArray();
            Integer[] boxedArray = Arrays.stream(randomIntsArray) // IntStream
                    .boxed()                // Stream<Integer>
                    .toArray(Integer[]::new);
            System.out.println("Input Array: " + Arrays.toString(boxedArray));

            HeapSort_dAry<Integer> hs = new HeapSort_dAry<Integer>(boxedArray, n_ary);
            System.out.println("Initial Array of Random integers loaded into Heap & Corresponnding Initial Tree: ");
            System.out.println("************************************************************************************************************************");
            hs.show(hs.getPq());
            System.out.println("************************************************************************************************************************");
            hs.printNaryTree();
            System.out.println("************************************************************************************************************************");
            System.out.println("Calling Heapsort: Heapify followed by sortdown: ");
            hs.sort();
            System.out.println("************************************************************************************************************************");
            System.out.println("************************************************************************************************************************");
            System.out.println("Final Sorted Array and Tree: ");
            hs.show(hs.getPq());
            System.out.println("************************************************************************************************************************");
            hs.printNaryTree();


        }

        //String based test of heapsort algorithm
        //user enters a string of characters and the list is heapified and sorted using n-ary heap!
        if(menuChoice ==2){
            boolean invalidStringInput = true;
            String myString = new String();
            do {


                System.out.println("Enter the string of charcaters that you want to construct heap and perform heapsort with.");
                System.out.println("Only alphabetic characters and any whitespaces will be ignored");
                System.out.println("Enter the string whose characters you want to create the heap with > ");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));



                br = new BufferedReader(new InputStreamReader(System.in));

                myString = br.readLine();

                if(myString.matches("^[a-zA-Z\\s]*$")) {
                    invalidStringInput = false;
                    myString = myString.replaceAll("\\s+","");
                }
                else{
                    System.out.println("Please enter a valid string with alphabetic charcters only (A-Z, a-z)");
                    invalidStringInput = true;
                }

            } while (invalidStringInput);
            String[] a = myString.split("(?!^)");
//            System.out.println("Input Array: " + Arrays.toString(a));
            HeapSort_dAry<String> hs2 = new HeapSort_dAry<String>(a, n_ary);
            System.out.println("Initial string of characters that are input for Heap & initial tree: ");
            System.out.println("************************************************************************************************************************");
            hs2.show(hs2.getPq());
            System.out.println("************************************************************************************************************************");
            hs2.printNaryTree();
            System.out.println("************************************************************************************************************************");
            System.out.println("Calling Heapsort: Heapify followed by sortdown: ");
            hs2.sort();
            System.out.println("************************************************************************************************************************");
            System.out.println("************************************************************************************************************************");
            System.out.println("Final Sorted Array and Tree: ");
            hs2.show(hs2.getPq());
            System.out.println("************************************************************************************************************************");
            hs2.printNaryTree();


        }

    }
}