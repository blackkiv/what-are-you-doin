package blck.wayd.exceptions;

import java.util.UUID;

/**
 * User Not Exists.
 */
public class UserNotExists extends AppException {

    public UserNotExists(UUID token) {
        super("User with token [%s] not exist".formatted(token));
    }

    public UserNotExists(String username) {
        super("User [%s] not exist".formatted(username));
    }
}
