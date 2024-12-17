package studyboot.shop.security;


import studyboot.shop.model.entity.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

public class AuthInfo implements Authentication {
    private boolean authenticated; //czy przeszedł autoryzację
    private String username; //kto
    private Set<Role> roles; //jaką mając rolę

    public AuthInfo(String username, Set<Role> roles) {
        this.username = username;
        this.roles = roles;
    }

    //zwraca kolekcją roli i uprawnień
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    //zwraca imię użytkownika
    @Override
    public Object getPrincipal() {
        return username;
    }

    //zwraca stan autoryzacji
    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    // zmienia stan autoryzacji na dokonaną
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    //zwraca "realne" imię usera, nie nick
    @Override
    public String getName() {
        return username;
    }

    //zwraca dane o koncie uzytkownika
    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }
}

