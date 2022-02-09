package com.zensar.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zensar.entity.ProducerJSONEntity;

public interface ProducerJSONRepo extends JpaRepository<ProducerJSONEntity, Integer>{

}
