package net.maploop.api;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import jdk.jshell.execution.Util;
import net.maploop.api.exception.SecretNoGeneratedException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TwoFactorAuth {
    /**
     * Service used to display QR code images
     */
    public static final String QR_CODE_SERVICE = "https://www.google.com/chart?chs=128x128&cht=qr&chl=otpauth://totp/";

    private String clientName;
    private String label;
    private String secret;

    /**
     * Generate a new 2FA QR code, can be used with Google Authenticator
     *
     * @param clientName Name of the application providing 2FA
     */
    public TwoFactorAuth(String clientName) {
        this.clientName = clientName;
        this.label = "Default Label";
    }

    /**
     * Generates a URL of a QR code based on your client secret
     *
     * @return URL of a QR code image
     * @throws SecretNoGeneratedException If {@link #secret} does not exist
     */
    public String generateQRCode() throws SecretNoGeneratedException {
        String raw = "%s?secret=%s&issuer=%s";
        if (secret == null)
            throw new SecretNoGeneratedException("Secret cannot be null");
        raw = String.format(raw, label, secret, clientName);

        String encoded = raw;
        try {
            encoded = URLEncoder.encode(raw, StandardCharsets.UTF_8.toString());
        } catch (Exception ignored) {
        }

        return QR_CODE_SERVICE + encoded;
    }

    /**
     * Generate your QR code as a {@link BufferedImage} instead of a URL
     *
     * @return Your QR code image that can be scanned by Google Authenticator
     * @throws SecretNoGeneratedException If {@link #secret} does not exist
     * @throws IOException If the API fails to generate the image from the URL
     */
    public BufferedImage generateQRCodeAsImage() throws SecretNoGeneratedException, IOException {
        String stringUrl = generateQRCode();
        URL url = new URL(stringUrl);
        URLConnection urc = url.openConnection();
        urc.addRequestProperty("User-Agent", "Mozilla//5.0");

        return ImageIO.read(urc.getInputStream());
    }

    /**
     * Generate your QR code as a {@link BufferedImage} instead of a URL with a custom size
     *
     * @param width Custom width of the image
     * @param height Custom height of the image
     * @return Your QR code image that can be scanned by Google Authenticator
     * @throws SecretNoGeneratedException If {@link #secret} does not exist
     * @throws IOException If the API fails to generate the image from the URL
     */
    public BufferedImage generateQRCodeAsImage(int width, int height) throws SecretNoGeneratedException, IOException {
        String stringUrl = generateQRCode();
        URL url = new URL(stringUrl);
        URLConnection urc = url.openConnection();
        urc.addRequestProperty("User-Agent", "Mozilla//5.0");

        BufferedImage image = ImageIO.read(urc.getInputStream());
        return Utility.toBufferedImage(image.getScaledInstance(width, height, image.getType()));
    }

    /**
     * Generates the secret key used by Google Authenticator and your server
     *
     * @return Instance of this class
     */
    public TwoFactorAuth generateSecret() {
        GoogleAuthenticator authenticator = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = authenticator.createCredentials();
        this.secret = key.getKey();
        return this;
    }

    /**
     * Change the label displayed as the user's username in Google Authenticator
     *
     * @param label New value of the label
     * @return Instance of this class
     */
    public TwoFactorAuth label(String label) {
        this.label = label;
        return this;
    }

    /**
     * Change the client name field
     * @param clientName New value of client name
     * @return Instance of this class
     */
    public TwoFactorAuth clientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public String getClientName() {
        return clientName;
    }

    public String getLabel() {
        return label;
    }

    public String getSecret() {
        return secret;
    }
}
