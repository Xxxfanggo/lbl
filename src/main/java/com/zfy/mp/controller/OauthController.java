package com.zfy.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zfy.mp.domain.vo.GithubUser;
import com.zfy.mp.domain.vo.TwdUserVO;
import com.zfy.mp.enums.RegisterOrLoginTypeEnum;
import com.zfy.mp.service.login.OauthLoginService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import me.zhyd.oauth.model.AuthCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Tag(name = "Oauth2")
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
    @Value("${spring.security.oauth2.client.provider.twd.token-uri}")
    public  String TWD_TOKEN_URL;
    @Value("${spring.security.oauth2.client.provider.twd.user-info-uri}")
    public  String TWD_USER_INFO_URL;
    @Value("${spring.security.oauth2.client.registration.twd.client-id}")
    public  String TWD_CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.twd.client-secret}")
    public  String TWD_CLIENT_SECRET;
    @Value("${spring.security.oauth2.client.registration.twd.authorization-grant-type}")
    public  String AUTHORIZATION_GRANT_TYPE;
    @Value("${spring.security.oauth2.client.registration.twd.redirect-uri}")
    public  String REDIRECT_URI;

    public static  String REGISTRATION_ID;



    @Resource
    private OauthLoginService oauthLoginService;

    @GetMapping("/github/callback")
    public void githubCallback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state, HttpServletResponse response) throws IOException {
        oauthLoginService.handleLogin(code, state, RegisterOrLoginTypeEnum.GITHUB.getRegisterType());
        response.sendRedirect("");
    }


//    @GetMapping("/{registrationId}/callback")
//    public ResponseEntity<?> githubCallback(@RequestParam(name = "code") String code, @PathVariable String registrationId) {
//        // todo get state from session
//        System.out.println(registrationId);
//        REGISTRATION_ID = registrationId;
//        // todo validate state
//
//        try {
//            String accessToken = "";
//            if (StrUtil.isBlank(REGISTRATION_ID))  {
//                return ResponseEntity.badRequest().body("registrationId is empty");
//            }
////            if (StrUtil.equals(registrationId, "twd")) {
////                accessToken = getTwdAccessToken(code);
////            } else
//            if (StrUtil.equals(registrationId, "github")) {
//                accessToken = getAccessToken(code);
//            }
//            // 根据 registrationId 选择对应的用户类
//            Class<?> userClass = getUserClassByRegistrationId(registrationId);
//            Object userInfo = getUserInfo(accessToken, userClass);
//            Map<String, Object> objectMap = BeanUtil.beanToMap(userInfo);
////            String jwt_token = JWTUtil.createToken(JSONUtil.toJsonStr(userInfo), objectMap);
//            String  jwt_token = "";
//            // todo 查询数据库用户是否存在
//
//            //  如果不存在，则创建新用户
//
//            if (userInfo instanceof GithubUser) {
//                ((GithubUser) userInfo).setJwtToken(jwt_token);
//            } else if (userInfo instanceof TwdUserVO) {
//                ((TwdUserVO) userInfo).setJwtToken(jwt_token);
//            }
//
//
//            return ResponseEntity.ok(userInfo);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body("Error during "+ registrationId +" OAuth callback");
//        }
//
//    }

    private Class<?> getUserClassByRegistrationId(String registrationId) {
        switch (registrationId) {
            case "github":
                return GithubUser.class;
            case "twd":
                return TwdUserVO.class;
            default:
                return null;
        }
    }

    private String getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        if (StrUtil.isBlank(REGISTRATION_ID)){
            return "";
        }

        HttpHeaders headers = new HttpHeaders();

        String tokenUrl = "";
        String clientId = "";
        String clientSecret = "";
        if (StrUtil.equals(REGISTRATION_ID, "github")) {

            tokenUrl = GITHUB_TOKEN_URL;
            clientId = GITHUB_CLIENT_ID;
            clientSecret = GITHUB_CLIENT_SECRET;
        } else if (StrUtil.equals(REGISTRATION_ID, "twd")) {
            tokenUrl = TWD_TOKEN_URL;
            clientId = TWD_CLIENT_ID;
            clientSecret = TWD_CLIENT_SECRET;
        }
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("code", code);

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);
        JSONObject jsonObject = JSONUtil.parseObj(response.getBody());
        return jsonObject.getStr("access_token");
    }


    private String getTwdAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("client_id", TWD_CLIENT_ID);
        params.add("client_secret", TWD_CLIENT_SECRET);
        params.add("grant_type", AUTHORIZATION_GRANT_TYPE);
        params.add("redirect_uri", "http://localhost:5173/login");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(TWD_TOKEN_URL, request, String.class);
        JSONObject jsonObject = JSONUtil.parseObj(response.getBody());
        Object dataObj = jsonObject.get("data");
        JSONObject data = JSONUtil.parseObj(dataObj);
        return data.getStr("access_token");
    }

    private <T>T getUserInfo(String accessToken, Class<T> userClass) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<?> request = new HttpEntity<>(headers);
        String userInfoUrl = "";
        if (StrUtil.equals(REGISTRATION_ID, "twd")) {
            userInfoUrl = TWD_USER_INFO_URL;
        } else if (StrUtil.equals(REGISTRATION_ID, "github")) {
            userInfoUrl = GITHUB_USER_INFO_URL;
        }
        Object body = restTemplate.exchange(userInfoUrl, HttpMethod.GET, request, Object.class).getBody();
        if (StrUtil.equals(REGISTRATION_ID, "twd")) {
            Object dataObj = JSONUtil.parseObj(body).get("data");
            return BeanUtil.copyProperties(dataObj, userClass);
        }
        return BeanUtil.copyProperties(body, userClass);
    }


}
