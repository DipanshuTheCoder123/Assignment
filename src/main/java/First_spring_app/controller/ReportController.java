package First_spring_app.controller;

import First_spring_app.dto.RepoAccess;
import First_spring_app.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ReportController {

    @Autowired
    private GitHubService service;

    @GetMapping("/access-report")
    public Map<String, List<RepoAccess>> getReport(@RequestParam String org) {

        if (org == null || org.isEmpty()) {
            throw new IllegalArgumentException("Organization cannot be empty");
        }

        return service.generateReport(org);
    }
}