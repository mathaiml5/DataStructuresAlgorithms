/**
 * The LinkedListDeque defines the Doubly Linked List and all the relevant methods to create the list, add/remove elements, and other operations
 * Uses the class DoubleNode to represent each node that has the element which can be any object (from class or type <T>)
 * Also includes a sample test method to test list operations on integers
 * All the methods and class variables from Assignment #3 on LinkedList are reused
 * 2 new methods are added
 * 1. append: Appends another linked list to current list.
 * 2. removeSubList: Delete a subList contained within specified start and end DoubleNode boundaries
 *
 * @author: Vishak Srikanth
 * @version: 10/18/2021
 */

import java.util.*;

/**
 * @param <T extends Comparable <T>> Type of objects stored in each node inside objects of inner class DoubelNode
 */
public class LinkedListDeque<T extends Comparable<T>> {


    //DoubleNode element at the head of list
    private DoubleNode head;
    //DoubleNode element at the end of list
    private DoubleNode tail;
    //Size of linked list ( # of elements in linked list)
    private int size;
//    public static long nextId = 0;


    //NOTE: Getters and setters for private variables are provided here for completeness but are not typically used as list operations
    // are the main interface and users will not typically set values for these private variables

    /**
     * Getter for head
     *
     * @return returns the head node of the list
     */
    public DoubleNode getHead() {
        return head;
    }

    /**
     * Setter for head
     *
     * @param head sets the head node of list with the parameter
     */
    public void setHead(DoubleNode head) {
        this.head = head;
    }

    /**
     * Getter for tail
     *
     * @return returns the tail node of the list
     */
    public DoubleNode getTail() {
        return tail;
    }

    /**
     * Setter for tail
     *
     * @param tail sets the tail node of list with the parameter
     */
    public void setTail(DoubleNode tail) {
        this.tail = tail;
    }

    /**
     * Getter for size of the list
     *
     * @return returns size of list
     */
    public int getSize() {
        return size;
    }

    /**
     * Setter for size of list
     *
     * @param size sets the size of the list
     */
    public void setSize(int size) {
        this.size = size;
    }


    /**
     * Default constructor for LinkedList creates an empty LinkedList with null for head and tail
     */
    public LinkedListDeque() {
        head = null;
        tail = null;
        size = 0;
    }


    /**
     * Creates a list with the given head and tail nodes, useful for creating sublists or segments of another list
     *
     * @param head head DoubleNode ptr of list
     * @param tail tail DoubleNode ptr of list
     * @param size size of list
     */
    public LinkedListDeque(DoubleNode head, DoubleNode tail, int size) {
        this.head = head;
        this.tail = tail;
        this.size = size;
//        nextId += size;
    }

    /**
     * Get the size of the linke list (i.e. number of DoubleNode objects in the list)
     *
     * @return number of nodes in LinkedList
     */
    public int size() {
        return size;
    }

    /**
     * Check if list is empty
     *
     * @return true if list is empty (size is zero) or false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Adds a new node containing the object passed in as parameter and puts this new node at head of list
     *
     * @param el element (object of generic type <T>) to be added at the beginning of the list
     */
    public void addAtStart(T el) {
        //head before inserting new node
        DoubleNode prevHead = head;
        //Create the node with the element to be added but don't set its next and previous pointers yet
        DoubleNode tmp = new DoubleNode(el, null, null);
//DEBUG        System.out.println("New node created is: " + tmp.printNode());

        // If list is not empty, point the previous headnode's previous node link to the new node
        // and set the next link of the new node to the element that was originally the head node!
        if (head != null) {
            prevHead.prev = tmp;
            tmp.next = prevHead;
        }

        // Since we have fixed the next and prev node links, now we make the newly created node the head of the list
        // so the newly added element is now located at the beginning of the list
        head = tmp;

        //Check if the list is empty before adding this node then set the tail of the list to also the newly created node
        if (tail == null) {
            tail = tmp;
        }
//DEBUG        System.out.println("headNode after insertion is: " + head.printNode());
//DEBUG        System.out.println("tailNode after insertion is: " + tail.printNode());

        //Since we have added an additional node at the head of the list, increment the list size by 1
        size++;

    }

