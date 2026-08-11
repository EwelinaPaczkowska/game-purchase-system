import java.io.Serializable;

public class Singleplayer implements Serializable {
    private String profileName;

    public Singleplayer(String profileName) {
        setProfileName(profileName);
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        if (profileName == null || profileName.trim().isEmpty()) {
            throw new IllegalArgumentException("Profile name cannot be empty");
        }
        this.profileName = profileName;
    }

    @Override
    public String toString() {
        return "Singleplayer{" +
                "profileName='" + profileName + '\'' +
                '}';
    }
}