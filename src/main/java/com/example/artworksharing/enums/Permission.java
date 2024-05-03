package com.example.artworksharing.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    CREATOR_READ("creator:read"),
    CREATOR_UPDATE("creator:update"),
    CREATOR_CREATE("creator:create"),
    CREATOR_DELETE("creator:delete"),

    AUDIENCE_READ("audience:read"),
    AUDIENCE_UPDATE("audience:update"),
    AUDIENCE_CREATE("audience:create"),
    AUDIENCE_DELETE("audience:delete"),

    AUDIENCE_BUY_ARTWORK("audience:buy"),

   ADMIN_READ("admin:read"),
   ADMIN_UPDATE("admin:update"),
   ADMIN_CREATE("admin:create"),
   ADMIN_DELETE("admin:delete"),

    SUPER_ADMIN_READ("super_admin:read"),
    SUPER_ADMIN_UPDATE("super_admin:update"),
    SUPER_ADMIN_CREATE("super_admin:create"),
    SUPER_ADMIN_DELETE("super_admin:delete")
    ;
    @Getter
    private  final String permission;

}
