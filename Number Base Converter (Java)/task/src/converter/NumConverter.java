package converter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumConverter  {

    public NumConverter() {
    }

    public static String[] splittedNums;

    public static String convertFromDecimalInt(BigDecimal num, int base) {
        StringBuilder convertedIntStr = new StringBuilder(); // used to store remainders
        // If number is less than base, no need to use the loop, just return the number
        if (num.compareTo(BigDecimal.valueOf(base)) < 0) {
            if (num.compareTo(BigDecimal.TEN) < 0)
                return num.toString();
            else return String.valueOf((char) ((num.intValue() - 10) + 'A'));
        }
        while (!num.equals(BigDecimal.ZERO)) {
            int remainder = num.remainder(BigDecimal.valueOf(base)).intValue(); // Divide by base and store the remainder in a StringBuilder
            /* Case: 1: remainder is less than 10 - no need to convert to a letter */
            if (remainder < 10) {
                convertedIntStr.append(remainder);
            }
            /* Case: 2: remainder is greater than 9 - need to convert to a letter - subtract by 10 and add 'A' - convert to char */
            else {
                convertedIntStr.append((char)((remainder - 10) + 'A'));
            }
            num = num.divide(BigDecimal.valueOf(base), 0, RoundingMode.FLOOR); // Use quotient for the next iteration
        }
        // calculation stores the answer in reverse, need to reverse it again.
        return convertedIntStr.reverse().toString();
    }
    public static String convertFromDecimalFractional(BigDecimal num, int toBase) {
        StringBuilder convertedFracStr = new StringBuilder();
        int integerPart;
        int count = 0;
        while (/*(num.compareTo(BigDecimal.valueOf(0)) > 0) &&*/ count <= 4) {
            // Get the integer part of the bigdecimal after multiplication with base, convert to String and append it
            num = num.multiply(BigDecimal.valueOf(toBase));
            integerPart = num.intValue();
            if (integerPart >= 10) {
                convertedFracStr.append((char) ((integerPart - 10) + 'A'));
            } else
                convertedFracStr.append(integerPart);
            // Get the remainder part and modify the num to it
            num = num.subtract(new BigDecimal(integerPart));
            count++;
        }
        return convertedFracStr.toString();
    }

    public static BigDecimal convertIntToDecimal(String numChar, int base) {
        BigDecimal decimalResult = BigDecimal.ZERO;
        BigDecimal sum = BigDecimal.ZERO;
        int numLength = numChar.length();
        int parsedInt;
        if (base != 10) {
            for (int i = 0; i < numLength; i++) {
                parsedInt = Integer.parseInt(String.valueOf(numChar.charAt(i)), base); // extract the number from each character of string
                sum = BigDecimal.valueOf((long) ((long) parsedInt * Math.pow(base, numLength - 1 - i))); // multiply by base^x to convert to decimal
                decimalResult = decimalResult.add(sum); // add result from each iteration to give final decimal value
            }
        }
        else {
            decimalResult = new BigDecimal(numChar); // if base is 10, no need to calculate, just return the int representation of the number
        }
        return decimalResult;
    }
    public static BigDecimal convertFractionalToDecimal(String fraction, int base) {
        BigDecimal fractionalDecimalResult = BigDecimal.ZERO;
        BigDecimal sum = BigDecimal.ZERO;
        int fractionLength = fraction.length();
        long parsedFraction;
        if (base != 10) {
            for (int i = 0; i < fractionLength; i++) {
                parsedFraction = Long.parseLong(String.valueOf(fraction.charAt(i)), base);
                //System.out.println(parsedFraction);
                sum = BigDecimal.valueOf(parsedFraction * Math.pow(base, -i - 1));
                // add the sum to result but round off to 5 decimal places before that to offer ease of calculation
                fractionalDecimalResult = fractionalDecimalResult.add(sum.setScale(5, RoundingMode.HALF_UP));
            }
        }
        return fractionalDecimalResult;
    }

    public static String convertNum(String fromNum, int fromBase, int toBase) {
        String convertedIntResult;
        String convertedFractionalResult;
        StringBuilder joinResult = new StringBuilder();
        if (fromNum.contains(".")) {
            splitNum(fromNum); // split the source number into integer and fractional part
            convertedIntResult = convertFromDecimalInt(convertIntToDecimal(splittedNums[0], fromBase), toBase);
            convertedFractionalResult = convertFromDecimalFractional(convertFractionalToDecimal(splittedNums[1], fromBase), toBase);
            joinResult.append(convertedIntResult).append(".").append(convertedFractionalResult);
        } else {
            convertedIntResult = convertFromDecimalInt(convertIntToDecimal(fromNum, fromBase), toBase);
            joinResult.append(convertedIntResult);
        }

        return joinResult.toString();
    }
    public static void splitNum(String fromNum) {
        splittedNums = fromNum.split("\\.");
    }

}
