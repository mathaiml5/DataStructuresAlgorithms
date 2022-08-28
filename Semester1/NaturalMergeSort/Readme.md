## Assignment 7

### Natural Merge Sort (NMS)
Natural Mergersort is an efficent version of the mergesort which leverages the fact that some subsequences in the LinkedList could already be sorted. By combining these already sorted sublists, we can build partially sorted lists and reduce the      
running time to O(n log n). The program design is as follows:                                                                                            
1. First we create a list (String list from letters of MERGESORTEXAMPLE created in the test example required in the problem)              
2. Then we iteratively repeat the steps 2a-2c until the entire list is sorted:                                               
2a. Traverse the list from head node, looking for sublist pairs of already sorted                                            
2b. Once we have a pair of sorted sublists we merge them.                                                                    
2c. Continue traversing list until end of input list is reached                                                              
3. Once iterations are complete and there are no more sublists to merge we are done!                                         

### NMS Tester
TesterNaturalMergesort is a tester class that prompts the user to input a comma separated list of numbers or strings and sorts them using natural mergesort. We allow the user to enter a list of string or integers or decimals. We then infer what type of elements are entered in the user's list. For example, we will need to determine if list has numbers which include commas in them (such as what you would see with integers with commas separating the 1000s blocks) ot the strings themselves have commas within them such as addresses. Users can enter such special numbersor strings by enclosing the entire element within double quotes. The input list is first split on commas that are not within quotes. Then each element is checked to see if it is quoted and then quotes are stripped. Then we determine the type of list (integer, decimal or string) and proceed to sort the list based on natural mergesort        
                                                                                                                                             
### LinkedList Deque
All the methods and class variables from Assignment #3 on LinkedList are reused.

The LinkedListDeque class defines the Doubly Linked List and all the relevant methods to create the list, add/remove elements, and other operations. It uses the class DoubleNode to represent each node that has the element which can be any object (from class or type \<T\>)                         
Also includes a sample test method to test list operations on integers                                                                                                                                     
2 new methods are added:                                                                                                                        
1. append: Appends another linked list to current list.                                                                                        
2. removeSubList: Delete a subList contained within specified start and end DoubleNode boundaries                                              
