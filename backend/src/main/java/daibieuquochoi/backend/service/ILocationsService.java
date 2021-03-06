package daibieuquochoi.backend.service;

import daibieuquochoi.backend.entity.LocationsEntity;

import java.util.List;
import java.util.Optional;

public interface ILocationsService {
    //Read
    Optional<LocationsEntity> findByName(String name);

    List<LocationsEntity> getProvinceAll();

    List<LocationsEntity> getDistrictByParentid(long parentid);

    List<LocationsEntity> getWardByParentid(long parentid);

    //Check
    boolean isLocationsExitByParentid(long id);
}
