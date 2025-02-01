package cn.darkjrong.mybatis.generator.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 语言配置
 *
 * @author Rong.Jia
 * @date 2025/01/21
 */
@Configuration
public class LanguageConfig {
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("views");
        return messageSource;
    }
}