package org.example.config.authorities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Authorities {

    @SuppressWarnings("InstantiationOfUtilityClass")
    @Getter
    private static final Authorities instance = new Authorities();

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";
    public static final String ROLE_SUPER_ADMIN = "SUPER_ADMIN";
    public static final String ROLE_CONTRACTOR = "CONTRACTOR";
    public static final String ROLE_ZERO = "ZERO";

}
