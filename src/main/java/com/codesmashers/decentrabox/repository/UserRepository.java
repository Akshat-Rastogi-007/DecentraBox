package com.codesmashers.decentrabox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.codesmashers.decentrabox.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    public Optional<User> findByEmail(String email);

}
