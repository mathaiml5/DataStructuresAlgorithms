/*  The main 2-3 tree Node class that is used to construct 2-3 Tree as outlined in Section 3.3. of Sedgewick et. al Algorithms 4th Edition
 *  1. Simplified 2-3 Node class directly uses keys as the node contents or value and does not have a key-value pairs stored in node
 *     Node class was made its own separate class for reuse and all the methods which directly assigned member variable values now are
 *     converted to using getters and setters.
 *  2. This node class is the parent of the 4-node class as outlined in Section 3.3. of Sedgewick et. al Algorithms 4th Edition which
 *     adds additional center key and a 2nd center child pointer
 *  3. has the key add method to add new item keys into node and fixup the node pointers
 *  4. The program prints the output for each step of adding an item to the tree. There is also vertical dendrogram style visualization
 *     of the final RB tree at each step
 *  @author: Vishak Srikanth
 *  @version: 11/22/2021
 */
import java.util.ArrayList;
import java.util.Collections;

// 2-3 tree node implementation
public class TwoThreeNode<Key extends Comparable, Value extends Comparable> {

    private Key lkey;        // The node's left key
    private Key rkey;        // The node's right key
    private Value lval;
    private Value rval;
    private TwoThreeNode<Key, Value> left;   // Pointer to left child
    private TwoThreeNode<Key, Value> center; // Pointer to middle child
    private TwoThreeNode<Key, Value> right;  // Pointer to right child
    private TwoThreeNode<Key, Value> parent;

    //Currently unused but can be used for future if data needs to be separate from keys
    private ArrayList<Value> data = new ArrayList<Value>();

    /**
     * Default constructor initializes an empty 2-3 node
     */
    public TwoThreeNode() {
        center = left = right = parent = null;
    }


    /** Constructor for creating a 2-3 Node
     * @param lk left key
     * @param rk right key
     * @param leftNode left node pointer
     * @param centernode center node pointer
     * @param rightNode right node pointer
     */
    public TwoThreeNode(Key lk, Value lv,  Key rk, Value rv,
                        TwoThreeNode<Key, Value> leftNode, TwoThreeNode<Key, Value> centernode,
                        TwoThreeNode<Key, Value> rightNode) {
        lkey = lk;
        rkey = rk;
        lval = lv;
        rval = rv;
        if (lk != null) data.add(lv);
        if (rk != null) data.add(rv);
        left = leftNode;
        center = centernode;
        right = rightNode;
    }


    /** Check if given 2-3 node is tree node
     * @return true if leaf node and false if internal or branch node
     */
    public boolean isLeaf() {
        return (left == null && center == null && right == null);
    }


    /** Check if a given node is a 2-node
     * @return true if 2-node and false if not
     */
    public boolean is2NodeType() {
        return (lkey() != null && rkey() == null);
    }

    /** Check if a given node is a 2-node
     * @return true if 3-node and false if not
     */
    public boolean is3NodeType() {
        //If node has both left and right key then it is a 3-node
        return (rkey() != null && lkey() != null);
    }


    /** gets data conetent within nodes (currently unused)
     * @return the data elements stored in node
     */
    public int getDataSize() {
        return data.size();
    }

    /** Check if given 2-3 node is leaf node or internal node
     * @return true if internal (non-leaf) or branch node and false otherwise
     */
    public boolean isBranch() {
        if (isLeaf()) {
            return false;
        } else {
            return (is2NodeType() || is3NodeType());
        }
    }


    /** Simplified 2-3 node directly uses the key as the data item in each node
     * @param item Key to be inserted into data field
     */
//    public void insertIntoNode(Key item) {
//        if (data.size() > 2) {
//            System.out.println("Error trying to insert too many values");
//        }
//        data.add(item);
//        Collections.sort(data);
//    }

    /* Getters and setters for all the member variables
        all getter methods are simply names without "get" prefix
        while all setters are namesd with a "set" prefix
     */
    public TwoThreeNode<Key, Value> lchild() {
        return left;
    }

