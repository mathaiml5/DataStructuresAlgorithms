/**
 * This is a utility class that implements Weighted QuickUnion Find With Path Compression algorithm
 * ***************** Main operations *********************
 * void union( elm1, elm2 )                 --> Merge two disjoint sets that elm1 and elm2 belong to
 * int find( x )                            --> Return set name (i.e set's id) that contains element x
 * int count()                              --> returns # of disjoint sets currently
 * boolean isConnected(int x, int y)        --> Check if x & y are part of the same set or connected already
 * void createDisjSetsTreeMap()             --> Create a treemap where key is set's id (or its name) and values are elements in set
 * String printDisjSets()                   --> print "Stringified" version of disjoint sets
 *
 * ******************Error checks ********************************
 * void validate(int p)     --> check if element is valid by checking its index
 *
 * @author: Vishak Srikanth
 * @version: 10/04/2021
 */

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Disjoint sets implemented with Weighted Quick Union Find with Path Compression algorithm
 * Elements in the set are numbered starting at 0.
 */
public class WeightedQuickUnionFindWithPathCompression {
    private int[] parent;  // parent[i] = index of parent of i-th element
    private int[] size;    // size[i] = number of elements in tree rooted at index i.
    private int count;     // number of disjoint sets
    private int numElements; //Total number of elements in all of the disjoint sets taken together
    private TreeMap<Integer, ArrayList<Integer>> disjSets = new TreeMap<Integer, ArrayList<Integer>>(); //Convenient private variable to print disjSets at any point in computation

    /**
     * Constructuctor for the disjoint sets object which initializes a list of elements
     *
     * @param numEl the initial number of disjoint sets.
     */
    public WeightedQuickUnionFindWithPathCompression(int numEl) {

        count = numEl;
        numElements = numEl;
        parent = new int[numEl];
        size = new int[numEl];
        //initialize each element's parent to the index of element itself
        //initialize size of each disjoint set to 1 (singleton sets)
        for (int i = 0; i < numEl; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }


    /**
     * Weighted Quick Union of two disjoint sets using the height heuristic.
     * For simplicity, we assume p and q are distinct
     * and find returns the index of the root element which is also the same as the set's name or id!
     *
     * @param p 1st element whose set is being considered for merging
     * @param q 2nd element whose set is being considered for merging
     */
    public void union(int p, int q) {

        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller set's root point to larger one and merge the two
        // by adding their size!
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        //Since we have merged 2 sets the total # of disjoint sets needs to be decremented by 1!
        count--;

    }

    /**
     * Returns the number of disjoint sets in total
     *
     * @return the number of sets (between 1 and numElements)
     */
    public int count() {
        return count;
    }


    /**
     * Perform a validation of the item being searched within find function using path compression
     * Error checks to make sure index is within bounds
     *
     * @param p the element being searched for.
     *          throws IllegalArgumentException if index is not within set's cardinality limit.
     */

    private void validate(int p) {
        int n = parent.length;
        // validate that p is a valid index
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }


    /**
     * Perform a find with path compression using 2 pass variant
     * Error checks are done in validate function
     *
     * @param x the element being searched for.
     * @return the index of root element of the top level parent of the set containing x.
     */
    public int find(int x) {
        //First do error checking before calling parent
        validate(x);
        int root = x;
        //2 pass variant of path compression algorithm
        while (root != parent[root])
            root = parent[root];
        //add second loop to find() to set the parent of each examined node to the root.
        while (x != root) {
            int newp = parent[x];
            parent[x] = root;
            x = newp;
        }
        return root;
    }


    /**
     * Returns true if the two elements are in the same set or "connected"
     *
     * @param x first element
     * @param y another element
     * @return true if p and y are in the same set false otherwise
     * @throws IllegalArgumentException unless 0 <= x < numElements and 0 <= y < numElements
     */
    public boolean isConnected(int x, int y) {
        return find(x) == find(y);
    }


    /**
     * Returns the internal set representation of the union-find data structure as a collection of
     * of disjoint sets where each set has the indices of the elements within the set and the 1st element being the root element
     * Note: Each set's id or name is the root element's index
     *
     * @return a String representation of the disjoint sets
     */
    public String printDisjSets() {

        createDisjSetsTreeMap();
        String tmp = new String();
        int c = 0;
        for (int k : disjSets.keySet()) {
            tmp += "{ ";
            ArrayList<Integer> setElems = disjSets.get(k);
            List<String> sNumbers = setElems.stream().map(n -> n.toString()).collect(Collectors.toList());
            tmp += String.join(", ", sNumbers);
            tmp += " }";
            if (c != (count - 1)) tmp += " , ";
            c++;
        }
        return tmp;
    }


    /**
     * Creates the Disjoint Sets on Demand by building treeMap
     * Each key of treemap represents the set's name (which is the cellIndex of its root node!)
     * and the values of the map are the cells in each disjoint set!
     */
    public void createDisjSetsTreeMap() {

        disjSets = new TreeMap<Integer, ArrayList<Integer>>();

        //Loop thru' all elements and find the parent of each
        for (int i = 0; i < parent.length; i++) {
            int currEl = i;
            int currPar = find(currEl);
            //If this set already exists then currPar will be there in TreeMap
            //so add the currEl to the set whose root is currPar
            if (disjSets.containsKey(currPar)) {
                disjSets.get(currPar).add(i);
            }
            //If this set is seen for the 1st time then currPar will not be there in TreeMap
            //so add the currEl to a new ArrayList and add it to the Treemap for a new key currPar which represents the set it belongs to!
            else {
                ArrayList<Integer> al = new ArrayList<Integer>();
                al.add(currEl);
                disjSets.put(currPar, al);
            }
        }
    }


    // Simple UnionFind algorithm tester, take NumElements elements and allot them in sequence to NumInSameSet
    // Output lines should be identical for each element in same set
    // e.g. For NumElements = 128 & NumInSameSet = 16 the 1st 16 entries are allotted evenly to set1, the next 16 to set 2 etc.
    public static void main(String[] args) {
        int numElements = 16;
        int numInSameSet = 8;

        WeightedQuickUnionFindWithPathCompression ds = new WeightedQuickUnionFindWithPathCompression(numElements);
        int root1, root2;
        System.out.println("********************************************************************************");
        System.out.println("************************* WEIGHTED UNION FIND TESTER ***************************");
        System.out.println("********************************************************************************");
        System.out.println("Testing Weighted Union Find with Path Compression with "+ numElements + " items ");
        System.out.println("distributed evenly so each set has " + numInSameSet + " elements");
        System.out.println("********************************************************************************");
        for (int k = 1; k < numInSameSet; k *= 2) {
            for (int j = 0; j + k < numElements; j += 2 * k) {

                root1 = ds.find(j);
                root2 = ds.find(j + k);

//DEBUG                System.out.println(" k = " + k + " , j = " + j + " root1: " + root1 + " root2: " + root2);
               ds.union(root1, root2);
               System.out.println("Merging sets with roots: " + " root1: " + root1 + " root2: " + root2);
               System.out.println(ds.printDisjSets());
               System.out.println("Total number of disjoint sets: " + ds.count());
               System.out.println("********************************************************************************");
            }
        }
        System.out.println();
        System.out.println("********************************************************************************");
        System.out.println("********************************************************************************");
        System.out.println("Final configuration: " + ds.count() + " disjoint sets: ");
        System.out.println(ds.printDisjSets());
        System.out.println();
        System.out.println("********************************************************************************");
    }
}
