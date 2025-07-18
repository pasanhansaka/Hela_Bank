package lk.pasanhansaka.bank.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.pasanhansaka.bank.core.entity.CustomerAccount;
import lk.pasanhansaka.bank.core.model.CustomerAccountStatus;
import lk.pasanhansaka.bank.core.service.CustomerAccountService;

import java.io.IOException;
import java.util.Base64;

@WebServlet("/activate")
public class CustomerAccountActivator extends HttpServlet {

    @EJB
    private CustomerAccountService customerAccountService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String verificationCode = request.getParameter("code");
        String username = new String(Base64.getDecoder().decode(id));

        //Activate Customer Account
        CustomerAccount customer = customerAccountService.getCustomerByUsername(username);
        if (customer != null && customer.getVerificationCode().equals(verificationCode)) {
            customer.setCustomerAccountStatus(CustomerAccountStatus.ACTIVE);
            customerAccountService.updateCustomerAccount(customer);

            response.sendRedirect(request.getContextPath() + "/activation/activation.jsp");

        } else {
            response.sendRedirect(request.getContextPath() + "/activation/activation-error.jsp");

        }

    }
}
