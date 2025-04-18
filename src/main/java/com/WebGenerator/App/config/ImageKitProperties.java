package com.WebGenerator.App.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "imagekit")
public class ImageKitProperties {

    private String urlEndpoint;
    private String privateKey;
    private String publicKey;

    public String getUrlEndpoint() { return urlEndpoint; }
    public void setUrlEndpoint(String urlEndpoint) { this.urlEndpoint = urlEndpoint; }

    public String getPrivateKey() { return privateKey; }
    public void setPrivateKey(String privateKey) { this.privateKey = privateKey; }

    public String getPublicKey() { return publicKey; }
    public void setPublicKey(String publicKey) { this.publicKey = publicKey; }
}
