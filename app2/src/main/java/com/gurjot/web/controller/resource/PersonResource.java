package com.gurjot.web.controller.resource;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class PersonResource {
    private String firstName;
    private String lastName;

}
