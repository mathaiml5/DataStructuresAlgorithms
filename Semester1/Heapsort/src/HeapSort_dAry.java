/*  The main d-ary heapsort algorithm is implemented in this class
 *  @author: Vishak Srikanth
 *  @version: 11/01/2021
 */

import java.util.Arrays;
import java.util.NoSuchElementException;

//heapsort with a generic type of Key
public class HeapSort_dAry<Key> {

    private int d;                       // d-ary heap parameter

    private Key[] pq;                    // Note: pq[0] is empty and items in PQ are stored at indices 1 to maxInitialCapacity
    private int maxInitialCapacity;      // number of items on priority queue excluding key[0] which is not used



    //Getters and Setters for private variables

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public Key[] getPq() {
        return pq;
    }

    public void setPq(Key[] pq) {
        this.pq = pq;
    }

    public int getMaxInitialCapacity() {
        return maxInitialCapacity;
    }

    public void setMaxInitialCapacity(int maxInitialCapacity) {
        this.maxInitialCapacity = maxInitialCapacity;
    }

    /**
     * Initializes an empty priority queue with the given initial capacity.
     *
     * @param initCapacity the initial capacity of this priority queue
     */
    public HeapSort_dAry(int initCapacity, int n_ary) {
        pq = (Key[]) new Object[initCapacity + 1];
        maxInitialCapacity = initCapacity;
        d = n_ary;
    }

    /**
     * Initializes an empty priority queue.
     */
    public HeapSort_dAry() {
        //Default ternary heap!
        this(1, 3);
    }


    /**
     * Initializes a priority queue from the array of keys.
     * Takes time proportional to the number of keys, using sink-based heap construction.
     *
     * @param keys the array of keys
     */
    public HeapSort_dAry(Key[] keys, int n_ary) {

        d = n_ary;
        pq = (Key[]) new Object[keys.length + 1];
        for (int i = 0; i < keys.length; i++)
            pq[i + 1] = keys[i];
        maxInitialCapacity = keys.length;
    }


    /**
     * Returns true if this priority queue is empty.
     *
     * @return true if this priority queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return maxInitialCapacity == 0;
    }

    /**
     * Returns the number of keys on this priority queue.
     *
     * @return the number of keys on this priority queue
     */
    public int size() {
        return maxInitialCapacity;
    }

    /**
     * Returns a largest key on this priority queue.
     *
     * @return a largest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }


    /** resize the underlying array in PQ to have the given capacity
     * @param capacity
     */
    private void resize(int capacity) {
        assert capacity > maxInitialCapacity;
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= maxInitialCapacity; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }


    /**
     * Adds a new key to this priority queue.
     *
     * @param x the new key to add to this priority queue
     */
    public void insert(Key x) {

        // double size of array if needed
        if (maxInitialCapacity == pq.length - 1) resize(pq.length + 1);

        // add x, and percolate it up to maintain heap invariance using bottm up reheapify as shown in Sedgewick Algorithms 4th Ed pp.315-316)
        pq[++maxInitialCapacity] = x;
        swim(maxInitialCapacity);

    }

    /**
     * Removes and returns a largest key on this priority queue.
     *
     * @return a largest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key delMax() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue is empty!");
        Key max = pq[1];
        exch(1, maxInitialCapacity--);
        sink(1);
        pq[maxInitialCapacity + 1] = null;     //Safety check
        if ((maxInitialCapacity > 0) && (maxInitialCapacity == (pq.length - 1) / 4)) resize(pq.length / 2);
        return max;
    }


    /***************************************************************************
     * Helper functions to reheapify using swim especailly after deletions
     ***************************************************************************/
    /** Bottom-up re-heapify implementation
     * @param k node key
     */
    private void swim(int k) {
        //For d-ary trees, parent of key k is  key whose id is floor{(k+d-2)/d}
        while (k > 1 && less((k + d - 2) / d, k)) {
            //swap with parent as long as larger than parent
            exch(k, (k + d - 2) / d);
            //Check parent to see if out of order
            k = (k + d - 2) / d;
        }
    }

    /** Top-down re-heapify implementation
     * @param k node key
     */
    private void sink(int k) {

        //Check if we are withing array limits
        while (d * k <= (maxInitialCapacity)) {
            Key x = pq[k];
//            System.out.println("Before calling sink for element: a[" + k + "] :" + x);
//            show(pq);
            int j = d * k;
            //The children will be from j-(d-2) to j+1 inclusive for a node which has all its chlildren filled
            int largestChild = j;
            if (j < (maxInitialCapacity)) {

                int[] childIndex = getChildNodes(k);
                for (int i = j - (d - 2); i <= j + 1; i++) {
                    if (less(largestChild, i)) {
                        largestChild = i;
                    }
                }

            }
//            System.out.println("Largest Child: a [" + largestChild + "] :" + pq[largestChild]);
            if (!less(k, largestChild)) break;
            exch(k, largestChild);
//            System.out.println("After calling sink for element: " + x + " now: a[" + k + "] :" + pq[k]);
//            show(pq);
            k = largestChild;

        }

    }

