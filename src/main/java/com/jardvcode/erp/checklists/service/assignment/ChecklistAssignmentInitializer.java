package com.jardvcode.erp.checklists.service.assignment;

import com.jardvcode.erp.checklists.repository.assignment.AssignmentItemRepository;
import com.jardvcode.erp.checklists.repository.assignment.AssignmentResponseRepository;
import com.jardvcode.erp.checklists.repository.assignment.AssignmentSectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChecklistAssignmentInitializer {

    private final AssignmentSectionRepository sectionRepository;
    private final AssignmentItemRepository itemRepository;
    private final AssignmentResponseRepository responseRepository;

    public ChecklistAssignmentInitializer(AssignmentSectionRepository sectionRepository, AssignmentItemRepository itemRepository, AssignmentResponseRepository responseRepository) {
        this.sectionRepository = sectionRepository;
        this.itemRepository = itemRepository;
        this.responseRepository = responseRepository;
    }

    public void initialize(Long templateId, Long assignmentId) {
        sectionRepository.snapshot(templateId, assignmentId);
        itemRepository.snapshot(templateId, assignmentId);
        responseRepository.createDefaultResponsesFromItems(assignmentId);
    }

}
