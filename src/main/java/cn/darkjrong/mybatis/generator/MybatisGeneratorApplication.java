package cn.darkjrong.mybatis.generator;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@MapperScan("cn.darkjrong.mybatis.generator.mapper")
@SpringBootApplication
public class MybatisGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MybatisGeneratorApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.setHeadless(false);
        application.run(args);
        MybatisGeneratorFxApplication.main(args);
    }

}
