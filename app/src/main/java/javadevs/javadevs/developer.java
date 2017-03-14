package javadevs.javadevs;

/**
 * Created by CHUKWU JOHNPAUL on 12/03/17.
 */

public class developer {
    private String Username;
    private String ImageUrl;
    private String ProfileUrl;

    public developer(String username, String imageUrl, String profileUrl) {
        Username = username;
        ImageUrl = imageUrl;
        ProfileUrl = profileUrl;
    }

    public String getUsername() {
        return Username;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getProfileUrl() {
        return ProfileUrl;
    }
}
