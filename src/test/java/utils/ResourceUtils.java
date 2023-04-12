package utils;

import lombok.val;

import java.io.InputStream;

public final class ResourceUtils {

    private ResourceUtils() { }

    public static <T> InputStream readAsStream(String path, Class<T> type) {
        val is = type.getResourceAsStream("/" + path);

        if (is == null) {
            throw new IllegalArgumentException("Failed to read resource path " + path);
        }

        return is;
    }
}
