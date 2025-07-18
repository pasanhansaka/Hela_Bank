package lk.pasanhansaka.bank.core.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class AccountException extends RuntimeException {
  public AccountException(String message) {
    super(message);
  }
}
