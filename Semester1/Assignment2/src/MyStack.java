/**
 * The MyStack class implements a stack using an arraylist, which has elements that are of a generic type, in order to help with converting infix notation
 * to postfix notation and uses the stack in computing the result from a stack in postfix notation.
 * @Author: Vishak Srikanth
 * @Version: 9/13/2021
 */

import java.util.ArrayList;
import java.util.EmptyStackException;

public class MyStack<T> {
    // initialize an arraylist with elements that are of a generic type
    private ArrayList<T> stackElements;
    /**
    *use a variable of an integer type called top that represents the index of the element in the array list that is
    *at the top of the stack
     */
    private int top;


    /**
     * The constructor for class MyStack creates a arraylist of a generic type and sets the index of the top element to be
     * -1.
     */
    public MyStack() {
        stackElements = new ArrayList<T>();
        top = -1;
    }

    /**
     * public void push(T el)
     * @param el, an element we want to add to the top of the stack
     * adds an element to the arraylist of elements in the stack, and one to top, the index in the arraylist that is at the top of the stack
     */
    public void push(T el) {
        stackElements.add(el);
        ++top;
    }
    /**
     * public T pop()
     * removes an element from the arraylist of elements in the stack, and subtracts one from top, the index in the arraylist that is at the top of the stack
     * throws EmptyStackException when you try to pop an empty stack
     * @return: el,
     */
    public T pop() {

        T el = null;
        try {
            el = stackElements.get(top);
        } catch (EmptyStackException e) {
            e.printStackTrace();
        }
        stackElements.remove(top);
        top--;
        return el;
    }

    /**
     * public T peek():
     * @return: a variable of type T, the top element in the arraylist, throws EmptyStackException if you try to peek on empty stack
     */
    public T peek() {
        T el = null;
        try {
            el = stackElements.get(top);
        } catch (EmptyStackException e) {
            e.printStackTrace();
        }
        return el;
    }

    /**
     * public boolean isEmpty():
     * @return: a boolean, whether the stack is empty
     */
    public boolean isEmpty() {
        //check whether top is -1 to see if the stack is empty
        return (top == -1);
    }

    /**
     * public void printStack():
     * prints the elements in the stack from top to bottom
     */
    public void printStack(){
        System.out.println("Stack Contents: ");
        System.out.println("****************");
        //loop over the arraylist stackElements and print the elements in the stack from top to bottom
        for (int i = top; i >= 0; i--) {
            System.out.println(stackElements.get(i));
        }
        System.out.println("****************");
        System.out.println("top is: " + top);

    }

    //Main driver method to test stack implementation works
    public static void main(String[] args) {
        //test the methods on a sample stack st
        MyStack<Integer> st = new MyStack<Integer>();
        System.out.println("Adding 1st element to stack: 2");
        st.push(2);
        st.printStack();
        System.out.println("Adding 2nd element to stack: 5");
        st.push(5);
        st.printStack();
        Integer t = st.pop();
        System.out.println("Popping the stack gives: " + t);
        st.printStack();
        System.out.println("Peeking on stack now yields: "  + st.peek());
        st.printStack();
        t = st.pop();
        System.out.println("Popping the stack gives: " + t);
        st.printStack();
        System.out.println("Is stack empty? " + (st.isEmpty() ? "YES" : "NO"));

    }

}
