package fun.bookish.plugin.archer.config;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class FreeMarkerCfg {

    private static final Configuration cfg;

    static {
        try {
            cfg = new Configuration(Configuration.VERSION_2_3_28);
            cfg.setDirectoryForTemplateLoading(new File(FreeMarkerCfg.class.getClassLoader().getResource("templates").toURI()));
            cfg.setEncoding(Locale.ENGLISH, "utf-8");
            cfg.setEncoding(Locale.CHINESE, "utf-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        }catch (Exception e){
            throw new IllegalStateException(e);
        }

    }

    private FreeMarkerCfg(){}

    public static Template getTemplate(String name){
        try {
            return cfg.getTemplate(name);
        } catch (IOException e) {
            throw new IllegalStateException("获取freemarker模版[" + name + "]失败");
        }
    }

}
