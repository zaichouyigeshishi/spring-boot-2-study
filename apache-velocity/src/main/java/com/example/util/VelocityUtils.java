package com.example.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.Properties;

/**
 * @author hudongshan
 */
public class VelocityUtils {

    public static void main(String[] args) {

        //  初始化 模版引擎
        Properties p = new Properties();
        try {
            // 加载classpath目录下的vm文件
            p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            // 定义字符集
            p.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
//            p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
            // 初始化Velocity引擎，指定配置Properties
            Velocity.init(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("packageName","com.haha.controller");
        velocityContext.put("className","AdminController");

        StringWriter stringWriter = new StringWriter();
        Template template = Velocity.getTemplate("vm/java/test.java.vm","UTF-8");
        template.merge(velocityContext,stringWriter);


        System.out.println("使用模版文件生成的内容：\n"+stringWriter.toString());
    }
}