package com.sqli.ccplatform.domain.entity;

import com.sqli.ccplatform.converter.StringListToJsonConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "operators")
@DiscriminatorValue("OPERATOR")
@EqualsAndHashCode(callSuper = true)
public class Operator extends User {

    @ElementCollection
    @Convert(converter = StringListToJsonConverter.class)
//    @CollectionTable(name = "operator_skills", joinColumns = @JoinColumn(name = "operator_id"))
    @Column(name = "skill")
    private List<String> skills = new ArrayList<>();

    @Column(name = "availability")
    private boolean availability = true;

    @OneToMany(mappedBy = "operator")
    private List<PreparationTask> assignedTasks = new ArrayList<>();

}