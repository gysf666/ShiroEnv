package com.example.shiro.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MainRealm extends AuthorizingRealm {
    public MainRealm() {
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        if (username.equals("admin") && password.equals("4ra1n")) {
            return new SimpleAuthenticationInfo(username, password, this.getName());
        } else {
            throw new IncorrectCredentialsException("Username or password is incorrect.");
        }
    }
}