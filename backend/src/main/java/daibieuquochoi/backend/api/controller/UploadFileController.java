package daibieuquochoi.backend.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import daibieuquochoi.backend.dto.FileRequest;
import daibieuquochoi.backend.exception.BadRequestException;
import daibieuquochoi.backend.response.ResponseMessage;
import daibieuquochoi.backend.service.UploadFileService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UploadFileController {
    @Autowired
    UploadFileService uploadFileService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        String message = "";
        try {
            String[] allowedMimeTypes = new String[]{"image/gif", "image/png", "image/jpeg"};
            List<String> fileNames = new ArrayList<>();
            Arrays.asList(files).stream().forEach(file -> {
                if (!ArrayUtils.contains(allowedMimeTypes, file.getContentType().toLowerCase())) {
                    throw new BadRequestException("Tệp không hợp lệ, các tệp hợp lệ bao gồm: .jpg, .png, .gif");
                }
                String fileName = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4,
                        file.getOriginalFilename().length());

                String uuidImage = UUID.randomUUID().toString().replaceAll("-", "") + fileName.toLowerCase();
                uploadFileService.save(file, uuidImage);
                fileNames.add(file.getOriginalFilename());

            });
            String strFileNames = fileNames.stream().map(Object::toString).collect(Collectors.joining(", "));
            message = "Đã tải tệp: " + strFileNames + "lên thành công!";
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage(new Date(), HttpStatus.OK.value(), message));
        } catch (BadRequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(new Date(),
                    HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
        }
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = uploadFileService.load(filename);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/jpeg"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "Content-Disposition: inlines").body(file);
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileRequest>> getListFiles() {
        List<FileRequest> FileRequest = uploadFileService.loadAll().map(file -> {
            String filename = file.getFileName().toString();

            String url = MvcUriComponentsBuilder
                    .fromMethodName(UploadFileController.class, "getFile", file.getFileName().toString()).build()
                    .toString();
            return new FileRequest(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(FileRequest);
    }
}
