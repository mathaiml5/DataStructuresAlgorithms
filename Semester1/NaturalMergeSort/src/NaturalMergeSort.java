/**
 * Natural Mergersort is an efficent version of the mergesort which leverages the fact that some subsequences in the LinkedList
 * could already be sorted. By combining these already sorted sublists, we can build partially sorted lists and reduce the
 * running time to O(n log n)
 * The program design is as follows:
 * 1. First we create a list (String list from letters of MERGESORTEXAMPLE created in this test example run below)
 * 2. Then we iteratively repeat the steps 2a-2c until the entire list is sorted:
 * 2a. Traverse the list from head node, looking for sublist pairs of already sorted
 * 2b. Once we have a pair of sorted sublists we merge them.
 * 2c. Continue traversing list until end of input list is reached
 * 3. Once iterations are complete and there are no more sublists to merge we are done!
 *
 * @author: Vishak Srikanth
 * @version: 10/18/2021
 */


public class NaturalMergeSort {



    //The currList private variable holds the LinkedList that is provided as input and also the in-place sorted output
    private LinkedListDeque currList = new LinkedListDeque();

    /**
     * Default contructor for natural mergesort
     */
    public NaturalMergeSort() {
    }

    /** Constructor for Natural mergesort class which takes the input doubly linked list
     * @param currList input Linkedlist that needs to be sorted
     */
    public NaturalMergeSort(LinkedListDeque currList) {
        this.currList = currList;
    }

    /** Getter for the current state of the list
     * @return returns the current state of the list
     */
    public LinkedListDeque getCurrList() {
        return currList;
    }

