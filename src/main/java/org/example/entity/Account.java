package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.example.entity.enums.Role;
import org.example.entity.enums.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"bills", "hibernateLazyInitializer", "handler"})
@Entity(name = "Account")
@Table(name = "account", schema = "shopaonam")
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID", nullable = false)
    private Integer accountID;

    @Column(name = "Username", length = 50, nullable = false)
    private String username;

    @Column(name = "Password", nullable = false)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="Role", nullable = false)
    protected Role role;

    @Column(name = "Phonenumber", length = 10, nullable = false)
    private String phoneNumber;

    @Column(name = "Address", length = 500, nullable = false)
    private String address;

    @Column(name = "Name", length = 100, nullable = false)
    private String name;

    @Column(name = "Email", length = 100, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;

    @Column(name = "Otp", length = 6)
    private String otp;

    @Column(name = "OtpExpiry")
    private LocalDateTime otpExpiry;

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.status == Status.Enable;
    }

    @OneToMany(mappedBy = "accountID")
    @ToString.Exclude
    private Set<Bill> bills = new LinkedHashSet<>();

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