    /**
     * method to add an element at the end of a list
     *
     * @param el element to be added at end of list
     */
    public void addAtEnd(T el) {

        //head before inserting new node
        DoubleNode prevTail = tail;

        //Create the node with the element to be added at the end of the list but don't set its next and previous pointers yet
        DoubleNode tmp = new DoubleNode(el, null, null);

        //If the list is not empty before adding this new node, then set the next link on the old tail node to the newly created node
        // then set the newly inserted node's prev pointer to what was the tail node before, so now this node will fall in place at the
        // end of the list
        if (tail != null) {
            prevTail.next = tmp;
            tmp.prev = prevTail;
        }

        //Now set the new node created to be the tail of the list (as we have already modified the next and previous links of the last node already!)
        tail = tmp;


        //If the list was empty before we inserted this new node, set the head to the newly added node at the tail of the list
        if (head == null) {
            head = tmp;
        }

        //Since we have added an additional node at the end of the list, increment the list size by 1
        size++;

    }

    /**
     * Method to remove the first element (head node) in the Linked List
     */
    public void removeFirstElement() {

        //Throw exception if trying to delete an element on an empty list!
        try {
            if (size == 0) {
                throw new NoSuchElementException("Invalid Operation: Trying to remove from an empty list!!");
            } else {
                //Set the old head node to new tmp node
                DoubleNode tmp = head;
                //Set the head to the next element of current head
                head = head.next;
                //set the new head node's (which will be 2nd node in original list) previous link to null
                head.prev = null;
                //Since we have removed the head node, decrement the list size by 1
                size--;

            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

    }




    public void removeSubList(DoubleNode start, DoubleNode end) {

        //Throw exception if trying to delete an element on an empty list!
        try {
            if (size == 0) {
                throw new NoSuchElementException("Invalid Operation: Trying to remove from an empty list!!");
            } else {
                //Set the old head node to new tmp node
                if (start == null || end == null) return;
                DoubleNode tmp = head;
                int removedCount = 0;
                //Set the head to the start element of segment
                while (tmp != null && !tmp.equals(start)) {
                    tmp = tmp.next;
                }
                if (tmp.equals(start)) {
                    removedCount++;
                    DoubleNode prevNode = tmp.prev;
                    DoubleNode nextNode = new DoubleNode();
                    DoubleNode currNode = start;
                    while (!currNode.equals(end) && currNode != null) {
                        currNode = currNode.next;
                        removedCount++;
                    }
                    if (currNode.equals(end)) {
                        nextNode = currNode.next;
                        removedCount++;
                    }
                    //Removing in the middle somewhere
                    if (prevNode != null) {
                        System.out.println("Prev Node: (" + prevNode.printNode() + ")");
                        prevNode.next = nextNode;

                    }
                    //Removing from head of list
                    else {
                        if (nextNode != null) {
                            nextNode.prev = null;
                            System.out.println("head Node: (" + nextNode.printNode() + ")");
                            head = nextNode;
                        }
                        else{
                            head = null;
                            tail = null;
                        }

                    }
                    //Removing to end of list
                    if (nextNode != null) {
                        System.out.println("Next Node: (" + nextNode.printNode() + ")");
                        nextNode.prev = prevNode;
                    } else {
                        if (prevNode != null) {
                        prevNode.next = null;
                        System.out.println("tail Node: (" + prevNode.printNode() + ")");
                        tail = prevNode;}
                        else{
                            head = null;
                            tail = null;
                        }

                    }
                    //Since we have removed the nodes from start to end (inclusive of both) decrement the list size by 1
                    size = -removedCount;

                }


            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

    }


    /**
     * Method to remove the last element (tail node) in the Linked List
     */
    public void removeLastElement() {

        //Throw exception if trying to delete an element on an empty list!
        try {
            if (size == 0) {
                throw new NoSuchElementException("Invalid operation: Trying to remove from an empty list!!");
            } else {
                //Set the current tail node to new tmp node
                DoubleNode tmp = tail;
                //Set the new tail of the list to the previous node of current tail
                tail = tmp.prev;
                //Set the next node of new tail node to null
                tail.next = null;
                //Since we have removed the head node, decrement the list size by 1
                size--;
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

    }


    public DoubleNode firstOccurenceNode(T el) {


        DoubleNode currNode = head;
        //Advance 1 node at a time and check if the node has the element we are searching for
        // We break out of this loop either when we have found a matched node containing the element we are searching for or we have reached the end of the list
        while (currNode != null && !currNode.listElement.equals(el)) {
            currNode = currNode.next;

        }
        //We return the position of the match which was set last inside the while loop
        return currNode;
    }


    /**
     * Method to locate the first occurence of an element in the linked list
     *
     * @param el the element which is an object of type <T> to be searched for in the list
     * @return the position (1-indexed) for the first occurence of the element in the list and -1 if element not already present in list
     */
    public int firstOccurencePosition(T el) {

        //Start at the beginning of list by setting position to 1
        int pos = 1;
        DoubleNode currNode = head;
        //Advance 1 node at a time and check if the node has the element we are searching for
        // We break out of this loop either when we have found a matched node containing the element we are searching for or we have reached the end of the list
        while (currNode != null && !currNode.listElement.equals(el)) {
            currNode = currNode.next;
            pos++;
        }
        //If current node is null, means we have reached the end of the list without finding the element we were searching for
        //In this case we set the returned value to -1 to indicate no match found
        if (currNode == null) {
            pos = -1;
        }
        //We return the position of the match which was set last inside the while loop
        return pos;
    }

    /**
     * EXTRA: Method to locate the nth occurence of an element in the linked list
     *
     * @param el the element which is an object of type <T> to be searched for in the list
     * @param n  the number of the occurence (or nth match) to be searched for
     * @return int which represents the position of the nth match for the element being searched in the list and -1 if there are fewer than n-matches for element in list
     */
    public int nThOccurencePosition(T el, int n) {

        //Start at the beginning of list by setting position to 1
        int pos = 1;
        //Set number of matched occurences to 0
        int nOccur = 0;
        //Start at the head
        DoubleNode currNode = head;
        //Loop thru' the list searching for matches and continue until either:
        // we reach end of list or the required number of matches have been found!
        //Note: when we break out and we have found the right number of matches the pos variable would have advanced by 1 past the position of n-th match
        while (currNode != null && nOccur != n) {
            //Every time we find an element in one of the nodes that match what we are searching for, increment the number of occurences by 1
            if (currNode.listElement.equals(el)) {
                nOccur++;
            }
            //Traverse the list and increment the position
            currNode = currNode.next;
            pos++;
        }

        //If the current node is null and we haven't seen n-matches then return -1 otherwise if n matches found decrement position by 1 since we have already gone 1 position past the correct element
        if (currNode == null) {
            if (nOccur < n) {
                pos = -1;
            } else if (nOccur == n) {
                pos--;
            }
        }
        // if we are here then n-matches were found
        else {
            pos--;
        }

        //Return the position of the n-th match
        return pos;
    }

    /**
     * Method to insert an element before the 1st occurence of an element in a given linked list
     * Note: if element is not present, we will insert the element at the end of the list
     *
     * @param newEl    : new element (object of type <T>) to be inserted
     * @param beforeEl : the element before which we need to insert the new element
     */
    public void insertBeforeNode(T newEl, T beforeEl) {

        //Find the position of the 1st occurence of the element before which we want to insert
        int pos = firstOccurencePosition(beforeEl);

        //Create a new node with the new element to be added but don't set its next and previous links
        DoubleNode tmp = new DoubleNode(newEl, null, null);

        //if inserting at the beginning i.e. 1st node itself matches the before element, then just use the method already defined
        // to insert at the beginning of the list
        if (pos == 1) {
            addAtStart(newEl);
        }
        //if inserting at the end i.e. there are no matches for the "before" element in the current list, then just use the method already defined
        // to insert at the end or tail of the list
        else if (pos == -1) {
            addAtEnd(newEl);
        }
        // If we are here it means we are inserting not at the ends of the list but somewhere in the middle
        else {

            //Start by setting current node to head
            DoubleNode currNode = head;

            //Traverse all nodes until you hit the node just prioe to "before element"
            for (int i = 1; i < pos; i++) {
                currNode = currNode.next;
            }

            //Now switch the newly added node's previous pointer to point to the before element's prev link
            // and the next link of the previous node to the newly inserted element
            //Now we only need to set the next pointer of the new node to the next pointer of the "before element
            DoubleNode prevNode = currNode.prev;
            tmp.prev = prevNode;
            prevNode.next = tmp;
            tmp.next = currNode;

            //Since we have inserted another element in the list add 1 to list's size
            size++;
        }
    }

    /**
     * Method to insert an element AFTER the 1st occurence of an element in a given linked list
     *
     * @param newEl   : new element (object of type <T>) to be inserted
     * @param afterEl : the element AFTER which we need to insert the new element
     */
    public void insertAfterNode(T newEl, T afterEl) {

        //Find the position of the 1st occurence of the element AFTER which we want to insert
        int pos = firstOccurencePosition(afterEl);

        //Create a new node with the new element to be added but don't set its next and previous links
        DoubleNode tmp = new DoubleNode(newEl, null, null);

        //if inserting at the end i.e. there are no matches for the "after" element in the current list, then just use the method already defined
        // to insert at the end or tail of the list
        if (pos == -1) {
            addAtEnd(newEl);
        }
        // If we are here it means we are inserting not at the end of the list but somewhere in the middle
        else {

            //Start by setting current node to head
            DoubleNode currNode = head;

            //Traverse all nodes until you hit the node just prior to "AFTER" element
            for (int i = 1; i < pos; i++) {
                currNode = currNode.next;
            }

            //Now switch the newly added node's next pointer to point to the AFTER element's next link
            // and the prev link of the new node to the AFTER element
            //Now we only need to set the prev pointer of the new node to the AFTER element (which will be the current node at the end of while loop!)
            DoubleNode nextNode = currNode.next;
            nextNode.prev = tmp;
            tmp.next = nextNode;
            currNode.next = tmp;
            tmp.prev = currNode;

            //Since we have inserted another element in the list add 1 to list's size
            size++;
        }


    }

    /**
     * Method to remove 1st occurrence of an element in a list
     *
     * @param el : element to search and remove the first occirence of, if found, otherwise no effect on list if not found.
     */
    public void removeFirstOccurence(T el) {

        //If empty list nothing to remove just return w/o raising any error as specified in the question
        if (head == null) return;

        //Start at head node
        DoubleNode currNode = head;
        DoubleNode prevNode = head;

        //traverse the list until either you reach the end of the list or you find a matching element in the list
        while (currNode != null && !currNode.listElement.equals(el)) {
            prevNode = currNode;
            currNode = currNode.next;
        }
        //If current node is null it means we have searched to the end of the list and not found the element, so nothing to remove!
        if (currNode == null)
            return;

        //If we found a match in the last node (tail node) then just call the already defined method to remove the entry at the end of list!
        if (currNode.equals(tail)) {
            removeLastElement();
        }
        //If we found a match in the first node (head node) then just call the already defined method to remove the entry at the head of list!
        else if (currNode.equals(head)) {
            removeFirstElement();
        }
        // If we are here, then we found an occurence somewhere in the middle of list
        else {
            //Set the next node after the match to point to the previous node and remove matched node!
            DoubleNode nextNode = currNode.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }

        //Since we have removed an element in the list decrement the list's size
        size--;

        //Nothing to return
        return;

    }


    public void append(LinkedListDeque other){
        if(this.isEmpty()){
            head = other.head;
            tail = other.tail;
        }
        else {
            tail.next = other.getHead();
            tail = other.tail;
        }
        size += other.size;
    }


    /**
     * Method to move an element if found in the list to the head of the list
     *
     * @param el : the element to move to the beginning
     *           Note: if element is not found there is nothing to delete
     *           Move to front = delete element in its current place + add the same element at head of list!
     */
    public void moveToFront(T el) {
        //Find 1st matching occurence and remove it first using method already defined
        removeFirstOccurence(el);
        // now we just need to add the new element at head of the list using the previosly defined method
        addAtStart(el);
    }

    /**
     * Method to move an element (if found in the list) to the end of the list
     *
     * @param el : the element to move to the end
     *           Note: if element is not found there is nothing to delete
     *           Move to front = delete element in its current place + add the same element at head of list!
     */
    public void moveToEnd(T el) {
        //Find 1st matching occurence and remove it first using method already defined
        removeFirstOccurence(el);
        // now we just need to add the new element at head of the list using the previosly defined method
        addAtEnd(el);
    }


    /**
     * Print the list with a nice format with a <HEAD> for the node at beginning and a <TAIL> following the last element in the
     * list
     */
    public void printList() {
        //Set the print string to begin marker <HEAD>
        String st = new String("<Head>");

        //If the list is not empty
        if (head != null) {
            //Add the head node's string representation to string
            st += head.toString();

            //Traverse the entire list util you reach the tail and append each node's string representation to output string st
            DoubleNode currNode = head;

            while (!currNode.equals(tail)) {
                currNode = currNode.next;
                //Add a nice link marker between nodes
                st += " -> ";
                //Add string representation of node to end of output string
                st += currNode.toString();

            }
        }
        //Put a nice end of list marker
        st += "<Tail>";

        //Print the string reprsentation of the list
//        System.out.println("List Contents After Performing Operation: " + st);
        System.out.println(st);
    }

    //Main driver test method to check with integer list and all the functions defined above
    public static void main(String a[]) {

        LinkedListDeque<Integer> lld = new LinkedListDeque<Integer>();
        //Add all the actions being performed on the list and output the list contents after each step
        System.out.println("Adding 32 at start: ");
        lld.addAtStart(23);
        lld.printList();
        System.out.println("Adding 56 at start: ");
        lld.addAtStart(56);
        lld.printList();
        System.out.println("Adding 37 at start: ");
        lld.addAtStart(37);
        lld.printList();
        System.out.println("Adding 37 at start: ");
        lld.addAtStart(37);
        lld.printList();
        System.out.println("Adding 56 at start: ");
        lld.addAtStart(56);
        lld.printList();
        System.out.println("Adding 56 at end: ");
        lld.addAtEnd(56);
        lld.printList();
        System.out.println("Adding 37 at end: ");
        lld.addAtEnd(37);
        lld.printList();
        System.out.println("Adding 364 at end: ");
        lld.addAtEnd(364);
        lld.printList();
        System.out.println("Adding 37 at end: ");
        lld.addAtEnd(37);
        lld.printList();
        System.out.println("Removing 1st element: ");
        lld.removeFirstElement();
        lld.printList();
        System.out.println("Removing last element: ");
        lld.removeLastElement();
        lld.printList();
        System.out.println("Position of 1st occurence of element: 37 is : " + lld.firstOccurencePosition(37));
        System.out.println("Position of 1st occurence of element: 56 is : " + lld.firstOccurencePosition(56));
        System.out.println("Position of 1st occurence of element: 364 is : " + lld.firstOccurencePosition(364));

        System.out.println("Position of 3rd occurence of element: 37 is : " + lld.nThOccurencePosition(37, 3));
        System.out.println("Position of 2nd occurence of element: 56 is : " + lld.nThOccurencePosition(56, 2));

        System.out.println("Removing 1st occurence of 37: ");
        lld.removeFirstOccurence(37);
        lld.printList();
        System.out.println("Now the position of 1st occurence of element: 56 is : " + lld.firstOccurencePosition(56));
        System.out.println("Now the position of 1st occurence of element: 364 is : " + lld.firstOccurencePosition(364));
        System.out.println("Removing 1st occurence of 56: ");
        lld.removeFirstOccurence(56);
        lld.printList();
        System.out.println("Now the position of 1st occurence of element: 364 is : " + lld.firstOccurencePosition(364));
        System.out.println("Removing 1st occurence of 364: ");
        lld.removeFirstOccurence(364);
        lld.printList();
        System.out.println("Position of 2nd occurence of element: 37 is : " + lld.nThOccurencePosition(37, 2));
        System.out.println("Position of 3rd occurence of element: 37 is : " + lld.nThOccurencePosition(37, 3));

        System.out.println("Now the position of 1st occurence of element: 364 (should be -1) is: " + lld.firstOccurencePosition(364));

        System.out.println("Adding back 364 at end: ");
        lld.addAtEnd(364);
        lld.printList();
        System.out.println("Adding 52 before 1st occurence of 37: ");
        lld.insertBeforeNode(52, 37);
        lld.printList();
        System.out.println("Adding 52 after 1st occurence of 37: ");
        lld.insertAfterNode(52, 37);
        lld.printList();
        System.out.println("Adding 52 before 1st occurence of 364: ");
        lld.insertBeforeNode(52, 364);
        lld.printList();
        System.out.println("Adding 25 after 1st occurence of 36 (not in list, so should add at end): ");
        lld.insertAfterNode(25, 36);
        lld.printList();
        System.out.println("Adding 75 before 1st occurence of 36 (not in list, so should add at end): ");
        lld.insertBeforeNode(75, 36);
        lld.printList();
        System.out.println("Adding 75 before 1st occurence of 56: ");
        lld.insertBeforeNode(75, 56);
        lld.printList();
        System.out.println("Adding 75 after 1st occurence of 75: ");
        lld.insertAfterNode(75, 75);
        lld.printList();
        DoubleNode st = lld.firstOccurenceNode(23);
//        System.out.println("Start Node: " + st.printNode());
        DoubleNode e = lld.firstOccurenceNode(56);
//        System.out.println("End Node: " + e.printNode());
        System.out.println("Removing segment from startnode:(" + st.printNode() + ") to endnode: (" + e.printNode() + ")");
        lld.removeSubList(st, e);
        lld.printList();
        st = lld.firstOccurenceNode(52);
//        System.out.println("Start Node: " + st.printNode());
        e = lld.firstOccurenceNode(364);
//        System.out.println("End Node: " + e.printNode());
        System.out.println("Removing segment from startnode:(" + st.printNode() + ") to endnode: (" + e.printNode() + ")");
        lld.removeSubList(st, e);
        lld.printList();

        st = lld.firstOccurenceNode(25);
//        System.out.println("Start Node: " + st.printNode());
        e = lld.firstOccurenceNode(75);
//        System.out.println("End Node: " + e.printNode());
        System.out.println("Removing segment from startnode:(" + st.printNode() + ") to endnode: (" + e.printNode() + ")");
        lld.removeSubList(st, e);
        lld.printList();


    }

}

