package First_spring_app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class GitHubClient {

    @Value("${github.token}")
    private String token;

    private final RestTemplate restTemplate = new RestTemplate();

    private HttpEntity<String> getEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Accept", "application/vnd.github+json");
        return new HttpEntity<>(headers);
    }

    // ✅ PAGINATION SUPPORT
    public List<Map<String, Object>> getRepos(String name, int page) {

        HttpEntity<String> entity = getEntity();

        // 🔹 Try ORG API
        String orgUrl = "https://api.github.com/orgs/" + name +
                "/repos?page=" + page + "&per_page=100";

        try {
            ResponseEntity<List> response = restTemplate.exchange(
                    orgUrl,
                    HttpMethod.GET,
                    entity,
                    List.class
            );

            return response.getBody();

        } catch (org.springframework.web.client.HttpClientErrorException.NotFound e) {

            // 🔥 FALLBACK to USER API
            String userUrl = "https://api.github.com/users/" + name +
                    "/repos?page=" + page + "&per_page=100";

            ResponseEntity<List> response = restTemplate.exchange(
                    userUrl,
                    HttpMethod.GET,
                    entity,
                    List.class
            );

            return response.getBody();
        }
    }
    public List<Map<String, Object>> getCollaborators(String owner, String repo) {

        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/collaborators";

        ResponseEntity<List<Map<String, Object>>> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        getEntity(),
                        new ParameterizedTypeReference<List<Map<String, Object>>>() {}
                );

        return response.getBody();
    }
}