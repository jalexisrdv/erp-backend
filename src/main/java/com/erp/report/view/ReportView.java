package com.erp.report.view;

import java.util.List;

public record ReportView(HeaderView header, List<SectionView> sections) {

}