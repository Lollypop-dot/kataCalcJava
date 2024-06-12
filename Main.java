package KATA;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Введите выражение (или 'exit' для выхода): ");
            String input = scanner.nextLine();
            
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Выход из калькулятора");
                break;
            }
            
            try {
                String res = calc(input);
                System.out.println("Результат: " + res);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        scanner.close();
    }

    static String calc(String input) throws Exception {
        String[] inputAr = input.split(" ");
        if (inputAr.length != 3) {
            throw new IllegalArgumentException("Неверный формат выражения");
        }
        
        int firstNum = parseNumber(inputAr[0]);
        int secondNum = parseNumber(inputAr[2]);
        char oper = inputAr[1].charAt(0);
        int res = 0;

        if (firstNum < 1 || firstNum > 10 || secondNum < 1 || secondNum > 10) {
            throw new IllegalArgumentException("Введите число от одного до десяти");
        }

        if (isRoman(inputAr[0]) != isRoman(inputAr[2])) {
            throw new IllegalArgumentException("Вместе арабские и римские цифры использовать нельзя");
        }

        switch (oper) {
            case '+':
                res = firstNum + secondNum;
                break;
            case '-':
                res = firstNum - secondNum;
                break;
            case '*':
                res = firstNum * secondNum;
                break;
            case '/':
                if (secondNum == 0) {
                    throw new ArithmeticException("Делить на ноль нельзя");
                }
                res = firstNum / secondNum;
                break;
            default:
                throw new IllegalArgumentException("Неверный оператор");
        }

        return isRoman(inputAr[0]) ? toRoman(res) : String.valueOf(res);
    }

    static int parseNumber(String str) throws Exception {
        if (isRoman(str)) {
            return fromRoman(str);
        } else {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Не тот формат числа");
            }
        }
    }

    static boolean isRoman(String str) {
        return str.matches("[IVXLCDM]+");
    }

    static int fromRoman(String roman) {
        int res = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            char romanChar = roman.charAt(i);
            int value;
            if (romanChar == 'I') {
                value = 1;
            } else if (romanChar == 'V') {
                value = 5;
            } else if (romanChar == 'X') {
                value = 10;
            } else if (romanChar == 'L') {
                value = 50;
            } else if (romanChar == 'C') {
                value = 100;
            } else if (romanChar == 'D') {
                value = 500;
            } else if (romanChar == 'M') {
                value = 1000;
            } else {
                throw new IllegalArgumentException("Некорректный символ в римском числе: " + romanChar);
            }
            
            if (value < prevValue) {
                res -= value;
            } else {
                res += value;
                prevValue = value;
            }
        }

        return res;
    }

    static final String[] ROMAN_SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    static final int[] ROMAN_VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

    static String toRoman(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("Число должно быть больше нуля");
        }

        StringBuilder roman = new StringBuilder();

        for (int i = 0; i < ROMAN_VALUES.length; i++) {
            while (number >= ROMAN_VALUES[i]) {
                roman.append(ROMAN_SYMBOLS[i]);
                number -= ROMAN_VALUES[i];
            }
        }

        return roman.toString();
    }
}