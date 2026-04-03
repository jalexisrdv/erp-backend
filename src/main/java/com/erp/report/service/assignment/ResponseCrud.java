package com.erp.report.service.assignment;

import com.erp.report.entity.assignment.ResponseEntity;
import com.erp.report.exception.assignment.response.ResponsesDoNotExistException;
import com.erp.report.repository.assignment.AssigmentRepository;
import com.erp.report.repository.assignment.ResponseRepository;
import com.erp.shared.domain.DomainError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public final class ResponseCrud {

    private final static Logger LOG = LoggerFactory.getLogger(ResponseCrud.class);

    private final ResponseRepository repository;

    public ResponseCrud(ResponseRepository repository, AssigmentRepository assigmentRepository) {
        this.repository = repository;
    }

    public List<ResponseEntity> update(List<ResponseEntity> entities) {
        try {
            List<Long> ids = entities.stream().map(ResponseEntity::getId).toList();

            Map<Long, ResponseEntity> foundEntities = repository.findAllById(ids)
                    .stream().collect(Collectors.toMap(ResponseEntity::getId, entity -> entity));

            if(foundEntities.size() != ids.size()) {
                throw new ResponsesDoNotExistException(ids);
            }

            List<ResponseEntity> entitiesToUpdate = new ArrayList<>();

            entities.forEach(entity -> {
                ResponseEntity foundEntity = foundEntities.get(entity.getId());

                foundEntity.update(entity.getStatus(), entity.getComment());

                entitiesToUpdate.add(foundEntity);
            });

            return repository.saveAll(entitiesToUpdate);
        } catch(DomainError e) {
            LOG.info(e.getMessage(), e);
            throw e;
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}
