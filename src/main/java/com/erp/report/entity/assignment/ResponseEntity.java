package com.erp.report.entity.assignment;

import com.erp.report.domain.ResponseStatusEnum;
import com.erp.report.entity.template.ItemEntity;
import com.erp.report.entity.template.SectionEntity;
import com.erp.report.exception.assignment.response.CommentRequiredException;
import jakarta.persistence.*;

@Entity
@Table(name = "report_responses")
public final class ResponseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    private AssignmentEntity assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    @Column(name = "status")
    private String status;

    @Column(name = "comment")
    private String comment;

    public static ResponseEntity create(Long id, Long assignmentId, Long itemId, String status, String comment) {
        AssignmentEntity assignmentEntity = new AssignmentEntity();
        assignmentEntity.setId(assignmentId);

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(itemId);

        ResponseEntity entity = new ResponseEntity();

        entity.id = id;
        entity.assignment = assignmentEntity;
        entity.item = itemEntity;
        entity.status = status;
        entity.comment = comment;

        return entity;
    }

    public static ResponseEntity create(Long id, String status, String comment) {
        ResponseEntity entity = new ResponseEntity();

        entity.id = id;
        entity.status = status;
        entity.comment = comment;

        return entity;
    }

    public void update(String status, String comment) {
        if(isCommentRequired(status, comment)) {
            throw new CommentRequiredException();
        }

        this.status = status;
        this.comment = comment;
    }

    private static boolean isCommentRequired(String status, String comment) {
        return !status.equalsIgnoreCase(ResponseStatusEnum.OK.name()) && (comment == null || comment.trim().isEmpty());
    }

    public AssignmentEntity assignment() {
        return assignment;
    }

    public SectionEntity section() {
        return item.getSection();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssignmentEntity getAssignment() {
        return assignment;
    }

    public void setAssignment(AssignmentEntity assignment) {
        this.assignment = assignment;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
