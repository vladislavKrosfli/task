import java.util.Stack;

public class Parsing {
    private static String expression;
    public Parsing(String exp) {
        expression = exp;
    }

    public static int GetPriority(char operation) {
        if (operation == ')') return -1;
        else if (operation == '(') return 1;
        else if (operation == '+' || operation == '-') return 2;
        else if (operation == '/' || operation == '*') return 3;
        else return 0;
    }

    public static String ExpressionToRPN() {
        String expr = expression;
        String current = "";
        Stack<Character> stack = new Stack<>();
        int priority;

        for (int i = 0; i < expr.length(); i++) {
            priority = GetPriority(expr.charAt(i));
            if (priority == 0) {
                current += expr.charAt(i);
            }

            else if (priority == 1) {
                stack.push(expr.charAt(i));
            }

            else if (priority == -1) {
                current += " ";
                while (GetPriority(stack.peek()) != 1) {
                    current += stack.pop();
                }
                stack.pop();
            }

            else {
                current += " ";
                while(!stack.empty()) {
                    if (GetPriority(stack.peek()) >= priority) {
                        current += stack.pop();
                    } else {
                        break;
                    }
                }
                stack.push(expr.charAt(i));
            }
        }

        while (!stack.empty()) {
            current += stack.pop();
        }

        return current;
    }
    public static Double parseAnswer() {
        String rpn = ExpressionToRPN();
        String operand = new String();
        Stack<Double> stack_ = new Stack<>();

        for (int i = 0; i < rpn.length(); i++) {
            if (rpn.charAt(i) == ' ') {
                continue;
            }

            if (GetPriority(rpn.charAt(i)) == 0) {
                while (rpn.charAt(i) != ' ' && GetPriority(rpn.charAt(i)) == 0) {
                    operand += rpn.charAt(i++);
                    if (i == rpn.length()) {
                        i--;
                        break;
                    }
                }
                stack_.push(Double.parseDouble(operand));
                operand = new String();
            }

            if (GetPriority(rpn.charAt(i)) > 1) {
                double a = stack_.pop();
                double b = stack_.pop();

                if (rpn.charAt(i) == '+')
                    stack_.push( b + a);
                if (rpn.charAt(i) == '-')
                    stack_.push(b - a);
                if (rpn.charAt(i) == '*')
                    stack_.push(b * a);
                if (rpn.charAt(i) == '/')
                    stack_.push(b / a);
            }
        }
        return stack_.pop();
    }
}