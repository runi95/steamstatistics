package com.steamstatistics.userauth;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege() {}

    public Privilege(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
