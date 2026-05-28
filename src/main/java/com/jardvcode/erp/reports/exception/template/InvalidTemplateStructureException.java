package com.jardvcode.erp.reports.exception.template;

import com.jardvcode.erp.shared.domain.DomainError;

import java.util.Map;

public final class InvalidTemplateStructureException extends DomainError {
    public InvalidTemplateStructureException(Map<String, Integer> duplicateSections, Map<String, Map<String, Integer>> duplicateItems) {
        super(createMessage(duplicateSections, duplicateItems));
    }

    private static String createMessage(Map<String, Integer> duplicateSections, Map<String, Map<String, Integer>> duplicateItems) {
        StringBuilder message = new StringBuilder();

        if(!duplicateSections.isEmpty()) {
            message.append("- Secciones duplicadas: " + duplicateSections.keySet() + "\n");
        }

        duplicateItems.forEach((sectionName, items) -> {
            if(!items.isEmpty()) {
                message.append("- Items duplicados en sección '" + sectionName + "': " + items.keySet() + "\n");
            }
        });

        return message.toString();
    }
}
