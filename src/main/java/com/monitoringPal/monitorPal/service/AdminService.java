package com.monitoringPal.monitorPal.service;


import com.monitoringPal.monitorPal.dao.AdminRepository;
import com.monitoringPal.monitorPal.entity.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createAdmin(String username, String rawPassword) {
        if (adminRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 존재하는 관리자입니다.");
        }

        System.out.println("on service");
        String encodedPassword = passwordEncoder.encode(rawPassword);


        Admin admin = Admin.builder()
                .username(username)
                .password(encodedPassword)
                .build();

        adminRepository.save(admin);
    }
}
