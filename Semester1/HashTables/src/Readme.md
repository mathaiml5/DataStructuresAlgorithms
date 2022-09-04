## Design and Implementation Notes
### Linear probing hash table
The main class for linear Probing Hash symbol table. Adapted from companions website for textbook Sedgewick et. al Algorithms, 4th Edition. https://algs4.cs.princeton.edu/34hash/LinearProbingHashST.java.html                                               
Following improvements were made to the code:                                                                    
 1.  Member variables to track what is the max loading threshold value of (alpha = n/m) to trigger a resizing of   
    hash table and total number of probes when inserting a set of keys into hash table                            
 2.  Method to calculate the average number of probes (= total probes/ size of table) when all keys have been input

