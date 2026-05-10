package com.erp.report.entity.assignment;

import com.erp.report.domain.AssignmentStatusEnum;
import com.erp.report.entity.template.SectionEntity;
import com.erp.report.entity.template.TemplateEntity;
import com.erp.user.entity.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "report_assignments")
public final class AssignmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private TemplateEntity template;

    @Column(name = "unit_number")
    private Integer unitNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_user_id")
    private UserEntity operator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mechanic_user_id")
    private UserEntity mechanic;

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
    private Set<ResponseEntity> responses = new HashSet<>();

    public static AssignmentEntity create(Long id, Long templateId, Integer unitNumber, Long operatorUserId, Long mechanicUserId, String mileage, String nextService, String timeIn, String timeOut) {
        TemplateEntity template = new TemplateEntity();
        template.setId(templateId);

        UserEntity operator = new UserEntity();
        operator.setId(operatorUserId);

        UserEntity mechanic = new UserEntity();
        mechanic.setId(mechanicUserId);

        AssignmentEntity entity = new AssignmentEntity();

        entity.id = id;
        entity.template = template;
        entity.unitNumber = unitNumber;
        entity.operator = operator;
        entity.mechanic = mechanic;
        entity.mileage = mileage;
        entity.nextService = nextService;
        entity.timeIn = OffsetDateTime.parse(timeIn).toLocalTime();
        entity.timeOut = OffsetDateTime.parse(timeOut).toLocalTime();
        entity.date = LocalDate.now();
        entity.status = AssignmentStatusEnum.PENDIENTE;

        return entity;
    }

    public static AssignmentEntity create(Long id, Set<ResponseEntity> responses) {
        AssignmentEntity entity = new AssignmentEntity();

        entity.id = id;
        entity.responses = responses;

        entity.responses.forEach(response -> response.setAssignment(entity));

        return entity;
    }

    public void update(Integer unitNumber, Long operatorUserId, Long mechanicUserId, String mileage, String nextService, LocalTime timeIn, LocalTime timeOut) {
        UserEntity operator = new UserEntity();
        operator.setId(operatorUserId);

        UserEntity mechanic = new UserEntity();
        mechanic.setId(mechanicUserId);

        this.unitNumber = unitNumber;
        this.operator = operator;
        this.mechanic = mechanic;
        this.mileage = mileage;
        this.nextService = nextService;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public void updateStatus() {
        boolean allResponsesAnswered = responses.stream().allMatch(ResponseEntity::isAnswered);

        this.status = allResponsesAnswered ? AssignmentStatusEnum.COMPLETADO : AssignmentStatusEnum.PENDIENTE;
    }

    public void createDefaultResponsesFrom(Set<SectionEntity> sections) {
        Set<ResponseEntity> responses = sections.stream()
                .flatMap(section -> section.getItems().stream())
                .map(item -> ResponseEntity.createDefaultResponse(this, item.getId()))
                .collect(Collectors.toSet());

        this.responses = responses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TemplateEntity getTemplate() {
        return template;
    }

    public void setTemplate(TemplateEntity template) {
        this.template = template;
    }

    public Integer getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(Integer unitNumber) {
        this.unitNumber = unitNumber;
    }

    public UserEntity getOperator() {
        return operator;
    }

    public void setOperator(UserEntity operator) {
        this.operator = operator;
    }

    public UserEntity getMechanic() {
        return mechanic;
    }

    public void setMechanic(UserEntity mechanic) {
        this.mechanic = mechanic;
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

    public Set<ResponseEntity> getResponses() {
        return responses;
    }

    public void setResponses(Set<ResponseEntity> responses) {
        this.responses = responses;
    }
}
