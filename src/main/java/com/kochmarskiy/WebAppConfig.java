package com.kochmarskiy;

import com.kochmarskiy.dao.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Created by Кочмарский on 15.10.2015.
 */
@Configuration
@ComponentScan
public class WebAppConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/style/**")
                .addResourceLocations("/style/");
        registry.addResourceHandler("/img/**")
                .addResourceLocations("/img/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/js/");
        registry.addResourceHandler("/lib/**")
                .addResourceLocations("/lib/");


    }
    @Bean
    public DataSource getDataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/shop");
        dataSource.setUsername("root");
        dataSource.setPassword("561147");
        return dataSource;

    }
    @Bean
    public ItemShortDescribeDAO getItemShortDescribeDAO() {
        return new ItemShortDescribeDAOImpl(getDataSource());
    }
    @Bean
    public ItemDAO getItemDAO() {
        return new ItemDAOImpl(getDataSource());
    }
    @Bean(name = "AnswerCommentDAO")
    public CommentDAO getAnswerCommentDAO(){
        return new AnswerCommentDAOImpl(getDataSource());
    }
    @Bean(name = "CommentDAO")
    public CommentDAO getCommentDAO(){
        return new CommentDAOImpl(getDataSource());

    }
    @Bean
    public CategoryDAO getCategoryDAO(){
        return new CategoryDAOImpl(getDataSource());

    }


}
