/**
 * Class DoubleNode represents all the information needed for each node in the doubly linked list or deque
 * The private variables are node's content (object from generic Comparable<T>) and pointers to the next and previous node in the linked list
 * This class was developed for Assignment #3 on Linked Lists and is modified to hold Comparable generic type and to implement the compareTo method
 * that is overriden
 * @author: Vishak Srikanth
 * @version: 10/18/2021
 */
import java.util.Objects;
public class DoubleNode<T extends Comparable<T>> implements Comparable

{

    Comparable listElement;
    //next node in linked list
    DoubleNode next;
    //previous node in linked list
    DoubleNode prev;



    /**
     * Constructor to create a DoubleNode object within linked list, needs the element for the new node to be constructed
     * and the pointers to the next and previous DoubleNode objects in linked list
     *  @param el   : holds the contents of the new node in linked list (or the content of the element in the list)
     * @param n : pointer to the next (or subsequent) DoubleNode object in the linked list
     * @param p : pointer to the previous (or prior) DoubleNode object in the linked list
     */
    public DoubleNode(Comparable el, DoubleNode n, DoubleNode p) {
        listElement = el;
        next = n;
        prev = p;
    }

    /**
     * Default constructor initializes to an empty node with no element contained
     */
    public DoubleNode() {
        listElement = null;
        next = null;
        prev = null;
    }

    /**
     * Method to check the equality of 2 DoubleNode objects in the Linked list
     * @param o the DoubleNode being compared to the current object
     * @return true if the DoubleNode is the same (i.e. has the same content and also the same previous and next DoubleNodes)
     */
    public boolean equals(DoubleNode o) {
        if (this == o) return true;
        DoubleNode that = (DoubleNode) o;
        return listElement.equals(that.listElement) && Objects.equals(next, that.next) && Objects.equals(prev, that.prev);
    }

    /**
     * Returns the object of type <T> stored inside the DoubleNode
     * @return String representation of the object stored within the node
     */
    @Override
    public String toString() {
        return listElement.toString();
    }

    /**
     * Return a nice string representation of the node showing its previous element, the current element, and the next element in the Linked List
     * @return a "stringified" representation of the node with its next ane previous nodes and the current element listed
     */
    public String printNode() {
        //Create a string with the previous node + current node + next node printed from left -> right
        return "prevNode: " + prev + " element: " + this + " nextNode: " + next;
    }


    /** Comparing 2 DoubleNodes and ordering them based on their elements natural order
     * @param obj another DoubleNode object (need to use generic Object class and casting to right class inside method)
     * @return -1 if this < obj and 1 if this > obj and 0 otherwise
     * */
    @Override
    public int compareTo(Object obj) {
        Comparable thisEl = this.listElement;
        Comparable otherEl = ((DoubleNode) obj).listElement;
        if(thisEl.compareTo(otherEl) < 0){
            return -1;
        }
        else if(thisEl.compareTo(otherEl) == 0){
            return 0;
        }
        else return 1;
    }

    /** Get node content
     * @return Comparabale object which represents the content type or element stored within node
     */
    public Comparable getListElement() {
        return listElement;
    }
}

