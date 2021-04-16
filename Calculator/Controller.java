package Calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.HashMap;

public class Controller {
    private StringBuilder exp = new StringBuilder();
    private final String[] operators = {"÷", "+", "*", "-", "="};

    @FXML
    public Text calc_text;

    public void handleKey(String key) {
        if (key.contains("DIGIT") || key.contains("NUMPAD")) {
            char numInp = key.charAt(key.length() - 1);
            handleInp(Character.toString(numInp));
        } else {
            switch (key) {
                case "ADD" -> handleInp("+");
                case "SUBTRACT" -> handleInp("-");
                case "MULTIPLY" -> handleInp("*");
                case "DIVIDE", "SLASH" -> handleInp("÷");
                case "EQUALS", "ENTER" -> handleInp("=");
                case "DECIMAL", "PERIOD" -> handleInp(".");
                case "BACK_SPACE" -> handleInp("Del");
                case "DELETE" -> handleInp("AC");
            }
        }
    }

    private void handleInp(String inp) {
        if (inp.equals("AC")) {
            calc_text.setText("");
            exp.replace(0, exp.length(), "");
            return;
        } else if (inp.equals("Del")) {
            if (exp.length() >= 1) {
                exp.deleteCharAt(exp.length() - 1);
                calc_text.setText(exp.toString());
            }
            return;
        }

        if (Arrays.asList(operators).contains(inp) && exp.length() == 0 && !inp.equals("-")) return;

        if (Arrays.asList(operators).contains(inp) && exp.length() >= 1 && Arrays.asList(operators).contains(Character.toString(exp.charAt(exp.length() - 1))))
            return;

        if (exp.length() == (int) (calc_text.getWrappingWidth() / 31.5789473)) {
            calc_text.setWrappingWidth(calc_text.getWrappingWidth() + 100);
        }

        if (inp.equals(".")) {
            if (exp.charAt(exp.length() - 1) == '.' || exp.length() == 0) return;
            else {
                boolean foundOperator = false;
                HashMap<String, String> Operators = new HashMap<>();
                Operators.put("+", "\\+");
                Operators.put("-", "-");
                Operators.put("/", "/");
                Operators.put("*", "\\*");

                int count = 0;
                for (char i : exp.toString().toCharArray()) {
                    if (Arrays.asList(operators).contains(Character.toString(i)) && !(exp.indexOf(Character.toString(i), count) == 0)) {
                        String[] exps = exp.toString().split(Operators.get(Character.toString(i)));
                        foundOperator = true;
                        System.out.println(exps[1]);
                        if (exps[1].contains(".")) {
                            return;
                        }
                    }
                    count++;
                }

                if (!foundOperator) {
                    if (exp.toString().contains(".")) return;
                }
            }
        }

        if (Arrays.asList(operators).contains(inp)) {
            int indCount = 1;
            for (char i : exp.toString().toCharArray()) {
                if (Arrays.asList(operators).contains(Character.toString(i)) && !(exp.indexOf(Character.toString(i), indCount - 1) == 0)) {
                    StringBuilder finalEval = evaluateExp(i, inp);
                    if (inp.equals("=") && exp.length() >= 2) {
                        calc_text.setText(finalEval.deleteCharAt(finalEval.length() - 1).toString());
                        exp.replace(0, exp.length(), finalEval.toString());
                    } else {
                        calc_text.setText(finalEval.toString());
                    }
                    return;
                }
                indCount++;
            }
            if (!inp.equals("=")) {
                exp.append(inp);
                calc_text.setText(exp.toString());
            }
        } else {
            exp.append(inp);
            calc_text.setText(exp.toString());
        }
    }

    public void returnButtonText(ActionEvent e) {
        String[] inp = e.getTarget().toString().split("'");
        handleInp(inp[1]);
    }

    public StringBuilder evaluateExp(char operator, String currOperator) {
        if (operator == '+') {
            exp.replace(0, exp.length(), addNum(exp.toString()) + currOperator);
            return exp;
        } else if (operator == '-') {
            exp.replace(0, exp.length(), SubtractNum(exp) + currOperator);
            return exp;
        } else if (operator == '÷') {
            exp.replace(0, exp.length(), DivNum(exp.toString()) + currOperator);
            return exp;
        } else if (operator == '*') {
            exp.replace(0, exp.length(), MultNum(exp.toString()) + currOperator);
            return exp;
        }
        return null;
    }

    public String addNum(String inp) {
        String[] numArr = inp.split("\\+");
        double NumPart = Math.round((Double.parseDouble(numArr[0]) + Double.parseDouble(numArr[1])) * 100);
        double sum = NumPart / 100;
        return checkDecimal(sum);
    }

    public String SubtractNum(StringBuilder inp) {
        int count = 0;
        for (char i : inp.toString().toCharArray()) {
            count++;
            if (i == '-' && !(count == 1)) {
                inp.setCharAt(count - 1, ' ');
                break;
            }
        }
        String[] numArr = inp.toString().split(" ");
        double NumPart = Math.round((Double.parseDouble(numArr[0]) - Double.parseDouble(numArr[1])) * 100);
        double sum = NumPart / 100;
        return checkDecimal(sum);
    }

    public String MultNum(String inp) {
        String[] numArr = inp.split("\\*");
        double NumPart = Math.round((Double.parseDouble(numArr[0]) * Double.parseDouble(numArr[1])) * 100);
        double sum = NumPart / 100;
        return checkDecimal(sum);
    }

    public String DivNum(String inp) {
        String[] numArr = inp.split("÷");
        double NumPart = Math.round((Double.parseDouble(numArr[0]) / Double.parseDouble(numArr[1])) * 100);
        double sum = NumPart / 100;
        return checkDecimal(sum);
    }

    public String checkDecimal(double inp1) {
        StringBuilder intResult = new StringBuilder();
        if (inp1 % 1 == 0) {
            intResult.append(Math.round(inp1));
            return intResult.toString();
        } else {
            return Double.toString(inp1);
        }
    }
}
