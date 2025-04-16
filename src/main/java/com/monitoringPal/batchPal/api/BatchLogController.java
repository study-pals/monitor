package com.monitoringPal.batchPal.api;

import com.monitoringPal.batchPal.dto.LogResponse;
import com.monitoringPal.batchPal.service.BatchLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BatchLogController {

    private final BatchLogService batchLogService;

    @GetMapping("/batchPal/jobs")
    public String viewLogs(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<LogResponse> logs = batchLogService.getLogs(pageable);
        model.addAttribute("logs", logs.getContent());
        model.addAttribute("page", logs);
        return "batchPal/jobs";
    }

    @GetMapping("/batchPal/getLog")
    @ResponseBody
    public List<LogResponse> getLogs(@PageableDefault(size = 10) Pageable pageable) {
        return batchLogService.getLogs(pageable).getContent();
    }
}