package com.monitoringPal.monitorPal.service;

import com.monitoringPal.monitorPal.dao.AdminRepository;
import com.monitoringPal.monitorPal.entity.Admin;
import com.monitoringPal.monitorPal.entity.AdminDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;


    @Override
    public UserDetails loadUserByUsername(String username) {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 관리자 없음"));
        return new AdminDetails(admin);
    }
}
