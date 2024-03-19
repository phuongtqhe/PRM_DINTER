package com.example.Dinter.models;

import java.util.Date;
import java.util.List;

public class UserModel {
    public static UserModel currentUser;
    private String _id;
    private String id; // Có trường hợp API trả ra id nên cho thêm 1 trường id này vào
    private String email;
    private String password;
    private Date createdAt;
    private Date updatedAt;
    private int __v;
    private String username;
    private String avatar;
    private List<String> friends;
    private String gender;
    private List<HobbyModel> hobbies;
    private String bio;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<HobbyModel> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<HobbyModel> hobbies) {
        this.hobbies = hobbies;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public UserModel() {
    }

    public UserModel(String _id, String email, String password, Date createdAt, Date updatedAt, int __v, String username, String avatar, List<String> friends, String gender, List<HobbyModel> hobbies, String bio) {
        this._id = _id;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.__v = __v;
        this.username = username;
        this.avatar = avatar;
        this.friends = friends;
        this.gender = gender;
        this.hobbies = hobbies;
        this.bio = bio;
    }

    public static class Account {
        String email;
        String password;

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

        public Account(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    public static class Login {
        String status;
        String message;
        String accessToken;
        String refreshToken;
        UserModel data;

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

        public UserModel getData() {
            return data;
        }

        public void setData(UserModel data) {
            this.data = data;
        }
    }

    public static class NewUser {
        String username;
        String email;
        String password;
        String confirmPassword;

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

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }

        public NewUser(String username, String email, String password, String confirmPassword) {
            this.username = username;
            this.email = email;
            this.password = password;
            this.confirmPassword = confirmPassword;
        }
    }
}
