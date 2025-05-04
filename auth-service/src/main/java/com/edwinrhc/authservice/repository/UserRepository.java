package com.edwinrhc.authservice.repository;

import com.edwinrhc.authservice.dto.user.UserListDTO;
import com.edwinrhc.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT NEW com.edwinrhc.authservice.dto.user.UserListDTO(u.id,u.name,u.email,u.contactNumber,u.role,u.status) FROM User u")
    List<UserListDTO> listUsers();


    User findByEmail(String email);

}
