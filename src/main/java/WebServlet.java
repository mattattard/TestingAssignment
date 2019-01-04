import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@javax.servlet.annotation.WebServlet("/index")
public class WebServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List <String> error = new ArrayList<>();
        String name = request.getParameter("name");
        if(request.getParameter("name").isEmpty()) error.add("name");
        if(request.getParameter("address").isEmpty()) error.add("address");
        if(request.getParameter("card").isEmpty()) error.add("card");
        if(request.getParameter("expiry").isEmpty()) error.add("expiry");
        if(request.getParameter("cvv").isEmpty()) error.add("cvv");
        if(request.getParameter("amount").isEmpty()) error.add("amount");
        String address = request.getParameter("address");
        String card = request.getParameter("card");
        String cardType = request.getParameter("cardType");
        String expiry = request.getParameter("expiry");
        String cvv = request.getParameter("cvv");
        String amount = request.getParameter("amount");
        System.out.println(name +"\n" + address + "\n" + card +"\n" + cardType + "\n" + expiry + "\n" + cvv + "\n" + amount);
        if(error.isEmpty()){
            CCInfo info = new CCInfo(name,address,cardType,card,expiry,cvv);
            PaymentProcessor paymentProcessor = new PaymentProcessor();
            int result = paymentProcessor.processPayment(info,Integer.parseInt(amount));
            if(result == 0){
                request.getRequestDispatcher("/successful.jsp").forward(request,response);
            }else {
                String message = "Error processing your transaction";
                request.setAttribute("result ", message);
                request.getRequestDispatcher("/failed.jsp").forward(request,response);
            }
        }else {
            String message = "These were found missing :" + error.toString();
            request.setAttribute("result", message );
            request.getRequestDispatcher("/failed.jsp").forward(request,response);
        }
    }
}
