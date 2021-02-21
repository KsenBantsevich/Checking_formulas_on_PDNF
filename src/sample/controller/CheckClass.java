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
    private static int zero = 0;
    private static int one = 1;
    private static int two =2;

    public static int checkFormula(String formula) {

        if (formula == null || formula.length() == (1) | formula.equals("")) {
            return two;
        }

        for (int i = 0; i < formula.length(); i++) {
            if (allSymbols.indexOf(formula.charAt(i)) == -1) //некорретные символы
                return two;
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

        String[] terms = breakTerms(formula, "|");  //делим на слагаемые
        String[] terms2 = new String[terms.length];  //избавляемся от скобок

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
                if (terms[i].equals(terms[j])) //проверяем,есть ли одинаковые слагаемые
                    return one;
            }
        }

        for (String term : terms2)
            for (int i = 0; i < term.length() - 1; i++) {
                if (term.charAt(i) == term.charAt(i + 1)) //повторение знаков, например,(AA&B)
                   return two;
            }

        String[] multiplierTerms;
        int multiplierCount = 0;

        for (String term : terms2) {
            multiplierTerms = breakTerms(term, "&"); //делим слагаемое на отдельные "формулы"
            //System.out.println(Arrays.toString(multiplierTerms));


            if (multiplierCount == 0)
                multiplierCount = multiplierTerms.length;
            else if (multiplierCount != multiplierTerms.length) { //сравниваем кол-во множителей у слагаемых
                //System.out.println("count multiplierTerms not equals");
                return two;
            }

            for (int i = 0; i < multiplierTerms.length; i++) {
                int lastIndex1 = multiplierTerms[i].length();
                if (lastIndex1 > 2) {//если кол-во знаков у множителя > 2
                    //System.out.println("count >2");
                    return two;
                }
                for (int j = i + 1; j < multiplierTerms.length; j++) {
                    int lastIndex2 = multiplierTerms[j].length();
                    //если в множителе некоторые символы ==, то это не сднф(A&(B&A))
                    if (multiplierTerms[i].charAt(lastIndex1 - 1) == multiplierTerms[j].charAt(lastIndex2 - 1)) {
                        //System.out.println("double symbol");
                        return one;
                    }
                }
            }
        }

        return isCorrectBrackets(terms, multiplierCount);
    }

    private static String[] breakTerms(String formula, String term) {
        StringTokenizer stringTokenizer = new StringTokenizer(formula, term);
        int tokensCount = stringTokenizer.countTokens();
        String[] result = new String[tokensCount];
        for (int i = 0; i < tokensCount; i++) {
            result[i] = stringTokenizer.nextToken();
        }
        return result;
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
                if (countOpenBrackets != (multiplierCount - 1 + countTerms - 1) ||
                        countCloseBrackets != multiplierCount - 1) {
                    //System.out.println("bracket dont eqv counts");
                    return two;
                }
            } else if (countCloseBrackets != (multiplierCount - 1 + 1) ||
                       countOpenBrackets != multiplierCount - 1) {
                ///System.out.println("bracket dont eqv counts");
                      return two;
            }
        }
                         return zero;
    }

}