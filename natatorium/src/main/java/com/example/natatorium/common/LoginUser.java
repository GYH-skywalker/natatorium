package com.example.natatorium.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.natatorium.entity.Consumer;
import com.example.natatorium.entity.DTO.userDTO;
import com.example.natatorium.entity.menus;
import com.example.natatorium.entity.users;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class LoginUser implements UserDetails {

    private users user;

    private Consumer consumer;

////    @JsonIgnore
//    @JSONField(serialize = false)
    private List<String> permissions;

    public LoginUser(users user,List<String> permissions){
        this.user = user;
        this.permissions = permissions;
    }


    //序列化会报错,所以这里忽略防止redis在序列化出问题
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;


    public LoginUser(Consumer consumer, List<String> perms) {
        this.consumer = consumer;
        this.permissions = perms;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //authorities中有权限信息时不需要再添加
        if(authorities != null){
            return authorities;
        }
        //填装权限信息
        authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        if(!Objects.isNull(user)){
            return user.getPassword();
        }else {
            return consumer.getPassword();
        }

    }

    @Override
    public String getUsername() {
        if(!Objects.isNull(user)){
            return user.getUserName();
        }else{
            return consumer.getPhone();
        }

    }

    public List<String> getPermissions(){
        return this.permissions;
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
