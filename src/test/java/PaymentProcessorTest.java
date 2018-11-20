
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class PaymentProcessorTest {

    PaymentProcessor paymentProcessor;

    @Before
    public void setup() {
        paymentProcessor = new PaymentProcessor();
    }

    @After
    public void tearDown() {
        paymentProcessor = null;
    }

    @Test
    public void TestOffLineVerification() {
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Master Card", "5555555555554444", "8/19", "875");

        int result = paymentProcessor.verifyOffLine(ccInfo);

        assertEquals(0, result);
    }

    @Test
    public void TestOffLineVerificationEmptyCardDetails() {
        CCInfo ccInfo = new CCInfo("", "", "", "", "", "");

        int result = paymentProcessor.verifyOffLine(ccInfo);

        assertEquals(1, result);
    }

    @Test
    public void TestOffLineVerificationIncorrectLuhnVerify() {
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Master Card", "5555555555554443", "8/19", "875");

        int result = paymentProcessor.verifyOffLine(ccInfo);

        assertEquals(2, result);
    }

    @Test
    public void TestingPrefixIncorrect() {
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Master Card", "30569309025904", "8/19", "875");

        int result = paymentProcessor.verifyOffLine(ccInfo);

        assertEquals(3, result);
    }

    @Test
    public void TestMasterCardCorrectPrefix() {
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Master Card", "5555555555554444", "8/19", "875");

        int result = paymentProcessor.verifyOffLine(ccInfo);

        assertEquals(0, result);
    }
    @Test
    public void TestVisaCardCorrectPrefix() {
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Visa", "4111111111111111", "8/19", "875");

        int result = paymentProcessor.verifyOffLine(ccInfo);

        assertEquals(0, result);
    }

    @Test
    public void TestAmericanExpressCorrectPrefix() {
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "American Express", "378282246310005", "8/19", "875");

        int result = paymentProcessor.verifyOffLine(ccInfo);

        assertEquals(0, result);
    }

    @Test
    public void TestDateExpiryWithCardExpired(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "American Express", "378282246310005", "6/19", "875");

        boolean result = paymentProcessor.checkExpiryDate("7/19", ccInfo.cardExpiryDate);

        assertTrue(result);
    }

    @Test
    public void TestDateExpiryWithCardInTheFuture(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "American Express", "378282246310005", "8/19", "875");

        boolean result = paymentProcessor.checkExpiryDate("7/19", ccInfo.cardExpiryDate);

        assertFalse(result);
    }

    @Test
    public void TestDateExpiryWithCardofSameDate(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "American Express", "378282246310005", "8/19", "875");

        boolean result = paymentProcessor.checkExpiryDate("8/19", ccInfo.cardExpiryDate);

        assertTrue(result);
    }

    @Test
    public void TestDateExpiryWithCardInTheFutureYear1(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "American Express", "378282246310005", "8/23", "875");

        boolean result = paymentProcessor.checkExpiryDate("7/19", ccInfo.cardExpiryDate);

        assertFalse(result);
    }

    @Test
    public void TestDateExpiryWithCardInTheFutureYear(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "American Express", "378282246310005", "8/22", "875");

        boolean result = paymentProcessor.checkExpiryDate("7/19", ccInfo.cardExpiryDate);

        assertFalse(result);
    }

    @Test
    public void TestDateExpiryWithCardInThePastYear(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "American Express", "378282246310005", "8/17", "875");

        boolean result = paymentProcessor.checkExpiryDate("7/19", ccInfo.cardExpiryDate);

        assertTrue(result);
    }

    @Test
    public void TestOfflineVerificationStubExpiryDate(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "American Express", "378282246310005", "8/17", "875");
        paymentProcessor = new PaymentProcessor("8/18");


        int result = paymentProcessor.verifyOffLine(ccInfo);
        assertEquals(4,result);
    }
    
//    @Test
//    public void TestMasterCardCorrect() {
//        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Master Card", "5555555555554444", "8/19", "875");
//
//        boolean result = paymentProcessor.verifyLuhn(ccInfo.cardNumber);
//
//        assertTrue(result);
//    }
//
//    @Test
//    public void TestMasterCardInCorrectInitialNumber() {
//        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Master Card", "6555555555554444", "8/19", "875");
//
//        boolean result = paymentProcessor.verifyLuhn(ccInfo.cardNumber);
//
//        assertFalse(result);
//    }
//
//    @Test
//    public void TestAmericanExpressCorrect() {
//        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "American Express", "378282246310005", "8/19", "875");
//
//        boolean result = paymentProcessor.verifyLuhn(ccInfo.cardNumber);
//
//        assertTrue(result);
//    }
//
//    @Test
//    public void TestAmericanExpressInCorrect() {
//        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "American Express", "078282246310005", "8/19", "875");
//
//        boolean result = paymentProcessor.verifyLuhn(ccInfo.cardNumber);
//
//        assertFalse(result);
//    }
//
//    @Test
//    public void TestVisaCardCorrect() {
//        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Visa", "4111111111111111", "8/19", "875");
//
//        boolean result = paymentProcessor.verifyLuhn(ccInfo.cardNumber);
//
//        assertTrue(result);
//    }
//
//    @Test
//    public void TestVisaCardInCorrectInitialNumber() {
//        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Visa", "2111111111111111", "8/19", "875");
//
//        boolean result = paymentProcessor.verifyLuhn(ccInfo.cardNumber);
//
//        assertFalse(result);
//    }
//
//    @Test
//    public void TestVerifyOffLineFailLuhnAlgorithm() {
//        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Master Card", "5555555555554443", "8/19", "875");
//
//        int result = paymentProcessor.processPayment(ccInfo, 100);
//
//        assertEquals(1, result);
//    }
}
