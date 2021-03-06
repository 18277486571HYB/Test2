package com.hyb.test2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.concurrent.TimeUnit;

//@Configuration
//@EnableAuthorizationServer
//public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    SheepUserDetailsService sheepUserDetailsService;
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//
//        // 定义了两个客户端应用的通行证
//        clients.inMemory()
//                .withClient("admin")
//                .secret(new BCryptPasswordEncoder().encode("123456"))
//                .authorizedGrantTypes("authorization_code", "refresh_token","password")
//                .scopes("all")
//                .autoApprove(true)
//                .redirectUris("http://192.168.216.1:8001/login","http://192.168.216.1:8004/login");
//
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//
//        endpoints.tokenStore(jwtTokenStore()).accessTokenConverter(jwtAccessTokenConverter());
//        DefaultTokenServices tokenServices = (DefaultTokenServices) endpoints.getDefaultAuthorizationServerTokenServices();
//        tokenServices.setTokenStore(endpoints.getTokenStore());
//        tokenServices.setSupportRefreshToken(true);
//        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
//        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
//        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1)); // 一天有效期
//        endpoints.tokenServices(tokenServices);
//
//        //密码模式配置
//        endpoints.authenticationManager(authenticationManager).userDetailsService(sheepUserDetailsService);
//    }
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security
//                .tokenKeyAccess("isAuthenticated()")
//                .checkTokenAccess("permitAll()")
//                .allowFormAuthenticationForClients();
//    }
//
//    @Bean
//    public TokenStore jwtTokenStore() {
//        return new JwtTokenStore(jwtAccessTokenConverter());
//    }
//
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter(){
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey("testKey");
//        //不设置这个会出现 Cannot convert access token to JSON
////        converter.setVerifierKey("testKey");
////        converter.setVerifier(new RsaVerifier("testKey"));
//        return converter;
//    }
//
//}
