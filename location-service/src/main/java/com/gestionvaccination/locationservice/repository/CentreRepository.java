package com.gestionvaccination.locationservice.repository;

import com.gestionvaccination.locationservice.entity.Centre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CentreRepository extends JpaRepository<Centre, Long> {

    List<Centre>  findByParentId(Long parentId);
}
