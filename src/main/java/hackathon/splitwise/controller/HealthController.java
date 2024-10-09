package hackathon.splitwise.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gauravlikhar
 * @project hackathon-splitwise
 */

@RestController
public class HealthController {
    @GetMapping("/health")
    public String healthCheck() {
        return "Application is running";
    }
}
