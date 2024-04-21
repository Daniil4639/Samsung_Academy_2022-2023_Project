package com.example.mathjourney;

import java.util.Formattable;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;

public class Solution {
    // активность, в которой происходит обработка польской записи выражения

    static double factorial(double arg){
        long result = 1;
        int i = 1;
        while (i<=arg){
            result*=i;
            i++;
        }
        return result;
    }

    static boolean isDelim(char c) { // тру если пробел
        return c == ' ';
    }

    static boolean isOperator(char c) { // возвращяем тру если один из символов ниже
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c =='^' || c == 'l' || c == 'n' || c == 'f' || c == 'a' || c == 's' || c == 'c' || c == 't' || c == 'q' || c == 'u' || c == 'i' || c == 'o' || c == 'p';
    }

    static int priority(char op) {
        switch (op) { // при + или - возврат 1, при * / % 2 иначе -1
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            case '^':
                return 3;
            case 'l':
            case 'n':
            case 'f':
            case 'a':
            case 's': // sin
            case 'c': // cos
            case 't': // tg
            case 'q': // ctg
            case 'u': // asin
            case 'i': // acos
            case 'o': // atg
            case 'p': // actg
                return 4;
            default:
                return -1;
        }
    }

    static void processOperator(LinkedList<String> st, char op, double arg) {
        String rStr, lStr;
        double r;
        double l = 1;

        rStr = st.removeLast();
        if (rStr.equals("x")){
            r = arg;
        }
        else if (rStr.equals("π")){
            r = Math.PI;
        }
        else if (rStr.equals("e")){
            r = Math.E;
        }
        else{
            r = Double.parseDouble(rStr);
        }

        if (op!='l' && op!='n' && op!='a' && op!='f' && op!='s' && op!='c' && op!='t' && op!='q' && op!='u' && op!='i' && op!='o' && op!='p') {
            lStr = st.removeLast();
            if (lStr.equals("x")){
                l = arg;
            }
            else if (lStr.equals("π")){
                l = Math.PI;
            }
            else if (lStr.equals("e")){
                l = Math.E;
            }
            else{
                l = Double.parseDouble(lStr);
            }
        }

        switch (op) { // выполняем действие между l и r в зависимости от оператора в кейсе и результат валим в st
            case '+':
                st.add(Double.toString(l + r));
                break;
            case '-':
                st.add(Double.toString(l - r));
                break;
            case '*':
                st.add(Double.toString(l * r));
                break;
            case '/':
                st.add(Double.toString(l / r));
                break;
            case '%':
                st.add(Double.toString(l % r));
                break;
            case '^':
                st.add(Double.toString(Math.pow(l, r)));
                break;
            case 'l':
                st.add(Double.toString(Math.log10(r)));
                break;
            case 'n':
                st.add(Double.toString(Math.log(r)));
                break;
            case 'a':
                st.add(Double.toString(Math.abs(r)));
                break;
            case 'f':
                st.add(Double.toString(factorial(r)));
                break;
            case 's':
                st.add(Double.toString(Math.sin(r)));
                break;
            case 'c':
                st.add(Double.toString(Math.cos(r)));
                break;
            case 't':
                st.add(Double.toString(Math.tan(r)));
                break;
            case 'q':
                st.add(Double.toString(1.0 / Math.tan(r)));
                break;
            case 'u':
                st.add(Double.toString(Math.asin(r)));
                break;
            case 'i':
                st.add(Double.toString(Math.acos(r)));
                break;
            case 'o':
                st.add(Double.toString(Math.atan(r)));
                break;
            case 'p':
                st.add(Double.toString(Math.PI/2 - Math.atan(r)));
                break;
        }
    }

    public static double eval(String s, double arg) {
        LinkedList<String> st = new LinkedList<String>(); // сюда наваливают цифры
        LinkedList<Character> op = new LinkedList<Character>(); // сюда опрераторы и st и op в порядке поступления
        for (int i = 0; i < s.length(); i++) { // парсим строку с выражением и вычисляем
            char c = s.charAt(i);
            if (isDelim(c))
                continue;
            if (c == '(')
                op.add('(');
            else if (c == ')') {
                while (op.getLast() != '(')
                    processOperator(st,op.removeLast(), arg);
                op.removeLast();
            } else if (isOperator(c)) {
                if ( c == 'l' && s.charAt(i+1) == 'n'){
                    c = 'n';
                }
                else if ( c == 'c' && s.charAt(i+1) == 't'){
                    c = 'q';
                }
                else if ( c == 'a' && s.charAt(i+1) == 's'){
                    c = 'u';
                }
                else if ( c == 'a' && s.charAt(i+1) == 'c'){
                    c = 'i';
                }
                else if ( c == 'a' && s.charAt(i+1) == 't'){
                    c = 'o';
                }
                else if ( c == 'a' && s.charAt(i+1) == 'c' && s.charAt(i+2) == 't'){
                    c = 'p';
                }
                if ( c == '-'){
                    boolean minusOk = false;
                    int j = i - 1;
                    while (j >= 0){
                        if (s.charAt(j) == ' '){
                            j--;
                        }
                        else if (Character.isDigit(s.charAt(j)) || s.charAt(j) == 'x'){
                            minusOk = true;
                            break;
                        }
                        else{
                            break;
                        }
                    }
                    if (!minusOk){
                        st.add("0");
                    }
                }
                while (!op.isEmpty() && priority(op.getLast()) >= priority(c))
                    processOperator(st, op.removeLast(), arg);
                op.add(c);
                if ( c == 'l' || c == 'a' || c == 's' || c == 'c' || c == 'q' || c == 'o'){
                    i+=2;
                }
                else if ( c == 'f' || c == 'u' || c == 'i' || c == 'p'){
                    i+=3;
                }
                else if ( c == 'n' || c == 't'){
                    i++;
                }
            }
            else if (c == 'x'){
                st.add("x");
            }
            else if (c == 'π'){
                st.add("π");
            }
            else if (c == 'e'){
                st.add("e");
            }else {
                String operand = "";
                while (i < s.length() && ((Character.isDigit(s.charAt(i)) || s.charAt(i)=='.')))
                    operand += s.charAt(i++);
                --i;
                st.add(operand);
            }
        }
        while (!op.isEmpty())
            processOperator(st, op.removeLast(), arg);
        String res = st.get(0);
        if (res.equals("x")){
            return arg;
        }
        else if (res.equals("e")){
            return Math.E;
        }
        else if (res.equals("π")){
            return Math.PI;
        }
        else{
            return Double.parseDouble(st.get(0));  // возврат результата
        }
    }
}
