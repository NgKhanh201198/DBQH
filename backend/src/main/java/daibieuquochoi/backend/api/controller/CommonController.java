package daibieuquochoi.backend.api.controller;

import daibieuquochoi.backend.exception.BadRequestException;
import daibieuquochoi.backend.service.UploadFileService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class CommonController {
    @Value("${system.baseUrl}")
    private String BASE_URL;

    @Autowired
    public UploadFileService uploadFileService;

//    public String generateUrlAvatar(MultipartFile avatar) {
//        String[] allowedMimeTypes = new String[]{"image/gif", "image/png", "image/jpeg"};
//
//        if (!ArrayUtils.contains(allowedMimeTypes, avatar.getContentType())) {
//            throw new BadRequestException("Tệp không hợp lệ, các tệp hợp lệ bao gồm: .jpg, .png, .gif");
//        }
//
//        String fileType = avatar.getOriginalFilename().substring(avatar.getOriginalFilename().length() - 4);
//        String uuidAvatar = "avatar-" + UUID.randomUUID().toString().replaceAll("-", "") + fileType.toLowerCase();
//        String urlAvatar = BASE_URL + "api/files/" + uuidAvatar;
//        uploadFileService.save(avatar, uuidAvatar);
//
//        return urlAvatar;
//    }

    public Date stringToDate(String strDate) {
        Date date = null;
        if (isDateValid(strDate, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                date = formatter.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                date = formatter.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return date;
    }

    public static boolean isDateValid(String date, String date_format) {
        try {
            DateFormat df = new SimpleDateFormat(date_format);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
