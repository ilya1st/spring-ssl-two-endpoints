package com.example.ssltest;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.apache.tomcat.util.net.SSLHostConfigCertificate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
public class ExternalHttpServer {

    @Value("${server.externalPort}")
    private int externalPort;
    @Value("${server.external.ssl.key-store}")
    private String keyStoreFile;
    @Value("${server.external.ssl.trust-store}")
    private String trustStoreFile;
    @Bean
    public ServletWebServerFactory servletContainer() throws Exception{
        var connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setPort(externalPort);
        connector.setScheme("https");
        connector.setSecure(true);
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        protocol.setSSLEnabled(true);
        SSLHostConfig sslHostConfig = new SSLHostConfig();
        sslHostConfig.setInsecureRenegotiation(true);
        // TODO: all to config
        var cert = new SSLHostConfigCertificate(sslHostConfig, SSLHostConfigCertificate.Type.RSA);



        var file = ResourceUtils.getFile(keyStoreFile);
        cert.setCertificateKeystoreFile(file.getAbsolutePath());
        cert.setCertificateKeystorePassword("qwerty");
        cert.setCertificateKeyPassword("qwerty");
        sslHostConfig.addCertificate(cert);

        file = ResourceUtils.getFile(trustStoreFile);
        sslHostConfig.setTruststoreFile(file.getAbsolutePath());
        sslHostConfig.setTruststorePassword("qwerty");
        //sslHostConfig.setSslProtocol("TLSv1.3");
        sslHostConfig.setCertificateVerification("REQUIRED"); // OMG
        sslHostConfig.setHostName("localhost");
        protocol.setDefaultSSLHostConfigName("localhost");
        protocol.addSslHostConfig(sslHostConfig);
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(connector);
        return tomcat;
    }
}