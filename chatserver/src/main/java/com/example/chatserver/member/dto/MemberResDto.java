package com.example.chatserver.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MemberResDto {
    private Long id;
    private String name;
    private String email;
    private int orderCount;
}
