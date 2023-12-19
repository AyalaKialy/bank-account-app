package com.example.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}