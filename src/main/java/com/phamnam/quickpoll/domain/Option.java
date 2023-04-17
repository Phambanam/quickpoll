package com.phamnam.quickpoll.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OPTION_ID")
    private Long id;
    @NotNull
    @Column(name = "OPTION_VALUE")
    private String value;

    public Option() {
    }

    public Option(String value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
