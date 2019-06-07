package com.example.myapplication;

public class Testing {
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
                if (pos>-1 && pos<str.length())System.out.println(str.charAt(pos));
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = pExp();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }
            double pExp() {
                double x = pTerm();
                for (;;) {
                    if      (eat('+')) x += pTerm(); // addition
                    else if (eat('-')) x -= pTerm(); // subtraction
                    else return x;
                }
            }

            double pTerm() {
                double x = pFac();
                for (;;) {
                    if      (eat('*')) x *= pFac(); // multiplication
                    else if (eat('/')) x /= pFac(); // division
                    else return x;
                }
            }

            double pFac() {
                if (eat('+')) return pFac(); // unary plus
                if (eat('-')) return -pFac(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = pExp();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }



                return x;
            }
        }.parse();
    }
    public static void main(String[] args){
        System.out.println(eval("4+1"));
    }
}
