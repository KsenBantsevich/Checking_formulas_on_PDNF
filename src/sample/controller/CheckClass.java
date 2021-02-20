//////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Выполнена студенткой группы 821703 БГУИР Банцевич Ксенией Андреевной
// Algorithm for checking a formula on SDNF
// 20.01.2021 v1.0
//
package sample.controller;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CheckClass {

    private static final String allSymbols = "()&|!ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /*private static final String binaryBundle = "&|";
    private static final String unaryBundle = "!";
    private static final String brackets = "()";*/

    public static boolean checkFormula(String formula) {

        if (formula == null || formula.length() == (1) | formula.equals("")) {
            return false;
        }

        for (int i = 0; i < formula.length(); i++) {
            if (allSymbols.indexOf(formula.charAt(i)) == -1) //некорретные символы
                return false;
        }

        int counter = 0;
        for (int i = 0; i < formula.length(); i++) { //проверяем на правильную последовательность скобок
            if (formula.charAt(i) == '(')
                counter++;
            else if (formula.charAt(i) == ')')
                counter--;
            if (counter < 0)
                return false;
        }
        if (counter != 0)
            return false;

        String[] terms = split(formula, "|");  //делим на слагаемые
        String[] terms2 = new String[terms.length];  //слагаемые без скобок

        int k = 0;
        for (String term : terms) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < term.length(); i++) {
                if (term.charAt(i) != '(' && term.charAt(i) != ')')
                    stringBuilder.append(term.charAt(i));
            }
            terms2[k] = stringBuilder.toString();
            k++;
        }
        System.out.println(Arrays.toString(terms2));

        for (int i = 0; i < terms2.length; i++) {
            for (int j = i + 1; j < terms2.length; j++) {
                if (terms[i].equals(terms[j])) //сравниваем слагаемые , если == то формула не сднф
                    return false;
            }
        }

        for (String term : terms2)
            for (int i = 0; i < term.length() - 1; i++) {
                if (term.charAt(i) == term.charAt(i + 1)) //если за следующим знаком идет такой же то возращаем false
                    return false;
            }

        String[] multipliers;
        int multiplierCount = 0;

        for (String term : terms2) {
            multipliers = split(term, "&"); //делим слагаемое на множители
            System.out.println(Arrays.toString(multipliers));

            if (multiplierCount == 0)
                multiplierCount = multipliers.length;
            else if (multiplierCount != multipliers.length) { //сравниваем кол-во множителей у слагаемых
                System.out.println("count multipliers not equals");
                return false;
            }

            for (int i = 0; i < multipliers.length; i++) {
                int lastIndex1 = multipliers[i].length();
                if (lastIndex1 > 2) {//если кол-во знаков у множителя > 2
                    System.out.println("count >2");
                    return false;
                }
                for (int j = i + 1; j < multipliers.length; j++) {
                    int lastIndex2 = multipliers[j].length();
                    //если в множителе некоторые символы ==, то это не сднф
                    if (multipliers[i].charAt(lastIndex1 - 1) == multipliers[j].charAt(lastIndex2 - 1)) {
                        System.out.println("double symbol");
                        return false;
                    }
                }
            }
        }

        return isCorrectBrackets(terms, multiplierCount);
    }

    private static String[] split(String formula, String delim) {
        StringTokenizer stringTokenizer = new StringTokenizer(formula, delim);
        int tokensCount = stringTokenizer.countTokens();
        String[] result = new String[tokensCount];
        for (int i = 0; i < tokensCount; i++) {
            result[i] = stringTokenizer.nextToken();
        }
        return result;
    }

    private static boolean isCorrectBrackets(String[] terms, int multiplierCount) {
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
                            return false;}
                catch(Exception e){
                    return false;
                }
            }

            if (i == 0) {
                if (countOpenBrackets != (multiplierCount - 1 + countTerms - 1) ||
                        countCloseBrackets != multiplierCount - 1) {
                    System.out.println("bracket dont eqv counts");
                    return false;
                }
            } else if (countCloseBrackets != (multiplierCount - 1 + 1) ||
                    countOpenBrackets != multiplierCount - 1) {
                System.out.println("bracket dont eqv counts");
                return false;
            }
        }
        return true;
    }

}