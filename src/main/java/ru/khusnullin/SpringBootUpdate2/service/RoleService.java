package ru.khusnullin.SpringBootUpdate2.service;

import java.util.List;
import ru.khusnullin.SpringBootUpdate2.model.Role;

public interface RoleService {
    List<Role> getRoles();
    void saveRole(Role role);
    void removeRoleById(Long id);
    Role getRoleByName(String name);
}
