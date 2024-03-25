package com.seyed.ali.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.endpoint.base-url}/authorize")
public class DemoLoginController {

    @GetMapping("/user-admin")
    @PreAuthorize("hasAnyRole('ROLE_user', 'ROLE_admin')")
    public String userAndAdminRoleAuthorized() {
        return "This endpoint is authorized and can be accessed by ROLE_user & ROLE_admin";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String adminRoleAuthorized() {
        return "This endpoint is authorized and can be accessed by ROLE_admin";
    }

}