    /***************************************************************************
     * Helper functions for compares and swaps for the non-sort cases
     ***************************************************************************/
    private boolean less(int i, int j) {
        return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }


    /**
     *  Print tree by traversing each level and taking care to put enough spacers and fillers
     */
    public void printNaryTree() {
        int depth = (int) log((size() - 1) * (d - 1) + 1, d) + 1;
        boolean[] flag = new boolean[depth];
        Arrays.fill(flag, true);
        printTreeNode(1, flag, 0, false);

    }


    /**
     * @param index node for whom we need the children
     * @return child indices
     */
    private int[] getChildNodes(int index) {
        int[] childIndex = new int[d + 1];
        Arrays.fill(childIndex, -1);

        int ind = 1;
        int j = d * index;

        if (j - (d - 2) <= (maxInitialCapacity)) {
            for (int i = j - (d - 2); i <= j + 1; i++) {
                if (i > maxInitialCapacity) break;
                childIndex[ind] = i;
                ind++;
            }

        }
        return childIndex;
    }


    /**
     * @param index node index within the tree
     * @param flag used to indicate which prefix character to print for the horizontal dendrogram
     * @param depth depth of tree
     * @param isLastNode flag which indicates whether this is last node at this level
     */
    public void printTreeNode(int index, boolean[] flag, int depth, boolean isLastNode) {

//        int depth = (int) log((size() - 1) * (d - 1) + 1, d) + 1;
//        int depth = log(index*(d-1), d) + 1;

        int level = log(index * (d - 1), d);
//            System.out.println("Level :" + level + " element: " + x);
        int curr_level_max = (level == 0) ? 1 : (int) ((Math.pow(d, level) - 1) / (d - 1));
        int curr_level_min = (level == 0) ? 1 : (int) ((Math.pow(d, level - 1) - 1) / (d - 1)) + 1;

        // Loop to print the depths of the
        // current node
        for (int i = 1; i < depth; ++i) {

            // Condition when the depth
            // is being explored
            if (flag[i] == true) {
                System.out.print("| "
                        + " "
                        + " "
                        + " ");
            }

            // Otherwise print
            // the blank spaces
            else {
                System.out.print(" "
                        + " "
                        + " "
                        + " ");
            }
        }

        // Condition when the current
        // node is the root node
        if (depth == 0)
            System.out.println(pq[index]);

            // Condition when the node is
            // the last node of
            // the exploring depth path
        else if (isLastNode) {
            System.out.print("+--- " + pq[index] + '\n');

            // No more childrens, turn flag off
            // to the non-exploring depth
            flag[depth] = false;
        } else {
            System.out.print("+--- " + pq[index] + '\n');
        }

        int it = 0;


        int[] childIndex = getChildNodes(index);

        for (int i = 1; i <= d; i++) {

            int currChildIdx = childIndex[i];
            ++it;
            if (currChildIdx != -1) {
                // Recursive call for the
                // children nodes
                printTreeNode(currChildIdx, flag, depth + 1,
                        currChildIdx == curr_level_max);
            }

        }
        flag[depth] = true;
    }

    /**
     * reheapify using top down
     */
    public void reheapify() {
        int n = size();
        for (int k = size() / d; k >= 1; k--) {
            sink(k);
            System.out.print(n + "\t" + k + "\t");
            show(pq);
        }

    }



    // log with base
    private int log(int x, int base) {
        return (int) (Math.log(x) / Math.log(base)); // = log(x) with base 10 / log(base) with base 10
    }


    /**
     * Simple print of the heap without dendrogram
     */
    public void dumpHeap() {
        //Height of a d-ary tree of n nodes is [logd(n-1)*(d - 1) + 1)] + 1
        int height = log((size() - 1) * (d - 1) + 1, d) + 1;

//        System.out.println("height :" + height);
        for (int i = 1, len = size() + 1; i < len; i++) {
            Key x = pq[i];
            int level = log(i * (d - 1), d) + 1;
//            System.out.println("Level :" + level + " element: " + x);
            int curr_level_max = (int) ((Math.pow(d, level) - 1) / (d - 1));
            int curr_level_min = (int) ((Math.pow(d, level - 1) - 1) / (d - 1)) + 1;
//            System.out.println("Height:" + height + " Level:" + level + " MinVal:" + curr_level_min + " MaxVal: " + curr_level_max + " NodeId:" + i);
//            System.out.print("\t Level: " + level + "\t");
            int spaces = 1;
            if (i == curr_level_min) {
                spaces = (height == level) ? 1 : (int) Math.pow(d, (height - level)); // + 1) * d;
//                System.out.println("InitialSpaces:" + spaces);
            } else {
                spaces = (height == level) ? 1 : (height - level) * (d - 1) - 1;
            }
//            char[] a = new char[spaces];
//            Arrays.fill(a, ' ');
//            String blankStr = new String(a);
//            System.out.print(blankStr);
//            System.out.print(x);
//            if (curr_level_max  == i) System.out.println();
        }
        System.out.println();
    }

