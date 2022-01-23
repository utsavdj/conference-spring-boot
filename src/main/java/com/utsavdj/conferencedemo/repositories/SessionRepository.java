package com.utsavdj.conferencedemo.repositories;

import com.utsavdj.conferencedemo.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
