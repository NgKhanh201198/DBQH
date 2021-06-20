package daibieuquochoi.backend.api.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import daibieuquochoi.backend.config.security.JwtTokenUtils;
import daibieuquochoi.backend.dto.AccountDTO;
import daibieuquochoi.backend.dto.LoginDTO;
import daibieuquochoi.backend.entity.AccountEntity;
import daibieuquochoi.backend.entity.AgencyEntity;
import daibieuquochoi.backend.entity.RoleEntity;
import daibieuquochoi.backend.exception.BadRequestException;
import daibieuquochoi.backend.response.JwtResponse;
import daibieuquochoi.backend.service.Impl.AgencyServiceImpl;
import daibieuquochoi.backend.service.Impl.RoleServiceImpl;
import daibieuquochoi.backend.service.UploadFileService;
import daibieuquochoi.backend.service.UserDetailsImpl;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import daibieuquochoi.backend.response.ResponseMessage;
import daibieuquochoi.backend.service.Impl.AccountServiceImpl;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AccountController {
    @Value("${system.baseUrl}")
    private String BASE_URL;

    @Autowired
    public AccountServiceImpl accountService;

    @Autowired
    public RoleServiceImpl roleService;

    @Autowired
    public AgencyServiceImpl agencyService;

    @Autowired
    public UploadFileService uploadFileService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public JwtTokenUtils jwtTokenUtils;

    @Autowired
    public AuthenticationManager authenticationManager;

/*    @PostMapping(path = "/account")
    public ResponseEntity<?> register(@ModelAttribute @Valid LoginDTO registerDTO) {
        try {
            if (accountService.isExistsByAccountName(registerDTO.getAccountName())) {
                return ResponseEntity.badRequest().body(new ResponseMessage(new Date(), HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.name(), "Tài khoản này đã được sử dụng!"));
            }
            CommonController controller = new CommonController();
            AccountEntity accountEntity = new AccountEntity();

//            Set Role
            RoleEntity roleEntity = roleService.findByKeyName(registerDTO.getRole().toUpperCase())
                    .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy quyền " + registerDTO.getRole().toUpperCase()));

//            Set Agency
            AgencyEntity agencyEntity = agencyService.findByAgencyName(registerDTO.getAgency())
                    .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy cơ quan " + registerDTO.getAgency()));

//            Set DateOfBirth
            Date date = controller.stringToDate(registerDTO.getDateOfBirth());

//             Set Avatar
            String[] allowedMimeTypes = new String[]{"image/gif", "image/png", "image/jpeg"};

            if (!ArrayUtils.contains(allowedMimeTypes, registerDTO.getAvatar().getContentType())) {
                throw new BadRequestException("Tệp không hợp lệ, các tệp hợp lệ bao gồm: .jpg, .png, .gif");
            }

            String fileType = registerDTO.getAvatar().getOriginalFilename().substring(registerDTO.getAvatar().getOriginalFilename().length() - 4);
            String uuidAvatar = "avatar-" + UUID.randomUUID().toString().replaceAll("-", "") + fileType.toLowerCase();
            String urlAvatar = BASE_URL + "api/files/" + uuidAvatar;
            uploadFileService.save(registerDTO.getAvatar(), uuidAvatar);

            accountEntity.setAccountName(registerDTO.getAccountName());
            accountEntity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            accountEntity.setFullname(registerDTO.getFullname());
            accountEntity.setDateOfBirth(date);
            accountEntity.setAvatar(urlAvatar);
            accountEntity.setPosition(registerDTO.getPosition());
            accountEntity.setCandidateplace(registerDTO.getCandidateplace());
            accountEntity.setStatus(registerDTO.getStatus().toUpperCase());
            accountEntity.setAgency(agencyEntity);
            accountEntity.setRole(roleEntity);
            accountService.create(accountEntity);

            return ResponseEntity.ok(new ResponseMessage(new Date(), HttpStatus.OK.value(), "Bạn đã đăng ký thành công!"));
        return new ResponseEntity<>(registerDTO, HttpStatus.OK);
        } catch (DataAccessException ex) {
            return ResponseEntity.badRequest().body(new ResponseMessage(new Date(), HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
        }
    }*/

    @PostMapping(path = "/account")
    public ResponseEntity<?> create(
            @RequestParam(value = "avatar") MultipartFile avatar,
            @RequestParam(value = "accountName") String accountName,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "fullname") String fullname,
            @RequestParam(value = "dateOfBirth") String dateOfBirth,
            @RequestParam(value = "position") String position,
            @RequestParam(value = "candidateplace") String candidateplace,
            @RequestParam(value = "status") String status,
            @RequestParam(value = "agency") String agency,
            @RequestParam(value = "role") String role
    ) {
        try {
            if (accountService.isExistsByAccountName(accountName)) {
                return ResponseEntity.badRequest().body(new ResponseMessage(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "Tài khoản này đã được sử dụng!"));
            }
            CommonController controller = new CommonController();
            AccountEntity accountEntity = new AccountEntity();

//            Set Role
            RoleEntity roleEntity = roleService.findByKeyName(role.toUpperCase()).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy quyền " + role.toUpperCase()));

//            Set Agency
            AgencyEntity agencyEntity = agencyService.findByAgencyName(agency).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy cơ quan " + agency));

//            Set DateOfBirth
            Date date = controller.stringToDate(dateOfBirth);

//             Set Avatar
            String[] allowedMimeTypes = new String[]{"image/gif", "image/png", "image/jpeg"};

            if (!ArrayUtils.contains(allowedMimeTypes, avatar.getContentType())) {
                throw new BadRequestException("Tệp không hợp lệ, các tệp hợp lệ bao gồm: .jpg, .png, .gif");
            }

            String fileType = avatar.getOriginalFilename().substring(avatar.getOriginalFilename().length() - 4);
            String uuidAvatar = "avatar-" + UUID.randomUUID().toString().replaceAll("-", "") + fileType.toLowerCase();
            String urlAvatar = BASE_URL + "api/files/" + uuidAvatar;
            uploadFileService.save(avatar, uuidAvatar);

            accountEntity.setAccountName(accountName);
            accountEntity.setPassword(passwordEncoder.encode(password));
            accountEntity.setFullname(fullname);
            accountEntity.setDateOfBirth(date);
            accountEntity.setAvatar(urlAvatar);
            accountEntity.setPosition(position);
            accountEntity.setCandidateplace(candidateplace);
            accountEntity.setStatus(status.toUpperCase());
            accountEntity.setAgency(agencyEntity);
            accountEntity.setRole(roleEntity);
            accountService.create(accountEntity);

            return ResponseEntity.ok(new ResponseMessage(new Date(), HttpStatus.OK.value(), "Bạn đã đăng ký thành công!"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new ResponseMessage(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        try {
            // Xác thực username password
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getAccountName(), loginDTO.getPassword()));

            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Tạo token
            String jwtToken = jwtTokenUtils.generateToken(authentication);

            // Truy xuất thông tin người dùng đang đặng nhập.
            UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();

            if (userDetailsImpl == null) {
                return ResponseEntity.badRequest().body(new ResponseMessage(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "Không tìm thấy người dùng!"));
            }

            List<String> role = userDetailsImpl.getAuthorities().stream().map(permission -> permission.getAuthority()).collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(userDetailsImpl.getId(), userDetailsImpl.getUsername(), userDetailsImpl.getFullname(), userDetailsImpl.getDateOfBirth(), userDetailsImpl.getAvatar(), userDetailsImpl.getPosition(), userDetailsImpl.getCandidateplace(), userDetailsImpl.getStatus(), role, userDetailsImpl.getAgency().getAgencyName(), jwtToken));

        } catch (BadCredentialsException ex) {
            ResponseMessage message = new ResponseMessage(new Date(), HttpStatus.UNAUTHORIZED.value(), "Unauthorized", "Địa chỉ email hoặc mật khẩu của bạn không chính xác!");
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        } catch (DisabledException ex) {
            ResponseMessage message = new ResponseMessage(new Date(), HttpStatus.UNAUTHORIZED.value(), "Unauthorized", "Tài khoản của bạn chưa được kích hoạt!");
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        } catch (LockedException ex) {
            ResponseMessage message = new ResponseMessage(new Date(), HttpStatus.UNAUTHORIZED.value(), "Unauthorized", "Tài khoản của bạn đã bị khóa!");
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        } catch (CredentialsExpiredException ex) {
            ResponseMessage message = new ResponseMessage(new Date(), HttpStatus.UNAUTHORIZED.value(), "Unauthorized", "Tài khoản của bạn chưa xác minh!");
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "account")
    public ResponseEntity<?> getAccountAll() {
        List<AccountEntity> listAccount = accountService.getAll();
        return new ResponseEntity<>(listAccount, HttpStatus.OK);
    }

    @GetMapping(path = "/account/{id}")
    public ResponseEntity<?> getAccountByID(@PathVariable("id") long id) {
        if (!(accountService.isExistsById(id))) {
            ResponseMessage message = new ResponseMessage(new Date(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), "Không tìm thấy id = " + id);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            AccountEntity accountEntity = accountService.getById(id);
            return new ResponseEntity<>(accountEntity, HttpStatus.OK);
        }
    }

    @PutMapping("/account/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") long id, @RequestBody @Valid AccountDTO accountDTO) {
        try {
            if (!(accountService.isExistsById(id))) {
                ResponseMessage message = new ResponseMessage(new Date(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), "Không tìm thấy id = " + id);
                return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            } else {
                AccountEntity oldAccountEntity = accountService.getById(id);

                if (accountService.isExistsByAccountName(accountDTO.getAccountName()) && !(accountDTO.getAccountName().equals(oldAccountEntity.getAccountName()))) {
                    return ResponseEntity.badRequest().body(new ResponseMessage(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "Tài khoản này đã được sử dụng!"));
                }

//                Set Role
                RoleEntity roleEntity = roleService.findByKeyName(accountDTO.getRole()).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy quyền " + accountDTO.getRole()));

//                Set Agency
                AgencyEntity agencyEntity = agencyService.findByAgencyName(accountDTO.getAgency()).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy cơ quan " + accountDTO.getAgency()));

                // Set DateOfBirth
                CommonController commonController = new CommonController();
                Date date = commonController.stringToDate(accountDTO.getDateOfBirth());

                oldAccountEntity.setFullname(accountDTO.getFullname());
                oldAccountEntity.setDateOfBirth(date);
                oldAccountEntity.setPosition(accountDTO.getPosition());
                oldAccountEntity.setCandidateplace(accountDTO.getCandidateplace());
                oldAccountEntity.setStatus(accountDTO.getStatus());
                oldAccountEntity.setRole(roleEntity);
                oldAccountEntity.setAgency(agencyEntity);

                accountService.updateByID(oldAccountEntity);

                return ResponseEntity.ok(new ResponseMessage(new Date(), HttpStatus.OK.value(), "Cập nhật thành công!"));
            }
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "Đã có lỗi xảy ra, vui lòng thử lại!"));
        }
    }

    @PutMapping("/account/avatar/{id}")
    public ResponseEntity<?> updateAvatar(@PathVariable("id") long id, @RequestParam("avatar") MultipartFile avatar) {
        try {
            if (!(accountService.isExistsById(id))) {
                ResponseMessage message = new ResponseMessage(new Date(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), "Không tìm thấy tài khoản có id = " + id);
                return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            } else {
                AccountEntity olAccountEntity = accountService.getById(id);

                String[] allowedMimeTypes = new String[]{"image/gif", "image/png", "image/jpeg"};

                if (!ArrayUtils.contains(allowedMimeTypes, avatar.getContentType())) {
                    throw new BadRequestException("Tệp không hợp lệ, các tệp hợp lệ bao gồm: .jpg, .png, .gif");
                }
                String fileName = avatar.getOriginalFilename().substring(avatar.getOriginalFilename().length() - 4);

                String uuidImage = "avatar-" + UUID.randomUUID().toString().replaceAll("-", "") + fileName.toLowerCase();
                String urlAvatar = BASE_URL + "api/files/" + uuidImage;
                if (olAccountEntity.getAvatar() != null) {
                    uploadFileService.deleteByName(olAccountEntity.getAvatar().substring(olAccountEntity.getAvatar().length() - uuidImage.length()));
                }
                uploadFileService.save(avatar, uuidImage);

                accountService.updateAvatar(id, urlAvatar);

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(new Date(), HttpStatus.OK.value(), "Cập nhật hình ảnh thành công."));
            }
        } catch (BadRequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
        }
    }
}
