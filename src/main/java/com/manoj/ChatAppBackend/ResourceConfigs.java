package com.manoj.ChatAppBackend;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class ResourceConfigs implements WebMvcConfigurer
{
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS =
    {
		"file:ChatApp/"
    };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/ChatApp/**")
		.addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS)
		.setCachePeriod(3000);
    }
}