package com.thoughtworks.rslist.domain;

import javax.validation.constraints.*;

/**
 * @author Boyu Yuan
 * @date 2020/9/16 11:26
 */
public class User {
    @NotNull
    @Size(max = 8)
    private String name;
    @NotNull
    private String gender;
    @NotNull
    @Max(150)
    @Min(18)
    private int age;
    @Email
    private String email;
    @Pattern(regexp = "1\\d{10}")
    private String phone;
    private int voteNum = 10;

    public User(String name, String genger, int age, String email, String phone) {
        this.name = name;
        this.gender = genger;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String genger) {
        this.gender = genger;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(int voteNum) {
        this.voteNum = voteNum;
    }
}
