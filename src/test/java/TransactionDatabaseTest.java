import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionDatabaseTest {

    TransactionDatabase transactionDatabase;

    @Before
    public void setup(){
        transactionDatabase = new TransactionDatabase();
    }

    @After
    public void tearDown(){
        transactionDatabase = null;
    }

    @Test
    public void testAddTransaction(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "American Express", "378282246310005", "8/19", "875");
        Transaction transaction = new Transaction(1000,ccInfo,1000, "offlineVerification");

        transactionDatabase.addTransaction(transaction);

        assertEquals(1, transactionDatabase.sizeOfDatabase());
    }

    @Test
    public void testAddTwoTransaction(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "American Express", "378282246310005", "8/19", "875");
        Transaction transaction = new Transaction(1000,ccInfo,1000, "offlineVerification");
        CCInfo ccInfo1 = new CCInfo("XYZ", "ABC", "American Express", "378282246310005", "8/19", "875");
        Transaction transaction1 = new Transaction(1001,ccInfo1,1000, "offlineVerification");

        transactionDatabase.addTransaction(transaction);
        transactionDatabase.addTransaction(transaction1);

        assertEquals(2, transactionDatabase.sizeOfDatabase());
    }

    @Test
    public void testAddTwoTransactionsWithSameId(){
        CCInfo ccInfo = new CCInfo("XYZ", "ABC", "American Express", "378282246310005", "8/19", "875");
        Transaction transaction = new Transaction(1000,ccInfo,1000, "offlineVerification");
        CCInfo ccInfo1 = new CCInfo("XYZ", "ABC", "American Express", "378282246310005", "8/19", "875");
        Transaction transaction1 = new Transaction(1000,ccInfo1,1000, "offlineVerification");

        transactionDatabase.addTransaction(transaction);
        transactionDatabase.addTransaction(transaction1);

        assertEquals(1,transactionDatabase.sizeOfDatabase());
    }

}
