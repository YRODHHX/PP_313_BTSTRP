package ru.uvuk.pp311.PP311.initialization;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.uvuk.pp311.PP311.Repozitori.RoleRepository;
import ru.uvuk.pp311.PP311.Repozitori.UserRepository;
import ru.uvuk.pp311.PP311.model.Role;
import ru.uvuk.pp311.PP311.model.User;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public DataInitializer(RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;

        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Role adminRole = roleRepository.findByRoleName("ADMIN")
                .orElseGet(() -> {
                    Role role = new Role("ADMIN");
                    return roleRepository.save(role);
                });

        Role userRole = roleRepository.findByRoleName("USER")
                .orElseGet(() -> {
                    Role role = new Role("USER");
                    return roleRepository.save(role);
                });

        User adminUser = userRepository.findByUsername("admin")
                .orElseGet(() -> {
                    User user = new User();
                    user.setUsername("admin");
                    user.setPassword(passwordEncoder.encode("admin"));
                    user.setRoles(Set.of(adminRole, userRole));
                    return userRepository.save(user);
                });

        User regularUser = userRepository.findByUsername("user")
                .orElseGet(() -> {
                    User user = new User();
                    user.setUsername("user");
                    user.setPassword(passwordEncoder.encode("user"));
                    user.setRoles(Set.of(userRole));
                    return userRepository.save(user);
                });
    }
}