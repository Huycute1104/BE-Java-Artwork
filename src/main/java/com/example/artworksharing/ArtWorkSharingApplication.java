package com.example.artworksharing;

import com.example.artworksharing.auth.AuthenticationService;
import com.example.artworksharing.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.example.artworksharing.enums.Role.*;

@SpringBootApplication
public class ArtWorkSharingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtWorkSharingApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            //supper admin
            var super_admin = RegisterRequest.builder()
                    .name("Super Admin")
                    .email("Superadmin@gmail.com")
                    .password("123")
                    .phone("0326514968")
                    .status(true)
                    .account_balance(0.0)
                    .role(SUPER_ADMIN)
                    .build();
            System.out.println("Super_Admin token :" + service.register(super_admin).getAccessToken());
            //admin
            var admin = RegisterRequest.builder()
                    .name("Admin")
                    .email("Admin@gmail.com")
                    .password("123")
                    .phone("0392272536")
                    .status(true)
                    .account_balance(0.0)
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token :" + service.register(admin).getAccessToken());

            //audience
            var audience = RegisterRequest.builder()
                    .name("Audience1")
                    .email("huypt110402@gmail.com")
                    .status(true)
                    .password("123")
                    .phone("0854512367")
                    .account_balance(0.0)
                    .role(AUDIENCE)
                    .build();
            System.out.println("Audience token :" + service.register(audience).getAccessToken());

            var audience2 = RegisterRequest.builder()
                    .name("Audience2")
                    .email("tuanpn123012@gmail.com")
                    .status(true)
                    .password("123")
                    .phone("0854512367")
                    .account_balance(0.0)
                    .role(AUDIENCE)
                    .build();
            service.register(audience2);

            //creator

            var creator = RegisterRequest.builder()
                    .name("Huy")
                    .email("huypt160548@fpt.edu.vn")
                    .status(true)
                    .password("123")
                    .phone("0326514875")
                    .role(CREATOR)
                    .account_balance(0.0)
                    .build();
            System.out.println("Creator token :" + service.register(creator).getAccessToken());

            var creator2 = RegisterRequest.builder()
                    .name("Vuong")
                    .email("votrongvuong001@gmail.com")
                    .status(true)
                    .password("123")
                    .phone("0326514875")
                    .role(CREATOR)
                    .account_balance(0.0)
                    .build();
            service.register(creator2);

        };
    }
}
