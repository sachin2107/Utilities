package com.https.enable.ssl;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.Ssl.ClientAuth;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class HttpServerConfig implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

	@Value("${server.port:0}")
	private String serverPort;

	@Value("${api.server.ssl.enable:Y}")
	private String apiServerSSLEnable;

	@Value("${api.server.ssl.client.auth:NEED}")
	private String apiServerSSLClientAuth;

	@Value("${api.server.ssl.protocol:TLSv1.2}")
	private String apiServerSSLProtocol;

	@Value("${api.server.ssl.enabled.protocol:TLSv1.2}")
	private String apiServerSSLEnabledProtocol;
	
	@Value("${api.server.ssl.keystore.location:folder_path/springboot.jks}")
	private String apiServersSSLKeystoreLocation;
	
	@Value("${api.server.ssl.keystore.password:password}")
	private String apiServerSSLKeystorePassword;
	
	@Value("${api.server.ssl.keystore.type:jks}")
	private String apiServerSSLKeystoreType;
	
	@Value("${api.server.ssl.truststore.location:folder_path/springboot.jks}")
	private String apiServerSSLTruststoreLocation;
	
	@Value("${api.server.ssl.truststore.password:password}")
	private String apiServerSSLTruststorePassword;
	
	@Value("${api.server.ssl.trustore.type:jks}")
	private String apiServerSSLTruststoreType;
	
	@Value("${api.server.ssl.enabled.protocol:TLSv1.2}")
	private String[] apiServerEnabledProtocol;
	
	@Value("${api.server.ssl.cipher.suits:TLS_DHE_DSS_WITH_AES_128_GCM_SHA256,TLS_DHE_DSS_WITH_AES_128_GCM_SHA384}")
	private String[] apiServerSSLCipherSuits;

	@Override
	public void customize(TomcatServletWebServerFactory factory) {
		if (apiServerSSLEnable.equals("Y")) {
			System.out.println("Loading SSL servlet context for api server...");
			getSSLContext(apiServerSSLProtocol, apiServersSSLKeystoreLocation, apiServerSSLKeystorePassword,
					apiServerSSLKeystoreType, apiServerSSLTruststoreLocation, apiServerSSLTruststorePassword,
					apiServerSSLTruststoreType);
			Ssl ssl = new Ssl();
			ssl.setProtocol(apiServerSSLProtocol);
			ssl.setEnabledProtocols(apiServerEnabledProtocol);
			ssl.setCiphers(apiServerSSLCipherSuits);
			ssl.setKeyStore(apiServersSSLKeystoreLocation);
			ssl.setClientAuth(ClientAuth.valueOf(apiServerSSLClientAuth));
			ssl.setKeyStoreType(apiServerSSLKeystoreType);
			ssl.setTrustStore(apiServerSSLTruststoreLocation);
			ssl.setTrustStorePassword(apiServerSSLTruststorePassword);
			ssl.setTrustStoreType(apiServerSSLTruststoreType);
			factory.setSsl(ssl);

		}
	}

	public SSLContext getSSLContext(String protocol, String keyStore, String keyStorePwd, String keyStoreType,
			String trustStore, String trustStorePwd, String trustStoreType) {
		SSLContext sslContext = null;
		try {
			System.out.println("Initializing SSL COntext" + protocol + " " + keyStore + " " + keyStoreType);
			sslContext = SSLContext.getInstance(protocol);

			KeyManager[] keyManager = getKeyManager(keyStore, keyStoreType, keyStorePwd);
			TrustManager[] trustManager = getTrustManager(trustStore, trustStoreType, trustStorePwd);

			sslContext.init(keyManager, trustManager, null);
			System.out.println("SSL context initialized");
		} catch (Exception e) {
			System.out.println("Exception in getSSLCOntext()=" + e);
		}
		return sslContext;
	}

	private TrustManager[] getTrustManager(String trustStore, String trustStoreType, String trustStorePwd)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		TrustManager[] trustManager = null;
		TrustManagerFactory trustManagerFactory = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		System.out.println("Factory for trust key managers = " + trustManagerFactory);

		KeyStore keyStoreLocal;
		if (null != trustStoreType) {
			keyStoreLocal = KeyStore.getInstance(trustStoreType);
		} else {
			keyStoreLocal = KeyStore.getInstance(KeyStore.getDefaultType());
		}

		if (null != trustStore && !"NONE".equals(trustStore)) {
			try (FileInputStream fileInputStream = new FileInputStream(trustStore)) {
				keyStoreLocal.load(fileInputStream, trustStorePwd.toCharArray());
			}
			trustManagerFactory.init(keyStoreLocal);
			trustManager = trustManagerFactory.getTrustManagers();
		}

		return trustManager;
	}

	public KeyManager[] getKeyManager(String keyStore, String keyStoreType, String keyStorePwd)
			throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException,
			UnrecoverableKeyException {
		KeyManager[] keyManager = null;

		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		System.out.println("Factory for key managers = " + keyManagerFactory);

		KeyStore keyStoreLocal;
		if (null != keyStoreType) {
			keyStoreLocal = KeyStore.getInstance(keyStoreType);
		} else {
			keyStoreLocal = KeyStore.getInstance(KeyStore.getDefaultType());
		}

		if (null != keyStore && !"NONE".equals(keyStore)) {
			try (FileInputStream fileInputStream = new FileInputStream(keyStore)) {
				keyStoreLocal.load(fileInputStream, keyStorePwd.toCharArray());
			}
			char[] password = null;
			if (keyStorePwd.length() > 0) {
				password = keyStorePwd.toCharArray();
			}
			keyManagerFactory.init(keyStoreLocal, password);
			keyManager = keyManagerFactory.getKeyManagers();
		}
		return keyManager;
	}

}
