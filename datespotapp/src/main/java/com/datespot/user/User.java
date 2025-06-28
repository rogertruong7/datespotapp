package com.datespot.user;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank(message = "First name is required")
    private String firstname;

    @NotBlank(message = "Username is required")
    @JsonProperty("username")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Last name is required")
    private String lastname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isPublic = true;


    private String profilePicture;
    private String biography;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "user_post_ids", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "post_id")
    private List<Integer> postIds = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "user_followers", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "follower_id")
    private List<Integer> followers = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "user_following", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "following_id")
    private List<Integer> following = new ArrayList<>();

    public void addPost(Integer postId) {
        postIds.add(postId);
    }

    public void addFollower(Integer followerId) {
        if (!followers.contains(followerId)) {
            followers.add(followerId);
        }
    }

    public void addFollowing(Integer followingId) {
        if (!following.contains(followingId)) {
            following.add(followingId);
        }
    }

    public void removeFollower(Integer followerId) {
        followers.remove(followerId);
    }

    public void removeFollowing(Integer followingId) {
        following.remove(followingId);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
