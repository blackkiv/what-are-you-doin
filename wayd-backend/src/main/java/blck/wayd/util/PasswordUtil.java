package blck.wayd.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Password Util.
 */
public record PasswordUtil() {

    public static String hashPassword(String rawPassword) {
        return DigestUtils.md5Hex(rawPassword);
    }
}
