package com.jardvcode.erp.checklists.dto.report;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ResponseDTO that = (ResponseDTO) o;
        return Objects.equals(section, that.section) && Objects.equals(item, that.item) && Objects.equals(status, that.status) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(section, item, status, comment);
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
