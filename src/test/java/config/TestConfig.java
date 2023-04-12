package config;

import lombok.Data;

@Data
public class TestConfig {

    private String url;
    private BrowserConfig browser;
    private CredentialsConfig credentials;
}
