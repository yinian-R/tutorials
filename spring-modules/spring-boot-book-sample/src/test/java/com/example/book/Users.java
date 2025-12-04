package com.example.book;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String account;
    private String pwd;
    
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "detailId", referencedColumnName = "id")
    private UsersDetail userDetail;
    
    @Override
    public String toString() {
        return String.format("Book [id=%s, name=%s, user detail=%s]", id, userDetail.getId());
    }
}