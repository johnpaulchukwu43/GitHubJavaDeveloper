package javadevs.javadevs;

/**
 * Created by CHUKWU JOHNPAUL on 12/03/17.
 This Class represents an actual model of the data we are going to be getting 
 */

public class developer {
    private String Username;//Username of the developer
    private String ImageUrl;//Their Image
    private String ProfileUrl;//Profile Url

    //Constructor to initialize values of fields
    public developer(String username, String imageUrl, String profileUrl) {
        Username = username;
        ImageUrl = imageUrl;
        ProfileUrl = profileUrl;
    }
    //Get Methods to access the respective fields

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
