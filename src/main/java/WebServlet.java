import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@javax.servlet.annotation.WebServlet("/index")
public class WebServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String card = request.getParameter("card");
        String cardType = request.getParameter("cardType");
        String expiry = request.getParameter("expiry");
        String cvv = request.getParameter("cvv");
        String amount = request.getParameter("amount");
        System.out.println(name +"\n" + address + "\n" + card +"\n" + cardType + "\n" + expiry + "\n" + cvv + "\n" + amount);
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }
}
