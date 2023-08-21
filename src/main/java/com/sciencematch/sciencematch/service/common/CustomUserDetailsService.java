package com.sciencematch.sciencematch.service.common;

import com.sciencematch.sciencematch.domain.Admin;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.infrastructure.AdminRepository;
import com.sciencematch.sciencematch.infrastructure.TeacherRepository;
import java.util.Collections;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final TeacherRepository teacherRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!Objects.equals(username, "hyh12100863@gmail.com")){
            return teacherRepository.findByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
        } else {
            Admin admin = adminRepository.getAdminById(1L);
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
                admin.getAuthority().toString());
            return new User(
                admin.getEmail(),
                admin.getPassword(),
                Collections.singleton(grantedAuthority)
            );
        }
    }

    // DB에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Teacher teacher) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
            teacher.getAuthority().toString());

        return new User(
            teacher.getEmail(),
            teacher.getPassword(),
            Collections.singleton(grantedAuthority)
        );
    }
}
