package com.ambjorn.webrisetask.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "subscriptions")
@NoArgsConstructor
@EqualsAndHashCode
@NamedEntityGraph(
        name = "Subscription.withUsers",
        attributeNodes = @NamedAttributeNode("users")
)
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @ManyToMany(mappedBy = "subscriptions")
    private List<User> users = new ArrayList<>();
}
