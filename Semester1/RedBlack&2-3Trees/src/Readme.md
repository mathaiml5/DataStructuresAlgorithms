## Design and Implementation Notes

### Node
Helper node data type adapted from Assignment 9 (BST)

### BinaryTreeNode
public interface BinaryTreeNode<E> defines abstract methods for preorder, postorder, and inorder traversal
 
### FourNode
The 4-Node class that occurs during construction/update of a 2-3 Tree as outlined in Section 3.3. of Sedgewick et. al Algorithms 4th Edition.
 1. 4- Node class directly uses keys as the node contents or value and does not have a key-value pairs stored in node
 2. This node class is the built as outlined in Section 3.3. of Sedgewick et. al Algorithms 4th Edition which adds additional center key and a 2nd center child pointer
 
### TwoThreeNode
The main 2-3 tree Node class that is used to construct 2-3 Tree as outlined in Section 3.3. of Sedgewick et. al Algorithms 4th Edition with few modifications:
 1. Simplified 2-3 Node class directly uses keys as the node contents or value and does not have a key-value pairs stored in node. Node class was made its own separate class for reuse and all the methods which directly assigned member variable values now are converted to using getters and setters. 
 2. This node class is the parent of the 4-node class as outlined in Section 3.3. of Sedgewick et. al Algorithms 4th Edition which adds additional center key and a 2nd center child pointer                                                                          
 3. Class has the key add method to add new item keys into node and fixup the node pointers                                                  
 4. The program prints the output for each step of adding an item to the tree. There is also vertical dendrogram style visualization of the final RB tree at each step  
 
### TwoThreeTree
The main 2-3 tree class as outlined in Section 3.3. of Sedgewick et. al Algorithms 4th Edition                                              
  1. Uses Simplified versions of TwoThreeNode class and FourNode class as outlined in Section 3.3. of Sedgewick et. al Algorithms 4th Edition 
  2. has the key add method to add new item keys into node and fixup the node pointers                                                        
  3. The program prints the output for each step of adding an item to the tree. There is also verical dendrogram style visualization          
   of the final RB tree at each step 
   
### RedBlackBST
RedBlackBST\<Key extends Comparable\<? super Key\>, Value\>

The main class for the Red-Black BST developed from code in Section 3.3, Problems 3.39-3.41 of Sedgewick et. al Algorithms 4th Edition. Following modifications and enhancements were done in developing this class:
 1. The Node class was made its own separate class for reuse and all the methods which cdirectly assigned member variable values now are converted to using getters and setters. 
 2. Several traversal methods were added to help with Inorder, Postorder and Preorder traversal.
 3. The program provides the a choice of numeric or string input and tests the RB BST algorithm for both scenarios. The user can either input a string of characters which they wish to use to create the tree (option #2) OR program can generate an ordered list of numbers in the range 1..n that user can use (option #1)
 4. The program prints the output for each step of adding an item to the tree. There is also verical dendrogram style visualization of the final RB tree at each step.  
