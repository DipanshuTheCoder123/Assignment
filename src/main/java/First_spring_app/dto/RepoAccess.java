package First_spring_app.dto;

public class RepoAccess {

    private String repo;
    private String access;

    public RepoAccess(String repo, String access) {
        this.repo = repo;
        this.access = access;
    }

    public String getRepo() { return repo; }
    public String getAccess() { return access; }
}