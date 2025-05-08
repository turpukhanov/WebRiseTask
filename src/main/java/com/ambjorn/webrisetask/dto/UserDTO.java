package com.ambjorn.webrisetask.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class UserDTO {

    private int id;

    private String name;

    private List<SubscriptionDTO> subscriptions = new ArrayList<>();
}
