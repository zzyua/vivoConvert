package com.boot.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlEntity {

    private String method ;

    private String url ;
}
