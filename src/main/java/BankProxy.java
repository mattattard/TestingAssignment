
public interface BankProxy {
    long auth(CCInfo ccInfo, long num);
    int capture(long num);
    int refund(long num1, long num2);
}
