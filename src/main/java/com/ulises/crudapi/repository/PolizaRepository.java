package com.ulises.crudapi.repository;

import com.ulises.crudapi.entity.Poliza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PolizaRepository extends JpaRepository<Poliza, Long> {

}
