package com.sqli.ccplatform.domain.entity;

import com.sqli.ccplatform.converter.MapToJsonConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "customers")
@DiscriminatorValue("CUSTOMER")
@EqualsAndHashCode(callSuper = true)
public class Customer extends User {

    private String address;

    @Convert(converter = MapToJsonConverter.class)
    @Column(columnDefinition = "TEXT")
    private Map<String, Object> preferences;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomOrder> orders = new ArrayList<>();
}