package com.test.Chain;

/**
 * @Author: 吴成伟
 * @date: 2022/8/14 14:53
 * @Description: TODO
 */
import java.io.Serializable;

public class User implements Serializable {

    private Integer id;
    private String userName;
    private String password;
    private String nikeName;
    private String email;
    private String phoneNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private Integer id;
        private String userName;
        private String password;
        private String nikeName;
        private String email;
        private String phoneNum;

        public Builder id(int id) {
            this.id = id;
            return this;
        }
        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }
        public Builder password(String password) {
            this.password = password;
            return this;
        }
        public Builder nikeName(String nikeName) {
            this.nikeName = nikeName;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder phoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(id);
            user.setUserName(userName);
            user.setPassword(password);
            user.setNikeName(nikeName);
            user.setEmail(email);
            user.setEmail(email);
            user.setPhoneNum(phoneNum);
            return user;
        }
    }

}

