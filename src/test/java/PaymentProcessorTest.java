
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
//
//    @Test
//    public void TestEmptyCCInfo() {
//        CCInfo ccInfo = new CCInfo("", "", "", "", "", "");
//
//        int result = paymentProcessor.processPayment(ccInfo, 100);
//
//        assertEquals(1, result);
//    }
//
//    @Test
//    public void TestingPrefixIncorrect() {
//        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Master Card", "30569309025904", "8/19", "875");
//
//        int result = paymentProcessor.processPayment(ccInfo, 100);
//
//        assertEquals(1, result);
//    }
//
//    @Test
//    public void TestMasterCardPrefix() {
//        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Master Card", "5555555555554444", "8/19", "875");
//
//        int result = paymentProcessor.verifyOffLine(ccInfo);
//
//        assertEquals(-1,result);
//    }
}
