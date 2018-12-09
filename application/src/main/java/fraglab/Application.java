package fraglab;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@EnableCaching
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Value("${git.hash:XXXXXXXX}")
    private String gitHash;

    @Value("${build.timestamp:yyyyMMddHHmm}")
    private String buildTimestamp;

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("authors");
    }

    @Bean
    public Mapper dozerMapper() {
        return DozerBeanMapperBuilder.buildDefault();
    }

    @Bean
    public BuildInfo buildInfo() {
        BuildInfo buildInfo = new BuildInfo();
        if (StringUtils.isBlank(gitHash) || gitHash.startsWith("$")) {
            buildInfo.setGitHash("N/A");
        } else {
            buildInfo.setGitHash(gitHash);
        }
        if (StringUtils.isBlank(buildTimestamp) || buildTimestamp.startsWith("$")) {
            DateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
            buildInfo.setTimeStamp(format.format(new Date()));
        } else {
            buildInfo.setTimeStamp(buildTimestamp);
        }
        return buildInfo;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedMethods("*");
            }
        };
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

}
