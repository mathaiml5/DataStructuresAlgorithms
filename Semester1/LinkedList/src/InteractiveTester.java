/**
 * The InteractiveTest is the runner class that lets the user perform various list operations on the LinkedListDeque Doubly Linked List such as add/remove elements, and other operations
 * Since this is only a runner or tester class there are no member variables or methods needed
 * The program is designed to be friendly and prompt/guide the user at each stage:
 * 1. A menu of commands 0-9 which represent various list operations available are presented
 * 2. User can enter the commands they wish to explore.
 * 3. Once user choose a particular command any additional inputs needed are presented
 * 4. The user can interactively build their list and explore various list operations
 * 5. At any time if the user wants to exit they can enter antything other than single digits that represent the command to exit
 * 6. If the user enters anything other than 0-9 they might have entered in error so program asks for confirmation before exiting
 * Only includes a Main method which tests all list operations on a chatacter list (implemented with String class to make it simple)
 * @author: Vishak Srikanth
 * @version: 9/20/2021
 */

import java.util.Scanner;

//Interactive tester
public class InteractiveTester {
    public static void main(String[] args) {

        //Print instructions for user and how to use the list
        System.out.println("*********************************************************************************************************");
        System.out.println("*********************** DOUBLY LINKED LIST (DEQUE) ******************************************************");
        System.out.println("*********************************************************************************************************");
        System.out.println("This program lets you interactively build a list and perform various operations on the list");
        System.out.println("The nodes in the linked list allow you to store any text string (which will cover numbers also!)");
        System.out.println("You can build the list interactively one node at a time and try out various list operations");
        System.out.println("for e.g. inserting elements at beginning or end of list, removing single occurence of an element etc.");
        System.out.println("The Linked List supports the following operations: ");
        System.out.println("1. Add Element at Beginning of List 2. Add Element at End of List  3. Remove Element at Beginning of List");
        System.out.println("4. Remove Element at End of List 5. Move Element to Beginning 6. Move element to end of List");
        System.out.println("7. Remove First Occurence of an Element 8. Insert after Element 9. Insert before Element");
        System.out.println("0. Print List");
        System.out.println("***************************************************************************************************");
        System.out.println("To begin, enter any one of the above command numbers (0-9) and you will be prompted to enter any other inputs to complete the operation");
        System.out.println("The contents of the list will be printed after every operation to confirm that the operation requested was performed.");
        System.out.println("To exit the program, press any key other than the keys for the linked list commands (0-9).");
//        System.out.print("Enter Command (0-9)> ");
        String commandStr;
        String elToAdd;
        String elToRemove;
        String elToMove;
        boolean invalidInput = true;
        boolean done = false;
        LinkedListDeque<String> lld = new LinkedListDeque<String>();
        while (!done) {

            // Keep prompting for a valid list operations until user enters something other than the single digit commands above
            do {

                System.out.print("Enter the next command (0-9) you want to try or press any other key to exit> ");
                Scanner inp = new Scanner(System.in);

                commandStr = inp.nextLine();
                //Check if valid command: only single digit (0-9) is valid, anything else means we need to exit
                if (commandStr.matches("[\\d]{1}$")) {
                    invalidInput = false;
                    int sel = Integer.parseInt(commandStr);
                    switch(sel) {
                        //Command Option 0: printlist
                        case 0:
                            System.out.println("Command Entered> PrintList");
//                            lld.printList();
                            break;
                        case 1:
                            System.out.println("Command Entered> Add Element at Beginning of List");
                            System.out.print("Enter the element you want to add at start of list> ");
                            elToAdd = inp.nextLine();
                            lld.addAtStart(elToAdd);
//                            lld.printList();
                            break;
                        case 2:
                            System.out.println("Command Entered> Add Element at End of List");
                            System.out.print("Enter the element you want to add at end of list> ");
                            elToAdd = inp.nextLine();
                            lld.addAtEnd(elToAdd);
//                            lld.printList();
                            break;
                        case 3:
                            System.out.println("Command Entered> Remove Element at Beginning of List");
                            lld.removeFirstElement();
//                            lld.printList();
                            break;
                        case 4:
                            System.out.println("Command Entered> Remove Element at End of List");
                            lld.removeLastElement();
//                            lld.printList();
                            break;
                        case 5:
                            System.out.println("Command Entered> Move Element to Beginning of List");
                            System.out.print("Enter the element you want to move to beginning of list> ");
                            elToMove = inp.nextLine();
                            lld.moveToFront(elToMove);
//                            lld.printList();
                            break;
                        case 6:
                            System.out.println("Command Entered> Move Element to End of List");
                            System.out.print("Enter the element you want to move to end of list> ");
                            elToMove = inp.nextLine();
                            lld.moveToEnd(elToMove);
//                            lld.printList();
                            break;
                        case 7:
                            System.out.println("Command Entered> Remove First Occurence of an Element");
                            System.out.print("Enter the element whose first occurence you want to remove> ");
                            elToRemove = inp.nextLine();
                            lld.removeFirstOccurence(elToRemove);
//                            lld.printList();
                            break;

                        case 8:
                            System.out.println("Command Entered> Insert Element After Node");
                            System.out.print("Enter the element you want to insert> ");
                            elToAdd = inp.nextLine();
                            System.out.print("Enter the node AFTER which you want to insert> ");
                            elToMove = inp.nextLine();
                            lld.insertAfterNode(elToAdd, elToMove);
//                            lld.printList();
                            break;

                        case 9:
                            System.out.println("Command Entered> Insert Element Before Node");
                            System.out.print("Enter the element you want to insert> ");
                            elToAdd = inp.nextLine();
                            System.out.print("Enter the node BEFORE which you want to insert> ");
                            elToMove = inp.nextLine();
                            lld.insertBeforeNode(elToAdd, elToMove);
//                            lld.printList();
                            break;

                    }
                    System.out.println("=========================================================================================================");
                    lld.printList();
                    System.out.println("=========================================================================================================");
                    System.out.println("List Commands Menu");
                    System.out.println("1. Add Element at Beginning of List 2. Add Element at End of List  3. Remove Element at Beginning of List");
                    System.out.println("4. Remove Element at End of List 5. Move Element to Beginning 6. Move element to end of List");
                    System.out.println("7. Remove First Occurence of an Element 8. Insert after Element 9. Insert before Element");
                    System.out.println("0. Print List");
                    System.out.println("************************************************************************************************************");

                }
                //User might have made a mistake with typing the command digit, so confirm with user before exit and prompt user to re-enter if they want to continue
                else {
                    System.out.print("Are you sure you want to exit? Enter Y or y to exit, any other key to continue> ");
                    commandStr = inp.nextLine();
                    if (commandStr.matches("[Yy]{1}$")){
                        done = true;
                    }
                }


            } while (invalidInput);




        }
    }
}

