package com.paymybuddy.sa_eg_p6_paymybuddy.dal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="email")
    private String email;

    @Column(name="mdp")
    private String mdp;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

}
