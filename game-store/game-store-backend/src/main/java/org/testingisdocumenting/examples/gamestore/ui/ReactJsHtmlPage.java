package org.testingisdocumenting.examples.gamestore.ui;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.json.JsonParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ReactJsHtmlPage {
    private final String css;
    private final String bundleJavaScript;

    public ReactJsHtmlPage() {
        Map<String, Object> manifest = loadManifest();
        css = resourceTextContent(manifest.get("main.css").toString());
        bundleJavaScript = resourceTextContent(manifest.get("main.js").toString());
    }

    private Map<String, Object> loadManifest() {
        String assetManifest = resourceTextContent("asset-manifest.json");
        return JsonParserFactory.getJsonParser().parseMap(assetManifest);
    }

    public static String resourceTextContent(String resourcePath) {
        InputStream stream = ReactJsHtmlPage.class.getClassLoader().getResourceAsStream(resourcePath);
        if (stream == null) {
            throw new IllegalArgumentException("can't find resource: " + resourcePath);
        }

        try {
            return IOUtils.toString(stream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
