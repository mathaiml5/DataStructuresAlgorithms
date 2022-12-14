/**
 * The ArithmeticExpressionEvaluator class first uses the MyStack class to convert an expression in Infix notation to
 * Postfix notation, and then evaluates the postfix expression.
 * @author: Vishak Srikanth
 * @version: 9/13/2021
 */
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class ArithmeticExpressionEvaluator {


    //Note the constructor for the class takes the input infix string and creates both the output ArrayList and the
    //output string. Since the constructor already computes and sets the private output fields, we do not provide traditional
    //public setter methods which intialize these values as it can be error prone with user input
    private ArrayList<String> postFixList = new ArrayList<String>();
    private String infixString = new String();
    private String postfixString = new String();

    /**
     * public String getInfixString():
     * Getter for the infixString input variable
     * @return : infixString private variable
     */
    public String getInfixString() {
        return infixString;
    }

    /**
     * public String getPostfixString():
     * Getter for the computed postFixString private variable
     * @return : postfixString private variable
     */
    public String getPostfixString() {
        return postfixString;
    }

    /**
     * public ArithmeticExpressionEvaluator(String infix):
     * The constructor takes the input infix string and creates the postfix ArrayList and by calling private createPostfixListfromInfix method
     *  Constructor also creates the postFix string from postfix Array List
     *  Note: to print the converted postfix string we will need to call getPostfixString public method
     * @param infix : input String which has infix notation
     */
    public ArithmeticExpressionEvaluator(String infix) {
        infixString = infix;
        createPostfixListfromInfix(infix);
        postfixString = infixToPostfix();
    }

    /**
     * public int getPrecedence(char c)
     * Utility method that gets precedence of an operator: highest precedence for ^ (exponent) , then *,/ (multiply/divide) and then +,- (add/subtract)
     * as per arithmetic rules
     * @param c : current operator character read from input string
     * @return
     */
    public int getPrecedence(char c){
        if(c=='+'|| c=='-') return 1;
        else if(c=='*' || c=='/') return 2;
        else if(c=='^') return 3;
        else return -1;
    }

    /**
     * boolean isUnaryMinus(char curr, char prev, int i):
     * @param curr, the current character
     * @param prev, the previous character
     * @param i, whether the character is the first character in a string
     * @return a boolean, whether a minus is a binary unus
     */
    boolean isUnaryMinus(char curr, char prev, int i){
        //For unary minus either the preceding character is an operator, or open paranthesis or it is the 1st character in the string
        return curr == '-' && (i==0 || getPrecedence(prev) > 0 || prev == '(');
    }

    /**
     * private void createPostfixListfromInfix(String s):
     * The algorithm populates the private ArrayList of strings in PostFix from from input Infix String as follows:
     * If the character is operand, append character to the current element of string in ArrayList
     * If the character is operator:
     * 1. If the stack is empty push this operator into the stack.
     * 2. Else, Pop all the operators from the stack which are greater than or equal to in precedence than that of the current operator.
     *    Then push the current operator to the stack.
     * 3. If character is openings paranthesis '(', push it into the stack.
     * 4. If character is closing paranthesis ')', pop all elements from the stack until matching '(' is reached while adding each character
     *    into the ArrayList as a separate element.
     * @param: s, the string we want to create a Postfix list out of
     */
    private void createPostfixListfromInfix(String s) {
        MyStack<Character> mystack = new MyStack<>();
        char prevChar = '\0';
        boolean inNumSeqFlag = false;
        //loop through the string s
        for (int i = 0; i < s.length(); i++) {
            char word = s.charAt(i);
            //If this is a whitespace char like blank, tab, etc. skip
            if (Character.isWhitespace(word)) {
                continue;
            }
            //If open parenthesis:
            if (word == '(') {
                mystack.push(word);
                inNumSeqFlag = false;
//DEBUG                System.out.println("After processing ( stack is:");
//DEBUG                mystack.printStack();
            }
            //If close parenthesis:
            else if (word == ')') {
                inNumSeqFlag = false;
//DEBUG                System.out.println("Begin processing ) stack is:");
                while (!mystack.isEmpty()) {
                    if (mystack.peek() == '(') {
                        mystack.pop();
//DEBUG                        mystack.printStack();
                        break;
                    } else {
//DEBUG                        System.out.println("Processing (:" + word);
//DEBUG                        System.out.println("popping & adding to list -> " + mystack.peek());
                        postFixList.add(mystack.pop() + "");
//DEBUG                        mystack.printStack();
                    }
                }
            } else if (getPrecedence(word) > 0 && (! isUnaryMinus(word, prevChar, i))) {
                inNumSeqFlag = false;
                if (mystack.isEmpty()) {
//DEBUG                    System.out.println("Empty stack encountered while processing op: " + word);
                    mystack.push(word);
                    //DEBUG                   System.out.println("Stack after processing op: " + word);
//DEBUG                    mystack.printStack();

                } else {
//DEBUG                    System.out.println("Checking lower precedence op: ");
                    while (!mystack.isEmpty() && getPrecedence(mystack.peek()) >= getPrecedence(word)) {
//DEBUG                        System.out.println("top of stack has: " + mystack.peek() + " has higher precedence than current op: " + word);
//DEBUG                        System.out.println("popped -> " + mystack.peek());
                        postFixList.add(mystack.pop() + "");
//DEBUG                        System.out.println(" After step in processing op: " + word + " Stack is:");
//DEBUG                        mystack.printStack();
                    }
//DEBUG                    System.out.println("current operator is " + word);
                    mystack.push(word);
//DEBUG                    System.out.println(" After completion of op: " + word + " Stack is:");
//DEBUG                    mystack.printStack();
                }
            } else {
                if(Character.isDigit(word) || word == '.' || word == '-') {
                    if (inNumSeqFlag) {
                        String lastNumber = postFixList.get(postFixList.size() - 1);
                        lastNumber += word;
                        postFixList.set(postFixList.size() - 1, lastNumber);
//DEBUG                    System.out.println("Last Number: " + lastNumber);
                    } else
                        postFixList.add(word + "");
                    inNumSeqFlag = true;
                }
            }
            prevChar = word;


        }
        while (!mystack.isEmpty()) {
//DEBUG            System.out.println("After reading all input and processing we still have non-empty stack, so popping -> " + mystack.peek());
            postFixList.add(mystack.pop() + "");
//DEBUG            mystack.printStack();
        }
//DEBUG        System.out.println("Final list: " + postFixList);
    }

    /**
     * private String infixToPostfix():
     * Create a single string that represents the postfix expression corresponding to the postfix ArrayList
     * @return postfix format of the infix input string
     */
    private String infixToPostfix(){
        String postfix = new String();
        for(String a: postFixList){
            postfix = postfix + " " + a;
        }
        return postfix;
    }


    /**
     * public double postfixCalculator():
     * The postfixCalculator() method computes the result of a computation from a stack implemented using an arraylist
     * @return the result from the computation, a double
     */
    public double postfixCalculator(){
        MyStack<Double> stack = new MyStack<>();
        //loop over postFixList
        for(int i=0;i<postFixList.size();i++){
            String word = postFixList.get(i);

            if(word.length()==1 && (word.charAt(0)=='+'||word.charAt(0)=='-'||word.charAt(0)=='*'||word.charAt(0)=='/' || word.charAt(0)=='^')){
                Double number2 = stack.pop();
                Double number1 = stack.pop();
                //If the next character is a plus, perform an addition operation and push the result back into the stack.
                if(word.charAt(0)=='+'){
                    Double ans = number1 + number2;
                    stack.push(ans);
                }
                //If the next character is a minus, perform an subtraction operation and push the result back into the stack.
                else if(word.charAt(0)=='-'){
                    Double number = number1 - number2;
                    stack.push(number);
                }
                //If the next character is a asterisk, perform an multiplication operation and push the result back into the stack.
                else if(word.charAt(0)=='*'){
                    Double number = number1*number2;
                    stack.push(number);
                }
                //If the next character is a forward slash, perform a division operation and push the result back into the stack.
                else if(word.charAt(0)=='/'){
                    Double number = number1/number2;
                    stack.push(number);
                }
                //If the next character is a carrot,  perform an exponentiation operation and push the result back into the stack.
                else if(word.charAt(0)=='^') {
                    Double number = Math.pow(number1, number2);
                    stack.push(number);
                }
            }else{
                Double number = Double.valueOf(word);
                stack.push(number);
            }
        }
        return stack.peek();
    }



    // Main driver method for getting user input and running the infix to postfix expression and expression evaluator multiple times
    public static void main(String[] args) {


//        String expr = "( 10.25    ^4 - 123.79*50.23 /(0.456*78900))";

        System.out.println("***************************************************************************************************");
        System.out.println("*********************** ARITHMETIC EXPRESSION CALCULATOR ******************************************");
        System.out.println("***************************************************************************************************");
        System.out.println("Please enter an Infix expression to convert to Postfix and evaluate at the prompt with a RETURN or");
        System.out.println("ENTER key at the end of the expression. For example (41-3*(85/4))");
        System.out.println("You can use any valid positive or negative decimal numbers for the operands. The basic arithmetic ");
        System.out.println("operations addition(+), subtraction(-), multiplication(*), division(/), or ");
        System.out.println("exponent(^) are allowed. You can also use any whitespace characters space, tab etc.");
        System.out.println("***************************************************************************************************");

        String infixStr;
        boolean invalidInput = true;
        boolean done = false;
        while(!done) {

            // Keep prompting for a valid infix string until user enters a string that has only valid decimal operands, arithmetic operators, and parenthesis
            do {
                System.out.print("Enter Infix Expression> ");
                Scanner inp = new Scanner(System.in);

                infixStr = inp.nextLine();
                //Check if valid string: only digits, whitespace, decimal, and operators are allowed
                //I am using java regex that looks for a string sequence that only has 0 or more occurrences of the following characters:
                //digits 0-9 (\d), whitespace( space, tab), opening or closing parenthesis( or ), .(decimal), plus(+), minus(-), multiply(*), division(/), exponent(^).
                if (infixStr.matches("[\\s\\d()\\+\\-\\*\\/\\^\\.]*$")) {
                    invalidInput = false;
                    if((infixStr.split("\\(",-1).length-1)!=(infixStr.split("\\)",-1).length-1)){
                        invalidInput = true;
                        System.out.println("Invalid input. Please enter a valid infix expression which contains balanced parenthesis");
                    }
                }
                //print error message and prompt user to re-enter a valid infix expression if the expression is invalid
                else {
                    System.out.println("Invalid input. Please enter a valid infix expression which contains only valid decimal numbers (0-9) and valid arithmetic operators (+, -, *, / , ^)  and parenthesis ()");
                }



            } while (invalidInput);


            //Constructor parses input expression and creates postfix ArrayList and postfix string!
            ArithmeticExpressionEvaluator arithEval = new ArithmeticExpressionEvaluator(infixStr);

            /** DEBUG  Alternative method to parse infixString and generate ArrayList using Java regex for additional validation
             ***        String[] split = expr.split("(?<=[\\d.])(?=[()\\+\\-\\*\\/\\^]{1})|(?<=[()\\+\\-\\*\\/\\^]{1})(?=[\\d.])");
             ***        System.out.println(Arrays.toString(split));
             **/


            System.out.println("You Entered> " + arithEval.getInfixString());


            //To print the converted postfix string, we only need to call the get method since cosntructor already has created it!
            System.out.println("Postfix Expression> " + arithEval.getPostfixString());
            double result = arithEval.postfixCalculator();

            DecimalFormat format = new DecimalFormat("0.###"); // Choose the number of decimal places to work with in case they are different than zero and zero value will be removed
            format.setRoundingMode(RoundingMode.UP); // Choose your Rounding Mode
            System.out.println("Calculator Answer> " + format.format(result));
            System.out.println("***************************************************************************************************");
            //ask the user if they want to enter another expression
            System.out.print("Do more calculations? (Enter Y for yes and any other key to stop)> ");
            Scanner cont = new Scanner(System.in);
            String choice = cont.next();
            if((choice.length() == 1) && choice.equalsIgnoreCase("Y")){
                continue;
            } else{
                done = true;
            }


        }
    }


}

