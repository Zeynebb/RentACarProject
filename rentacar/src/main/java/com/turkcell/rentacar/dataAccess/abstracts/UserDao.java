package com.turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentacar.entities.abstracts.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);

}
