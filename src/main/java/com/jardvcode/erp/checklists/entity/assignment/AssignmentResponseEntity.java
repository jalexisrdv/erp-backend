package com.jardvcode.erp.checklists.entity.assignment;

import com.jardvcode.erp.checklists.domain.ResponseStatusEnum;
import com.jardvcode.erp.checklists.exception.assignment.response.CommentRequiredException;
import jakarta.persistence.*;

@Entity
@Table(name = "assignment_responses")
public final class AssignmentResponseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    private AssignmentEntity assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private AssignmentItemEntity item;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ResponseStatusEnum status;

    @Column(name = "comment")
    private String comment;

    public static AssignmentResponseEntity create(Long id, ResponseStatusEnum status, String comment) {
        AssignmentResponseEntity entity = new AssignmentResponseEntity();

        entity.id = id;
        entity.status = status;
        entity.comment = comment;

        return entity;
    }

    public void update(ResponseStatusEnum status, String comment) {
        if(isCommentRequired(status, comment)) {
            throw new CommentRequiredException();
        }

        this.status = status;
        this.comment = comment;
    }

    public boolean isAnswered() {
        return status != null;
    }

    private static boolean isCommentRequired(ResponseStatusEnum status, String comment) {
        return status != null && !status.equals(ResponseStatusEnum.OK) && (comment == null || comment.isBlank());
    }

    public AssignmentEntity assignment() {
        return assignment;
    }

    public AssignmentSectionEntity section() {
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

    public AssignmentItemEntity getItem() {
        return item;
    }

    public void setItem(AssignmentItemEntity item) {
        this.item = item;
    }

    public ResponseStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ResponseStatusEnum status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
