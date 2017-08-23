package com.ysx.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ysx.entity.Letter;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Integer> {
	
}
