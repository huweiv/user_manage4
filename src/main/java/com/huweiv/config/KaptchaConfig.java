package com.huweiv.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName CaptchaConfig
 * @Description TODO
 * @CreateTime 2022/4/12 13:31
 */
@Configuration
public class KaptchaConfig {

    @Bean("kaptchaProducer")
    public DefaultKaptcha getKaptchaBean() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.border.color", "105,179,90");
        properties.setProperty("kaptcha.image.width", "100");
        properties.setProperty("kaptcha.image.height", "45");
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        properties.setProperty("kaptcha.textproducer.font.size", "35");
        properties.setProperty("kaptcha.textprodecer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.names", "\\u5B8B\\u4F53,\\u6977\\u4F53,\\u5FAE\\u8F6F\\u96C5\\u9ED1");

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
