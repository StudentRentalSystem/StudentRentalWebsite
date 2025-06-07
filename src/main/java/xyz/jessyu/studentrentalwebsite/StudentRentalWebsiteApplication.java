package xyz.jessyu.studentrentalwebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "xyz.jessyu.studentrentalwebsite")
@EnableCaching
public class StudentRentalWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentRentalWebsiteApplication.class, args);
    }

}
