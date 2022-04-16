package com.master.authservice;

import com.master.authservice.domain.Canton;
import com.master.authservice.domain.Entity;
import com.master.authservice.domain.Municipality;
import com.master.authservice.model.Institution;
import com.master.authservice.model.Role;
import com.master.authservice.model.UserAccount;
import com.master.authservice.repository.InstitutionRepository;
import com.master.authservice.repository.RoleRepository;
import com.master.authservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class AuthServiceApplication {
    private static Logger logger = LoggerFactory.getLogger(AuthServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
        System.out.println("Lejla je zakon");
    }

    @Bean
    public CommandLineRunner addData(
            RoleRepository roleRepository,
            UserRepository userRepository,
            InstitutionRepository institutionRepository
    ) {
        return(args) -> {
            Role role1 = roleRepository.save(new Role("Administrator"));
            Role role2 = roleRepository.save(new Role("Covid Statistic Provider"));
            Role role3 = roleRepository.save(new Role("Covid Rules Provider"));
            logger.info("Role table seeded");

            UserAccount user1 = userRepository.save(new UserAccount("Roger", "Federer", "roger@mail.com", "pass123", Arrays.asList(role1)));
            UserAccount user2 = userRepository.save(new UserAccount("Rafa", "Nadal", "rafa@mail.com", "pass123", Arrays.asList(role2, role3)));
            logger.info("User table seeded");

            Institution institution = institutionRepository.save(new Institution(
                    "id-inst-number",
                    "Dom zdravlja",
                    Entity.FBIH,
                    Canton.KS,
                    Municipality.SARAJEVO,
                    "Adresa",
                    "123-123-123",
                    false,
                    user2
            ));
            logger.info("Institution table seeded");
        };
    }

}
