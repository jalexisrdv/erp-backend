package com.jardvcode.erp.checklists.entity.assignment;

import com.jardvcode.erp.checklists.domain.AssignmentStatusEnum;
import com.jardvcode.erp.checklists.exception.assignment.LocalTimeParseException;
import com.jardvcode.erp.checklists.exception.assignment.response.ResponseDoNotExistException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "checklist_assignments")
public final class AssignmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "template_id")
    private Long templateId;

    @Column(name = "template_name")
    private String templateName;

    @Column(name = "unit_number")
    private Integer unitNumber;

    @Column(name = "operator_user_id")
    private Long operatorUserId;

    @Column(name = "operator_full_name")
    private String operatorFullName;

    @Column(name = "mechanic_user_id")
    private Long mechanicUserId;

    @Column(name = "mechanic_full_name")
    private String mechanicFullName;

    @Column(name = "mileage")
    private String mileage;

    @Column(name = "next_service")
    private String nextService;

    @Column(name = "time_in")
    private LocalTime timeIn;

    @Column(name = "time_out")
    private LocalTime timeOut;

    @Column(name = "date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AssignmentStatusEnum status = AssignmentStatusEnum.PENDIENTE;

    @OneToMany(mappedBy = "assignment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AssignmentResponseEntity> responses = new HashSet<>();

    public static AssignmentEntity create(Long id, Long templateId, String templateName, Integer unitNumber, Long operatorUserId, String operatorFullName, Long mechanicUserId, String mechanicFullName, String mileage, String nextService, String timeIn, String timeOut) {
        LocalTime localTimeIn = null;
        LocalTime localTimeOut = null;

        try {
            localTimeIn = LocalTime.parse(timeIn);
        } catch(Exception e) {
            throw new LocalTimeParseException(timeIn);
        }

        try {
            localTimeOut = LocalTime.parse(timeOut);
        } catch(Exception e) {
            throw new LocalTimeParseException(timeOut);
        }

        AssignmentEntity entity = new AssignmentEntity();

        entity.id = id;
        entity.templateId = templateId;
        entity.templateName = templateName;
        entity.unitNumber = unitNumber;
        entity.operatorUserId = operatorUserId;
        entity.operatorFullName = operatorFullName;
        entity.mechanicUserId = mechanicUserId;
        entity.mechanicFullName = mechanicFullName;
        entity.mileage = mileage;
        entity.nextService = nextService;
        entity.timeIn = localTimeIn;
        entity.timeOut = localTimeOut;
        entity.date = LocalDate.now();
        entity.status = AssignmentStatusEnum.PENDIENTE;

        return entity;
    }

    public void update(Integer unitNumber, String operatorFullName, String mechanicFullName, String mileage, String nextService, String timeIn, String timeOut) {
        LocalTime localTimeIn = null;
        LocalTime localTimeOut = null;

        try {
            localTimeIn = LocalTime.parse(timeIn);
        } catch(Exception e) {
            throw new LocalTimeParseException(timeIn);
        }

        try {
            localTimeOut = LocalTime.parse(timeOut);
        } catch(Exception e) {
            throw new LocalTimeParseException(timeOut);
        }

        this.unitNumber = unitNumber;
        this.operatorFullName = operatorFullName;
        this.mechanicFullName = mechanicFullName;
        this.mileage = mileage;
        this.nextService = nextService;
        this.timeIn = localTimeIn;
        this.timeOut = localTimeOut;
    }

    public void updateResponses(List<AssignmentResponseEntity> incomingResponses) {
        Map<Long, AssignmentResponseEntity> responsesById = responses.stream()
                .collect(Collectors.toMap(AssignmentResponseEntity::getId, response -> response));

        incomingResponses.forEach(response -> {
            AssignmentResponseEntity foundResponse = responsesById.get(response.getId());

            if (foundResponse == null) {
                throw new ResponseDoNotExistException();
            }

            foundResponse.update(response.getStatus(), response.getComment());
        });

        status = determineStatus();
    }

    public AssignmentStatusEnum determineStatus() {
        boolean allResponsesAnswered = responses.stream().allMatch(AssignmentResponseEntity::isAnswered);
        return allResponsesAnswered ? AssignmentStatusEnum.COMPLETADO : AssignmentStatusEnum.PENDIENTE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(Integer unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Long getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Long operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public String getOperatorFullName() {
        return operatorFullName;
    }

    public void setOperatorFullName(String operatorFullName) {
        this.operatorFullName = operatorFullName;
    }

    public Long getMechanicUserId() {
        return mechanicUserId;
    }

    public void setMechanicUserId(Long mechanicUserId) {
        this.mechanicUserId = mechanicUserId;
    }

    public String getMechanicFullName() {
        return mechanicFullName;
    }

    public void setMechanicFullName(String mechanicFullName) {
        this.mechanicFullName = mechanicFullName;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getNextService() {
        return nextService;
    }

    public void setNextService(String nextService) {
        this.nextService = nextService;
    }

    public LocalTime getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(LocalTime timeIn) {
        this.timeIn = timeIn;
    }

    public LocalTime getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(LocalTime timeOut) {
        this.timeOut = timeOut;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AssignmentStatusEnum getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatusEnum status) {
        this.status = status;
    }

    public Set<AssignmentResponseEntity> getResponses() {
        return responses;
    }

    public void setResponses(Set<AssignmentResponseEntity> responses) {
        this.responses = responses;
    }

}
