package ru.ylab.security;

import ru.ylab.model.Role;
import ru.ylab.model.User;


public class SecurityManager {
    public static boolean hasAccessToCarAndOrderMenu(User user) {
        if (user.getRole() == Role.MANAGER){
            return true;
        }
        return false;
    }

    public static boolean hasAccessToUserMenu(User user) {
        if (user.getRole() == Role.ADMIN){
            return true;
        }
        return false;
    }

    public static boolean hasAccessToClientMenu(User user) {
        if (user.getRole() == Role.CLIENT){
            return true;
        }
        return false;
    }

}
