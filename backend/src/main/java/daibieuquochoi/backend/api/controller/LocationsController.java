package daibieuquochoi.backend.api.controller;

import daibieuquochoi.backend.entity.LocationsEntity;
import daibieuquochoi.backend.service.Impl.LocationsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/locations")
public class LocationsController {
    @Autowired
    LocationsServiceImpl locationsService;

    @GetMapping(path = "/province")
    public ResponseEntity<?> getProvinceAll() {
        List<LocationsEntity> listProvincce = locationsService.getProvinceAll();
        return new ResponseEntity<>(listProvincce, HttpStatus.OK);
    }

    @GetMapping(path = "/district")
    public ResponseEntity<?> getDistrictByProvince(@RequestParam(value = "province") String province) {
        LocationsEntity locationsEntity = locationsService.findByName(province).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tỉnh: " + province));
        List<LocationsEntity> listDistrict = locationsService.getDistrictByParentid(locationsEntity.getId());
        return new ResponseEntity<>(listDistrict, HttpStatus.OK);
    }

    @GetMapping(path = "/ward")
    public ResponseEntity<?> getWardByDistrict(@RequestParam(value = "district") String district) {
        LocationsEntity locationsEntity = locationsService.findByName(district).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tỉnh: " + district));
        List<LocationsEntity> listWard = locationsService.getWardByParentid(locationsEntity.getId());
        return new ResponseEntity<>(listWard, HttpStatus.OK);
    }
}
