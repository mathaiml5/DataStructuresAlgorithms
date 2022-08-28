/*  The main 2-3 tree class as outlined in Section 3.3. of Sedgewick et. al Algorithms 4th Edition
 *  1. Uses Simplified versions of TwoThreeNode class and FourNode class as outlined in Section 3.3. of Sedgewick et. al Algorithms 4th Edition
 *  2. has the key add method to add new item keys into node and fixup the node pointers
 *  3. The program prints the output for each step of adding an item to the tree. There is also verical dendrogram style visualization
 *     of the final RB tree at each step
 *  @author: Vishak Srikanth
 *  @version: 11/22/2021
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TwoThreeTree<Key extends Comparable, Value extends Comparable> {
    private TwoThreeNode<Key, Value> root;
    private int treeSize = 0;

    /**
     * Default constructor
     */
    public TwoThreeTree() {
        root = null;
    }

    /** Creates a 2-3 tree from a 2-3 node object passed
     * @param rootNode rootNode node (of type TowThreeNode)
     */
    public TwoThreeTree(TwoThreeNode<Key, Value> rootNode) {
        this.root = rootNode;
        treeSize++;
    }



    /** Constructor for creating a 2-3 tree from a key (which is also the value) in the simplified 2-3 Node
     * @param rootkey key of the root node
     * @param rootval value of the root node
     */
    public TwoThreeTree(Key rootkey, Value rootval) {
        TwoThreeNode<Key, Value> rootNode = new TwoThreeNode<Key, Value>(rootkey, rootval, null, null, null, null, null);
        this.root = rootNode;
        treeSize++;
    }


    /** Returns the size of the tree in terms of number of nodes
     * @return number of nodes in tree
     */
    public int getTreeSize() {
        return treeSize;
    }

    /** Sets tree size to a value
     * @param treeSize node count
     */
    public void setTreeSize(int treeSize) {
        this.treeSize = treeSize;
    }

    //Getter and setter for root node
    public TwoThreeNode getRoot() {
        return root;
    }

    public void setRoot(TwoThreeNode root) {
        this.root = root;
    }


    /** Calculate the depth of the tree (path length from root to leaf node
     * Note: since 2-3 node is balanced so same depth everywhere
     * @return depth of tree (or height in terms of number of levels deep)
     */
    public int depth() {
        //The tree is balanced, so we have the same depth everywhere.
        int depthCount = 0;

        TwoThreeNode curNode = root;

        //Just go left until we have no children any more, count each "level".
        while (curNode != null) {
            curNode = curNode.lchild();
            depthCount++;
        }
        depthCount--; //We counted the root as a level so adjust for this scenario

        return depthCount;
    }

    /** Gives number of nodes of each type (2-node, 3-node) in the subtree starting from a given node
     * @param curNode the node from which we want to get the subtree node counts
     * @return
     */
    private int[] getNodeTypeCounts(TwoThreeNode curNode) {

        //totalNodeCounts: index 0 is TwoNodeCount, index 1 is ThreeNodeCount.
        int[] totalNodeCounts = new int[2];

        //Current node is a... TwoNode.
        if (curNode.lkey() == null)
            totalNodeCounts[0]++;
        else //... ThreeNode.
            totalNodeCounts[1]++;

        //Count the left children if we have any.
        if (curNode.lchild() != null) {
            int[] childNodeCounts = getNodeTypeCounts(curNode.lchild());
            totalNodeCounts[0] += childNodeCounts[0];
            totalNodeCounts[1] += childNodeCounts[1];
        }

        //Count the middle children if we have any.
        if (curNode.cchild() != null) {
            int[] childNodeCounts = getNodeTypeCounts(curNode.cchild());
            totalNodeCounts[0] += childNodeCounts[0];
            totalNodeCounts[1] += childNodeCounts[1];
        }

        //Count the right children if we have any.
        if (curNode.rchild() != null) {
            int[] childNodeCounts = getNodeTypeCounts(curNode.rchild());
            totalNodeCounts[0] += childNodeCounts[0];
            totalNodeCounts[1] += childNodeCounts[1];
        }

        return totalNodeCounts;
    }

    /** Lookup the given key exists in the tree and return node where it needs to be inserted if not there already
     * @param item key being searched
     * @param beginNode subtree from which to search
     * @param returnNullOnMissing If not found should we retrun null or throw any exception
     * @return
     */
    private TwoThreeNode getNode(Key item, TwoThreeNode beginNode, boolean returnNullOnMissing) {
        //If we should return null on a missing item then the branch we've just gone into will be null if it is where the item should be.
        if (returnNullOnMissing) {
            if (beginNode == null)
                return null;
        } else //If we should find the node the item should be in, we need to check for an empty branch before going down, so we can return before that happens.
        {
            //Doesn't matter what branch we check for nulls since we have a balanced tree, if any branch is null we have a leaf node.
            if (beginNode.lchild() == null)
                return beginNode;
        }

        if (beginNode.is2NodeType()) {
            //If the search item equal to our left or right item, then return current node this logic is the same for both TwoNodes and ThreeNodes.
            if ((beginNode.lkey() != null && item.compareTo(beginNode.lkey()) == 0))
                return beginNode;
                //If smaller than the first value search left, this is the same for both TwoNodes and ThreeNodes.
            else if (item.compareTo(beginNode.lkey()) < 0)
                return getNode(item, beginNode.lchild(), returnNullOnMissing);
            else if (item.compareTo(beginNode.lkey()) > 0)
                return getNode(item, beginNode.rchild(), returnNullOnMissing);

        } else if (beginNode.is3NodeType()) {
            //If the search item equal to our left or right item, then return current node this logic is the same for both TwoNodes and ThreeNodes.
            if ((beginNode.lkey() != null && item.compareTo(beginNode.lkey()) == 0) || (beginNode.rkey() != null && item.compareTo(beginNode.rkey()) == 0))
                return beginNode;
                //If smaller than the first value search left, this is the same for both TwoNodes and ThreeNodes.
            else if (item.compareTo(beginNode.lkey()) < 0)
                return getNode(item, beginNode.lchild(), returnNullOnMissing);
            else if (item.compareTo(beginNode.rkey()) > 0)
                return getNode(item, beginNode.rchild(), returnNullOnMissing);
            else if (item.compareTo(beginNode.lkey()) > 0 && item.compareTo(beginNode.rkey()) < 0)
                return getNode(item, beginNode.cchild(), returnNullOnMissing);

        }

        //Failsafe for compilation otherwise returns in branched logic does nto work
        return null;
    }


    /** Split 4-Node to 2 2-nodes
     * @param input4Node input 4-node
     * @return
     */
    private TwoThreeNode convertFourNodetoTwoNode(FourNode input4Node) {
        // Convert a FourNode that looks like this:
        //
        //		(a, b, c)
        //		/  |  |	 \
        //	   /   |  |	  \
        //    1    2  3    4
        //
        //			 (b)
        //			/   \
        //	 	   /     \
        //	 	 (a)     (c)
        //   	/	\	/   \
        //     /	 \ /     \
        //    1      2 3     4
        // newLeft = a, newright = c, newRoot = b
        //Create a new local root node, b, from the middle keyValue.
        TwoThreeNode newRoot = new TwoThreeNode(input4Node.mkey(), input4Node.mval(), null, null, null, null, null);

        //New left, a, is the left child. New right, c, is the right child.
        TwoThreeNode newLeft = new TwoThreeNode(input4Node.lkey(), input4Node.lval(), null, null, null, null, null);
        TwoThreeNode newRight = new TwoThreeNode(input4Node.rkey(), input4Node.rval(), null, null, null, null, null);

        //Set the new children to the NewRoot.
        newRoot.setLeftChild(newLeft);
        newRoot.setRightChild(newRight);

        //Set new left and right node parent to the root node.
        newLeft.setParent(newRoot);
        newRight.setParent(newRoot);

        //Move branch 1, and relink its parent if we have such a branch.
        newLeft.setLeftChild(input4Node.lchild());

        if (newLeft.lchild() != null)
            newLeft.lchild().setParent(newLeft);

        //Move branch 2, and relink its parent if we have such a branch.
        newLeft.setRightChild(input4Node.cchild());


        if (newLeft.rchild() != null)
            newLeft.rchild().setParent(newLeft);

        //Move branch 3, and relink its parent if we have such a branch.
        newRight.setLeftChild(input4Node.cchild2());

        if (newRight.lchild() != null)
            newRight.lchild().setParent(newRight);

        //Move branch 4, and relink its parent if we have such a branch.
        newRight.setRightChild(input4Node.rchild());

        if (newRight.rchild() != null)
            newRight.rchild().setParent(newRight);

        return newRoot;
    }


    /** merges 2 2-3nodes in the tree
     * @param existingTreeNode current node already in tree
     * @param newNodeAdded new node added
     * @return
     */
    private FourNode MergeNodes(TwoThreeNode existingTreeNode, TwoThreeNode newNodeAdded) {
        //The separate node we are sending in is assumed to be a TwoNode.

        //Possible merge result.
        FourNode newFourNode = null;

        //If the node in the tree we are merging with is a TwoNode.
        if (existingTreeNode.rkey() == null) {
            //The thing we are merging is smaller than the treeNode's key.
            if (newNodeAdded.lkey().compareTo(existingTreeNode.lkey()) <= -1) {
                //Move the key/values to the right and insert.
                existingTreeNode.setRight(existingTreeNode.lkey(), existingTreeNode.lval());
                existingTreeNode.setLeft(newNodeAdded.lkey(), newNodeAdded.lval());

                //Move the children to the right and insert the newNodeAdded's children into the tree node.
//                existingTreeNode.right = existingTreeNode.middle;
                existingTreeNode.setLeftChild(newNodeAdded.lchild());
                existingTreeNode.setCenterChild(newNodeAdded.rchild()); //Right child in the newNodeAdded.
//                existingTreeNode.setRightChild(newNodeAdded.lchild());
            } else if (newNodeAdded.lkey().compareTo(existingTreeNode.lkey()) >= 1) {
                //Insert key/values.
                existingTreeNode.setRight(newNodeAdded.lkey(), newNodeAdded.lval());

                //Insert the children.
                existingTreeNode.setRightChild(newNodeAdded.rchild()); //Right node in the newNodeAdded.
                existingTreeNode.setCenterChild(newNodeAdded.lchild());
            }

            //Don't forget to relink the parent property after we've moved children around.
            newNodeAdded.rchild().setParent(existingTreeNode);
            newNodeAdded.lchild().setParent(existingTreeNode);
        } else //If the node in the tree we are merging with is a ThreeNode.
        {
            //If the key in the separate node is smaller than the three node's first key.
            if (newNodeAdded.lkey().compareTo(existingTreeNode.lkey()) <= -1) {
                newFourNode = new FourNode(newNodeAdded.lkey(), newNodeAdded.lval(), existingTreeNode.lkey(), existingTreeNode.lval(), existingTreeNode.rkey(), existingTreeNode.rval(), null, null, null, null);

                newFourNode.setLeftChild(newNodeAdded.lchild());
                newFourNode.setCenterChild(newNodeAdded.rchild());
                newFourNode.setCenterChild2(existingTreeNode.cchild());
                newFourNode.setRightChild(newNodeAdded.rchild());

            } else if (newNodeAdded.lkey().compareTo(existingTreeNode.lkey()) > 0 && newNodeAdded.lkey().compareTo(existingTreeNode.rkey()) < 0) {
                newFourNode = new FourNode(existingTreeNode.lkey(), existingTreeNode.lval(), newNodeAdded.lkey(), newNodeAdded.lval(), existingTreeNode.rkey(), existingTreeNode.rval(),null, null, null, null);

                newFourNode.setLeftChild(existingTreeNode.lchild());
                newFourNode.setCenterChild(newNodeAdded.lchild());
                newFourNode.setCenterChild2(newNodeAdded.rchild());
                newFourNode.setRightChild(existingTreeNode.rchild());


            } else //If not smaller or in the middle of our values it must be bigger.
            {
                newFourNode = new FourNode(existingTreeNode.lkey(), existingTreeNode.lval(), existingTreeNode.rkey(), existingTreeNode.rval(), newNodeAdded.lkey(), newNodeAdded.lval(), null, null, null, null);


                newFourNode.setLeftChild(existingTreeNode.lchild());
                newFourNode.setCenterChild(existingTreeNode.cchild());
                newFourNode.setCenterChild2(newNodeAdded.lchild());
                newFourNode.setRightChild(newNodeAdded.rchild());

            }

            //Relink the children to our FourNode.
            newFourNode.lchild().setParent(newFourNode);
            newFourNode.cchild().setParent(newFourNode);
            newFourNode.cchild2().setParent(newFourNode);
            newFourNode.rchild().setParent(newFourNode);
        }

        //If no new FourNode then this will be null.
        return newFourNode;
    }


    /** Adds a new 4-node to tree
     * @param currentNode the node where we want to add and reconcile the 4-node with
     * @param tmpFourNode the 4-node created as a result of any tree operation
     */
    private void add4NodeToTree(TwoThreeNode currentNode, FourNode tmpFourNode) {
        //Split the FourNode into a TwoNode.
        TwoThreeNode splitResult = convertFourNodetoTwoNode(tmpFourNode);
        treeSize++;

        //If we've worked our way up to the root, then we don't need to merge, just set the root to the split result.
        if (currentNode == root) {
            root = splitResult;
            treeSize++;
            //Normally splitting produces two new children, and then merging will reduce that by one.
            //Since we are not merging now we need to count the split above one more time.
        } else {
            TwoThreeNode parent = currentNode.getParent();

            //Merge the splitResult with the parent.
            FourNode mergeResult = MergeNodes(parent, splitResult);

            //If the merge result is null the parent was a TwoNode, and we are done. It's inserted.
            //If not, we need to merge the new FourNode we have with the parent and repeat.
            if (mergeResult != null)
                add4NodeToTree(parent, mergeResult);
        }
    }


    /** Main method to insert a new key into the 2-3 tree
     * @param itemkey key to be inserted
     */
    public void insertKeyIntoTree(Key itemkey, Value itemval) {
        //Null values are not supported, cause then get will not work properly.
        //Assume we store a null value and then get will return that null value. The calling
        //code will then not know if we have found the value (that is null) or if the search
        //was unsuccessful and that's why we are returning null.
        if (itemkey == null)
            throw new IllegalArgumentException("Null values not supported!");

        //If we don't have a root node we don't have a tree.
        //Create a TwoNode and store our key/value in it, done.
        if (root == null) {
            root = new TwoThreeNode(itemkey, itemval, null, null, null, null, null);
            treeSize = 1; //We need to add 1 to our size variable, we have not done a split, but we have one node now.
            return;
        }

        //Find the node we should add the key/value to.
        TwoThreeNode foundNode = getNode(itemkey, root, false);

        //If the node is a TwoNode, then it's simple. Convert that TwoNode to a ThreeNode and add.
        //Since we are always adding from the bottom we don't have any children to take into account.
        if (foundNode.rkey() == null) {
            if (itemkey.compareTo(foundNode.lkey()) < 0)        //Key is less than key in node.
            {
                //We need to move stuff if we are inserting to the left.
                foundNode.setRight(foundNode.lkey(), foundNode.lval());
                foundNode.setLeft(itemkey, itemval);
            } else if (itemkey.equals(foundNode.lkey()))            //Equal to key in node, replace value.
                foundNode.setLeft(itemkey, itemval);
            else if (itemkey.compareTo(foundNode.lkey()) > 0)    //Key is greater than key in node.
                foundNode.setRight(itemkey, itemval);

        } else //If the node is a ThreeNode, then it's more complicated. Create a temporary FourNode, split it to a TwoNode and merge that back into the tree.
        {

            //If we have any key with the key we are trying to add, then just replace those values.
            if (foundNode.lkey().equals(itemkey)) {
                foundNode.setLeft(itemkey, itemval);
                return;
            }
            if (foundNode.rkey().equals(itemkey)) {
                foundNode.setRight(itemkey, itemval);
                return;
            }

            FourNode tempFourNode = null;

            //Key is less than left key, insert new value to the left.
            // New     | Lo  | Hi |
            //        /      \    \
            // null  Left   Ctr  Right
            if (itemkey.compareTo(foundNode.lkey()) <= -1)
                tempFourNode = new FourNode(itemkey, itemval, foundNode.lkey(), foundNode.lval(), foundNode.rkey(), foundNode.rval(),null, foundNode.lchild(), foundNode.cchild(), foundNode.rchild());

                //Key is larger then the left key, and smaller then the right key, thus it should go in the middle
                //         | Lo New | Hi |
                //        /    \         \
                //       Left   Ctr1 null Right
            else if (itemkey.compareTo(foundNode.lkey()) >= 1 && itemkey.compareTo(foundNode.rkey()) <= -1)
                tempFourNode = new FourNode(foundNode.lkey(), foundNode.lval(), itemkey, itemval, foundNode.rkey(), foundNode.rval(), foundNode.lchild(), foundNode.cchild(), null, foundNode.rchild());

                //Key is more then the right key, insert new value in the right.
                //      | Lo  | Hi | New
                //     /      \    \
                // Left       Ctr  Right null
            else if (itemkey.compareTo(foundNode.rkey()) >= 1)
                tempFourNode = new FourNode(foundNode.lkey(), foundNode.lval(), foundNode.rkey(), foundNode.rval(), itemkey, itemval, foundNode.lchild(), foundNode.cchild(), foundNode.rchild(), null);

            //Insert this FourNode into the tree.
            add4NodeToTree(foundNode, tempFourNode);
        }
    }


    /***************************************************************************
     *  TREE PRINTING FUNCTIONS
     *  Print a nice textual visualization of 2-3 Tree
     ***************************************************************************/

    public void printTree() {
        int maxLevel = depth();

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private void printNodeInternal(List<TwoThreeNode> nodes, int level, int maxLevel) {
        if (treeSize == 0){
            System.out.println("Tree is Empty!");
        }
        if (nodes.isEmpty() || isAllElementsNull(nodes)) {
            return;
        }
        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(3, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(3, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(3, (floor + 1)) - 1;

        printWhitespaces(firstSpaces);

        List<TwoThreeNode> newNodes = new ArrayList<TwoThreeNode>();
        for (TwoThreeNode node : nodes) {
            if (node != null) {
                System.out.print("||" + node.lkey() + ":" + node.lval() + "|");
                if(node.rkey() != null)
                    System.out.print(node.rkey() + ":" + node.rval() + "||");
                else
                    System.out.print(" |");
                newNodes.add(node.lchild());
                newNodes.add(node.cchild());
                newNodes.add(node.rchild());
            } else {
                newNodes.add(null);
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

                if (nodes.get(j).lchild() != null) {
                    printWhitespaces(1);
                    System.out.print("/");

                } else
                    printWhitespaces(1);

                printWhitespaces(i + i - 1);

                if (nodes.get(j).cchild() != null) {
                    printWhitespaces(1);
                    System.out.print("|");

                } else
                    printWhitespaces(1);

                printWhitespaces(i + i - 1);

                if (nodes.get(j).rchild() != null) {
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



    private boolean isAllElementsNull(List list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }


    /** Main driver method that is used to test the 2-3 Tree
     * @param args cmd line args
     */
    public static void main(String[] args) {

//        String inputStr = "YLPMXHCRAESTBCA";
//        String inputStr = "ALGORITHMSXYZ";
//        String inputStr = "SEARCHEXAMPLE";
        String inputStr = "OHSFINALEXAMS";
        int maxLen = inputStr.length();
        TwoThreeTree<String, Integer> myTree = new TwoThreeTree<String, Integer>();
        for (int i = 0; i < maxLen; i++) {
            String key = new String(String.valueOf(inputStr.charAt(i)));
            System.out.println("After adding " + key + " to tree: ");
            myTree.insertKeyIntoTree(key, i+1);
//                    (myTree.root, key);
            myTree.printTree();

        }

    }


}
