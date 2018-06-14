package com.boot.entity;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

    //主题
    private String subject;

    //内容
    private String message ;

    private Set<String> receivers ;
}
