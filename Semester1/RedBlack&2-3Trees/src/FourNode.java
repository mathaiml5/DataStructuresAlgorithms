/*  The 4-Node class that occurs during construction/update of a 2-3 Tree as outlined in Section 3.3. of Sedgewick et. al Algorithms 4th Edition
 *  1. 4- Node class directly uses keys as the node contents or value and does not have a key-value pairs stored in node
 *  2. This node class is the built as outlined in Section 3.3. of Sedgewick et. al Algorithms 4th Edition which
 *     adds additional center key and a 2nd center child pointer
 */

public class FourNode<Key extends Comparable<? super Key>, Value extends Comparable<? super Value>>  extends TwoThreeNode{
    private Key mkey;        // The node's middle key



    private Value mval;
    private TwoThreeNode<Key, Value> center2;

    public FourNode() {
    }

    /** Constructor for Four Node
     * @param lk left key
     * @param mkey middle key
     * @param rk right key
     * @param leftNode left subtree child node pointer
     * @param centernode 1st middle subtree child node pointer
     * @param centernode2 2nd middle subtree child node pointer
     * @param rightNode right subtree child node pointer
     */
    public FourNode(Key lk, Value lval, Key mkey, Value mval, Key rk, Value rval, TwoThreeNode<Key, Value> leftNode, TwoThreeNode<Key, Value> centernode, TwoThreeNode<Key, Value> centernode2, TwoThreeNode<Key, Value> rightNode) {
        super(lk, lval, rk, rval, leftNode, centernode, rightNode);
        this.mkey = mkey;
        this.mval = mval;
        this.center2 = centernode2;
    }

    //Getter and setter for the 2 additional member variables middle key and 2nd middle subtree node
    public Key mkey() {
        return mkey;
    }

    public void setMkey(Key mkey) {
        this.mkey = mkey;
    }

    public Value mval() {
        return mval;
    }

    public void setMval(Value mval) {
        this.mval = mval;
    }

    public TwoThreeNode<Key, Value> cchild2() {
        return center2;
    }

    public void setCenterChild2(TwoThreeNode<Key, Value> center2) {
        this.center2 = center2;
    }
}
