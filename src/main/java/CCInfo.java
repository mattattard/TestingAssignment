public class CCInfo {

    String customerName;
    String customerAddress;
    String cardType;
    String cardNumber;
    String cardExpiryDate;
    String cardCVV;

    public CCInfo(String customerName, String customerAddress, String cardType, String cardNumber, String cardExpiryDate, String cardCVV) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.cardExpiryDate = cardExpiryDate;
        this.cardCVV = cardCVV;
    }
}
