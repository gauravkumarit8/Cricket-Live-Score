package com.cric.apis.CrickInfo.repository;

import com.cric.apis.CrickInfo.entity.matchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepo extends JpaRepository<matchEntity,Long> {
}
