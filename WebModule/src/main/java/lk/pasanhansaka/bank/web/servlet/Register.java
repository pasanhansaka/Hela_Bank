package lk.pasanhansaka.bank.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.pasanhansaka.bank.core.email.CustomerAccountActivationEmail;
import lk.pasanhansaka.bank.core.entity.CustomerAccount;
import lk.pasanhansaka.bank.core.mailer.MailServiceProvider;
import lk.pasanhansaka.bank.core.service.CustomerAccountService;
import lk.pasanhansaka.bank.core.util.Encryptor;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@WebServlet("/register")
public class Register extends HttpServlet {

    @EJB
    private CustomerAccountService customerAccountService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String username = request.getParameter("username");
        String nic = request.getParameter("nic");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String password = request.getParameter("password");

        String encryptedPassword = Encryptor.encrypt(password);
        String verificationCode = UUID.randomUUID().toString();

        //Create Customer Account
        CustomerAccount customerAccount = new CustomerAccount(fname, lname, username, nic, email, mobile, encryptedPassword);
        customerAccount.setVerificationCode(verificationCode);
        customerAccount.setCreationDate(new Date());

        //Register Customer
        customerAccountService.registerCustomerAccount(customerAccount);

        //Make Verify ULR Data
        String baseURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        //Send Email
        CustomerAccountActivationEmail accountActivationEmail = new CustomerAccountActivationEmail(username, verificationCode, baseURL);
        MailServiceProvider.getInstance().sendMail(accountActivationEmail);

        //Go to login page
        response.sendRedirect(request.getContextPath() + "/login.jsp");


    }
}
