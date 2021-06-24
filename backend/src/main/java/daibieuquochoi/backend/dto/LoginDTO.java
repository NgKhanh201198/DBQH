package daibieuquochoi.backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginDTO {
    @NotBlank(message = "{AccountName.NotBlank}")
    private String accountName;

    @NotBlank(message = "{Password.NotBlank}")
//    @Size(min = 6, max = 20, message = "{Password.Size}")
    private String password;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
