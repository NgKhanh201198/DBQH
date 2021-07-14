package daibieuquochoi.backend.dto;

public class RecommendationsDTO {
    private String title;
    private int day;
    private String agency;

    public RecommendationsDTO() {
        super();
    }

    public RecommendationsDTO(String title, int day, String agency) {
        this.title = title;
        this.day = day;
        this.agency = agency;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }
}
