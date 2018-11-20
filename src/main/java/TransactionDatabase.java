import java.util.HashMap;

public class TransactionDatabase {

    HashMap<Long, Transaction> transactionHashMap;

    TransactionDatabase(){
        transactionHashMap = new HashMap<Long, Transaction>();
    }

    public void addTransaction(Transaction transaction){
        transactionHashMap.put(transaction.id, transaction);
    }

    public int sizeOfDatabasse(){
        return transactionHashMap.size();
    }

}
