package com.steamstatistics.userauth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

    public Role findByName(String name);

}
