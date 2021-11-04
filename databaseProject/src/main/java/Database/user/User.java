package Database.user;

import Database.UserRole;

public class User {
    private Integer userId;
    private String username;
    private String password;
    private UserRole userRole;
    private Integer readerId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userIdl) {
        this.userId = userIdl;
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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Integer getReaderId() {
        return readerId;
    }

    public void setReaderId(Integer readerId) {
        this.readerId = readerId;
    }
}
