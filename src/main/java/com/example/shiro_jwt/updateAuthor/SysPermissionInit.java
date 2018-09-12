package com.example.shiro_jwt.updateAuthor;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysPermissionInit {
    private String permissionInit;  //代表权限和角色
    private String url;             //代表拦截的链接
}
