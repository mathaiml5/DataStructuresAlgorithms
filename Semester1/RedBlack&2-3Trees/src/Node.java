// BST helper node data type
public class Node<Key extends Comparable<? super Key>, Value> implements BinaryTreeNode{
    private Key key;           // key
    private Value val;         // associated data
    private Node left;
    private Node right;

    private Node parent;  // links to left and right subtrees
    private boolean color;     // color of parent link
    private int size;          // subtree count


    public Node(Key key, Value val, boolean color, int size) {
        this.key = key;
        this.val = val;
        this.color = color;
        this.size = size;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Value getVal() {
        return val;
    }

    public void setVal(Value val) {
        this.val = val;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
        if(left != null) left.setParent(this);
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
        if(right != null)  right.setParent(this);
    }

    public boolean getColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
//        while(parent.getColor() && parent != null){ parent = parent.getParent(); }
        this.parent = parent;
    }




    /**
     * Visits the nodes in this tree in preorder.
     */
    public void traversePreorder(BinaryTreeNode.Visitor visitor) {
        visitor.visit(this);
        if (left != null)
            left.traversePreorder(visitor);
        if (right != null)
            right.traversePreorder(visitor);
    }

    /**
     * Visits the nodes in this tree in postorder.
     */
    public void traversePostorder(Visitor visitor) {
        if (left != null)
            left.traversePostorder(visitor);
        if (right != null)
            right.traversePostorder(visitor);
        visitor.visit(this);
    }

    /**
     * Visits the nodes in this tree in inorder.
     */
    public void traverseInorder(Visitor visitor) {
        if (left != null)
            left.traverseInorder(visitor);
        visitor.visit(this);
        if (right != null)
            right.traverseInorder(visitor);
    }

    public String toString(){
        Node left = getLeft();
        Node right = getRight();
        Node parent = getParent();

        return "NodeType: " + (getColor() ? "RED" : "BLACK") + ", Key: " + getKey().toString() + ", LeftNode: " + (left != null ? left.getKey().toString(): "null") + ", RightNode: " + (right != null ? getRight().getKey().toString() : "null") + ", Parent:" + (parent != null ? getParent().getKey().toString() : "null");
    }

    public int depth() {
        int depth = 0;
        while(getParent() != null ){
            if(! getParent().getColor()) depth += 1;
        }
        return depth;
    }
}
