package lk.pasanhansaka.bank.web.servlet;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.pasanhansaka.bank.core.entity.CustomerAccount;
import lk.pasanhansaka.bank.core.service.CustomerAccountService;
import lk.pasanhansaka.bank.core.util.Encryptor;

import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {

    @Inject
    private SecurityContext securityContext;

    @EJB
    private CustomerAccountService customerAccountService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String encryptedPassword = Encryptor.encrypt(password);

        //Check Authentication
        AuthenticationParameters parameters = AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(username, encryptedPassword));

        AuthenticationStatus status = securityContext.authenticate(request, response, parameters);
        System.out.println("Authentication Status : " + status);

        //Do the tasks
        if (status == AuthenticationStatus.SUCCESS) {
            CustomerAccount customer = customerAccountService.getCustomerByUsername(username);
            request.getSession().setAttribute("customer", customer);

            response.sendRedirect(request.getContextPath() + "/index.jsp");

        } else {
            System.err.println("Login failed for user: " + username);
            request.setAttribute("error", "Invalid Username or Password");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

    }
}
