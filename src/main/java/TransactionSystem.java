public class TransactionSystem {
    public boolean capture = false , Void = false ,refund = false, auth = false, offlineVerification = true;

    public boolean isCapture(){
        return capture;
    }

    public boolean isVoid(){
        return Void;
    }

    public boolean isRefund(){
        return refund;
    }

    public boolean isAuth(){
        return auth;
    }

    public void Auth(){
        if(offlineVerification && !capture && !Void && !refund && !auth){
            auth = true;
            offlineVerification = false;
            Void = false;
        }
    }

    public void Capture(){
        if (auth && !capture && !Void && !refund && !offlineVerification){
            capture = true;
            auth = false;
        }
    }

    public void NotCapture(){
        if (auth && !capture && !Void && !refund && !offlineVerification ){
            auth = false;
            Void = true;
        }
    }

    public void Refund(){
        if(capture && !auth && !Void && !refund && !offlineVerification){
            capture = false;
            refund = true;
        }
    }


}
