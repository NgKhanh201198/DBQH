package daibieuquochoi.backend.api.controller;

import daibieuquochoi.backend.entity.AccountEntity;
import daibieuquochoi.backend.entity.AgencyEntity;
import daibieuquochoi.backend.entity.FeedbackEntity;
import daibieuquochoi.backend.entity.RecommendationsEntity;
import daibieuquochoi.backend.exception.BadRequestException;
import daibieuquochoi.backend.response.ResponseMessage;
import daibieuquochoi.backend.service.Impl.AccountServiceImpl;
import daibieuquochoi.backend.service.Impl.AgencyServiceImpl;
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

    @Autowired
    private AgencyServiceImpl agencyService;

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
            @RequestParam(value = "status")
            @NotBlank(message = "{Status.NotBlank}")
                    String status,
            @RequestParam(value = "accountName")
            @NotBlank(message = "{Account.NotBlank}")
                    String accountName,
            @RequestParam(value = "recommendationsid")
            @NotBlank()
                    String recommendationsid
    ) {
        try {
            FeedbackEntity feedbackEntity = new FeedbackEntity();

            AccountEntity accountEntity = accountService.findByAccountName(accountName).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản: " + accountName));
            RecommendationsEntity recommendationsEntity = recommendationsService.findByID(Long.parseLong(recommendationsid)).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy phản ánh có id = " + recommendationsid));

            feedbackEntity.setFiles(null);
            feedbackEntity.setTitle(title);
            feedbackEntity.setContents(contents);
            feedbackEntity.setStatus(status);
            feedbackEntity.setAccount(accountEntity);
            feedbackEntity.setRecommendations(recommendationsEntity);

            if (recommendationsEntity.getFeedback().size() == 0 && status.equals("Phản hồi")) {
                recommendationsService.updateStatus(Long.parseLong(recommendationsid), "Đã phản hồi");
            }
            feedbackService.create(feedbackEntity);

            return ResponseEntity.ok(new ResponseMessage(new Date(), HttpStatus.OK.value(), "Phản hồi thành công!"));

        } catch (ConstraintViolationException ex) {
            return ResponseEntity.badRequest().body(new ResponseMessage(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
        }
    }

    @PostMapping(path = "/forwarded")
    public ResponseEntity<?> forwarded(
            @RequestParam(value = "agency")
            @NotBlank(message = "{Agency.NotBlank}")
                    String agency,
            @RequestParam(value = "title")
            @NotBlank(message = "{Title.NotBlank}")
                    String title,
            @RequestParam(value = "contents")
            @NotBlank(message = "{Contents.NotBlank}")
                    String contents,
            @RequestParam(value = "status")
            @NotBlank(message = "{Status.NotBlank}")
                    String status,
            @RequestParam(value = "accountName")
            @NotBlank(message = "{Account.NotBlank}")
                    String accountName,
            @RequestParam(value = "recommendationsid")
            @NotBlank()
                    String recommendationsid
    ) {
        try {
            FeedbackEntity feedbackEntity = new FeedbackEntity();

            AccountEntity accountEntity = accountService.findByAccountName(accountName).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản: " + accountName));
            RecommendationsEntity recommendationsEntity = recommendationsService.findByID(Long.parseLong(recommendationsid)).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy phản ánh có id = " + recommendationsid));


            feedbackEntity.setFiles(null);

            feedbackEntity.setTitle(title);
            feedbackEntity.setContents(contents);
            feedbackEntity.setStatus(status);
            feedbackEntity.setAccount(accountEntity);
            feedbackEntity.setRecommendations(recommendationsEntity);

            AgencyEntity agencyEntity = agencyService.findByAgencyName(agency).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy cơ quan: " + agency));
            RecommendationsEntity oldRecommendationsEntity = recommendationsService.findByID(Long.parseLong(recommendationsid)).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy id = " + recommendationsid));
            oldRecommendationsEntity.setAgency(agencyEntity);
            recommendationsService.update(oldRecommendationsEntity);

            feedbackService.create(feedbackEntity);

            return ResponseEntity.ok(new ResponseMessage(new Date(), HttpStatus.OK.value(), "Chuyển tiếp thành công!"));

        } catch (ConstraintViolationException ex) {
            return ResponseEntity.badRequest().body(new ResponseMessage(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
        }
    }

    @GetMapping(path = "feedback/recommendations")
    public ResponseEntity<?> getFeedbackByRecommendations(@RequestParam(value = "id") long id) {
        RecommendationsEntity recommendationsEntity = recommendationsService.findByID(id).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy id này"));

        List<FeedbackEntity> listFeedback = feedbackService.getByRecommendations(recommendationsEntity, "Phản hồi");
        return new ResponseEntity<>(listFeedback, HttpStatus.OK);
    }

}
