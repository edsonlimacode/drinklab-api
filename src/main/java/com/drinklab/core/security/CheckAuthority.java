package com.drinklab.core.security;


import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckAuthority {

    @PreAuthorize("hasAuthority('ADMIN')")
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Admin {
    }

    @PreAuthorize("hasAuthority('MASTER')")
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Master {
    }

    @PreAuthorize("hasAuthority('MASTER') or hasAuthority('ADMIN')")
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface MasterOrAdmin {
    }
}
