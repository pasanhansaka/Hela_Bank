package lk.pasanhansaka.bank.core.interceptor;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import lk.pasanhansaka.bank.core.annotation.LoggedException;

import java.util.logging.Logger;

@Interceptor
@LoggedException
@Priority(Interceptor.Priority.APPLICATION)
public class ExceptionLogger {

    private static final Logger LOGGER = Logger.getLogger(ExceptionLogger.class.getName());

    @AroundInvoke
    public Object logExceptions(InvocationContext invocationContext) throws Exception {
        try {
            return invocationContext.proceed();

        } catch (Exception e) {
            LOGGER.severe("An exception occurred in method: " + invocationContext.getMethod().getName() + " of class: " + invocationContext.getTarget().getClass().getName());
            LOGGER.severe("Exception details: " + e.getMessage());
            e.printStackTrace();

            return null;
        }
    }

}
