## Assignment 11

## Hash tables
### Problem1: Hashing with Separate Chaining
Trace of inserting EXAMQUSTION in that order into an initially empty table of M = 5 lists, using separate chaining. Used the hash function 11 k % M to transform the kth letter of the alphabet into a table index (with A=1, B=2 etc.)

### Problem2: Hashing with Linear Probing
Trace of inserting EXAMQUSTION in that order into an initially empty hash table of size M =16 using linear probing. Used the hash function 11 k % M to transform the kth letter of the alphabet into a table index (with A=1, B=2 etc.)

### Problem 3: Spell Checker
Implement a spelling checker by using a hash table. Create a dictionary of correctly spelled words. You can read the words from a file words.txt.
Then write a driver program that prompts you to type a word and checks for misspelled words. If the word is spelled correctly it should out “no mistakes found”. For misspelled words it should list any words in the dictionary that are obtainable by applying any of the following rules:
* a. Add one character to the beginning
* b. Add one character to the end
* c. Remove one character from the beginning
* d. Remove one character from the end
* e. Exchange adjacent characters
