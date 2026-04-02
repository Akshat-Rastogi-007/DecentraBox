package com.codesmashers.decentrabox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codesmashers.decentrabox.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
