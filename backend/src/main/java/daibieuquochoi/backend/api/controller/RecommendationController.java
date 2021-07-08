package daibieuquochoi.backend.api.controller;

import daibieuquochoi.backend.entity.AgencyEntity;
import daibieuquochoi.backend.entity.RecommendationsEntity;
import daibieuquochoi.backend.exception.BadRequestException;
import daibieuquochoi.backend.response.ResponseMessage;
import daibieuquochoi.backend.service.Impl.AgencyServiceImpl;
import daibieuquochoi.backend.service.Impl.RecommendationsServiceImpl;
import daibieuquochoi.backend.service.UploadFileService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@Validated
public class RecommendationController {
    @Value("${system.baseUrl}")
    private String BASE_URL;

    @Autowired
    public UploadFileService uploadFileService;

    @Autowired
    RecommendationsServiceImpl recommendationsService;

    @Autowired
    AgencyServiceImpl agencyService;

    @PostMapping(path = "/recommendations")
    public ResponseEntity<?> create(
            @RequestParam(value = "files")
                    MultipartFile files,
            @RequestParam(value = "object")
            @NotBlank(message = "{Object.NotBlank}")
                    String object,
            @RequestParam(value = "fullname")
            @NotBlank(message = "{Fullname.NotBlank}")
            @Pattern(regexp = "^[aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ\\s]{2,50}$", message = "Tên người dân cho phép tối đa 50 ký tự chỉ bao gồm chữ cái!")
                    String fullname,
            @RequestParam(value = "address")
            @NotBlank(message = "{Address.NotBlank}")
                    String address,
            @RequestParam(value = "phonenumber")
            @NotBlank(message = "{Phonenumber.NotBlank}")
            @Pattern(regexp = "^(05[5|8|9]|08[1|2|3|4|5|8|6|9]|03[2|3|4|5|6|7|8|9]|07[0|9|7|6|8]|09[0|2|1|4|3|6|7|8|9]|01[2|9])+([0-9]{7})\\b$", message = "Số điện thoại không hợp lệ")
                    String phonenumber,
            @RequestParam(value = "email")
//            @NotBlank(message = "{Email.NotBlank}")
//            @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", message = "Email không hợp lệ")
                    String email,
            @RequestParam(value = "title")
            @NotBlank(message = "{Title.NotBlank}")
                    String title,
            @RequestParam(value = "commenttype")
            @NotBlank(message = "{Commenttype.NotBlank}")
                    String commenttype,
            @RequestParam(value = "fields")
            @NotBlank(message = "{Fields.NotBlank}")
                    String fields,
            @RequestParam(value = "contents")
            @NotBlank(message = "{Contents.NotBlank}")
                    String contents,
            @RequestParam(value = "status")
            @NotBlank(message = "{Status.NotBlank}")
                    String status,
            @RequestParam(value = "reportingdeadline")
            @NotBlank(message = "{Reportingdeadline.NotBlank}")
                    String reportingdeadline,
            @RequestParam(value = "agency")
            @NotBlank(message = "{Agency.NotBlank}")
                    String agency
    ) {
        try {
            RecommendationsEntity recommendationsEntity = new RecommendationsEntity();
            AgencyEntity agencyEntity = new AgencyEntity();
            if (agency.isEmpty()) {
                recommendationsEntity.setAgency(null);
            } else {
                agencyEntity = agencyService.findByAgencyName(agency).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy cơ quan: " + agency));
            }

            if (files.isEmpty()) {
                recommendationsEntity.setFiles(null);
            } else {
//                setFiles
                String[] allowedMimeTypes = new String[]{
                        "application/msword",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                        "application/vnd.ms-excel",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        "application/pdf", "image/png", "image/jpeg"
                };
                if (!ArrayUtils.contains(allowedMimeTypes, files.getContentType())) {
                    throw new BadRequestException("Tệp không hợp lệ, các tệp hợp lệ bao gồm: word, excel, pdf, ảnh[.jpg, .png]");
                }
                String fileName = files.getOriginalFilename();
                ArrayList<String> listFileName = new ArrayList<>();
                uploadFileService.loadAll().forEach(file -> {
                    listFileName.add(file.getFileName().toString());
                });
                if (listFileName.contains(fileName)) {
                    throw new BadRequestException("Tên tệp đã tồn tại! Vui lòng đổi tên tệp và thử lại.");
                } else {
                    uploadFileService.save(files, fileName);
                }
                String urlFile = BASE_URL + "api/files/" + fileName;
                recommendationsEntity.setFiles(urlFile);
            }

            if (email.isEmpty()) {
                recommendationsEntity.setEmail(null);
            } else {
                java.util.regex.Pattern VALID_EMAIL_REGEX = java.util.regex.Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
                Matcher matcher = VALID_EMAIL_REGEX.matcher(email);
                if (matcher.matches()) {
                    recommendationsEntity.setEmail(email);
                } else {
                    throw new BadRequestException("Email không hợp lệ!");
                }
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = formatter.parse(reportingdeadline);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            recommendationsEntity.setObject(object);
            recommendationsEntity.setFullname(fullname);
            recommendationsEntity.setAddress(address);
            recommendationsEntity.setPhonenumber(phonenumber);
            recommendationsEntity.setTitle(title);
            recommendationsEntity.setContents(contents);
            recommendationsEntity.setReportingdeadline(date);
            recommendationsEntity.setCommenttype(commenttype);
            recommendationsEntity.setAgency(agencyEntity);
            recommendationsEntity.setStatus(status);
            recommendationsEntity.setFields(fields);

            recommendationsService.create(recommendationsEntity);

            return ResponseEntity.ok(new ResponseMessage(new Date(), HttpStatus.OK.value(), "Thêm thành công!"));
        } catch (ConstraintViolationException ex) {
            return ResponseEntity.badRequest().body(new ResponseMessage(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
        }
    }

    @GetMapping(path = "/recommendations")
    public ResponseEntity<?> getRecommendationsAll() {
        List<RecommendationsEntity> list = recommendationsService.getRecommendationsAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(path = "/recommendations/{id}")
    public ResponseEntity<?> getRecommendationsByID(@PathVariable("id") long id) {
        RecommendationsEntity recommendationsEntity = recommendationsService.findByID(id).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy id = " + id));
        return new ResponseEntity<>(recommendationsEntity, HttpStatus.OK);
    }

    @GetMapping(path = "/recommendations/search")
    public ResponseEntity<?> search(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
        List<RecommendationsEntity> list = recommendationsService.search(keyword);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
