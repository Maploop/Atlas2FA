package net.maploop.api;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import net.maploop.api.exception.ValidationException;

public class TwoFactorAPI {

    /**
     * Check if a 6-digit code is a valid 2FA key
     *
     * @param secret The secret key unique to the user
     * @param code The 6-digit code that was provided
     * @return If the 6-digit code provided is a valid 2FA key
     */
    public static boolean isValidKey(String secret, int code) {
        return new GoogleAuthenticator().authorize(secret, code);
    }

    /**
     * Check if a 6-digit code is a valid 2FA key
     *
     * @param secret The secret key unique to the user
     * @param code The 6-digit code that was provided
     * @return If the 6-digit code provided is a valid 2FA key
     * @throws ValidationException If the string code provided cannot be parsed as an integer
     */
    public static boolean isValidKey(String secret, String code) throws ValidationException {
        int integerCode;
        try {
            integerCode = Integer.parseInt(code);
        } catch (NumberFormatException ex) {
            throw new ValidationException("'" + code + "' is not a valid integer");
        }

        return new GoogleAuthenticator().authorize(secret, integerCode);
    }
}
