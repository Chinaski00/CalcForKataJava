import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String result = calc(input);
        System.out.println(result);
    }

    public static String calc(String input) {
        try {
            // Разбиваем входную строку на числа и операцию
            String[] parts = input.trim().split("\\s+");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid input format");
            }

            String operand1 = parts[0];
            String operator = parts[1];
            String operand2 = parts[2];

            // Проверяем, что используется только одна система счисления (арабские или римские числа)
            boolean isArabic = isArabicNumber(operand1) && isArabicNumber(operand2);
            boolean isRoman = isRomanNumber(operand1) && isRomanNumber(operand2);

            if (!isArabic && !isRoman) {
                throw new IllegalArgumentException("Invalid numeral system");
            }

            // Преобразуем числа в целочисленное значение
            int num1 = isArabic ? Integer.parseInt(operand1) : romanToArabic(operand1);
            int num2 = isArabic ? Integer.parseInt(operand2) : romanToArabic(operand2);

            // Выполняем арифметическую операцию
            int result = performOperation(num1, num2, operator);

            // Проверяем результат на соответствие правилам
            if (isArabic) {
                return String.valueOf(result);
            } else {
                return arabicToRoman(result);
            }
        } catch (Exception ex) {
            return "throws Exception";
        }
    }

    private static int performOperation(int num1, int num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    throw new IllegalArgumentException("Division by zero");
                }
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    private static boolean isArabicNumber(String str) {
        try {
            int num = Integer.parseInt(str);
            return num >= 1 && num <= 10;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private static boolean isRomanNumber(String str) {
        return str.matches("^(I|II|III|IV|V|VI|VII|VIII|IX|X)$");
    }

    private static int romanToArabic(String romanNumeral) {
        Map<Character, Integer> romanToArabicMap = new HashMap<>();
        romanToArabicMap.put('I', 1);
        romanToArabicMap.put('V', 5);
        romanToArabicMap.put('X', 10);
        romanToArabicMap.put('L', 50);
        romanToArabicMap.put('C', 100);
        romanToArabicMap.put('D', 500);
        romanToArabicMap.put('M', 1000);

        int result = 0;
        int prevValue = 0;

        for (int i = romanNumeral.length() - 1; i >= 0; i--) {
            char currentChar = romanNumeral.charAt(i);
            int currentValue = romanToArabicMap.get(currentChar);

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            prevValue = currentValue;
        }

        return result;
    }

    private static String arabicToRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Number out of Roman numeral range");
        }

        StringBuilder result = new StringBuilder();
        Map<Integer, String> arabicToRomanMap = new TreeMap<>(Collections.reverseOrder());
        arabicToRomanMap.put(1000, "M");
        arabicToRomanMap.put(900, "CM");
        arabicToRomanMap.put(500, "D");
        arabicToRomanMap.put(400, "CD");
        arabicToRomanMap.put(100, "C");
        arabicToRomanMap.put(90, "XC");
        arabicToRomanMap.put(50, "L");
        arabicToRomanMap.put(40, "XL");
        arabicToRomanMap.put(10, "X");
        arabicToRomanMap.put(9, "IX");
        arabicToRomanMap.put(5, "V");
        arabicToRomanMap.put(4, "IV");
        arabicToRomanMap.put(1, "I");

        for (int key : arabicToRomanMap.keySet()) {
            while (number >= key) {
                result.append(arabicToRomanMap.get(key));
                number -= key;
            }
        }

        return result.toString();
    }
}