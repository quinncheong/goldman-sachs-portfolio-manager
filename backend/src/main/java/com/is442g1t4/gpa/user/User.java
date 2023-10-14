package com.is442g1t4.gpa.user;

import java.util.Collection;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;

@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails{
    @Id
    private ObjectId id;
    private String name;
    private String username;
    private String password;
    private String email;
    private List<ObjectId> portfolioIds;
    // private ArrayList<String> role;

    // public User(String username, String password, ArrayList<String> role){
    //     this.username= username;
    //     this.password = username;
    //     this.role = role;
    // }
    // @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
        // return Collections.emptyList();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    } 
}
