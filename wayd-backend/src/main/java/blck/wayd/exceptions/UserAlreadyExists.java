package blck.wayd.exceptions;

public class UserAlreadyExists extends AppException {

    public UserAlreadyExists(String username) {
        super("User [%s] already exists".formatted(username));
    }
}
