package org.apache.fineract.audit;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.azure.core.annotation.QueryParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
@SecurityRequirement(name = "auth")
@RequestMapping("/api/v1/audit")
@Tag(name = "Users API")
public class AuditApiResource {
    private AuditService auditService;

    public AuditApiResource(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    @Operation(summary = "List Audits", description = "Get a 200 list of audits that match the criteria supplied and sorted by audit id in descending order, and are within the requestors' data scope. Also it supports pagination and sorting\n" + "\n" + "Example Request:\n" + "\n" + "audits\n" + "\n" + "audits?actionName=UPDATE&entityName=AppUser\n")
    public Page<AuditSource> retrieveAuditEntries(@QueryParam("actionName") @Parameter(description = "actionName") final String actionName,
                                                  @QueryParam("entityName") @Parameter(description = "entityName") final String entityName,
                                                  @QueryParam("resourceId") @Parameter(description = "resourceId") final Long resourceId,
                                                  @QueryParam("makerId") @Parameter(description = "makerId") final Long makerId,
                                                  @QueryParam("makerDateTimeFrom") @Parameter(description = "makerDateTimeFrom") final Date makerDateTimeFrom,
                                                  @QueryParam("makerDateTimeTo") @Parameter(description = "makerDateTimeTo") final Date makerDateTimeTo,
                                                  @QueryParam("processingResult") @Parameter(description = "processingResult") final String processingResult,
                                                  @QueryParam("offset") @Parameter(description = "offset") final Integer offset,
                                                  @QueryParam("limit") @Parameter(description = "limit") final Integer limit,
                                                  @QueryParam("orderBy") @Parameter(description = "orderBy") final String orderBy,
                                                  @QueryParam("sortOrder") @Parameter(description = "sortOrder") final String sortOrder) {
        AuditSpec auditSpec = new AuditSpec();
        AuditSearch search = new AuditSearch(actionName, entityName, resourceId, makerId, makerDateTimeFrom, makerDateTimeTo, processingResult);
        return this.auditService.getAudits(auditSpec.getFilter(search), new PageRequest(offset, limit, new Sort(Sort.Direction.valueOf(sortOrder), orderBy)));
    }
}
