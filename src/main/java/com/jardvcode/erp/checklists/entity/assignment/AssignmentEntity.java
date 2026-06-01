package com.jardvcode.erp.checklists.entity.assignment;

import com.jardvcode.erp.checklists.domain.AssignmentStatusEnum;
import com.jardvcode.erp.checklists.entity.template.SectionEntity;
import com.jardvcode.erp.checklists.entity.template.TemplateEntity;
import com.jardvcode.erp.checklists.exception.assignment.LocalTimeParseException;
import com.jardvcode.erp.checklists.exception.assignment.response.ResponseDoNotExistException;
import com.jardvcode.erp.users.entity.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

    public static AssignmentEntity create(Long id, TemplateEntity template, Integer unitNumber, Long operatorUserId, Long mechanicUserId, String mileage, String nextService, String timeIn, String timeOut) {
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
        entity.timeIn = localTimeIn;
        entity.timeOut = localTimeOut;
        entity.date = LocalDate.now();
        entity.status = AssignmentStatusEnum.PENDIENTE;
        entity.responses = entity.createDefaultResponsesFrom(template.getSections());

        return entity;
    }

    private Set<ResponseEntity> createDefaultResponsesFrom(Set<SectionEntity> sections) {
        return sections.stream()
                .flatMap(section -> section.getItems().stream())
                .map(item -> ResponseEntity.createDefaultResponse(this, item.getId()))
                .collect(Collectors.toSet());
    }

    public void update(Integer unitNumber, Long operatorUserId, Long mechanicUserId, String mileage, String nextService, String timeIn, String timeOut) {
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

        UserEntity operator = new UserEntity();
        operator.setId(operatorUserId);

        UserEntity mechanic = new UserEntity();
        mechanic.setId(mechanicUserId);

        this.unitNumber = unitNumber;
        this.operator = operator;
        this.mechanic = mechanic;
        this.mileage = mileage;
        this.nextService = nextService;
        this.timeIn = localTimeIn;
        this.timeOut = localTimeOut;
    }

    public void updateResponses(List<ResponseEntity> incomingResponses) {
        Map<Long, ResponseEntity> responsesById = responses.stream()
                .collect(Collectors.toMap(ResponseEntity::getId, response -> response));

        incomingResponses.forEach(response -> {
            ResponseEntity foundResponse = responsesById.get(response.getId());

            if (foundResponse == null) {
                throw new ResponseDoNotExistException();
            }

            foundResponse.update(response.getStatus(), response.getComment());
        });

        status = determineStatus();
    }

    public AssignmentStatusEnum determineStatus() {
        boolean allResponsesAnswered = responses.stream().allMatch(ResponseEntity::isAnswered);
        return allResponsesAnswered ? AssignmentStatusEnum.COMPLETADO : AssignmentStatusEnum.PENDIENTE;
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
