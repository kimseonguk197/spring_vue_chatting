package com.example.chatserver.member.dto;

import com.example.chatserver.member.domain.Member;
import com.example.chatserver.member.domain.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MemberSaveReqDto {
    private String name;
    @NotEmpty(message = "email is essential")
    private String email;
    @NotEmpty(message = "password is essential")
    @Size(min = 8, message = "password minimum length is 8")
    private String password;
    private Role role = Role.USER;

    public Member toEntity(String password){
        Member member = Member.builder()
                .name(this.name)
                .email(this.email)
                .password(password)
                .role(this.role)
                .build();
        return member;
    }
}
