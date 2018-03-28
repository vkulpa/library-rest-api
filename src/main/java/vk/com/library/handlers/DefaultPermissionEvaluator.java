package vk.com.library.handlers;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import vk.com.library.models.services.LibraryUser;

import java.io.Serializable;

// In our current implementation we don't use permission yet
// only targetId and auth/current user/role are taken into consideration
public class DefaultPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication auth, Object targetId, Object permission) {
        return hasPermission(auth, targetId);
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        return hasPermission(auth, targetId);
    }

    private boolean hasPermission(Authentication auth, Object targetId) {
        if (auth != null && targetId != null) {
            LibraryUser principal = (LibraryUser)auth.getPrincipal();
            if ( auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Admin"))
                    || targetId.equals(principal.getUserId()) ) {
                return true;
            }
        }
        return false;
    }
}
