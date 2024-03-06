package com.example.Dinter.models;

import java.util.Date;
import java.util.List;

public class User {
    private String id;
    private String username;
    private String email;
    private String password;
    private String gender;
    private Date dateOfBirth;
    private String avatar;
    private String bio;
    private String address;
    private List<User> friends;
    private Boolean isAdmin;
//    private List<Hobby> hobbies;
    private Boolean isSetUpProfile;

    public User(String id, String username, String email, String password, String gender, Date dateOfBirth, String avatar, String bio, String address, List<User> friends, Boolean isAdmin, Boolean isSetUpProfile) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.avatar = avatar;
        this.bio = bio;
        this.address = address;
        this.friends = friends;
        this.isAdmin = isAdmin;
        this.isSetUpProfile = isSetUpProfile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getSetUpProfile() {
        return isSetUpProfile;
    }

    public void setSetUpProfile(Boolean setUpProfile) {
        isSetUpProfile = setUpProfile;
    }

    public static class Login {
        String status;
        String message;
        String accessToken;
        String refreshToken;
        User data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public User getData() {
            return data;
        }

        public void setData(User data) {
            this.data = data;
        }
    }
}
