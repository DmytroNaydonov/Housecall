package manager;

import config.TestConfig;
import lombok.RequiredArgsConstructor;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

import static utils.ResourceUtils.readAsStream;

@RequiredArgsConstructor
public class ConfigManager {

    private static final String CONFIG_FILENAME = "properties.yaml";

    public TestConfig create() {
        try (InputStream is = readAsStream(CONFIG_FILENAME, TestConfig.class)) {
            return new Yaml().loadAs(is, TestConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read resource path: %s".formatted(CONFIG_FILENAME), e);
        }
    }
}
