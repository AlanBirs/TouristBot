package org.example.repository;

import org.example.model.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

    CityEntity findFirstByCityName(String cityName);

    CityEntity findFirstById(long id);

    CityEntity findFirstByOrderByIdDesc();

}
