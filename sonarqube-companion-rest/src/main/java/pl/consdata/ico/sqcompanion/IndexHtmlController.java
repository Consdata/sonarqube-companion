package pl.consdata.ico.sqcompanion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.MeterRegistry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class IndexHtmlController {

    private final ResourceLoader resourceLoader;
    private final String indexTemplatePath;
    private final ObjectMapper objectMapper;
    private final MeterRegistry meterRegistry;

    public IndexHtmlController(
            final ResourceLoader resourceLoader,
            final @Value("${app.indexTemplatePath:classpath:/resources/index.html}") String indexTemplatePath,
            final ObjectMapper objectMapper,
            final MeterRegistry meterRegistry) {
        this.resourceLoader = resourceLoader;
        this.indexTemplatePath = indexTemplatePath;
        this.objectMapper = objectMapper;
        this.meterRegistry = meterRegistry;
    }

    @RequestMapping({"/", "/index.html"})
    public String indexHtml(@RequestParam("baseHref") Optional<String> baseHrefParam) {
        meterRegistry.counter("counter.service.IndexHtmlController.indexHtml").increment();
        try (final InputStream indexInputStream = getIndexResource(indexTemplatePath).getInputStream()) {
            final Document template = Jsoup.parse(indexInputStream, "UTF-8", "");

            baseHrefParam.ifPresent(baseHref -> appendBaseHref(template, baseHref));
            appendAppConfig(template);

            return template.toString();
        } catch (IOException exception) {
            throw new SQCompanionException("Can't load index.html template", exception);
        }
    }

    private void appendAppConfig(Document template) throws JsonProcessingException {
        final Map<String, Object> config = new HashMap<>();
        config.put("random", 4);
        template.getElementById("appServerDefaultConfig")
                .after(
                        new StringBuilder()
                                .append("<script type=\"text/javascript\">")
                                .append("window.serverAppConfig = window.serverAppConfig || {};")
                                .append("Object.assign(window.serverAppConfig, ")
                                .append(objectMapper.writeValueAsString(config))
                                .append(");")
                                .append("</script>")
                                .toString()
                );
    }

    private void appendBaseHref(final Document template, final String baseHref) {
        template.getElementById("baseHrefElement").attr("href", baseHref);
    }

    private Resource getIndexResource(final String path) {
        final Resource indexResource = resourceLoader.getResource(path);
        if (indexResource.exists()) {
            return indexResource;
        } else {
            throw new SQCompanionException(String.format("Missing index.html on app classpath (%s)", path));
        }
    }

}
