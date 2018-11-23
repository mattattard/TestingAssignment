public class PaymentProcessor {

    String dateToday;
    BankProxy bankProxy;
    TransactionDatabase transDB;

    PaymentProcessor() {
        this.dateToday = "11/18";
        transDB = new TransactionDatabase();
    }

    PaymentProcessor(String dateToday) {
        this.dateToday = dateToday;

    }

    PaymentProcessor(BankProxy bankProxy) {
        this.bankProxy = bankProxy;
    }


    public int verifyOffLine(CCInfo ccInfo) {
        if (!checkIfInfoIsEmpty(ccInfo)) {
            System.out.println("Some of the card details are missing.");
            return 1;
        }
        if (!verifyLuhn(ccInfo.cardNumber)) {
            System.out.println("The Card Number cannot be verified by the Luhn Algorithm.");
            return 2;
        }
        if (!verifyprefixandlength(ccInfo)) {
            System.out.println("The Card Number prefix does not correspond to the Card Type.");
            return 3;
        }
        if (checkExpiryDate(dateToday, ccInfo.cardExpiryDate)) {
            System.out.println("The Card Expiry Date must be in the future.");
            return 4;
        }
        return 0;
    }

    public int processPayment(CCInfo ccInfo, long transID) {
        //Checks that the card is verified and that it is store in the transaction database
        if (verifyOffLine(ccInfo) != 0) {
            System.out.println("The Verification of the Card has not ended successfully.");
            return 1;
        }
        Object transaction = auth(ccInfo);
        if(transaction instanceof Transaction){
            transDB.addTransaction((Transaction) transaction);
        }else{
            System.out.println(transaction);
            return 2;
        }
        return 0;
    }

    // return true if it is expired
    public boolean checkExpiryDate(String today, String expire) {
        String todayMonth = today.substring(0, today.indexOf('/'));
        String todayYear = today.substring(today.indexOf('/') + 1);

        String expireMonth = expire.substring(0, expire.indexOf('/'));
        String expireYear = expire.substring(expire.indexOf('/') + 1);

        if (todayYear.equals(expireYear)) {
            if (todayMonth.equals(expireMonth)) {
                return true;
            } else return Integer.parseInt(todayMonth) > Integer.parseInt(expireMonth);
        } else return Integer.parseInt(todayYear) > Integer.parseInt(expireYear);
    }

    public boolean verifyLuhn(String card) {
        char checkDigit = card.charAt(card.length() - 1);
        String digit = calculateCheckDigit(card.substring(0, card.length() - 1));
        return checkDigit == digit.charAt(0);
    }

    private boolean checkIfInfoIsEmpty(CCInfo ccInfo) {
        boolean notEmpty = true;
        if (ccInfo.customerName == null || ccInfo.customerName.equals(""))
            notEmpty = false;
        if (ccInfo.cardExpiryDate == null || ccInfo.cardExpiryDate.equals(""))
            notEmpty = false;
        if (ccInfo.cardType == null || !(ccInfo.cardType.equals("Master Card") || ccInfo.cardType.equals("American Express") || ccInfo.cardType.equals("Visa")))
            notEmpty = false;
        if (ccInfo.customerAddress == null || ccInfo.customerAddress.equals(""))
            notEmpty = false;
        if (ccInfo.cardNumber == null || ccInfo.cardNumber.equals(""))
            notEmpty = false;
        if (ccInfo.cardCVV == null || ccInfo.cardCVV.equals("")) {
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
        for (int i = digits.length - 1; i >= 0; i -= 2) {
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

    public boolean verifyprefixandlength(CCInfo ccInfo) {
        if (ccInfo.cardType.equals("American Express") && (ccInfo.cardNumber.startsWith("34") || ccInfo.cardNumber.startsWith("37") && ccInfo.cardNumber.length() == 15))
            return true;
        else if (ccInfo.cardType.equals("Visa") && ccInfo.cardNumber.startsWith("4") && (ccInfo.cardNumber.length() == 13 || ccInfo.cardNumber.length() == 16))
            return true;
        else
            return ccInfo.cardType.equals("Master Card") && (ccInfo.cardNumber.startsWith("51") || ccInfo.cardNumber.startsWith("52") || ccInfo.cardNumber.startsWith("53") || ccInfo.cardNumber.startsWith("54") || ccInfo.cardNumber.startsWith("55")) && ccInfo.cardNumber.length() == 16;
    }

    public Object auth(CCInfo info) {
        long transID = bankProxy.auth(info, 1000);
        Transaction transaction;
        if (transID > 0) {
            transaction = new Transaction(transID, info, 1000, "auth");
        } else if (transID == -1) {
            return "Credit card details are invalid in any way";
        } else if (transID == -2) {
            return "Credit card details are valid but the customer does not have enough funds ";
        } else if (transID == -3) {
            return "Unknown error occurred";
        } else{
            return "Unknown error occurred";
        }
        return transaction;
    }

    public Object caputre(){
        return null;
    }

}
