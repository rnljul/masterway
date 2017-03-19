package ru.fancytraffic.masterway.templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * @author Roman Ljulchenko
 *         rnljul@gmail.com
 *         -----
 *         Created  on 02.03.2017.
 */
public class PageGenerator {
    private static final String HTML_DIR = "src/server/resources";
    private  static PageGenerator pageGenerator;
    private final Configuration cfg;

    private PageGenerator() {
        cfg = new Configuration();
    }

    public static PageGenerator instance(){
        if(pageGenerator == null){
            pageGenerator = new PageGenerator();
        }
        return pageGenerator;
    }

    public  String getPage(Map<String, Object> pageVariables){
        Writer stream = new StringWriter();
        try {
            Template template = cfg.getTemplate(HTML_DIR + File.separator + "status.html");
            template.process(pageVariables, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }

}
