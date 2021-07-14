package daibieuquochoi.backend.api.controller;

import daibieuquochoi.backend.dto.AgencyDTO;
import daibieuquochoi.backend.entity.AgencyEntity;
import daibieuquochoi.backend.response.ResponseMessage;
import daibieuquochoi.backend.service.Impl.AgencyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AgencyController {
    @Autowired
    AgencyServiceImpl agencyService;

    @PostMapping(path = "/agency")
    public ResponseEntity<?> create(@RequestBody @Valid AgencyDTO agencyDTO) {
        try {
            if (agencyService.isExistsByAgencyName(agencyDTO.getAgencyName())) {
                return ResponseEntity.badRequest().body(new ResponseMessage(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "Tên cơ quan đã được sử dụng!"));
            }
            AgencyEntity agencyEntity = new AgencyEntity();
            agencyEntity.setAgencyName(agencyDTO.getAgencyName());
            agencyEntity.setStatus(agencyDTO.getStatus());
            agencyEntity.setNote(agencyDTO.getNote());

            agencyService.create(agencyEntity);

            return ResponseEntity.ok(new ResponseMessage(new Date(), HttpStatus.OK.value(), "Thêm mới thành công!"));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new ResponseMessage(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), exception.getMessage()));
        }
    }

    @GetMapping(path = "/agency")
    public ResponseEntity<?> getAgencyAll() {
        List<AgencyEntity> listAgency = agencyService.getAll();
        return new ResponseEntity<>(listAgency, HttpStatus.OK);
    }

    @GetMapping(path = "/agency/{id}")
    public ResponseEntity<?> getAgencyByID(@PathVariable("id") long id) {
        if (!(agencyService.isExistsById(id))) {
            ResponseMessage message = new ResponseMessage(new Date(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), "Không tìm thấy tài khoản có id = " + id);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            AgencyEntity agency = agencyService.getById(id);
            return new ResponseEntity<>(agency, HttpStatus.OK);
        }
    }

    @PutMapping(path = "/agency/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody @Valid AgencyDTO agencyDTO) {
        try {
            if (!(agencyService.isExistsById(id))) {
                ResponseMessage message = new ResponseMessage(new Date(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), "Không tìm thấy tài khoản có id = " + id);
                return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            } else {
                AgencyEntity oldagencyEntity = agencyService.getById(id);

                if (agencyService.isExistsByAgencyName(agencyDTO.getAgencyName()) && !(agencyDTO.getAgencyName().equals(oldagencyEntity.getAgencyName()))) {
                    return ResponseEntity.badRequest().body(new ResponseMessage(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "Tài khoản này đã được sử dụng!"));
                }

                oldagencyEntity.setAgencyName(agencyDTO.getAgencyName());
                oldagencyEntity.setStatus(agencyDTO.getStatus());
                oldagencyEntity.setNote(agencyDTO.getNote());

                agencyService.updateById(oldagencyEntity);

                return ResponseEntity.ok(new ResponseMessage(new Date(), HttpStatus.OK.value(), "Cập nhật thành công!"));
            }
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new ResponseMessage(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), exception.getMessage()));
        }
    }

    @DeleteMapping(path = "/agency/{id}")
    public ResponseEntity<?> deleteByID(@PathVariable("id") long id) {
        try {
            if (!(agencyService.isExistsById(id))) {
                ResponseMessage message = new ResponseMessage(new Date(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), "Không tìm thấy id = " + id);
                return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            } else {
                AgencyEntity oldAgencyEntity = agencyService.getById(id);
                oldAgencyEntity.setStatus("Không hoạt động");
                agencyService.updateById(oldAgencyEntity);
                return ResponseEntity.ok(new ResponseMessage(new Date(), HttpStatus.OK.value(), "Xóa thành công!"));
            }
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new ResponseMessage(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "Vẫn tồn tại dữ liệu thuộc cơ quan này!"));
        }
    }
}
