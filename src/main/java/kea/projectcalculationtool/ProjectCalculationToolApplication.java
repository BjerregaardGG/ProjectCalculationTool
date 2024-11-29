package kea.projectcalculationtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "kea.projectcalculationtool")
public class ProjectCalculationToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectCalculationToolApplication.class, args);
    }

}
