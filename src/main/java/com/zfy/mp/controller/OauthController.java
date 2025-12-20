package com.zfy.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zfy.mp.common.utils.JWTUtil;
import com.zfy.mp.vo.user.GithubUser;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/oauth2")
public class OauthController {
    
    @Value("${spring.security.oauth2.client.provider.github.token-uri}")
    public  String GITHUB_TOKEN_URL;
    @Value("${spring.security.oauth2.client.provider.github.user-info-uri}")
    public  String GITHUB_USER_INFO_URL;
    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    public  String GITHUB_CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    public  String GITHUB_CLIENT_SECRET;

    @Resource
    private HttpSession httpSession;

    @GetMapping("/github/callback")
    public ResponseEntity<?> githubCallback(@RequestParam(name = "code") String code) {
        // todo get state from session

        // todo validate state

        try {

            String accessToken = getAccessToken(code);

            GithubUser userInfo = getUserInfo(accessToken);
            // todo 查询数据库用户是否存在

            //  如果不存在，则创建新用户

            Map<String, Object> objectMap = BeanUtil.beanToMap(userInfo);
            String jwt_token = JWTUtil.createJWT(JSONUtil.toJsonStr(userInfo), objectMap);
            userInfo.setJwtToken(jwt_token);
            return ResponseEntity.ok(userInfo);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error during GitHub OAuth callback");
        }

    }




    private String getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("client_id", GITHUB_CLIENT_ID);
        params.put("client_secret", GITHUB_CLIENT_SECRET);
        params.put("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(GITHUB_TOKEN_URL, request, String.class);
        JSONObject jsonObject = JSONUtil.parseObj(response.getBody());
        return jsonObject.getStr("access_token");
    }

    private GithubUser getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<?> request = new HttpEntity<>(headers);
        Object body = restTemplate.exchange(GITHUB_USER_INFO_URL, HttpMethod.GET, request, Object.class).getBody();
        GithubUser githubUser = BeanUtil.copyProperties(body, GithubUser.class);
        return githubUser;
    }
}
