public class BankProxyStub implements BankProxy{
    public long auth(CCInfo ccInfo, long num) {
        return 0;
    }
    public int capture(long num) {
        return 0;
    }
    public int refund(long num1, long num2) {
        return 0;
    }
}
