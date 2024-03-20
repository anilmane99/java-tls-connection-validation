# SSLValidator

## Overview

`SSLValidator` is a Java class that provides functionality to validate SSL connections using a truststore. It enables users to establish secure connections over HTTPS and verify the SSL certificates of remote servers.

## Usage

The `main` method of the `SSLValidator` class serves as the entry point for validating SSL connections. Users can execute this class with command-line arguments to specify the parameters required for establishing the SSL connection and validating the server certificate.

### Command-Line Syntax

```sh
java SSLValidator <url> <port> <truststorePath> <truststorePassword> [truststoreType]

<url>: The hostname or IP address of the server to connect to.
<port>: The port number of the servers HTTPS service.
<truststorePath>: The file path to the truststore containing trusted certificates.
<truststorePassword>: The password for accessing the truststore.
[truststoreType] (optional): The type of the truststore (default is "JKS").
SSL Validation Process
The SSLValidator class performs the following steps to validate SSL connections:
```

`Loading Truststore:` Load the truststore file specified by the user, containing trusted certificates.
`Creating TrustManagerFactory:` Create a TrustManagerFactory instance using the loaded truststore.
`Initializing SSLContext:` Initialize an SSLContext object with the TrustManagerFactory, enabling SSL communication with trusted certificates.
`Setting SSLSocketFactory:` Set a custom SSLSocketFactory to the HttpsURLConnection, enabling it to use the SSL context for secure connections.
`Establishing HTTPS Connection:` Construct an HttpsURLConnection object with the specified URL and port, and establish the HTTPS connection.
`Validating Server Certificate:` Retrieve the SSL certificate of the server, extract its details such as subject, issuer, and validity period, and print them to the console.
`Handling Connection Status:` Check the response code of the connection. If the response code is HTTP_OK, the connection is successful; otherwise, display an error message.
`Disconnecting:` Disconnect the HTTPS connection.
### Example
Here's an example command to validate an SSL connection using `SSLValidator`:
```
java SSLValidator example.com 443 /path/to/truststore.jks truststore_password PKCS12
```
This command establishes an HTTPS connection to https://example.com:443/, validates the server certificate using the specified truststore, and prints the certificate details.

### Dependencies
This class depends on the following Java libraries:
```
java.io.FileInputStream
java.io.InputStream
java.security.KeyStore
java.security.cert.Certificate
java.security.cert.X509Certificate
javax.net.ssl.SSLContext
javax.net.ssl.SSLSocketFactory
javax.net.ssl.TrustManagerFactory
javax.net.ssl.HttpsURLConnection
java.net.URL
Ensure that these libraries are available in your Java environment to use SSLValidator.
