package com.example.springbatch.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    @Id
    private Integer id;
    private String name;
    private String dept;
    private Integer salary;
    private Date time;


}
