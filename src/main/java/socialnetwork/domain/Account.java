package socialnetwork.domain;

public class Account extends Entity<Long>{
    private String username;
    private String password;
    private long id_user;

    public Account(String username, String password, long id_user) {
        this.username = username;
        this.password = password;
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }
}