    public TwoThreeNode<Key, Value> rchild() {
        return right;
    }

    public TwoThreeNode<Key, Value> cchild() {
        return center;
    }

    public Key lkey() {
        return lkey;
    }  // Left key

    public Value lval() {
        return lval;
    }  // Left value

    public Key rkey() {
        return rkey;
    }  // Right key

    public Value rval() {
        return rval;
    }  // Right value

    public void setLeft(Key k, Value v) {
        lkey = k;
        lval = v;
    }

    public void setRight(Key k, Value v) {
        rkey = k;
        rval = v;
    }

    public void setLeftChild(TwoThreeNode<Key, Value> it) {
        left = it;
    }

    public void setCenterChild(TwoThreeNode<Key, Value> it) {
        center = it;
    }

    public void setRightChild(TwoThreeNode<Key, Value> it) {
        right = it;
    }

    public TwoThreeNode<Key, Value> getParent() {
        return parent;
    }

    public void setParent(TwoThreeNode<Key, Value> parent) {
        this.parent = parent;
    }

    //Section 3.3, Problems 3.39-3.41 of Sedgewick et. al Algorithms 4th Edition
    public Key getSmallestVal() {
        ArrayList<Key> myData = (ArrayList<Key>) data.clone();
        Collections.sort(myData);
        return myData.get(0);

    }

    public Key getLargestVal() {
        ArrayList<Key> myData = (ArrayList<Key>) data.clone();
        Collections.sort(myData);
        if (myData.size() > 2) {

            return myData.get(2);
        } else if (myData.size() == 2) {
            return myData.get(1);
        } else return null;
    }

    public Key getMiddleVal() {
        ArrayList<Key> myData = (ArrayList<Key>) data.clone();
        Collections.sort(myData);
        if (myData.size() == 3) {

            return myData.get(1);
        } else return null;
    }

    // Add a new key/value pair to the node. There might be a subtree
    // associated with the record being added. This information comes
    // in the form of a 2-3 tree node with one key and a (possibly null)
    // subtree through the center pointer field.
    public TwoThreeNode<Key, Value> add(TwoThreeNode<Key, Value> newItem) {
        if (rkey == null) { // Only one key, then it is a 2-node
            //if key of new item is less than left key of the current node
            //then move the current left key over to right and add the new item to left key
            // also switch the node pointers to those of the new node's left and center child
            if (lkey.compareTo(newItem.lkey()) < 0) {
                rkey = newItem.lkey();
                rval = newItem.lval();
                center = newItem.lchild();
                right = newItem.cchild();
            //if the key is larger than current node's left key just add it to the right slot and
            //make the node's center and right child point to the added item's center child and right child
            } else {
                rkey = lkey;
                rval = lval;
                right = center;
                lkey = newItem.lkey();
                lval = newItem.lval();
                center = newItem.cchild();
            }
            return this;

        } else if (lkey.compareTo(newItem.lkey()) >= 0) { // Add left of existing node
            //Create a new 2-3 Node first
            TwoThreeNode<Key, Value> N1 = new TwoThreeNode<Key, Value>(lkey, lval, null, null, newItem, this, null);

            newItem.setLeftChild(left);
            left = center;
            center = right;
            right = null;
            lkey = rkey;
            lval = rval;
            rkey = null;
            rval = null;
            return N1;
        } else if (rkey.compareTo(newItem.lkey()) >= 0) { // Add center area of existing node
            newItem.setCenterChild(new TwoThreeNode<Key, Value>(rkey, rval, null, null, newItem.cchild(), right, null));
            newItem.setLeftChild(this);
            rkey = null;
            rval = null;
            right = null;
            return newItem;
        } else { // Add right of existing node
            TwoThreeNode<Key, Value> N1 = new TwoThreeNode<Key, Value>(rkey, rval, null, null, this, newItem, null);
            newItem.setLeftChild(right);
            right = null;
            rkey = null;
            rval = null;
            return N1;
        }
    }
}


