package com.test.webim.repository;

import com.test.webim.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepo extends JpaRepository<Token, String> {


}