    /** print Key array
     * @param a array of key objects
     */

    public void show(Key[] a) {
        for (int i = 1; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    /** Utility method for in-place top down reheapify using the same Key array
     * @param pq priority queue
     * @param k current node in top down heapify step
     * @param n limits of tree nodes to swap with
     * @param d tree'sorder
     */
    private void sink(Key[] pq, int k, int n, int d) {

        while (d * k <= n) {
            Key x = pq[k];
//            System.out.println("Before calling sink for element: a[" + k + "] :" + x);
//            show(pq);
            int j = d * k;
            int largestChild = j - (d-2);
            for (int i = j - (d - 2); i <= j + 1 ; i++) {
                if ((j < n) && !less(pq, i, largestChild)) {
                        largestChild = i;
                    }
                }


//            System.out.println("Largest Child: a [" + largestChild + "] :" + pq[largestChild]);
            if (!less(pq, k, largestChild)) break;
            exch(pq, k, largestChild);

//            System.out.println("After calling sink for element: " + x + " now: a[" + k + "] :" + pq[k]);
//            show(pq);
            k = largestChild;

        }

    }

    /***************************************************************************
     * Helper functions for comparisons and swaps.
     ***************************************************************************/
    //check if eleent at i is smaller than j
    private boolean less(Key[] pq, int i, int j) {
        Comparable a = (Comparable)pq[i];
        return a.compareTo((Comparable)pq[j]) < 0;
    }

   //Excahnge elements at i, j
    private static void exch(Object[] pq, int i, int j) {
        Object swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    /**
     Rearranges the array in ascending order, using the natural order.
     */
    public void sort() {
        int n = maxInitialCapacity;

        // heapify phase
        for (int k = n/ d; k >= 1; k--) {
            sink(pq, k, n, d);
//            sink(k);
            System.out.print(n + "\t" + k + "\t");
            show(pq);
        }
        System.out.println("************************************************************************************************************************");
        System.out.println("Array & Tree After Heapify Phase: ");
        System.out.println("************************************************************************************************************************");
        show(pq);
        System.out.println("************************************************************************************************************************");
        printNaryTree();
        System.out.println("************************************************************************************************************************");
        // sortdown phase
        System.out.println("Sortdown phase: ");
        while (n > 1) {
//            exch(1, n--);
//            sink(1);
//            System.out.println("Swapping:");
//            System.out.println("************************************************************************************************************************");
            // sortdown phase
            exch(pq, 1, n--);
//            show(pq);
            System.out.println("************************************************************************************************************************");

            sink(pq,1, n, d);
            System.out.print(n + "\t" + 1 + "\t");
            show(pq);
//            n--;
        }

    }




    /**
     * Reads in a sequence of strings from standard input; heapsorts them;
     * and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String myString = "SORTEXAMPLE";
//        String myString = "TSRPNOAEIHG";
        String[] a = myString.split("(?!^)");
        System.out.println("Input Array: " + Arrays.toString(a));
        //        int cap = a.length;



        HeapSort_dAry<String> hs1 = new HeapSort_dAry<String>(0, 2);
        //Using insert to add 1 letter at a time
        // System.out.println("Inserting a letter at a time: ");
//        for (int i = 0; i < a.length; i++) {
//            hs1.insert(a[i]);
////            System.out.println();
//            System.out.println("Heap after inserting: a[" + (i+1) + "] : " + a[i]);
//            hs1.show(hs1.pq);
////
//        }
////        hs1.dumpHeap();
//        hs1.show(hs1.pq);
//        hs1.sort();
//        hs1.printNaryTree();
//        hs1.show(hs1.pq);


        HeapSort_dAry<String> hs2 = new HeapSort_dAry<String>(a, 3);
        System.out.println("Initial Array and Tree: ");
        System.out.println("************************************************************************************************************************");
        hs2.show(hs2.getPq());
        System.out.println("************************************************************************************************************************");
        hs2.printNaryTree();
        System.out.println("************************************************************************************************************************");
        hs2.sort();
        System.out.println("************************************************************************************************************************");
        System.out.println("************************************************************************************************************************");
        System.out.println("Final Sorted Array and Tree: ");
        hs2.show(hs2.getPq());
        hs2.printNaryTree();

    }




}
