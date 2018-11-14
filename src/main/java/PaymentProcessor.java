public class PaymentProcessor {

    public int verifyOffLine(CCInfo ccInfo){
        if(!checkIfInfoIsEmpty(ccInfo)){
            System.out.println("Some of the card details are missing");
            return 1;
        }
        if(!verifyLuhn(ccInfo.cardNumber)) {
            System.out.println("The Card Number cannot be verified by the Luhn Algorithm");
            return 2;
        }
        return 0;
    }

//    public int processPayment(CCInfo ccInfo, long transID){
//        if(verifyOffLine(ccInfo) == -1)
//            return 1;
//        return 0;
//    }
//
//    public int verifyOffLine(CCInfo ccInfo){
//        // check if CCInfo the details are not empty.
//        if(!checkIfInfoIsEmpty(ccInfo))
//            return -1;
//        // Luhn Algorithm
//        if(!verifyLuhn(ccInfo.cardNumber))
//            return -1;
//        // verify prefix and card type match
//        if(!verifyPrefix(ccInfo))
//            return -1;
//        //verify date
//
//        return 0;
//    }
//
    public boolean verifyLuhn (String card) {
        char checkDigit = card.charAt(card.length() - 1);
        String digit = calculateCheckDigit(card.substring(0, card.length() - 1));
        return checkDigit == digit.charAt(0);
    }

    private boolean checkIfInfoIsEmpty(CCInfo ccInfo){
        boolean notEmpty = true;
        if(ccInfo.customerName == null || ccInfo.customerName.equals(""))
            notEmpty = false;
        if(ccInfo.cardExpiryDate == null || ccInfo.cardExpiryDate.equals(""))
            notEmpty = false;
        if(ccInfo.cardType == null || !(ccInfo.cardType.equals("Master Card") || ccInfo.cardType.equals("American Express") || ccInfo.cardType.equals("Visa")))
            notEmpty = false;
        if(ccInfo.customerAddress == null || ccInfo.customerAddress.equals(""))
            notEmpty = false;
        if (ccInfo.cardNumber == null || ccInfo.cardNumber.equals(""))
            notEmpty = false;
        if (ccInfo.cardCVV == null || ccInfo.cardCVV.equals("")){
            notEmpty = false;
        }
        return notEmpty;
    }
//
    private String calculateCheckDigit(String card) {
        String digit;
        /* convert to array of int for simplicity */
        int[] digits = new int[card.length()];
        for (int i = 0; i < card.length(); i++) {
            digits[i] = Character.getNumericValue(card.charAt(i));
        }

        /* double every other starting from right - jumping from 2 in 2 */
        for (int i = digits.length - 1; i >= 0; i -= 2)	{
            digits[i] += digits[i];

            /* taking the sum of digits grater than 10 - simple trick by substract 9 */
            if (digits[i] >= 10) {
                digits[i] = digits[i] - 9;
            }
        }
        int sum = 0;
        for (int i = 0; i < digits.length; i++) {
            sum += digits[i];
        }
        /* multiply by 9 step */
        sum = sum * 9;

        /* convert to string to be easier to take the last digit */
        digit = sum + "";
        return digit.substring(digit.length() - 1);
    }
//
//    private boolean verifyPrefix(CCInfo ccInfo){
//        if(ccInfo.cardType.equals("American Express") && (ccInfo.cardNumber.startsWith("34") || ccInfo.cardNumber.startsWith("37")))
//            return true;
//        else if (ccInfo.cardType.equals("Visa") && ccInfo.cardNumber.startsWith("4"))
//            return true;
//        else
//            return ccInfo.cardType.equals("Master Card") && (ccInfo.cardNumber.startsWith("51") || ccInfo.cardNumber.startsWith("52") || ccInfo.cardNumber.startsWith("53") || ccInfo.cardNumber.startsWith("54") || ccInfo.cardNumber.startsWith("55"));
//    }
}
