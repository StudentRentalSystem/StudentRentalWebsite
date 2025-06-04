package xyz.jessyu.studentrentalwebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "xyz.jessyu.studentrentalwebsite")
public class StudentRentalWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentRentalWebsiteApplication.class, args);
    }

}
