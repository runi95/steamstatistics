package com.steamstatistics.userauth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, String> {

    public Privilege findByName(String name);

}
