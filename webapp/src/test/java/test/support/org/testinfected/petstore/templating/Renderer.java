package test.support.org.testinfected.petstore.templating;

import org.testinfected.hamcrest.ExceptionImposter;
import org.testinfected.petstore.MustacheRendering;
import org.testinfected.petstore.ClassPathResourceLoader;
import org.w3c.dom.Element;

import java.util.HashMap;

import static test.support.com.pyxis.petstore.views.HTMLDocument.toElement;

public class Renderer {

    public static Renderer render(String template) {
        return new Renderer(template);
    }

    private final String template;

    private String charset;
    private Object context;
    private String content;

    private Renderer(String template) {
        this(template, "utf-8");
    }

    private Renderer(String template, String charset) {
        this.template = template;
        this.charset = charset;
        this.context = new HashMap<String, String>();
    }

    public void withEncoding(String encoding) {
        this.charset = encoding;
    }

    public Renderer using(Object context) {
        this.context = context;
        return this;
    }

    public String asString() {
        render();
        return content;
    }

    public Element asDom() {
        return toElement(asString());
    }

    private void render() {
        try {
            MustacheRendering mustache = new MustacheRendering(new ClassPathResourceLoader(), charset);
            content = mustache.render(template, context);
        } catch (Exception e) {
            throw ExceptionImposter.imposterize(e);
        }
    }
}

