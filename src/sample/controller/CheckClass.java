//////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Выполнена студенткой группы 821703 БГУИР Банцевич Ксенией Андреевной
// Algorithm for checking a formula on SDNF
// 15.03.2021 v2.0
//
package sample.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;


public class CheckClass {

    private static final String allSymbols = "()/\\!ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String conjunction = "/\\";
    private static final String disjunction = "\\/";
    private static final String brackets = "()";

    private static final int zero = 0;
    private static final int one = 1;
    private static final int two =2;

    public static int checkFormula(String formula) {

        if (formula == null || formula.equals("")) {

            return two;
        }

        for (int i = 0; i < formula.length(); i++) {
            if (allSymbols.indexOf(formula.charAt(i)) == -1) { //некорретные символы

                return one;
            }
        }


        int counter = 0;
        for (int i = 0; i < formula.length(); i++) { //проверяем на правильную последовательность скобок
            if (formula.charAt(i) == '(')
                counter++;
            else if (formula.charAt(i) == ')')
                counter--;
            if (counter < 0)
                return two;

        }
        if (counter != 0)
            return two;


        String[] terms = breakTerms(formula,disjunction);  //делим на слагаемые
        String[] terms2 = new String[terms.length];  //избавляемся от скобок

        int k = 0;
        for (String term : terms) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < term.length(); i++) {
                if (brackets.indexOf(term.charAt(i)) == -1)
                    stringBuilder.append(term.charAt(i));
            }
            terms2[k] = stringBuilder.toString();
            k++;
        }
        System.out.println(Arrays.toString(terms2));

        for (int i = 0; i < terms2.length; i++) {
            for (int j = i + 1; j < terms2.length; j++) {
                if (terms[i].equals(terms[j])) //проверяем,есть ли одинаковые слагаемые
                  return one;

            }
        }

        for (String term : terms2)
            for (int i = 0; i < term.length() - 1; i++) {
                if (term.charAt(i) == term.charAt(i + 1)) //повторение знаков, например,(AA&B)
                   return two;

            }

        String[] multipliers;
        String[] firstMultipliers = new String[0];
        int multiplierCount = 0;

        for (String term : terms2) {
            multipliers = breakTerms(term,conjunction); //делим слагаемое на отдельные "формулы"



            if (multiplierCount == 0) {
                multiplierCount = multipliers.length;
                firstMultipliers = multipliers;
            } else {
                if (multiplierCount != multipliers.length) { //сравниваем кол-во множителей у слагаемых

                    return two;
                }

                for (int i = 0; i < multiplierCount; i++) {
                    int count = 0;
                    int start = 0;
                    for (int j = 0; j < multiplierCount; j++) {

                        while (firstMultipliers[i].indexOf(multipliers[j].charAt(multipliers[j].length() - 1), start) != -1) {
                            count++;
                            start = firstMultipliers[i].indexOf(multipliers[j].charAt(multipliers[j].length() - 1), start) + 1;
                        }

                    }
                    if (count != 1)
                        return one;
                }
            }

            for (int i = 0; i < multipliers.length; i++) {
                int lastIndex1 = multipliers[i].length();
                if (lastIndex1 > 2) {//если кол-во знаков у множителя > 2
                    //System.out.println("count >2");

                    return one;
                }
                for (int j = i + 1; j < multipliers.length; j++) {
                    int lastIndex2 = multipliers[j].length();
                    //если в множителе некоторые символы ==, то это не сднф(A&(B&A))
                    if (multipliers[i].charAt(lastIndex1 - 1) == multipliers[j].charAt(lastIndex2 - 1)) {
                        System.out.println("double symbol");
                        return one;
                    }
                }
            }
        }

        return isCorrectBrackets(terms, multiplierCount);
    }

    private static String[] breakTerms(String formula, String term) {
        int end;
        ArrayList<String> result = new ArrayList<>();

        int start = 0;
        while (true) {
            if (formula.indexOf(term, start) == -1) {
                result.add(formula.substring(start));
                break;
            }

            end = formula.indexOf(term, start);
            result.add(formula.substring(start, end));
            start = end + 2;
        }


        return result.toArray(new String[0]);
    }

    private static int isCorrectBrackets(String[] terms, int multiplierCount) {
        int countTerms = terms.length;

        for (int i = 0; i < countTerms; i++) {
            int countOpenBrackets = 0;
            int countCloseBrackets = 0;
            for (int j = 0; j < terms[i].length(); j++) {
                if (terms[i].charAt(j) == '(')
                    countOpenBrackets++;
                if (terms[i].charAt(j) == ')')
                    countCloseBrackets++;
                try{
                    if (terms[i].charAt(j) == '!')
                        if (terms[i].charAt(j - 1) == '(' && terms[i].charAt(j + 2) == ')') {
                            countOpenBrackets--;
                            countCloseBrackets--;
                        } else

                    return two;}
                catch(Exception e){

                    return two;
                }
            }

            if (i == 0) {
                if (terms.length == 1 && breakTerms(terms[i], conjunction).length == 1) {
                    if (countOpenBrackets != 0 || countCloseBrackets != 0)
                        return one;
                } else if (countOpenBrackets != (multiplierCount - 1 + countTerms - 1) ||
                        countCloseBrackets != multiplierCount - 1) {

                    return two;
                }
            } else if (countCloseBrackets != (multiplierCount - 1 + 1) ||
                    countOpenBrackets != multiplierCount - 1) {

                return two;
            }
        }
        return zero;
    }

}