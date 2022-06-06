package com.master.authservice;

import com.master.authservice.domain.Canton;
import com.master.authservice.domain.Entity;
import com.master.authservice.domain.Institution;
import com.master.authservice.domain.Municipality;
import com.master.authservice.model.InstitutionEntity;
import com.master.authservice.model.RoleEntity;
import com.master.authservice.model.UserAccountEntity;
import com.master.authservice.repository.InstitutionRepository;
import com.master.authservice.repository.RoleRepository;
import com.master.authservice.repository.UserRepository;
import com.master.authservice.service.InstitutionService;
import com.master.authservice.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
public class AuthServiceApplication {
    private static Logger logger = LoggerFactory.getLogger(AuthServiceApplication.class);

    @Bean
    //@LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
        System.out.println("Lejla je zakon");
    }

    @Bean
    public CommandLineRunner addData(
            RoleRepository roleRepository,
            UserRepository userRepository,
            InstitutionRepository institutionRepository,
            InstitutionService institutionService
    ) {
        return(args) -> {
            RoleEntity roleEntity1 = roleRepository.save(new RoleEntity("Admin"));
            RoleEntity roleEntity2 = roleRepository.save(new RoleEntity("Covid Statistic Provider"));
            RoleEntity roleEntity3 = roleRepository.save(new RoleEntity("Covid Rules Provider"));
            RoleEntity roleEntity4 = roleRepository.save(new RoleEntity("Institution"));
            logger.info("Role table seeded");

            UserAccountEntity user1 = userRepository.save(new UserAccountEntity("Roger", "Federer", "roger@mail.com", PasswordUtil.hashPassword("pass123"), Arrays.asList(roleEntity1)));
            UserAccountEntity user2 = userRepository.save(new UserAccountEntity("Rafa", "Nadal", "rafa@mail.com", PasswordUtil.hashPassword("pass123"), Arrays.asList(roleEntity2, roleEntity3, roleEntity4)));
            logger.info("User table seeded");

            Institution institution = institutionService.add(new Institution(
                    null,
                    "id-inst-number",
                    "Dom zdravlja Novi Grad",
                    Entity.FBIH,
                    Canton.KS,
                    Municipality.SARAJEVO,
                    "Adresa",
                    "123-123-123",
                    false,
                    user2.toDomain()
            ));
            logger.info("Institution table seeded");
        };
    }

}
