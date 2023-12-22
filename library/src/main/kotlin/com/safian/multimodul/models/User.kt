package com.safian.multimodul.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String = "",
    val email: String = "",
    @JsonIgnore
    @Column(name = "password")
    val pwd: String = "",

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    val roles: List<Role> = emptyList(),
) : UserDetails {

    @JsonIgnore
    override fun getAuthorities() = this.roles

    @JsonIgnore
    override fun getPassword() = this.pwd

    @JsonIgnore
    override fun getUsername() = this.email

    @JsonIgnore
    override fun isAccountNonExpired() = true

    @JsonIgnore
    override fun isAccountNonLocked() = true

    @JsonIgnore
    override fun isCredentialsNonExpired() = true

    @JsonIgnore
    override fun isEnabled() = true
}
