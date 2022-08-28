## Assignment 3
### Linked List

LinkedListDeque class defines the Doubly Linked List and all the relevant methods to create the list, add/remove elements, and other operations
Uses an inner class DoubleNode to represent each node that has the element which can be any object (from class or type \<T\>)                  
Also includes a sample test method to test list operations on integers 

LinkedListTester class is the runner class that tests the various scenarios for operation performed on the LinkedListDeque Doubly Linked List such as add/remove elements, and other operations Since this is only a runner or tester class there are no member variables or methods needed                                                                                                                                        

The InteractiveTester class is the runner class that lets the user perform various list operations on the LinkedListDeque Doubly Linked List such as add/remove elements, and other operations
Since this is only a runner or tester class there are no member variables or methods needed
The program is designed to be friendly and prompt/guide the user at each stage:
1. A menu of commands 0-9 which represent various list operations available are presented
2. User can enter the commands they wish to explore.
3. Once user choose a particular command any additional inputs needed are presented
4. The user can interactively build their list and explore various list operations
5. At any time if the user wants to exit they can enter antything other than single digits that represent the command to exit
6. If the user enters anything other than 0-9 they might have entered in error so program asks for confirmation before exiting
Only includes a Main method which tests all list operations on a character list (implemented with String class to make it simple)
