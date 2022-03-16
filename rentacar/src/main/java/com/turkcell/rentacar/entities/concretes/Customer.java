package com.turkcell.rentacar.entities.concretes;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.turkcell.rentacar.entities.abstracts.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "customers")
public class Customer extends User {

}
