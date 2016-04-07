package org.springframework.boot.bunny.multitenant.example.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bunny.multitenant.example.entity.DataSourceTable;
import org.springframework.boot.bunny.multitenant.example.entity.User;
import org.springframework.boot.bunny.multitenant.example.repository.DataSourceTableRepository;
import org.springframework.boot.bunny.multitenant.example.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class Users implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private DataSourceTableRepository repository;

    @Override
    public UserDetailsModify loadUserByUsername(String email) throws UsernameNotFoundException {

        // sua entidade da tabela de usuário normal
        User user = repo.findByEmail(email);

        if (user == null) {
            return null;
        }

        // mock
        List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

        DataSourceTable dataSourceTable = repository.findOne(user.getId());

        return new UserDetailsModify(user.getEmail(), user.getSenha(), auth, dataSourceTable);
    }

}
