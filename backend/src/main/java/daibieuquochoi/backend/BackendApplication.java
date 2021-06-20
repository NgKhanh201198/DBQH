package daibieuquochoi.backend;

import daibieuquochoi.backend.service.UploadFileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {
    @Resource
    private UploadFileService uploadFileService;

    @Override
    public void run(String... args) throws Exception {
        uploadFileService.init();

        System.out.println("data: " + new Date());
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
