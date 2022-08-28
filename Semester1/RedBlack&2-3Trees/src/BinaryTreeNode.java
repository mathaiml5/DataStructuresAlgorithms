public interface BinaryTreeNode<E> {

    /**
     * Visits the nodes in this tree in preorder.
     */
    void traversePreorder(Visitor visitor);

    /**
     * Visits the nodes in this tree in postorder.
     */
    void traversePostorder(Visitor visitor);

    /**
     * Visits the nodes in this tree in inorder.
     */
    void traverseInorder(Visitor visitor);

    /**
     * Simple visitor interface.
     */
    public interface Visitor {
        <E> void visit(Node node);
    }
}
