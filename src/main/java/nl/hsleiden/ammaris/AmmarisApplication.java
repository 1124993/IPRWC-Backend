package nl.hsleiden.ammaris;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmmarisApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmmarisApplication.class, args);
        System.out.println("AmmarisApplication started");
	}

}
