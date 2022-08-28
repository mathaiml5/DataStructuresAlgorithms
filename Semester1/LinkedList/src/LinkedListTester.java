/**
 * The LinkedListTester is the runner class that tests the various scenarios for operation performed on the LinkedListDeque Doubly Linked List such as add/remove elements, and other operations
 * Since this is only a runner or tester class there are no member variables or methods needed
 * Only includes a Main method which tests all list operations on a chatacter list (implemented with String class to make it simple)
 * @author: Vishak Srikanth
 * @version: 9/20/2021
 */
public class LinkedListTester {

    //Main method that tests all the scenarios for all list operations
    public static void main(String[] args) {
        LinkedListDeque<String> lld = new LinkedListDeque<String>();

        System.out.println("***************************************************************************************************");
        System.out.println("*********************** LINKED LIST (DEQUE) TESTER ******************************************");
        System.out.println("***************************************************************************************************");
        System.out.println("We build a LinkedList consisting of an initial letter sequence and perform various list operations");
        System.out.println("to understand and show how list traversal and manipulation are performed");
        System.out.println("The tester initializes a linked list with the letters of the word COMPUTE in that order and then performs ");
        System.out.println("various operations such as addition or deletion of elements of the list in various positions e.g. at either");
        System.out.println("end or in the middle or before/after specific elements in the list. Various edge cases of adding or ");
        System.out.println("deleting are also explored such as adding before/after a non-existant element or adding to empty list etc.");
        System.out.println("For each test scenario the operation and expected output are first printed and then the actual list ");
        System.out.println("content is shown in the following line prefixed by 'List Contents After Performing Operation:' to validate that the output");
        System.out.println("***************************************************************************************************");
        System.out.println("We add the letters C, O, M, P, U, T, E one at a time to the end of list to produce initial linked list!");
        lld.addAtEnd("C");
        lld.addAtEnd("O");
        lld.addAtEnd("M");
        lld.addAtEnd("P");
        lld.addAtEnd("U");
        lld.addAtEnd("T");
        lld.addAtEnd("E");
        System.out.println("1. Printing the contents of the list should yield: C-> O -> M -> P -> U -> T -> E : ");
        System.out.println("=========================================================================================================");
        lld.printList();
        System.out.println("=========================================================================================================");

        System.out.println("2. Inserting M at the beginning of the list should yield M -> C -> O -> M -> P -> U -> T -> E : ");
        lld.addAtStart("M");
        System.out.println("=========================================================================================================");
        lld.printList();
        System.out.println("=========================================================================================================");

        System.out.println("3. Inserting R at the end of the list must now yield M -> C -> O -> M -> P -> U -> T -> E -> R: ");
        lld.addAtEnd("R");
        System.out.println("=========================================================================================================");
        lld.printList();
        System.out.println("=========================================================================================================");

        System.out.println("4. Remove from the beginning now must yield C -> O -> M -> P -> U -> T -> E -> R : ");
        lld.removeFirstElement();
        System.out.println("=========================================================================================================");
        lld.printList();
        System.out.println("=========================================================================================================");

        System.out.println("5. Remove from the end now must yield C -> O -> M -> P -> U -> T -> E : ");
        lld.removeLastElement();
        System.out.println("=========================================================================================================");
        lld.printList();
        System.out.println("=========================================================================================================");

        System.out.println("6. Insert M before P now must yield C-> O -> M -> M -> P -> U -> T -> E : ");
        lld.insertBeforeNode("M", "P");
        System.out.println("=========================================================================================================");
        lld.printList();
        System.out.println("=========================================================================================================");

        System.out.println("7. Insert H before M must now yield C-> O -> H -> M -> M -> P -> U -> T -> E : ");
        lld.insertBeforeNode("H", "M");
        System.out.println("=========================================================================================================");
        lld.printList();
        System.out.println("=========================================================================================================");

        System.out.println("7a. Removing 1st occurence of H must now yield C-> O -> M -> M -> P -> U -> T -> E : ");
        lld.removeFirstOccurence("H");
        System.out.println("=========================================================================================================");
        lld.printList();
        System.out.println("=========================================================================================================");

        System.out.println("8. Inserting B before A (no A in list so B goes at end of list!) must now yield  C-> O -> M -> M -> P -> U -> T -> E -> B : ");
        lld.insertBeforeNode("B", "A");
        System.out.println("=========================================================================================================");
        lld.printList();
        System.out.println("=========================================================================================================");

        System.out.println("9. Inserting C after P must now yield C -> O -> M -> M -> P-> C -> U -> T -> E -> B : ");
        lld.insertAfterNode("C", "P");
        System.out.println("=========================================================================================================");
        lld.printList();
        System.out.println("=========================================================================================================");

        System.out.println("10. Inserting L after M must now yield C -> O -> M -> L -> M -> P -> C -> U -> T -> E -> B : ");
        lld.insertAfterNode("L", "M");
        System.out.println("=========================================================================================================");
        lld.printList();
        System.out.println("=========================================================================================================");

        System.out.println("11. Removing M must now yield C -> O -> L -> M -> P -> C -> U -> T -> E -> B : ");
        lld.removeFirstOccurence( "M");
        System.out.println("=========================================================================================================");
        lld.printList();
        System.out.println("=========================================================================================================");

        System.out.println("12.Removing G (there is no G so no change) must now yield C -> O -> L -> M -> P -> C -> U -> T -> E -> B : ");
        lld.removeFirstOccurence( "G");
        System.out.println("=========================================================================================================");
        lld.printList();
        System.out.println("=========================================================================================================");

        System.out.println("13. Moving P to the front of the list must now yield P -> C -> O -> L -> M -> C-> U -> T -> E -> B : ");
        lld.moveToFront( "P");
        System.out.println("=========================================================================================================");
        lld.printList();
        System.out.println("=========================================================================================================");

        System.out.println("14. Moving L to the end of the list must now yield P -> C -> O -> M -> C -> U -> T -> E -> B -> L : ");
        lld.moveToEnd( "L");
        System.out.println("=========================================================================================================");
        lld.printList();
        System.out.println("=========================================================================================================");

    }
}
