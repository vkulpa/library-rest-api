package vk.com.library.handlers;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

//TO-DO
public class UserEditEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication auth, Object targetId, Object permission) {
        if (auth == null || targetId == null || !(permission instanceof String)) {
            return false;
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        if (auth == null || targetId == null || !(permission instanceof String)) {
            return false;
        }

        return false;
    }
}
