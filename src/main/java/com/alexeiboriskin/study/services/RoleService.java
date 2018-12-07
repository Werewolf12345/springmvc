package com.alexeiboriskin.study.services;

import com.alexeiboriskin.study.models.Role;
import com.alexeiboriskin.study.repositories.RoleRepository;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import static org.springframework.data.domain.ExampleMatcher.matching;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final Logger logger = Logger.getLogger(this.getClass());

    public RoleService(RoleRepository userRepository
                       ) {
        this.roleRepository = userRepository;
    }

    public Role saveRole(Role role) {
        Role exampleRole = new Role();
        exampleRole.setRole(role.getRole());
        ExampleMatcher matcher = matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id")
                .withMatcher("role",
                        GenericPropertyMatcher::exact);
        Role roleInDb = roleRepository.findOne(Example.of(exampleRole, matcher)).orElse(null);

        if (roleInDb != null && roleInDb.getId() != role.getId()) {
            logger.info("Role already exists!");
            return roleInDb;
        }
        return roleRepository.save(role);

    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public List<Role> listAllRoles() {
        return roleRepository.findAll();
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public Role findByRoleName(String role) {
        Role exampleRole = new Role();
        exampleRole.setRole(role);
        ExampleMatcher matcher = matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id")
                .withMatcher("role",
                        GenericPropertyMatcher::exact);

        return roleRepository.findOne(Example.of(exampleRole, matcher)).orElse(null);
    }
}
