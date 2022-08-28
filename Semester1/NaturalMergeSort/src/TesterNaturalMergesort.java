/**
 * This is a tester class that prompts the user to input a comma separated list of numbers or strings and sorts them using natural mergesort
 * We allow the user to enter a list of string or integers or decimals. We then infer what type of elements are entered in the user's list
 * For example, we will need to determine if list has numbers which include commas in them (such as what you would see with integers with
 * commas separating the 1000s blocks) ot the strings themselves have commas within them such as addresses. Users can enter such special numbers
 * or strings by enclosing the entire element within double quotes.
 * The input list is first split on commas that are not within quotes. Then each element is checked to see if it is quoted and then quotes
 * are stripped. Then we determine the type of list (integer, decimal or string) and proceed to sort the list based on natural mergesort
 *
 * @author: Vishak Srikanth
 * @version: 10/18/2021
 */
import java.util.Scanner;
public class TesterNaturalMergesort {
    public static void main(String[] args) {

        System.out.println("************************************************************************************************************************");
        System.out.println("***************************** LINKED LIST (DEQUE) NATURAL MERGESORT ****************************************************");
        System.out.println("************************************************************************************************************************");
        System.out.println("We build a LinkedList based a comma separated sequence of numbers or strings that user is prompted to input. We then demonstrate an ");
        System.out.println("in-place natural mergesort to sort the input list. We show how list traversal and merging of sublists are done during each iteration.");
        System.out.println("The tester initializes a linked list with the inputs in the order entered by user and then performs ");
        System.out.println("natural mergesort on that LinkedList. For each test iteration, the input and output lists from the merge");
        System.out.println("operations are printed and then the final list after each iteration is also shown.");
        System.out.println("************************************************************************************************************************");
        System.out.println("NOTE: If any item in your list contains commas, input those items within double quotes.");
        System.out.println("Leading and trailing whitespaces in each element are ignored.");
        System.out.print("Enter Input List to be Sorted (comma separated)> ");

        Scanner inp = new Scanner(System.in);

        String inputStr = inp.nextLine();
        //This regular expression splits the input list entered by user on commas but not on commas inside quoted strings
        String[] items = inputStr.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        for (int i = 0; i < items.length; i++) {
            //Since the user could enter integers or floats which have commas or strings that have commas within them such as addresses
            // we need to first strip out all trailing and leading whitespaces using trim() and then remove all the quotes
            items[i] = items[i].trim().replaceAll("\"", "");
//            System.out.println("> "+ items[i]);
        }


        Long[] longItems = new Long[items.length];
        Double[] doubleItems = new Double[items.length];
        //User could enter any combination of quoted and unquoted lists which have integers, decimals or just alphanumeric strings
        //So we first need to infer what kind of elements are present in the list and set the boolean flags that indicate which type of list is
        //entered by the user. If none of the long or doble flags are set then the list is assumed to have strings
        boolean longList = true, doubleList = true;

        //First check if all elements of list entered by user are integers
        for (int i = 0; i < items.length; i++) {
            //ParseLong will throw an exception for any element that is not an integer but first we need to remove any commas that separate the 1000s blocks
            try {
                Long eleLong = Long.parseLong(items[i].replaceAll(",", ""));
                longItems[i] = eleLong;
            }
            //If we get this exception it means the list does not contain all integers and set the longList to false and immediately exit
            catch (NumberFormatException e) {
                longList = false;
                break;
            }
        }
        //Then we check if all elements of list entered by user are decimal numbers only if it is not already determined to be a list o integers
        if (!longList) {
            for (int i = 0; i < items.length; i++) {
                //ParseDouble will throw an exception for any element that is not a double but first we need to remove any commas that separate the 1000s blocks
                try {
                    Double eleDouble = Double.parseDouble(items[i].replaceAll(",", ""));
                    doubleItems[i] = eleDouble;

                }
                //If we get this exception it means the list does not contain all doubles and set the doubleList to false and immediately exit
                catch (NumberFormatException f) {
                    doubleList = false;
                    break;
                }
            }

        }


        NaturalMergeSort nms = new NaturalMergeSort();
        //Depending on the type of list entered by the user we create a LinkedList whose elements are the type of elements in the user's list
        //We create the LinkedListDeque based on the type and add all the elements in the order in which user entered them into the list
        if (longList) {
            //Here we have a list of Longs
//            System.out.println("List of Longs Detected!");
            LinkedListDeque<Long> inputList = new LinkedListDeque<>();
            //Add each item from user's list into inputList by inerting at the end of the list
            for (int i = 0; i < longItems.length; i++) {
                inputList.addAtEnd(longItems[i]);

            }
            nms = new NaturalMergeSort(inputList);
        } else if (doubleList) {
            //Here we have a list of Doubles
//            System.out.println("List of Doubles Detected!");
            LinkedListDeque<Double> inputList = new LinkedListDeque<>();
            //Add each item from user's list into inputList by inerting at the end of the list
            for (int i = 0; i < doubleItems.length; i++) {
                inputList.addAtEnd(doubleItems[i]);

            }
            nms = new NaturalMergeSort(inputList);
        }
        //If the list is not all integers or decimal numbers, then it must just be a list of Strings
        else {
            LinkedListDeque<String> inputList = new LinkedListDeque<>();
            //Add each item from user's list into inputList by inerting at the end of the list
            for (int i = 0; i < items.length; i++) {
                inputList.addAtEnd(items[i]);

            }
            nms = new NaturalMergeSort(inputList);
        }

        //We print the list before sorting
        System.out.println("========================================================================================================================");
        System.out.print("List Before Sorting: ");
        nms.getCurrList().printList();
        System.out.println("========================================================================================================================");
        //perform the sort operation
        nms.sort();
        System.out.println("========================================================================================================================");
        System.out.print("List After Sorting: ");
        nms.getCurrList().printList();
        System.out.println("========================================================================================================================");


        inp.close();
    }
}
