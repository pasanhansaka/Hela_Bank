package lk.pasanhansaka.bank.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.pasanhansaka.bank.core.entity.CustomerAccount;
import lk.pasanhansaka.bank.core.model.BankAccountType;
import lk.pasanhansaka.bank.core.service.BankAccountService;

import java.io.IOException;

@WebServlet("/customer/newAccount")
public class OpenSavingsBankAccount extends HttpServlet {

    @EJB
    private BankAccountService bankAccountService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            CustomerAccount customer = (CustomerAccount) request.getSession().getAttribute("customer");

            //Check if Customer is in the session and Customer isn't null.
            if (request.getSession().getAttribute("customer") == null || customer == null) {
                System.out.println("Customer is null or nic not found");
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }

            //Create Bank Account
            bankAccountService.createBankAccount(customer, BankAccountType.SAVINGS);
            System.out.println("Bank Account Created Successfully");
            response.sendRedirect(request.getContextPath() + "/index.jsp");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
