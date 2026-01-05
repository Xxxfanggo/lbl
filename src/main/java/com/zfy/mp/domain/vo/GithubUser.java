package com.zfy.mp.domain.vo;

import lombok.Data;

@Data
public class GithubUser {
    private Long id;
    private String login;
    private String name;
    private String email;
    private String bio;
    private String avatarUrl;
    private String htmlUrl;
    private String location;
    private String company;
    private String blog;
    private String createdAt;
    private String jwtToken;
}
