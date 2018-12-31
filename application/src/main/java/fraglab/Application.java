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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

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

    @Bean
    public WebMvcConfigurer resourceHandlersConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry
                        .addResourceHandler("/app", "/app/", "/app/**", "/app/**/*")
                        .addResourceLocations("classpath:/META-INF/resources/")
                        .resourceChain(true)
                        .addResolver(new PathResourceResolver() {
                            @Override
                            protected Resource getResource(String resourcePath, Resource location) {
                                return new ClassPathResource("/META-INF/resources/index.html");
                            }
                        });
            }
        };
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
