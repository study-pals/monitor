package com.monitoringPal.studyPal.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/studyPal")
public class LogViewController {

    // /logs/view 로 접속하면 templates/logs/log-view.html 반환
    @GetMapping("/logs")
    public String viewLogs() {
        return "studyPal/log-view";
    }
}