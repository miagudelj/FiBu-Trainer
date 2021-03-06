package ch.fibuproject.fibu;

import ch.fibuproject.fibu.controller.Controller;
import ch.fibuproject.fibu.util.Configuration;
import ch.fibuproject.fibu.database.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.*;

@SpringBootApplication(scanBasePackageClasses = {Controller.class})
public class FibuApplication implements WebMvcConfigurer {

    private String path = (new File(Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getParentFile().getParentFile().toString() + File.separator + "music" + File.separator).replace("file:", "");

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("file://" + path)
                .setCachePeriod(60);
    }

    public static void main(String[] args) {
        SpringApplication.run(FibuApplication.class, args);
        Configuration.init();
        Database.init();
    }

}
