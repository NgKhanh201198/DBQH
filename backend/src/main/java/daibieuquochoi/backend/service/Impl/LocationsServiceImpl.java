package daibieuquochoi.backend.service.Impl;

import daibieuquochoi.backend.entity.LocationsEntity;
import daibieuquochoi.backend.repository.LocationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daibieuquochoi.backend.service.ILocationsService;

import java.util.List;
import java.util.Optional;

@Service
public class LocationsServiceImpl implements ILocationsService {
    @Autowired
    LocationsRepository locationsRepository;

    @Override
    public Optional<LocationsEntity> findByName(String name) {
        return locationsRepository.findByName(name);
    }

    @Override
    public List<LocationsEntity> getProvinceAll() {
        return locationsRepository.findAllByParentid(0);
    }

    @Override
    public List<LocationsEntity> getDistrictByProvince(long parentid) {
        return locationsRepository.findAllByParentid(parentid);
    }

    @Override
    public List<LocationsEntity> getWardByDistrict(long parentid) {
        return locationsRepository.findAllByParentid(parentid);
    }


}
