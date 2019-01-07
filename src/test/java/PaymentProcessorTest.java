import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

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


    @Test
    public void TestProcessPaymentInvalidDataEntry(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "American Express", "378282246310005", "8/17", "875");

        int result = paymentProcessor.processPayment(ccInfo, 201);
        assertEquals(1,result);
    }

    @Test
    public void TestProcessPaymentSuccessful(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Master Card", "5555555555554444", "8/19", "875");
        long transId = 1000;
        BankProxy bankProxy = Mockito.mock(BankProxy.class);
        Mockito.when(bankProxy.auth(ccInfo,transId)).thenReturn(transId);

        assertEquals(0, paymentProcessor.processPayment(ccInfo, transId));
    }

    @Test
    public void TestCardNumberWithLengthFifteenAmericanExpress(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "American Express", "378282246310005", "8/17", "875");

        boolean result = paymentProcessor.verifyprefixandlength(ccInfo);

        assertTrue(result);

    }

    @Test
    public void TestCardNumberWithLengthSixteenMastercard(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Master Card", "5555555555554444", "8/19", "875");

        boolean result = paymentProcessor.verifyprefixandlength(ccInfo);

        assertTrue(result);
    }

    @Test
    public void TestCardNumberWithLengthSixteenVISA(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Visa", "4111111111111111", "8/19", "875");

        boolean result = paymentProcessor.verifyprefixandlength(ccInfo);

        assertTrue(result);
    }

    @Test
    public void TestCardNumberWithLengthThirteenVISA(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Visa", "4111111111111", "8/19", "875");

        boolean result = paymentProcessor.verifyprefixandlength(ccInfo);

        assertTrue(result);
    }

    @Test
    public void TestCardNumberLengthLessThanFifteenAmericanExpress(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "American Express", "378282", "8/17", "875");

        boolean result = paymentProcessor.verifyprefixandlength(ccInfo);

        assertFalse(result);
    }

    @Test
    public void TestCardNumberLengthGreaterThanFifteenAmericanExpress(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "American Express", "37828232323131231231223", "8/17", "875");

        boolean result = paymentProcessor.verifyprefixandlength(ccInfo);

        assertFalse(result);
    }

    @Test
    public void TestCardNumberLengthLessThanSixteenMastercard(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Master Card", "54767", "8/19", "875");

        boolean result = paymentProcessor.verifyprefixandlength(ccInfo);

        assertFalse(result);
    }

    @Test
    public void TestCardNumberLengthGreaterThanSixteenMastercard(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Master Card", "54767362763273236278323627362736821188238213", "8/19", "875");

        boolean result = paymentProcessor.verifyprefixandlength(ccInfo);

        assertFalse(result);
    }

    @Test
    public void TestCardNumberWithLengthLessThanSixteenVISA(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Visa", "411", "8/19", "875");

        boolean result = paymentProcessor.verifyprefixandlength(ccInfo);

        assertFalse(result);
    }

    @Test
    public void TestCardNumberWithLengthGreaterThanSixteenVISA(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Visa", "411111131231231312331231313123131231111111111", "8/19", "875");

        boolean result = paymentProcessor.verifyprefixandlength(ccInfo);

        assertFalse(result);
    }

    @Test
    public void TestCardNumberWithLengthLessThanThirteenVISA(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Visa", "411", "8/19", "875");

        boolean result = paymentProcessor.verifyprefixandlength(ccInfo);

        assertFalse(result);
    }

    @Test
    public void TestCardNumberWithLengthGreaterThanThirteenVISA(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Visa", "411131231223123123123131232131231232132131", "8/19", "875");

        boolean result = paymentProcessor.verifyprefixandlength(ccInfo);

        assertFalse(result);
    }

    @Test
    public void TestAuthSuccesfulResult(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Visa", "411131231223123123123131232131231232132131", "8/19", "875");
        BankProxy bankProxy = Mockito.mock(BankProxy.class);
        Mockito.when(bankProxy.auth(ccInfo, 1000)).thenReturn((long) 1000);
        paymentProcessor = new PaymentProcessor(bankProxy);

        assertTrue(paymentProcessor.auth(ccInfo) instanceof Transaction);
    }

    @Test
    public void TestAuthInvalidCardDetails(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Visa", "411131231223123123123131232131231232132131", "8/19", "875");
        BankProxy bankProxy = Mockito.mock(BankProxy.class);
        Mockito.when(bankProxy.auth(ccInfo, 1000)).thenReturn((long) -1);
        paymentProcessor = new PaymentProcessor(bankProxy);

        assertEquals("Credit card details are invalid in any way", paymentProcessor.auth(ccInfo));
    }

    @Test
    public void TestAuthInsufficientFunds(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Visa", "411131231223123123123131232131231232132131", "8/19", "875");
        BankProxy bankProxy = Mockito.mock(BankProxy.class);
        Mockito.when(bankProxy.auth(ccInfo, 1000)).thenReturn((long) -2);
        paymentProcessor = new PaymentProcessor(bankProxy);

        assertEquals("Credit card details are valid but the customer does not have enough funds ", paymentProcessor.auth(ccInfo));
    }

    @Test
    public void TestAuthUnknownErrorOccured(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Visa", "411131231223123123123131232131231232132131", "8/19", "875");
        BankProxy bankProxy = Mockito.mock(BankProxy.class);
        Mockito.when(bankProxy.auth(ccInfo, 1000)).thenReturn((long) -3);
        paymentProcessor = new PaymentProcessor(bankProxy);

        assertEquals("Unknown error occurred", paymentProcessor.auth(ccInfo));
    }

    @Test
    public void TestAuthSuccesful(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "Visa", "411131231223123123123131232131231232132131", "8/19", "875");
        paymentProcessor = Mockito.mock(PaymentProcessor.class);
        Mockito.when(paymentProcessor.refund(1000, 300)).thenReturn("Successful");
        paymentProcessor = new PaymentProcessor();

        assertTrue(paymentProcessor.auth(ccInfo) instanceof Transaction);
    }

    @Test
    public void TestSuccessfulCapture(){
        long num = 100;
        BankProxy bankProxy = Mockito.mock(BankProxy.class);
        Mockito.when(bankProxy.capture(num)).thenReturn(0);

        paymentProcessor = new PaymentProcessor(bankProxy);

        assertEquals("Successful", paymentProcessor.capture(num));
    }

    @Test
    public void TestCaptureTransactionExistsAndCaptured(){
        long num = 100;
        BankProxy bankProxy = Mockito.mock(BankProxy.class);
        Mockito.when(bankProxy.capture(num)).thenReturn(-1);

        paymentProcessor = new PaymentProcessor(bankProxy);

        assertEquals("Transaction already exist but has already been captured", paymentProcessor.capture(num));
    }

    @Test
    public void TestCaptureTransactionExistsAndCaptured2(){
        long num = 100;
        BankProxy bankProxy = Mockito.mock(BankProxy.class);
        Mockito.when(bankProxy.capture(num)).thenReturn(-2);

        paymentProcessor = new PaymentProcessor(bankProxy);

        assertEquals("Transaction exists but has already been captured", paymentProcessor.capture(num));
    }

    @Test
    public void TestCaptureTransactionBeingVoided(){
        long num = 100;
        BankProxy bankProxy = Mockito.mock(BankProxy.class);
        Mockito.when(bankProxy.capture(num)).thenReturn(-3);

        paymentProcessor = new PaymentProcessor(bankProxy);

        assertEquals("Transaction exists but the 7 day period has expired resulting in the transaction being voided", paymentProcessor.capture(num));
    }

    @Test
    public void TestCaptureUnknownError(){
        long num = 100;
        BankProxy bankProxy = Mockito.mock(BankProxy.class);
        Mockito.when(bankProxy.capture(num)).thenReturn(-4);

        paymentProcessor = new PaymentProcessor(bankProxy);

        assertEquals("Unknown error has occurred", paymentProcessor.capture(num));
    }

    @Test
    public void TestRefundSuccessful(){
        long transId = 100;
        long amount = 1000;
        BankProxy bankProxy = Mockito.mock(BankProxy.class);
        Mockito.when(bankProxy.refund(transId,amount)).thenReturn(0);

        paymentProcessor = new PaymentProcessor(bankProxy);

        assertEquals("Successful", paymentProcessor.refund(transId,amount));
    }

    @Test
    public void TestRefundTransactionNotExist(){
        long transId = 100;
        long amount = 1000;
        BankProxy bankProxy = Mockito.mock(BankProxy.class);
        Mockito.when(bankProxy.refund(transId,amount)).thenReturn(-1);

        paymentProcessor = new PaymentProcessor(bankProxy);

        assertEquals("Transaction does not exist", paymentProcessor.refund(transId,amount));
    }

    @Test
    public void TestRefundTransactionHasNotBeenCaptured(){
        long transId = 100;
        long amount = 1000;
        BankProxy bankProxy = Mockito.mock(BankProxy.class);
        Mockito.when(bankProxy.refund(transId,amount)).thenReturn(-2);

        paymentProcessor = new PaymentProcessor(bankProxy);

        assertEquals("Transaction exists but has not been captured", paymentProcessor.refund(transId,amount));
    }

    @Test
    public void TestRefundTransactionExistButGivenARefund(){
        long transId = 100;
        long amount = 1000;
        BankProxy bankProxy = Mockito.mock(BankProxy.class);
        Mockito.when(bankProxy.refund(transId,amount)).thenReturn(-3);

        paymentProcessor = new PaymentProcessor(bankProxy);

        assertEquals("Transaction exists but a refund has already been processed against it", paymentProcessor.refund(transId,amount));
    }

    @Test
    public void TestRefundAmountGreaterThanCaptureAmount(){
        long transId = 100;
        long amount = 1000;
        BankProxy bankProxy = Mockito.mock(BankProxy.class);
        Mockito.when(bankProxy.refund(transId,amount)).thenReturn(-4);

        paymentProcessor = new PaymentProcessor(bankProxy);

        assertEquals("Refund amount is greater than the captured amount", paymentProcessor.refund(transId,amount));
    }

    @Test
    public void TestRefundUnknownErrorOccurred(){
        long transId = 100;
        long amount = 1000;
        BankProxy bankProxy = Mockito.mock(BankProxy.class);
        Mockito.when(bankProxy.refund(transId,amount)).thenReturn(-5);

        paymentProcessor = new PaymentProcessor(bankProxy);

        assertEquals("Unknown error occurred", paymentProcessor.refund(transId,amount));
    }
}
