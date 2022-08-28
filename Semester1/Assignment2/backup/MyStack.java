
import java.util.ArrayList;

public class MyStack<T> {

    private ArrayList<T> stackElements;
    private int top;


    public MyStack() {
        stackElements = new ArrayList<T>();
        top = -1;
    }

    public void push(T el) {
        stackElements.add(el);
        ++top;
    }

    public T pop() {
        T el = stackElements.get(top);
        stackElements.remove(top);
        top--;
        return el;
    }

    public T peek() {
        return stackElements.get(top);
    }

    public boolean isEmpty() {
        return (top == -1);
    }

    public void printStack(){
        System.out.println("Stack Contents: ");
        System.out.println("****************");
        for (int i = top; i >= 0; i--) {
            System.out.println(stackElements.get(i));
        }
        System.out.println("****************");
        System.out.println("top is: " + top);

    }

    public static void main(String[] args) {
        MyStack<Integer> st = new MyStack<Integer>();
        System.out.println("Adding 1st element: 2");
        st.push(2);
        st.printStack();
        System.out.println("Adding 2nd element: 5");
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
