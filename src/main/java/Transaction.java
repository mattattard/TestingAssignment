public class Transaction {

    long id;
    CCInfo info;
    long amount;
    String state;

    public Transaction(long id, CCInfo info, long amount, String state) {
        this.id = id;
        this.info = info;
        this.amount = amount;
        this.state = state;
    }
}
