import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

public class SSLValidator {

    public static void main(String[] args) {
        if (args.length < 4) {
            System.err.println("Usage: SSLValidator <url> <port> <truststorePath> <truststorePassword> [truststoreType]");
            System.exit(1);
        }
        String urlStr = args[0];
        int port = Integer.parseInt(args[1]);
        String truststorePath = args[2];
        String truststorePassword = args[3];
        String truststoreType = args.length > 4 ? args[4] : "JKS"; // Default truststore type is JKS

        try {
            // Load truststore
            KeyStore truststore = KeyStore.getInstance(truststoreType);
            InputStream truststoreStream = new FileInputStream(truststorePath);
            truststore.load(truststoreStream, truststorePassword.toCharArray());

            // Create TrustManagerFactory using truststore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(truststore);

            // Create SSLContext with TrustManagerFactory
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            // Set custom SSLSocketFactory to HttpsURLConnection
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);

            // Example: Make an HTTPS connection
            URL url = new URL("https", urlStr, port, "");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.connect();

            // Get SSL certificate details
            Certificate[] certificates = connection.getServerCertificates();
            X509Certificate serverCertificate = (X509Certificate) certificates[0]; // Assuming first certificate is server certificate
            String subject = serverCertificate.getSubjectDN().getName();
            String issuer = serverCertificate.getIssuerDN().getName();
            String validFrom = serverCertificate.getNotBefore().toString();
            String validUntil = serverCertificate.getNotAfter().toString();

            // Check if connection was successful
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                System.out.println("Connection successful!");
                System.out.println("SSL certificate details:");
                System.out.println("Subject: " + subject);
                System.out.println("Issuer: " + issuer);
                System.out.println("Valid From: " + validFrom);
                System.out.println("Valid Until: " + validUntil);
            } else {
                System.out.println("Connection failed with response code: " + responseCode);
            }

            // Disconnect
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
