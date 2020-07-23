package org.example.controller;

import org.example.model.CityEntity;
import org.example.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("cities")
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @GetMapping
    public List<CityEntity> getCities(){
        return cityRepository.findAll();
    }

    @GetMapping("/{id}")
    public CityEntity getOneCityById(@PathVariable Long id) {
        return cityRepository.findFirstById(id);
    }

    @PostMapping
    public CityEntity create(@RequestBody CityEntity cityEntity){
        cityRepository.save(cityEntity);
        return cityEntity;
    }

    @PutMapping("{id}")
    public CityEntity update(@PathVariable Long id, @RequestBody CityEntity cityEntity) {
        CityEntity cityFromDB = getCityById(id);

        cityFromDB.setCityName(cityEntity.getCityName());
        cityFromDB.setCityDescription(cityEntity.getCityDescription());

        cityRepository.save(cityFromDB);

        return cityFromDB;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        CityEntity cityFromDB = getCityById(id);

        cityRepository.delete(cityFromDB);
    }

    private CityEntity getCityById(long id){
        return cityRepository.findFirstById(id);
    }
}
