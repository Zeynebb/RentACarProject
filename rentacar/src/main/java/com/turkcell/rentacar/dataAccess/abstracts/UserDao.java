package com.turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentacar.entities.abstracts.User;

public interface UserDao extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);

}