    /** Checks if 1st argument is less than second based on a specific comparator operation defined on the class
     * @param v object that implements Comparable
     * @param w object that implements Comparable
     * @return
     */
    private boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }



    /** Main Natural Mergesort method
     *  The algorithm is explained in the comments at the top of the file.
     */
    public void sort() {

        //Get head of current list
        DoubleNode head = currList.getHead();

        //If empty lis nothing to do just return
        if (head == null) return;

        //Setting artifically to a number > 1 so that while loop will run at least once!
        int numMerge = 2;
        int level = 0;

        //This loops runs until there is at least a pair to be merged for each iteration
        while (numMerge > 1) {

            //Set the nextHead to current list's head
            DoubleNode nextHead = head;
            //Keep a pointer to the tail of last subList upto which we have already merged
            DoubleNode lastTail = null;

            //Initialize head, merge counter and the currently sorted subList (used for printing and assigment)
            head = null;
            numMerge = 0;
            LinkedListDeque cList = new LinkedListDeque();
            LinkedListDeque seg1 = null, seg2 = null;

            //While there are subLists
            while (nextHead != null) {

                //At the beginning of each iteration the # of merges will be
                if(numMerge == 0){
                    level++;

                }
                //print where we are in the mergesort process
                System.out.println("Iteration #" + (level) + " Merge #" + (numMerge + 1) + ":");

                //Get the 1st longest sorted subsequence
                seg1 = getSortedSegment(nextHead);


                System.out.print("1st Sorted SubList is: ");
                if (seg1!= null && !seg1.isEmpty()) {
                    seg1.printList();
                } else {
                    System.out.println("Empty!");
                }

                //Get the 2nd longest sorted subsequence that just begins after 1st one above ends
                System.out.print("2nd Sorted SubList is: ");
                seg2 = getSortedSegment(seg1.getTail().next);
                if (seg2!= null && !seg2.isEmpty()) {
                    seg2.printList();
                } else {
                    System.out.println("Empty!");
                }
//                }
                // Set the head node for next merging
                nextHead = (seg2 == null) ? null : seg2.getTail().next;

                //Create merged list
                LinkedListDeque merged = merge(seg1, seg2);

                //Print merged list to show progress
                System.out.print("After merging sublists, the Merged List is: ");
                merged.printList();
                System.out.println();

                //Add the merged subsequences so it can be shown at the end of each iteration.
                cList.append(merged);

                //Connect the last merged sublist to current merged sublist
                if (lastTail != null) lastTail.next = merged.getHead();
                lastTail = merged.getTail();
                if (head == null) head = merged.getHead();
                //increment merge counter
                numMerge++;

            }
            System.out.println("========================================================================================================================");
            System.out.print("After Iteration " + level + " List is :");
            //Inplace modify the list
            currList = cList;
            currList.printList();
            System.out.println("========================================================================================================================");
        }
        /** START DEBUG CODE
        LinkedListDeque outList = new LinkedListDeque();
        if (head != null) {


            //Traverse the entire list util you reach the tail and fix the pointers to sorted list
            DoubleNode currNode = head;
            head.prev = null;


            while (currNode != null) {
                outList.addAtEnd(currNode);
                DoubleNode nextNode = currNode.next;
                if (nextNode != null) {
                    nextNode.prev = currNode;
                    currNode = nextNode;
                } else {
                    currNode.next = null;
                    currNode = null;
                }
            }
        }
        currList = outList;
        END DEBUG CODE **/

    }


    /** Finds the largest sorted subsequence starting from a given node in the doubly linked list
     * @param head starting point to find longest sorted sublist
     * @return sorted subList as a linkedlist object
     */
    private LinkedListDeque getSortedSegment(DoubleNode head) {
        if (head == null) return null;
        DoubleNode tail = head;
        int size = 1;
        while (tail.next != null && !less(tail.next, tail)) {
            tail = tail.next;
            size++;
        }
        return new LinkedListDeque(head, tail, size);
    }


    /** Merges 2 sorted sublists; preserves the sorted order as the 2 lists are merged.
     * @param sortedSublist1 1st sorted sublist being merged
     * @param sortedSubList2 2nd sorted sublist being merged
     * @return merged subList
     */
    private LinkedListDeque merge(LinkedListDeque sortedSublist1, LinkedListDeque sortedSubList2) {

        //If either list is empty just return the other as the final merged list
        if (sortedSubList2 == null) return sortedSublist1;
        if (sortedSublist1 == null) return sortedSubList2;

        //We traverse each list from their respective head nodes and compare the elements as we move in sorted order in
        // each list
        DoubleNode dummy = new DoubleNode();
        DoubleNode node1 = sortedSublist1.getHead();
        DoubleNode node2 = sortedSubList2.getHead();
        DoubleNode node = dummy;
        long size1 = sortedSublist1.size(), size2 = sortedSubList2.size();

        //repeat only when both sublists are non-empty
        while (size1 > 0 && size2 > 0) {
            //as long as elements from sublist2 are greater than current node from sublist one
            //move along sublist1
            if (!less(node2, node1)) {
                node.next = node1;
                node1 = node1.next;
                size1--;
            }
            //If node from list 2 is less than current node, traverse list 2 comparing with subList 1 one at a time
            else {
                node.next = node2;
                node2 = node2.next;
                size2--;
            }
            node = node.next;
        }

        // Once we have merged the last node that we encounter it needs to be set as the tail for merged list
        DoubleNode tail = node;

        //if we have more elements that are left in either sublist, then append all of them at the end of current merged list
        if (size1 > 0) {
            node.next = node1;
            tail = sortedSublist1.getTail();
        } else if (size2 > 0) {
            node.next = node2;
            tail = sortedSubList2.getTail();
        }

        tail.next = null;
        //Set final values for head, tail and size of merged list and return the new merges linkedlist!
        return new LinkedListDeque(dummy.next, tail, sortedSublist1.size() + sortedSubList2.size());
    }


    /** Main runner method that creates the linked list from the letters of the string MERGESORTEXAMPLE
     *  and sorts the list in alphabetical (natural) sort order.
     * @param args comandline arguments (none needed)
     */
    public static void main(String[] args) {

        LinkedListDeque<Integer> lld = new LinkedListDeque<Integer>();
//        //Add all the actions being performed on the list and output the list contents after each step
//        System.out.println("Adding 32 at start: ");
//        lld.addAtStart(23);
//        lld.printList();
//        System.out.println("Adding 56 at start: ");
//        lld.addAtStart(56);
//        lld.printList();
//        System.out.println("Adding 37 at start: ");
//        lld.addAtStart(37);
//        lld.printList();
//        System.out.println("Adding 37 at start: ");
//        lld.addAtStart(37);
//        lld.printList();
//        System.out.println("Adding 56 at start: ");
//        lld.addAtStart(56);
//        lld.printList();
//        System.out.println("Adding 56 at end: ");
//        lld.addAtEnd(56);
//        lld.printList();
//        System.out.println("Adding 37 at end: ");
//        lld.addAtEnd(37);
//        lld.printList();
//        System.out.println("Adding 364 at end: ");
//        lld.addAtEnd(364);
//        lld.printList();
//        System.out.println("Adding 37 at end: ");
//        lld.addAtEnd(37);
//        lld.printList();
//        System.out.println("Removing 1st element: ");
//        lld.removeFirstElement();
//        lld.printList();
//        System.out.println("Removing last element: ");
//        lld.removeLastElement();
//        lld.printList();
//        System.out.println("Position of 1st occurence of element: 37 is : " + lld.firstOccurencePosition(37));
//        System.out.println("Position of 1st occurence of element: 56 is : " + lld.firstOccurencePosition(56));
//        System.out.println("Position of 1st occurence of element: 364 is : " + lld.firstOccurencePosition(364));
//
//        System.out.println("Position of 3rd occurence of element: 37 is : " + lld.nThOccurencePosition(37, 3));
//        System.out.println("Position of 2nd occurence of element: 56 is : " + lld.nThOccurencePosition(56, 2));
//
//        System.out.println("Removing 1st occurence of 37: ");
//        lld.removeFirstOccurence(37);
//        lld.printList();
//        System.out.println("Now the position of 1st occurence of element: 56 is : " + lld.firstOccurencePosition(56));
//        System.out.println("Now the position of 1st occurence of element: 364 is : " + lld.firstOccurencePosition(364));
//        System.out.println("Removing 1st occurence of 56: ");
//        lld.removeFirstOccurence(56);
//        lld.printList();
//        System.out.println("Now the position of 1st occurence of element: 364 is : " + lld.firstOccurencePosition(364));
//        System.out.println("Removing 1st occurence of 364: ");
//        lld.removeFirstOccurence(364);
//        lld.printList();
//        System.out.println("Position of 2nd occurence of element: 37 is : " + lld.nThOccurencePosition(37, 2));
//        System.out.println("Position of 3rd occurence of element: 37 is : " + lld.nThOccurencePosition(37, 3));
//
//        System.out.println("Now the position of 1st occurence of element: 364 (should be -1) is: " + lld.firstOccurencePosition(364));
//
//        System.out.println("Adding back 364 at end: ");
//        lld.addAtEnd(364);
//        lld.printList();
//        System.out.println("Adding 52 before 1st occurence of 37: ");
//        lld.insertBeforeNode(52, 37);
//        lld.printList();
//        System.out.println("Adding 52 after 1st occurence of 37: ");
//        lld.insertAfterNode(52, 37);
//        lld.printList();
//        System.out.println("Adding 52 before 1st occurence of 364: ");
//        lld.insertBeforeNode(52, 364);
//        lld.printList();
//        System.out.println("Adding 25 after 1st occurence of 36 (not in list, so should add at end): ");
//        lld.insertAfterNode(25, 36);
//        lld.printList();
//        System.out.println("Adding 75 before 1st occurence of 36 (not in list, so should add at end): ");
//        lld.insertBeforeNode(75, 36);
//        lld.printList();
//        System.out.println("Adding 75 before 1st occurence of 56: ");
//        lld.insertBeforeNode(75, 56);
//        lld.printList();
//        System.out.println("Adding 75 after 1st occurence of 75: ");
//        lld.insertAfterNode(75, 75);
//        lld.printList();
//        DoubleNode st = lld.firstOccurenceNode(23);
////        System.out.println("Start Node: " + st.printNode());
//        DoubleNode e = lld.firstOccurenceNode(56);
////        System.out.println("End Node: " + e.printNode());
//        System.out.println("Removing segment from startnode:(" + st.printNode() + ") to endnode: (" + e.printNode() + ")");
//        lld.removeSubList(st, e);
//        lld.printList();
//        st = lld.firstOccurenceNode(52);
////        System.out.println("Start Node: " + st.printNode());
//        e = lld.firstOccurenceNode(364);
////        System.out.println("End Node: " + e.printNode());
//        System.out.println("Removing segment from startnode:(" + st.printNode() + ") to endnode: (" + e.printNode() + ")");
//        lld.removeSubList(st, e);
//        lld.printList();
//
//        st = lld.firstOccurenceNode(25);
////        System.out.println("Start Node: " + st.printNode());
//        e = lld.firstOccurenceNode(75);
////        System.out.println("End Node: " + e.printNode());
//        System.out.println("Removing segment from startnode:(" + st.printNode() + ") to endnode: (" + e.printNode() + ")");
//        lld.removeSubList(st, e);
//        lld.printList();

        System.out.println("************************************************************************************************************************");
        System.out.println("***************************** LINKED LIST (DEQUE) NATURAL MERGESORT ****************************************************");
        System.out.println("************************************************************************************************************************");
        System.out.println("We build a LinkedList consisting of an initial letter sequence and then we use an in-place natural mergesort");
        System.out.println("to sort the list. We show how list traversal and merging of sublists are done during each iteration.");
        System.out.println("The tester initializes a linked list with the letters of the string MERGESORTEXAMPLE in that order and then performs ");
        System.out.println("natural mergesort on that LinkedList created from the letters of the string. Various edge where empty lists can be ");
        System.out.println("produced are handled internally within the programs either by prinitng error messages or rasiing expceptions.");
        System.out.println("For each test iteration, the input and output lists from the merge operations are printed and then the final list ");
        System.out.println("after each iteration is also shown.");
        System.out.println("************************************************************************************************************************");
        System.out.println("We add the letters M,E,R,G,E,S,O,R,T,E,X,A,M,P,L,E one at a time to the end of list to produce initial linked list!");
        System.out.println("1. Printing the contents of the list should yield: M -> E -> R -> G -> E -> S -> O -> R -> T -> E -> X -> A -> M -> P -> L -> E : ");
        System.out.println("========================================================================================================================");
        String myString = "MERGESORTEXAMPLE";
        String[] a = myString.split("(?!^)");
        LinkedListDeque<String> inpuList = new LinkedListDeque<>();

        for (int i = 0; i < a.length; i++) {
            inpuList.addAtEnd(a[i]);

        }
        NaturalMergeSort nms = new NaturalMergeSort(inpuList);
        System.out.println("========================================================================================================================");
        System.out.print("List Before Sorting: ");
        nms.currList.printList();
        System.out.println("========================================================================================================================");
        nms.sort();
        System.out.println("========================================================================================================================");
        System.out.print("List After Sorting: ");
        nms.currList.printList();
        System.out.println("========================================================================================================================");
    }
}





