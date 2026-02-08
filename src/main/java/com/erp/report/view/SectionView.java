package com.erp.report.view;

import java.util.List;

public record SectionView(
        Long id,
        String name,
        List<ResponseView> responses
) {
}
