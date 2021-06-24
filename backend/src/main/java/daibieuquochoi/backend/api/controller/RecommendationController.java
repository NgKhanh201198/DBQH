package daibieuquochoi.backend.api.controller;

import daibieuquochoi.backend.entity.AccountEntity;
import daibieuquochoi.backend.entity.AgencyEntity;
import daibieuquochoi.backend.entity.RoleEntity;
import daibieuquochoi.backend.exception.BadRequestException;
import daibieuquochoi.backend.response.ResponseMessage;
import daibieuquochoi.backend.service.Impl.RecommendationsServiceImpl;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class RecommendationController {
    @Autowired
    RecommendationsServiceImpl recommendationsService;

    @PostMapping(path = "/recommendations")
    public ResponseEntity<?> create(
            @RequestParam(value = "files") MultipartFile avatar,
            @RequestParam(value = "object") String object,
            @RequestParam(value = "fullname") String fullname,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "phonenumber") String phonenumber,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "commenttype") String commenttype,
            @RequestParam(value = "fields") String fields,
            @RequestParam(value = "contents") String contents,
            @RequestParam(value = "status") String status,
            @RequestParam(value = "reportingdeadline") String reportingdeadline,
            @RequestParam(value = "agency") String agency
    ) {
        try {
//            CommonController controller = new CommonController();
//            AccountEntity accountEntity = new AccountEntity();

////             Set Avatar
//            String[] allowedMimeTypes = new String[]{"image/gif", "image/png", "image/jpeg"};
//
//            if (!ArrayUtils.contains(allowedMimeTypes, avatar.getContentType())) {
//                throw new BadRequestException("Tệp không hợp lệ, các tệp hợp lệ bao gồm: .jpg, .png, .gif");
//            }
//
//            String fileType = avatar.getOriginalFilename().substring(avatar.getOriginalFilename().length() - 4);
//            String uuidAvatar = "avatar-" + UUID.randomUUID().toString().replaceAll("-", "") + fileType.toLowerCase();
//            String urlAvatar = BASE_URL + "api/files/" + uuidAvatar;
//            uploadFileService.save(avatar, uuidAvatar);
//
//            accountEntity.setAccountName(accountName);
//            accountEntity.setPassword(passwordEncoder.encode(password));
//            accountEntity.setFullname(fullname);
//            accountEntity.setDateOfBirth(date);
//            accountEntity.setAvatar(urlAvatar);
//            accountEntity.setPosition(position);
//            accountEntity.setCandidateplace(candidateplace);
//            accountEntity.setStatus(status.toUpperCase());
//            accountEntity.setAgency(agencyEntity);
//            accountEntity.setRole(roleEntity);
//            accountService.create(accountEntity);

            return ResponseEntity.ok(new ResponseMessage(new Date(), HttpStatus.OK.value(), "Bạn đã đăng ký thành công!"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new ResponseMessage(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
        }
    }
}
