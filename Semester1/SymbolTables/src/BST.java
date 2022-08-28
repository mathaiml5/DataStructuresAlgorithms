import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class BST<Key extends Comparable<Key>, Value> {
    private Node root;             // root of BST
    private int maxKeyLen;
    private int maxValLen;
    private int numCompares;
    private ArrayList<Key> keysExamined = new ArrayList<Key>();

    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree


        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }

        public Node() {
            this.key = null;
            this.val = null;
            this.size = 1;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public BST() {
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }


    /** Helper method that returns number of key-value pairs in BST rooted at node x
     * @param x : node whose subtree size we want
     * @return : integer corresponding to # of nodes in subtree = 1 + (# nodes in left subtree size)
     * + (# nodes in right subtree)
     */
    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     * {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }


   /** Helper method that returns the value associated with key if the key is found in a subtree rooted at node x
    * @param x : the search scoped to subtree rooted at the node x
    * @param key : the key to be found
    * @return : the value associated with the key if it found in the subtree rooted at x, otherwise return null
    */
    private Value get(Node x, Key key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     * and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        return get(root, key);
    }



    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        assert check();
    }

    /** Helper method add a new node with (key, value) pair in a subtree rooted at node x
     *  if key is found in subtree, just update the associated value, otherwise add a new leaf node
     * @param x
     * @param key
     * @param val
     * @return
     */
    private Node put(Node x, Key key, Value val) {
        //If x is null it means this key is not present, so add a new node with
        //subtree size = 1 and height = 0 since it is a leaf node!
        if (x == null) return new Node(key, val, 1);
        //If the node is not null recursively look in left and right subtree
        //to find the key
        int cmp = key.compareTo(x.key);
        numCompares++;
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        //Since we have added a node we need to update the subtree sizes
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }


    /**
     * Removes the smallest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
        assert check();
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Removes the largest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMax(root);
        assert check();
    }

    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, key);
        assert check();
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }


    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        else return max(x.right);
    }

    /**
     * Returns the largest key in the symbol table less than or equal to {@code key}.
     *
     * @param key the key
     * @return the largest key in the symbol table less than or equal to {@code key}
     * @throws NoSuchElementException   if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
        Node x = floor(root, key);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        keysExamined.add(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    public Key floor2(Key key) {
        Key x = floor2(root, key, null);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else return x;

    }

    private Key floor2(Node x, Key key, Key best) {
        if (x == null) return best;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return floor2(x.left, key, best);
        else if (cmp > 0) return floor2(x.right, key, x.key);
        else return x.key;
    }

    /**
     * Returns the smallest key in the symbol table greater than or equal to {@code key}.
     *
     * @param key the key
     * @return the smallest key in the symbol table greater than or equal to {@code key}
     * @throws NoSuchElementException   if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");
        Node x = ceiling(root, key);
        if (x == null) throw new NoSuchElementException("argument to ceiling() is too large");
        else return x.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        keysExamined.add(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) {
            Node t = ceiling(x.left, key);
            if (t != null) return t;
            else return x;
        }
        return ceiling(x.right, key);
    }

    /**
     * Return the key in the symbol table of a given {@code rank}.
     * This key has the property that there are {@code rank} keys in
     * the symbol table that are smaller. In other words, this key is the
     * ({@code rank}+1)st smallest key in the symbol table.
     *
     * @param rank the order statistic
     * @return the key in the symbol table of given {@code rank}
     * @throws IllegalArgumentException unless {@code rank} is between 0 and
     *                                  <em>n</em>–1
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
        keysExamined.add(x.key);
        System.out.println("\tCalling select(" + x.key + "," + rank + ")");
        int leftSize = size(x.left);
        String myLeftkey = new String();
        if (x.left == null) {
            myLeftkey = "null";
        }
        else{
            myLeftkey = x.left.key.toString();
        }

        System.out.println("\tLeftNode is " + myLeftkey + " with size=" + size(x.left));
        if (leftSize > rank) {

            System.out.println("\tCompare(" + leftSize + "," + rank + ") > 0 so calling select on left subtree select(" + myLeftkey + "," + rank + ")");
            return select(x.left, rank);
        }
        else if (leftSize < rank) {
            System.out.println("\tCompare(" + leftSize + "," + rank + ") < 0 so calling select on right subtree on remaining (" + rank + "-" + leftSize + "-" + 1 + ") items: select(" + x.right.key + "," + (rank - leftSize - 1) + ")");
            return select(x.right, rank - leftSize - 1);
        }
        else {
            System.out.println("\tCompare(" + leftSize + "," + rank + ") = 0 so returning with " + x.key);
            return x.key;
        }
    }

    /**
     * Return the number of keys in the symbol table strictly less than {@code key}.
     *
     * @param key the key
     * @return the number of keys in the symbol table strictly less than {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    }

    // Number of keys in the subtree less than key.
    private int rank(Key key, Node x) {

        if (x == null) return 0;
        System.out.println("Calling: rank(" + key + "," + x.key + ")");
        int cmp = key.compareTo(x.key);
        keysExamined.add(x.key);
        if (cmp < 0)
        {
            System.out.println("\tCompare(" + key + "," + x.key + ") < 0 so calling on left subtree rank(" + key + "," + x.left.key + ")");
            return rank(key, x.left);
        }
        else if (cmp > 0) {
            System.out.println("\tCompare(" + key + "," + x.key + ") > 0 so we add to rank " + (1 + size(x.left)) + " the rank(" + key + "," + x.right.key + ")");
            return 1 + size(x.left) + rank(key, x.right);
        }
        else {
            System.out.println("\tCompare(" + key + "," + x.key + ") yields 0 so calling returning with rank(" + key + "," + x.left + ") =" + size(x.left));
            return size(x.left);
        }
    }

    /**
     * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in the symbol table
     */
    public Iterable<Key> keys() {
        if (isEmpty()) return new Queue<Key>();
        return keys(min(), max());
    }

    /**
     * Returns all keys in the symbol table in the given range,
     * as an {@code Iterable}.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return all keys in the symbol table between {@code lo}
     * (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *                                  is {@code null}
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    /**
     * Returns the number of keys in the symbol table in the given range.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return the number of keys in the symbol table between {@code lo}
     * (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *                                  is {@code null}
     */
    public int size(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }


    /** Helper method to recursively call the getHeight method and calculate the max height of subtree
     *  rooted at node X by looking at both left and right subtrees and the node itself
     * @param x : the node whose subtree getHeight we need
     * @return : getHeight of node
     */
    private int getHeight(Node x) {
         int nodeheight;
        //if the node is null we return a value of -1 so that every leaf node will have getHeight 0
        // as we will can return -1 for left and right subtree and the summation will yield 0
        if (x == null) {nodeheight = -1;}
        else {
            nodeheight = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        }
        return nodeheight;
    }

    /**
     * Returns the getHeight of the BST
     *
     * @return the getHeight of the BST
     * Note: A 1-node tree has height= 0, leaf nodes have height 0
     */
    public int getHeight() {
        return getHeight(root);
    }


    /**
     * Returns the keys in the BST in level order (for debugging).
     *
     * @return the keys in the BST in level order traversal
     */
    public Iterable<Key> levelOrder() {
        Queue<Key> keys = new Queue<Key>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }

    /*************************************************************************
     *  Check integrity of BST data structure.
     ***************************************************************************/
    private boolean check() {
        if (!isBST()) System.out.println("Not in symmetric order");
        if (!isSizeConsistent()) System.out.println("Subtree counts not consistent");
        if (!isRankConsistent()) System.out.println("Ranks not consistent");
        return isBST() && isSizeConsistent() && isRankConsistent();
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
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    // are the size fields correct?
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        if (x.size != size(x.left) + size(x.right) + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (Key key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }


    public void printNode(Node root) {
        int maxLevel = maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
        if (size() == 0) {
            System.out.println("Tree is Empty!");
        }
        if (nodes.isEmpty() || isAllElementsNull(nodes)) {
            return;
        }
        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        printWhitespaces(firstSpaces);

        List<Node> newNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node != null) {
                System.out.print(node.key + ":" + node.val);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null) {
                    printWhitespaces(1);
                    System.out.print("/");

                } else
                    printWhitespaces(1);

                printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null) {
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

    private void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private int maxLevel(Node node) {
        if (node == null)
            return 0;

        return Math.max(maxLevel(node.left), maxLevel(node.right)) + 1;
    }

    private boolean isAllElementsNull(List list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

    private void resetkeysExamined() {
        keysExamined = new ArrayList<Key>();
    }

    private Node getNode(Key key) {


        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (key == root.key) return root;
        return getNode(root, key);
    }


    private Node getNode(Node currNode, Key key) {
        int cmp = key.compareTo(currNode.key);
        if (cmp < 0) return getNode(currNode.left, key);
        else if (cmp > 0) return getNode(currNode.right, key);
        else return currNode;
    }


    /**
     * Method to print keys in ascending order
     * Method relies on the fact that an inorder traversal of a BST will always yield the
     * keys in sorted order. Since we are using a numeric or lexicographic comparison in the
     * BST node keys we will always get the keys in ascending order when we traverse the BST
     * with an inorder traversal
     */
    public void printKeys(){
        System.out.println("Keys in Ascending order: ");
        traverseInorder(root);
    }

    private void visit(Node n) {
        System.out.print(n.key + "  ");
//        System.out.println("Key: " + n.key + " Value: " + n.val);
    }

    /**
     * Visits the nodes in this tree in preorder.
     */
    private void traversePreorder(Node start) {
        visit(start);
        if (start.left != null)
            traversePreorder(start.left);
        if (start.right != null)
            traversePreorder(start.right);
    }

    /**
     * Visits the nodes in this tree in postorder.
     */
    public void traversePostorder(Node start) {
        if (start.left != null)
            traversePostorder(start.left);
        if (start.right != null)
            traversePostorder(start.right);
        visit(start);
    }

    /**
     * Visits the nodes in the subtree rooted at start node with inorder traversal
     *
     * @param start : start node for the inorder traversal
     */
    public void traverseInorder(Node start) {
        if (start.left != null)
            traverseInorder(start.left);
        visit(start);
        if (start.right != null)
            traverseInorder(start.right);
    }


    /**
     * Unit tests the {@code BST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        BST<String, Integer> st = new BST<String, Integer>();
//        String inputStr = "MIDTERMQUESTION";
        String inputStr = "NGUDMRXAFKPSVYHL";
        int maxLen = inputStr.length();
        st.maxValLen = String.valueOf(maxLen).length();
        st.maxKeyLen = 1;
//        System.out.println("key \t value \t");
        for (int i = 0; i < maxLen; i++) {
            String key = new String(String.valueOf(inputStr.charAt(i)));
            st.put(key, i);
//            System.out.println("BST after adding letter key: " + key + " with value: " + i + " Total Compares: " + st.numCompares);
//            st.printNode(st.root);
        }


        System.out.println("Final BST after adding all letters in order they appear in: " + inputStr);
        st.printNode(st.root);
        System.out.println("******************************************************************************************");
//        System.out.println("BST sequence when deleting one letter at a time in the order they were first inserted : " + inputStr);
//        System.out.println("******************************************************************************************");
//        for (int i = 0; i < maxLen; i++) {
//            String key = new String(String.valueOf(inputStr.charAt(i)));
//            st.delete(key);
//            System.out.println("BST after deleting letter key: " + key);
//            st.printNode(st.root);
//            System.out.println("**************************************************************************************");
//        }


        ArrayList<String> alphaSortedKeys = new ArrayList<String>();
        for (String s : st.keys()) {
            alphaSortedKeys.add(s);
            System.out.println("Key : " + s + " Subtree size:" + st.getNode(s).size);
        }
//        System.out.println("BST sequence when deleting letters one by one in alphabetical order : " + alphaSortedKeys.toString());
//        System.out.println("******************************************************************************************");
//        for (int i = 0; i < alphaSortedKeys.size(); i++) {
//            String key = alphaSortedKeys.get(i);
//            st.delete(key);
//            System.out.println("BST after deleting letter key: " + key);
//            st.printNode(st.root);
//            System.out.println("**************************************************************************************");
//        }

//        System.out.println("BST sequence when deleting letters by successively deleting the key at the root");
//        System.out.println("******************************************************************************************");
//        while(st.size() > 0){
//
//            System.out.println("BST after deleting letter at the root: " + st.root.key);
//            st.delete(st.root.key);
//            st.printNode(st.root);
//            System.out.println("**************************************************************************************");
//        }

        System.out.println("BST sequence of nodes examined when calling: rank(\"S\")");
        int rankS = st.rank("S");
        System.out.println(st.keysExamined);
        System.out.println("The result from calling: rank(S):");
        System.out.println(rankS);

        st.resetkeysExamined();
        System.out.println("BST sequence of nodes examined when calling: select(11)");
        String select5 = st.select(11);
        System.out.println(st.keysExamined);
        System.out.println("The result from calling: select(11):");
        System.out.println(select5);

        st.resetkeysExamined();
        System.out.println("Preorder Traversal: \n");
        st.traversePreorder(st.root);
        System.out.println();
        System.out.println("Inorder Traversal: \n");
        st.traverseInorder(st.root);
        System.out.println();
        System.out.println("Postorder Traversal: \n");
        st.traversePostorder(st.root);


        System.out.println("BST sequence when deleting letters by successively deleting the keys at the root");
        System.out.println("******************************************************************************************");
        String[] keys_to_del_seq = {"K", "G", "N"};
        for (int i = 0; i < keys_to_del_seq.length; i++) {
            String del_key = keys_to_del_seq[i];
            System.out.println("BST after deleting letter: " + del_key);
            st.delete(del_key);
            st.printNode(st.root);
            System.out.println("**************************************************************************************");
        }

//        st.resetkeysExamined();
//        System.out.println("BST sequence of nodes examined when calling: ceiling(\"V\")");
//
//        try {
//            String ceilingV = st.ceiling("V");
//            System.out.println("The result from calling: ceiling(\"V\") :");
//            System.out.println(st.keysExamined);
//            System.out.println(ceilingV);
//        }
//        catch(NoSuchElementException e){
//            System.out.println(st.keysExamined);
//            e.printStackTrace();
//        }


//        st.resetkeysExamined();
//        System.out.println("BST sequence of nodes examined when calling: rank(\"S\")");
//        int rankS = st.rank("S");
//        System.out.println(st.keysExamined);
//        System.out.println("The result from calling: rank(\"S\"):");
//        System.out.println(rankS);


//        for (String s : st.levelOrder())
//            StdOut.println(s + " " + st.get(s));
//        StdOut.println();
//
//        for (String s : st.keys())
//            StdOut.println(s + " " + st.get(s));

        BST<Integer, Integer> num_Bst = new BST<Integer, Integer>();
        int[] numsInput = {50, 25, 60, 20, 35, 90, 30, 75, 70};
        for (int i = 0; i < numsInput.length; i++) {
            num_Bst.put(numsInput[i], i+1);
        }
        num_Bst.printKeys();
        System.out.println("Level Order Traversal of Keys:");
        for (Integer num : num_Bst.levelOrder()){
            System.out.println("Key : " + num + " Count:" + num_Bst.getNode(num).size + " Height : " + num_Bst.getHeight(num_Bst.getNode(num)));
        }
//            StdOut.println(s + " " + st.get(s));
//        StdOut.println();
    }




}
