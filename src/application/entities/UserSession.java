package application.entities;

public class UserSession {
    private static UserSession instance;

    private String username;
    private int id;

    private UserSession(String username, int id) {
        this.username = username;
        this.id = id;
    }

    //phiên bản 2 đối số
    public static UserSession getInstace(String username, int id) {
        if (instance == null) {
            instance = new UserSession(username, id);
        }
        return instance;
    }
    
    //phiên bản không đối số
    public static UserSession getInstace() {
        if (instance == null) {
            throw new IllegalStateException("Session has not been initialized. Call getInstace(String, int) first.");
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return id;
    }
    public void endSession() {
    	instance = null;
    }
  

    
}
