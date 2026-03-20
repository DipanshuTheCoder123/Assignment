package First_spring_app.service;

import First_spring_app.dto.RepoAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class GitHubService {

    @Autowired
    private GitHubClient client;

    public Map<String, List<RepoAccess>> generateReport(String org) {

        Map<String, List<RepoAccess>> userRepoMap = new ConcurrentHashMap<>();

        // ✅ Pagination
        List<Map<String, Object>> allRepos = new ArrayList<>();
        int page = 1;

        while (true) {
            List<Map<String, Object>> repos = client.getRepos(org, page);

            if (repos == null || repos.isEmpty()) break;

            allRepos.addAll(repos);
            page++;
        }

        // ✅ Thread Pool
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (Map<String, Object> repo : allRepos) {

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {

                try {
                    String repoName = (String) repo.get("name");
                    String owner = ((Map<String, String>) repo.get("owner")).get("login");

                    List<Map<String, Object>> collaborators =
                            client.getCollaborators(owner, repoName);

                    for (Map<String, Object> user : collaborators) {

                        String userName = (String) user.get("login");

                        Map<String, Object> permissions =
                                (Map<String, Object>) user.get("permissions");

                        boolean isAdmin = (boolean) permissions.get("admin");
                        boolean canPush = (boolean) permissions.get("push");

                        String access = isAdmin ? "admin" : (canPush ? "write" : "read");

                        RepoAccess repoAccess = new RepoAccess(repoName, access);

                        userRepoMap
                                .computeIfAbsent(userName,
                                        k -> Collections.synchronizedList(new ArrayList<>()))
                                .add(repoAccess);
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            }, executor);

            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        executor.shutdown();

        return userRepoMap;
    }
}