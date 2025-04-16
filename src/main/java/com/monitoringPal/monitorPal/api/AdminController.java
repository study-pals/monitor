package com.monitoringPal.monitorPal.api;

import com.monitoringPal.monitorPal.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/register")
    public String registerPage() {
        return "admin/register"; // templates/admin/register.html
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {
        try {
            adminService.createAdmin(username, password);
            model.addAttribute("message", "등록 성공!");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "등록 실패: " + e.getMessage());
            return "admin/register";
        }
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
        }
        if (logout != null) {
            model.addAttribute("message", "로그아웃 되었습니다.");
        }
        return "admin/login"; // templates/admin/login.html
    }
}