package lk.pasanhansaka.bank.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.pasanhansaka.bank.core.service.BankAccountService;

import java.io.IOException;

@WebServlet("/customer/account/status")
public class AccountStatus extends HttpServlet {

    @EJB
    private BankAccountService bankAccountService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String accountNumber = request.getParameter("accountNumber");

        if (action != null && accountNumber != null) {
            try {

                if (action.equals("disable")) {
                    bankAccountService.disableBankAccount(accountNumber);

                } else if (action.equals("enable")) {
                    bankAccountService.enableBankAccount(accountNumber);

                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        response.sendRedirect(request.getContextPath() + "/index.jsp");

    }
}
