import static org.junit.Assert.*;

import enums.TransactionState;
import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.Test;

import java.util.Random;

public class TransactionModelTest implements FsmModel {

    //System under test
    TransactionSystem sut = new TransactionSystem();

    //setting state
    TransactionState state = TransactionState.OFFLINE_VERIFICATION;
    boolean offlineVerification = true, auth=false, capture=false, Void =false, refund = false;

    public Object getState() {
        return state;
    }

    public void reset(boolean b) {
        if (b) {
            sut = new TransactionSystem();
        }
        state = TransactionState.OFFLINE_VERIFICATION;
        offlineVerification = true;
        auth = false;
        capture = false;
        Void = false;
        refund = false;

    }

    public boolean authGuard(){
        return state == TransactionState.OFFLINE_VERIFICATION;
    }

    public @Action void auth(){
        sut.Auth();

        if(offlineVerification && !capture && !Void && !refund && !auth){
            state = TransactionState.AUTH;
            offlineVerification = false;
            auth = true;
            capture = false;
            Void = false;
            refund = false;
        }

        assertEquals(auth, sut.isAuth());
    }

    public boolean notCaptureGuard(){
        return state == TransactionState.AUTH;
    }

    public @Action void notCapture(){
        sut.NotCapture();

        if(auth && !capture && !Void && !refund && !offlineVerification ){
            state = TransactionState.VOID;
            offlineVerification = false;
            auth = false;
            capture = false;
            Void = true;
            refund = false;
        }

        assertEquals(Void, sut.isVoid());
    }

    public boolean captureGuard(){
        return state == TransactionState.AUTH;
    }

    public @Action void capture(){
        sut.Capture();

        if(auth && !capture && !Void && !refund && !offlineVerification){
            state = TransactionState.CAPTURE;
            offlineVerification = false;
            auth = false;
            capture = true;
            Void = false;
            refund = false;
        }

        assertEquals(capture, sut.isCapture());
    }

    public boolean refundGuard(){
        return state == TransactionState.CAPTURE;
    }

    public @Action void refund(){
        sut.Refund();

        if(capture && !auth && !Void && !refund && !offlineVerification){
            state = TransactionState.REFUND;
            offlineVerification = false;
            auth = false;
            capture = false;
            Void = false;
            refund = true;
        }

        assertEquals(refund, sut.isRefund());
    }

    @Test
    public void TelephoneModelRunner() {
        final Tester tester = new GreedyTester(new TransactionModelTest()); //Creates a test generator that can generate random walks. A greedy random walk gives preference to transitions that have never been taken before. Once all transitions out of a state have been taken, it behaves the same as a random walk.
        tester.setRandom(new Random()); //Allows for a random path each time the model is run.
        tester.buildGraph(); //Builds a model of our FSM to ensure that the coverage metrics are correct.
        tester.addListener(new StopOnFailureListener()); //This listener forces the test class to stop running as soon as a failure is encountered in the model.
        tester.addListener("verbose"); //This gives you printed statements of the transitions being performed along with the source and destination states.
        tester.addCoverageMetric(new TransitionPairCoverage()); //Records the transition pair coverage i.e. the number of paired transitions traversed during the execution of the test.
        tester.addCoverageMetric(new StateCoverage()); //Records the state coverage i.e. the number of states which have been visited during the execution of the test.
        tester.addCoverageMetric(new ActionCoverage()); //Records the number of @Action methods which have ben executed during the execution of the test.
        tester.generate(500); //Generates 500 transitions
        tester.printCoverage(); //Prints the coverage metrics specified above.
    }


}
