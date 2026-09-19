package com.jardvcode.erp.checklists.dto.report;

public final class ResponseDTO {

    private String section;
    private String item;
    private String status;
    private String comment;

    public ResponseDTO(String section, String item, String status, String comment) {
        this.section = section;
        this.item = item;
        this.status = status;
        this.comment = comment;
    }

    public String getSection() {
        return section;
    }

    public String getItem() {
        return item;
    }

    public String getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "group='" + section + '\'' +
                ", item='" + item + '\'' +
                ", status='" + status + '\'' +
                ", observation='" + comment + '\'' +
                '}';
    }

}
