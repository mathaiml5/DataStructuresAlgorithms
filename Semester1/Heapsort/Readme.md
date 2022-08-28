## Assignment 8

### Heapsort
Implement a version of heapsort based on complete heap-ordered 3-ary as described in the text. 
Test your implementation using 100 randomly ordered distinct keys.

#### Heapsort with a generic type of Key
HeapSort_dAry\<Key\> is the class where d-ary heapsort algorithm is implemented 
The code from textbook for heap is enhanced to include d-ary heaps with modifications to the sink and swim functions
In particular, in the bottoms up reheapify swim function, for d-ary trees, parent of key k is  key whose id is $\lfloor {\frac{(k+d-2)}{d} \rfloor}$

### Heapseort Tester 
The heapsort tester class produces an interactive menu of options and performs the testing of the various functionalities based on user's input                                                                                                                                 
The program asks the user for a choice of numeric or string input and tests the heapsort algorithm for any multiway (d-ary) heap: For example specifying d=2 creates a binary heap and specifying d=3 builds and sorts a list of items using a ternary heap.                     
The user can either input a string of characters which they wish to use to create the heap (option #2) OR it can generate random list of numbers with a user specified range (option #1)                                                                 
The program creates maximum order Priority Queue and sorts them in-place using d-ary heapsort.                                                 
The program prints the output for each step of heapify and each sink step in the heapsort algorithm                                            
There is also horizontal dendrogram style visualization of the final n-ary heap sorted tree as well as the array contents during each step (Note: the array contents are preceded by 2 numbers N and k where N=element that is swapped and k is the node from which sink is currently taking place)                                                                                                     
