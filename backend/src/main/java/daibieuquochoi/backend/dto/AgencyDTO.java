package daibieuquochoi.backend.dto;

import javax.validation.constraints.NotBlank;

public class AgencyDTO {
    @NotBlank(message = "{Agency.NotBlank}")
    private String agencyName;

    private String note;

    private String status;

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
