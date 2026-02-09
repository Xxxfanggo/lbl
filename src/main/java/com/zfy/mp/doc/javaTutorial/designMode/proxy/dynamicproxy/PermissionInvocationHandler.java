package com.zfy.mp.doc.javaTutorial.designMode.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 权限控制调用处理器
 */
public class PermissionInvocationHandler implements InvocationHandler {

    private Object target;
    private String currentUser;

    public PermissionInvocationHandler(Object target, String currentUser) {
        this.target = target;
        this.currentUser = currentUser;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 检查权限
        String methodName = method.getName();
        if (methodName.equals("deleteUser") && !currentUser.equals("admin")) {
            throw new SecurityException("权限不足: 只有管理员可以删除用户");
        }

        if (methodName.equals("addUser") && !currentUser.equals("admin") &&
            !currentUser.equals("editor")) {
            throw new SecurityException("权限不足: 只有管理员和编辑可以添加用户");
        }

        // 权限通过，执行方法
        System.out.println("✅ 权限验证通过: " + currentUser);
        return method.invoke(target, args);
    }
}
