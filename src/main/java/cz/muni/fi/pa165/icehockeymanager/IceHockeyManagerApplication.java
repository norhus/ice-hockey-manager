package cz.muni.fi.pa165.icehockeymanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class IceHockeyManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IceHockeyManagerApplication.class, args);
    }
}
