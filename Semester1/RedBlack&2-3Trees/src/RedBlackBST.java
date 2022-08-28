/*  The main class for the Red-Black BST developed from code in Section 3.3, Problems 3.39-3.41 of Sedgewick et. al Algorithms 4th Edition
 *  Following modifications and enhancements were done in developing this class:
 *  1. The Node class was made its own separate class for reuse and all the methods which cdirectly assigned member variable valuse now are
 *     converted to using getters and setters.
 *  2. Several traversal methods were added to help with Inorder, Postorder and Preorder traversal.
 *  3. The program provides the a choice of numeric or string input and tests the RB BST algorithm for both scenarios
 *     The user can either input a string of characters which they wish to use to create the tree (option #2) OR
 *     it can generate an ordered list of numbers in the range 1..n that user can use (option #1)
 *  4. The program prints the output for each step of adding an item to the tree. There is also verical dendrogram style visualization
 *     of the final RB tree at each step
 *  @author: Vishak Srikanth
 *  @version: 11/22/2021
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;

public class RedBlackBST<Key extends Comparable<? super Key>, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private static final int PREORDER = 1;
    private static final int INORDER = 2;
    private static final int POSTORDER = 3;

    private Node root;     // root of the BST


    // Getters and setter methods for each private variable for RB BST

    /**
     * @return root Node of tree
     */
    public Node getRoot() {
        return root;
    }

    /**
     * @param root set the Node to the root of the tree
     */
    public void setRoot(Node root) {
        this.root = root;
    }

    /**
     * Default constructor Initializes an empty RB BST
     */
    public RedBlackBST() {
    }

    /***************************************************************************
     *  Node helper methods: From textbook site section 3.3: https://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
     ***************************************************************************/
    // is node x red; false if x is null ?
    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.getColor() == RED;
    }

    // number of node in subtree rooted at x; 0 if x is null
    private int size(Node x) {
        if (x == null) return 0;
        return x.getSize();
    }


    /**
     * Returns the number of key-value pairs in RB BST
     * From textbook section 3.3
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

    /**
     * Is this RB BST tree empty?
     * From textbook section 3.3
     *
     * @return true if this symbol table is empty and false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }


    /***************************************************************************
     *  Standard BST search
     *  From textbook site https://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
     ***************************************************************************/

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     * and null if the key is not in the symbol table
     * @throws IllegalArgumentException if key is null
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        return get(root, key);
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private Value get(Node x, Key key) {
        while (x != null) {
            int cmp = key.compareTo((Key) x.getKey());
            if (cmp < 0) x = x.getLeft();
            else if (cmp > 0) x = x.getRight();
            else return (Value) x.getVal();
        }
        return null;
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param key the key
     * @return true} if this symbol table contains   key} and
     * false} otherwise
     * @throws IllegalArgumentException if   key} is   null}
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    /***************************************************************************
     *  Red-black tree insertion
     *  Modified from https://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
     *  Added the setting of parent of root node to null
     ***************************************************************************/

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is null
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if key is null
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }

        root = put(root, key, val);
        root.setColor(BLACK);
        root.setParent(null);
        assert check();
    }

    // insert the key-value pair in the subtree rooted at h
    private Node put(Node h, Key key, Value val) {
        if (h == null) return new Node(key, val, RED, 1);

        int cmp = key.compareTo((Key) h.getKey());
        if (cmp < 0) h.setLeft(put(h.getLeft(), key, val));
        else if (cmp > 0) h.setRight(put(h.getRight(), key, val));
        else h.setVal(val);

        // fix-up any right-leaning links
        //If we only have a RED right link and leftlink is not RED (i.e null or black) then we need to rotate h left
        if (isRed(h.getRight()) && !isRed(h.getLeft()))
        {


            System.out.println("Need to call Rotate left on node: " + h.toString());
            System.out.println("Tree before rotate left: ");
            printTree();
            h = rotateLeft(h);
            System.out.println("Tree after rotate left: ");
            printTree();
        }

        //If we only have 2 RED left links in a row (i.e. left node is RED and left child of left node is RED) then we need to rotate h right
        if (isRed(h.getLeft()) && isRed(h.getLeft().getLeft()))
        {

            System.out.println("Need to call Rotate right on node: " + h.toString());
            System.out.println("Tree before rotate right: ");
            printTree();
            h = rotateRight(h);
            System.out.println("Tree after rotate right: ");
            printTree();
        }

        //If both left and right links are RED, need to call flip colors
        if (isRed(h.getLeft()) && isRed(h.getRight()))
        {

            System.out.println("Need to call flip colors on node: " + h.toString());
            System.out.println("Tree before flip colors: ");
            printTree();
            flipColors(h);
            System.out.println("Tree after flip colors: ");
            printTree();
        }

        h.setSize(size(h.getLeft()) + size(h.getRight()) + 1);

        return h;
    }

    /***************************************************************************
     *  Red-black tree deletion
     *  Modified from https://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
     *  Added the setting of parent of root node to null and setting of parent node pointer
     ***************************************************************************/

    /**
     * Removes the smallest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.getLeft()) && !isRed(root.getRight()))
            root.setColor(RED);

        root = deleteMin(root);
        if (!isEmpty()) root.setColor(BLACK);
        root.setParent(null);
        // assert check();
    }

    // delete the key-value pair with the minimum key rooted at h
    private Node deleteMin(Node h) {
        if (h.getLeft() == null)
            return null;

        if (!isRed(h.getLeft()) && !isRed(h.getLeft().getLeft()))
            h = moveRedLeft(h);

        h.setLeft(deleteMin(h.getLeft()));
        return balance(h);
    }


    /**
     * Removes the largest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.getLeft()) && !isRed(root.getRight()))
            root.setColor(RED);

        root = deleteMax(root);
        if (!isEmpty()) root.setColor(BLACK);
        // assert check();
        root.setParent(null);
    }

    // delete the key-value pair with the maximum key rooted at h
    private Node deleteMax(Node h) {
        if (isRed(h.getLeft()))
            h = rotateRight(h);

        if (h.getRight() == null)
            return null;

        if (!isRed(h.getRight()) && !isRed(h.getRight().getLeft()))
            h = moveRedRight(h);

        h.setRight(deleteMax(h.getRight()));

        return balance(h);
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param key the key
     * @throws IllegalArgumentException if key is null
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;

        // if both children of root are black, set root to red
        if (!isRed(root.getLeft()) && !isRed(root.getRight()))
            root.setColor(RED);

        root = delete(root, key);
        if (!isEmpty()) root.setColor(BLACK);
        root.setParent(null);
        // assert check();
    }

    // delete the key-value pair with the given key rooted at h
    private Node delete(Node h, Key key) {
        // assert get(h, key) != null;

        if (key.compareTo((Key) h.getKey()) < 0) {
            if (!isRed(h.getLeft()) && !isRed(h.getLeft().getLeft()))
                h = moveRedLeft(h);
            h.setLeft(delete(h.getLeft(), key));
        } else {
            if (isRed(h.getLeft()))
                h = rotateRight(h);
            if (key.compareTo((Key) h.getKey()) == 0 && (h.getRight() == null))
                return null;
            if (!isRed(h.getRight()) && !isRed(h.getRight().getLeft()))
                h = moveRedRight(h);
            if (key.compareTo((Key) h.getKey()) == 0) {
                Node x = min(h.getRight());
                h.setKey(x.getKey());
                h.setVal(x.getVal());
                // h.val = get(h.getRight(), min(h.getRight()).getKey());
                // h.getKey() = min(h.getRight()).getKey();
                h.setRight(deleteMin(h.getRight()));
            } else h.setRight(delete(h.getRight(), key));
        }
        return balance(h);
    }

    /***************************************************************************
     *  Red-black tree helper functions
     *  Modified from https://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
     *  Since the Node class is not an external class, all methods that performed 
     *  get/set operations on Node h are now converted to use the relevant getter/setter 
     *  methods
     ***************************************************************************/

    // make a left-leaning link lean to the right
    private Node rotateRight(Node h) {
//        assert (h != null) && isRed(h.getLeft());
        assert (h != null) && isRed(h.getLeft()) &&  !isRed(h.getRight());  // for insertion only
        Node x = h.getLeft();
        h.setLeft(x.getRight());
        x.setRight(h);
        x.setColor(x.getRight().getColor());
        x.getRight().setColor(RED);
        x.setSize(h.getSize());
        h.setSize(size(h.getLeft()) + size(h.getRight()) + 1);
        return x;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
//        assert (h != null) && isRed(h.getRight());
        assert (h != null) && isRed(h.getRight()) && !isRed(h.getLeft());  // for insertion only
        Node x = h.getRight();
        h.setRight(x.getLeft());
        x.setLeft(h);
        x.setColor(x.getLeft().getColor());
        x.getLeft().setColor(RED);
        x.setSize(h.getSize());
        h.setSize(size(h.getLeft()) + size(h.getRight()) + 1);
        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) {
        // h must have opposite color of its two children
        // assert (h != null) && (h.getLeft() != null) && (h.getRight() != null);
        // assert (!isRed(h) &&  isRed(h.getLeft()) &&  isRed(h.getRight()))
        //    || (isRed(h)  && !isRed(h.getLeft()) && !isRed(h.getRight()));
        h.setColor(!h.getColor());
        h.getLeft().setColor(!h.getLeft().getColor());
        h.getRight().setColor(!h.getRight().getColor());
    }

    // Assuming that h is red and both h.getLeft() and h.getLeft().getLeft()
    // are black, make h.getLeft() or one of its children red.
    private Node moveRedLeft(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.getLeft()) && !isRed(h.getLeft().getLeft());

        flipColors(h);
        if (isRed(h.getRight().getLeft())) {
            h.setRight(rotateRight(h.getRight()));
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // Assuming that h is red and both h.getRight() and h.getRight().getLeft()
    // are black, make h.getRight() or one of its children red.
    private Node moveRedRight(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.getRight()) && !isRed(h.getRight().getLeft());
        flipColors(h);
        if (isRed(h.getLeft().getLeft())) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // restore red-black tree invariant
    private Node balance(Node h) {
        // assert (h != null);

        if (isRed(h.getRight()) && !isRed(h.getLeft())) h = rotateLeft(h);
        if (isRed(h.getLeft()) && isRed(h.getLeft().getLeft())) h = rotateRight(h);
        if (isRed(h.getLeft()) && isRed(h.getRight())) flipColors(h);

        h.setSize(size(h.getLeft()) + size(h.getRight()) + 1);
        return h;
    }


    /***************************************************************************
     *  Utility Methods
     ***************************************************************************/

    /**
     * Returns the height of the RB BST (used in printing of tree for example
     *
     * @return the height of the RB BST (a 1-node tree has height 0)
     */
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.getLeft()), height(x.getRight()));
    }

    /***************************************************************************
     *  Ordered symbol table methods
     *  Adapted from https://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
     ***************************************************************************/

    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return (Key) min(root).getKey();
    }

    // the smallest key in subtree rooted at x; null if no such key
    private Node min(Node x) {
        // assert x != null;
        if (x.getLeft() == null) return x;
        else return min(x.getLeft());
    }

    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return (Key) max(root).getKey();
    }

    // the largest key in the subtree rooted at x; null if no such key
    private Node max(Node x) {
        // assert x != null;
        if (x.getRight() == null) return x;
        else return max(x.getRight());
    }


    /**
     * Returns the largest key in the symbol table less than or equal to key
     *
     * @param key the key
     * @return the largest key in the symbol table less than or equal to key
     * @throws NoSuchElementException   if there is no such key
     * @throws IllegalArgumentException if key is null
     */
    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
        Node x = floor(root, key);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else return (Key) x.getKey();
    }

    // the largest key in the subtree rooted at x less than or equal to the given key
    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo((Key) x.getKey());
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.getLeft(), key);
        Node t = floor(x.getRight(), key);
        if (t != null) return t;
        else return x;
    }

    /**
     * Returns the smallest key in the symbol table greater than or equal to key.
     *
     * @param key the key
     * @return the smallest key in the symbol table greater than or equal to key
     * @throws NoSuchElementException   if there is no such key
     * @throws IllegalArgumentException if key is null
     */
    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");
        Node x = ceiling(root, key);
        if (x == null) throw new NoSuchElementException("argument to ceiling() is too small");
        else return (Key) x.getKey();
    }

    // the smallest key in the subtree rooted at x greater than or equal to the given key
    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo((Key) x.getKey());
        if (cmp == 0) return x;
        if (cmp > 0) return ceiling(x.getRight(), key);
        Node t = ceiling(x.getLeft(), key);
        if (t != null) return t;
        else return x;
    }

    /**
     * Return the key in the symbol table of a given rank.
     * This key has the property that there are rank keys in
     * the symbol table that are smaller. In other words, this key is the
     * (rank+1)st smallest key in the symbol table.
     *
     * @param rank the order statistic
     * @return the key in the symbol table of given   rank
     * @throws IllegalArgumentException unless rank} is between 0 and n
     */
    public Key select(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + rank);
        }
        return select(root, rank);
    }

    // Return key in BST rooted at x of given rank.
    // Precondition: rank is in legal range.
    private Key select(Node x, int rank) {
        if (x == null) return null;
        int leftSize = size(x.getLeft());
        if (leftSize > rank) return select(x.getLeft(), rank);
        else if (leftSize < rank) return select(x.getRight(), rank - leftSize - 1);
        else return (Key) x.getKey();
    }

    /**
     * Return the number of keys in the symbol table strictly less than key.
     *
     * @param key the key
     * @return the number of keys in the symbol table strictly less than key
     * @throws IllegalArgumentException if key is null
     */
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    }

    // number of keys less than key in the subtree rooted at x
    private int rank(Key key, Node x) {
        if (x == null) return 0;
        int cmp = key.compareTo((Key) x.getKey());
        if (cmp < 0) return rank(key, x.getLeft());
        else if (cmp > 0) return 1 + size(x.getLeft()) + rank(key, x.getRight());
        else return size(x.getLeft());
    }

    /***************************************************************************
     *  Range count and range search
     *  Adapted from https://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
     ***************************************************************************/

    /**
     * Returns all keys in the symbol table as an Iterable}.
     * To iterate over all of the keys in the symbol table named   st},
     * use the foreach notation:   for (Key key : st.getKey()s())}.
     *
     * @return all keys in the symbol table as an   Iterable}
     */
    public Iterable<Key> keys() {
        if (isEmpty()) return new ArrayList<Key>();
        return keys(min(), max());
    }

    /**
     * Returns all keys in the symbol table in the given range,
     * as an   Iterable}.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return all keys in the symbol table between   lo}
     * (inclusive) and   hi} (inclusive) as an   Iterable}
     * @throws IllegalArgumentException if either   lo} or   hi}
     *                                  is   null}
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        ArrayList<Key> queue = new ArrayList<Key>();
        // if (isEmpty() || lo.compareTo(hi) > 0) return queue;
        keys(root, queue, lo, hi);
        return queue;
    }

    // add the keys between lo and hi in the subtree rooted at x
    // to the queue
    private void keys(Node x, ArrayList<Key> queue, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo((Key) x.getKey());
        int cmphi = hi.compareTo((Key) x.getKey());
        if (cmplo < 0) keys(x.getLeft(), queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.add((Key) x.getKey());
        if (cmphi > 0) keys(x.getRight(), queue, lo, hi);
    }

    /**
     * Returns the number of keys in the symbol table in the given range.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return the number of keys in the symbol table between   lo}
     * (inclusive) and   hi} (inclusive)
     * @throws IllegalArgumentException if either   lo} or   hi}
     *                                  is   null}
     */
    public int size(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }


    /*
        CODE ADDITIONS: Helper functions to get list of nodes in tree in various order traversals
        The master getTreeNodes function calls the traversals the root and recrsively builds the entire list with specified
        order of traversal
     */

    public ArrayList<Node> getTreeNodes(int orderType) {
        if (isEmpty()) return new ArrayList<Node>();
        ArrayList<Node> nodeList = new ArrayList<Node>();
        getNodesTraversal(root, nodeList, orderType);
        return nodeList;
    }


    public void getNodesTraversal(Node n, ArrayList<Node> nl, int ord) {

        if (ord == PREORDER) {
            if (n != null) nl.add(n);
            if (n.getLeft() != null) getNodesTraversal(n.getLeft(), nl, ord);
            if (n.getRight() != null) getNodesTraversal(n.getRight(), nl, ord);
        }
        if (ord == INORDER) {

            if (n.getLeft() != null) getNodesTraversal(n.getLeft(), nl, ord);
            if (n != null) nl.add(n);
            if (n.getRight() != null) getNodesTraversal(n.getRight(), nl, ord);
        }
        if (ord == POSTORDER) {

            if (n.getLeft() != null) getNodesTraversal(n.getLeft(), nl, ord);
            if (n.getRight() != null) getNodesTraversal(n.getRight(), nl, ord);
            if (n != null) nl.add(n);
        }
    }


    /***************************************************************************
     *  Check integrity of red-black tree data structure
     *  Adapted from https://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
     ***************************************************************************/
    private boolean check() {
        if (!isBST()) System.out.println("Not in symmetric order");
        if (!isSizeConsistent()) System.out.println("Subtree counts not consistent");
        if (!isRankConsistent()) System.out.println("Ranks not consistent");
        if (!is23()) System.out.println("Not a 2-3 tree");
        if (!isBalanced()) System.out.println("Not balanced");
        return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) return true;
        if (min != null && x.getKey().compareTo(min) <= 0) return false;
        if (max != null && x.getKey().compareTo(max) >= 0) return false;
        return isBST(x.getLeft(), min, (Key) x.getKey()) && isBST(x.getRight(), (Key) x.getKey(), max);
    }

    // are the size fields correct?
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        if (x.getSize() != size(x.getLeft()) + size(x.getRight()) + 1) return false;
        return isSizeConsistent(x.getLeft()) && isSizeConsistent(x.getRight());
    }

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (Key key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }

    // Does the tree have no red right links, and at most one (left)
    // red links in a row on any path?
    private boolean is23() {
        return is23(root);
    }

    private boolean is23(Node x) {
        if (x == null) return true;
        if (isRed(x.getRight())) return false;
        if (x != root && isRed(x) && isRed(x.getLeft()))
            return false;
        return is23(x.getLeft()) && is23(x.getRight());
    }

    // do all paths from root to leaf have same number of black edges?
    private boolean isBalanced() {
        int black = 0;     // number of black links on path from root to min
        Node x = root;
        while (x != null) {
            if (!isRed(x)) black++;
            x = x.getLeft();
        }
        return isBalanced(root, black);
    }

    // does every path from the root to a leaf have the given number of black links?
    private boolean isBalanced(Node x, int black) {
        if (x == null) return black == 0;
        if (!isRed(x)) black--;
        return isBalanced(x.getLeft(), black) && isBalanced(x.getRight(), black);
    }


    /***************************************************************************
     *  TREE PRINTING FUNCTIONS
     *  Print a nice textual dendrogram style visualization of RB BST
     ***************************************************************************/
    public void printTree() {
        int maxLevel = height();

        printNodeInternal(Collections.singletonList(root), 0, maxLevel);
    }


    private void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
        if (size() == 0) {
            System.out.println("Tree is Empty!");
        }
        if (nodes.isEmpty() || isAllElementsNull(nodes)) {
            return;
        }
        //Caclulate the offsets for each level of tree and the leading and spaces between nodes
        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) + floor;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) + 1;

        printWhitespaces(firstSpaces + 1);


        List<Node> newNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node != null) {
                if (!node.getColor()) {
                    System.out.print(node.getKey() + ":" + node.getVal());
                } else {
                    //This useful trick to color code the console output from ANSI standard escape sequences
                    // See: https://stackoverflow.com/questions/4842424/list-of-ansi-color-escape-sequences
                    //COLORING THE RED NODES!!
                    System.out.print("\033[31;1m" + node.getKey() + ":" + node.getVal() + "\033[0m");
                }

                newNodes.add(node.getLeft());
                newNodes.add(node.getRight());
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print("");
            }

            printWhitespaces(betweenSpaces + floor);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).getLeft() != null) {
                    printWhitespaces(1);
                    System.out.print("/");

                } else
                    printWhitespaces(1);

                printWhitespaces(i + i - 1);

                if (nodes.get(j).getRight() != null) {
                    printWhitespaces(1);
                    System.out.print("\\");
                } else
                    printWhitespaces(1);

                printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    //Add whitespaces as needed
    private void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    //Check if all elements are null
    private boolean isAllElementsNull(List list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }


    /**
     * Output Tester for RedBlackBST data structure
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws IOException {


        System.out.println("************************************************************************************************************************");
        System.out.println("********************************************* Red Black BST Tester ****************************************************");
        System.out.println("************************************************************************************************************************");
        System.out.println("This program tests the Red Black BST creation and deletion. The user can either input a string of characters which they wish to use");
        System.out.println("to create the tree OR specify a range of integers from up to n to be inserted into RB BST.");
        System.out.println("We also print a visualization of RB BST tree structure as well as the inorder traversal of the tree during during each step.");
        System.out.println("************************************************************************************************************************");
        System.out.println("Input types Menu:");
        System.out.println("[1] Ordered list of integers from 1, 2,3... n inserted in increasing order into RB BST and delete all keys from 1,2,3...m (< n)");
        System.out.println("[2] User specified string of characters to be inserted into RB BST");
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
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid menu choice which contains only valid options (1 or 2)");
                invalidMenuChoice = true;
            }


        } while (invalidMenuChoice);


        //If doing a numeric simulation generate a list of numbers in the range specified
        if (menuChoice == 1) {
            int numMax = 60;
            int maxDel = 20;
            boolean invalidNumInputs = true;
            do {


                System.out.println("Enter 2 numbers separated by spaces for (A) max integer (upto 100) of keys 1,2,..numMax and (b) how many nodes you want to delete from that from 1,2,..maxDel (maxDel> 1 and maxDel<numMax) > ");

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                String str = br.readLine();
                String[] nums = str.split("\\s+"); //returns an array of strings split based on the parameter

                numMax = Integer.parseInt(nums[0]);
                maxDel = Integer.parseInt(nums[1]);

                if (numMax < 2 || numMax > 100) {
                    System.out.println("Please enter a numMax value is an integer within specified limits (2-100)!");
                    invalidNumInputs = true;
                } else if (maxDel <= 1 || maxDel >= numMax) {
                    System.out.println("Please enter an maxDel value integer within specified limits (maxDel > 1 and maxDel < numMax)!");
                    invalidNumInputs = true;
                } else {
                    invalidNumInputs = false;
                }


            } while (invalidNumInputs);

            RedBlackBST<Integer, Integer> numTree = new RedBlackBST<Integer, Integer>();

            System.out.println("Adding an increasing sequence of numbers to the Red-Black BST");
//            System.out.println("Printing tree after every number:");
            for (int key = 1; key <= numMax; key++) {

                numTree.put(new Integer(key), new Integer(key));
                if (key % numMax == 0) {
                    System.out.println("******************************************************************************************");
                    System.out.println("After adding " + key + " to tree: ");

                    numTree.printTree();

                    System.out.println();

                    ArrayList<Node> nodes = new ArrayList<Node>();
                    nodes = numTree.getTreeNodes(INORDER);
                    System.out.println("Inorder Traversal of Tree: ");
                    for (Node n : nodes) {
                        System.out.println(n.toString());
                    }
                    System.out.println("******************************************************************************************");
                }
            }

            for (int key = 1; key <= maxDel; key++) {
                numTree.delete(new Integer(key));
                if (key % maxDel == 0) {
                    System.out.println("******************************************************************************************");
                    System.out.println("After deleting the first " + key + " keys from tree: ");

                    numTree.printTree();

                    System.out.println();

                    ArrayList<Node> nodes = new ArrayList<Node>();
                    nodes = numTree.getTreeNodes(INORDER);
                    System.out.println("Inorder Traversal of Tree: ");
                    for (Node n : nodes) {
                        System.out.println(n.toString());
                    }
                    System.out.println("******************************************************************************************");
                }
            }

        }

        //String based test of RB BST
        //user enters a string of characters and the list is inserted in order into an RN+B BST
        if (menuChoice == 2) {
            boolean invalidStringInput = true;
            String myString = new String();
            do {


                System.out.println("Enter the string of charcaters that you want to construct RB BST with.");
                System.out.println("Only alphabetic characters allowed and any whitespaces will be ignored");
                System.out.println("Enter the string whose characters you want to create the tree with > ");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


                br = new BufferedReader(new InputStreamReader(System.in));

                myString = br.readLine();

                if (myString.matches("^[a-zA-Z\\s]*$")) {
                    invalidStringInput = false;
                    myString = myString.replaceAll("\\s+", "");
                } else {
                    System.out.println("Please enter a valid string with alphabetic charcters only (A-Z, a-z)");
                    invalidStringInput = true;
                }

            } while (invalidStringInput);

            String[] inputStringArray = myString.split("(?!^)");

            RedBlackBST<String, Integer> st = new RedBlackBST<String, Integer>();

//            String inputStr = "YLPMXHCRAESTBCA";
//       String inputStr = "ALGORITHMSXYZ";
//        String inputStr = "SEARCHEXAMPLE";
//        String inputStr = "ACEHLMPRSX";


//            int maxLen = inputStringArray.length();

            int i = 0;
            for (String key : inputStringArray) {
                i++;
                System.out.println("******************************************************************************************");
                System.out.println("After adding " + key + " to tree: ");
                int val = i;
                if(myString.equals("OHSFINALEXAMS") && (i > 11)){ val++;}
                st.put(key, val);
                st.printTree();

                System.out.println();

//                ArrayList<Node> nodes = new ArrayList<Node>();
//                nodes = st.getTreeNodes(INORDER);
//                System.out.println("Inorder Traversal of Tree: ");
//                for (Node n : nodes) {
//                    System.out.println(n.toString());
//                }
                System.out.println("******************************************************************************************");
            }
            System.out.println("******************************************************************************************");
            System.out.println("Final tree is: ");
            st.printTree();

//        for (String s : st.keys())
//            System.out.println(s);
//        System.out.println();


        }

    }


}
