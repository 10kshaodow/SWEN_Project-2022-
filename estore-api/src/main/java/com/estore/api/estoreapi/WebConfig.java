package com.estore.api.estoreapi;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.estore.api.estoreapi.model.products.Product;
import com.estore.api.estoreapi.model.products.ProductCombinedSerializer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
    }

    /**
     * Allow the backend to serve static image files to whoever requests them
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/uploads/");
    }

    /**
     * Used by spring and jackson to correctly convert incoming HTTP messages into
     * the correct product POJO to be proccessed by the controller layer
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // TODO Auto-generated method stub
        WebMvcConfigurer.super.configureMessageConverters(converters);
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializerByType(Product.class, new ProductCombinedSerializer.ProductSerializer());
        builder.deserializerByType(Product.class, new ProductCombinedSerializer.ProductDeserializer());
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    }
}