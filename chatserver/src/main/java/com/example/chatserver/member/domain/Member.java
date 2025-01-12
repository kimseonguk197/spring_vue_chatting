package com.example.chatserver.member.domain;

import com.example.chatserver.common.domain.BaseTimeEntity;
import com.example.chatserver.member.dto.MemberResDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

    public MemberResDto fromEntity(){
        return MemberResDto.builder().id(this.id)
                .name(this.name)
                .email(this.email)
                .build();
    }

    public void updatePassword(String password){
        this.password = password;
    }
}
