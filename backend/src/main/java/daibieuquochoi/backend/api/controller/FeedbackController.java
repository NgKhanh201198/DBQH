package daibieuquochoi.backend.api.controller;

import daibieuquochoi.backend.entity.AccountEntity;
import daibieuquochoi.backend.entity.FeedbackEntity;
import daibieuquochoi.backend.entity.RecommendationsEntity;
import daibieuquochoi.backend.exception.BadRequestException;
import daibieuquochoi.backend.response.ResponseMessage;
import daibieuquochoi.backend.service.Impl.AccountServiceImpl;
import daibieuquochoi.backend.service.Impl.FeedbackServiceImpl;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@Validated
public class FeedbackController {
    @Value("${system.baseUrl}")
    private String BASE_URL;

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private RecommendationsServiceImpl recommendationsService;

    @Autowired
    private FeedbackServiceImpl feedbackService;

    @PostMapping(path = "/feedback")
    public ResponseEntity<?> create(
            @RequestParam(value = "files")
                    MultipartFile files,
            @RequestParam(value = "title")
            @NotBlank(message = "{Title.NotBlank}")
                    String title,
            @RequestParam(value = "contents")
            @NotBlank(message = "{Contents.NotBlank}")
                    String contents,
            @RequestParam(value = "accountName")
            @NotBlank(message = "{Account.NotBlank}")
                    String accountName,
            @RequestParam(value = "recommendationsid")
            @NotBlank(message = "{Agency.NotBlank}")
                    String recommendationsid
    ) {
        try {
            FeedbackEntity feedbackEntity = new FeedbackEntity();

            AccountEntity accountEntity = accountService.findByAccountName(accountName).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản: " + accountName));
            RecommendationsEntity recommendationsEntity = recommendationsService.findByID(Long.parseLong(recommendationsid)).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy phản ánh có id = " + recommendationsid));

//            Set FILE
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

            feedbackEntity.setFiles(urlFile);
            feedbackEntity.setTitle(title);
            feedbackEntity.setContents(contents);
            feedbackEntity.setAccount(accountEntity);
            feedbackEntity.setRecommendations(recommendationsEntity);

            feedbackService.create(feedbackEntity);

            return ResponseEntity.ok(new ResponseMessage(new Date(), HttpStatus.OK.value(), "Phản hồi thành công!"));
//            return new ResponseEntity<>(feedbackEntity, HttpStatus.OK);
        } catch (ConstraintViolationException ex) {
            return ResponseEntity.badRequest().body(new ResponseMessage(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
        }
    }

    @GetMapping(path = "feedback/recommendations")
    public ResponseEntity<?> getFeedbackByRecommendations(@RequestParam(value = "id") long id) {
        RecommendationsEntity recommendationsEntity = recommendationsService.findByID(id).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy id này"));

        List<FeedbackEntity> listFeedback = feedbackService.getByRecommendations(recommendationsEntity);
        return new ResponseEntity<>(listFeedback, HttpStatus.OK);
    }

}
