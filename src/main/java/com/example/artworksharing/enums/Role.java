package com.example.artworksharing.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.artworksharing.enums.Permission.*;

@RequiredArgsConstructor
public enum Role {
    GUEST(Collections.emptySet()),
    CREATOR(
            Set.of(
                    CREATOR_DELETE,
                    CREATOR_CREATE,
                    CREATOR_READ,
                    CREATOR_UPDATE,

                    AUDIENCE_CREATE,
                    AUDIENCE_DELETE,
                    AUDIENCE_UPDATE,
                    AUDIENCE_READ,
                    AUDIENCE_BUY_ARTWORK
            )),
    AUDIENCE(
            Set.of(
                    AUDIENCE_CREATE,
                    AUDIENCE_DELETE,
                    AUDIENCE_UPDATE,
                    AUDIENCE_READ,
                    AUDIENCE_BUY_ARTWORK
            )),
    ADMIN(
            Set.of(
                    ADMIN_CREATE,
                    ADMIN_DELETE,
                    ADMIN_UPDATE,
                    ADMIN_READ
            )),
    SUPER_ADMIN(
            Set.of(
                    SUPER_ADMIN_CREATE,
                    SUPER_ADMIN_DELETE,
                    SUPER_ADMIN_UPDATE,
                    SUPER_ADMIN_READ,

                    ADMIN_CREATE,
                    ADMIN_DELETE,
                    ADMIN_UPDATE,
                    ADMIN_READ

            ))

    ;

    @Getter
    private final Set<Permission> permissions;
    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities =
               getPermissions().stream()
                        .map(permission1 -> new SimpleGrantedAuthority(permission1.getPermission()))
                        .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
//    public List<SimpleGrantedAuthority> getAuthorities(){
//        var author = getPermissions()
//                .stream()
//                .map(permission -> new SimpleGrantedAuthority(permission.name()))
//                .toList();
//        System.out.println(author);
//        author.add(new SimpleGrantedAuthority("ROLE_"+ this.name()) );
//        return author;
//    }

}
