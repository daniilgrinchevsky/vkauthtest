package com.test.webim.repository;

import com.test.webim.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends JpaRepository<User, Integer> {

     User getByVkId(String id);

     User getByUsername(String userName);

     User getById(int id);
}
