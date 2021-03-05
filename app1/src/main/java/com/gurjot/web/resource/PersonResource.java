package com.gurjot.web.resource;

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
