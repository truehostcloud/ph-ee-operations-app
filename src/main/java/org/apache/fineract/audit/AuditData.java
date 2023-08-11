/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.audit;

import java.time.ZonedDateTime;

/**
 * Immutable data object representing audit data.
 */
public final class AuditData {

    @SuppressWarnings("unused")
    private final Long id;
    @SuppressWarnings("unused")
    private final String actionName;
    private final String entityName;
    @SuppressWarnings("unused")
    private final Long resourceId;
    @SuppressWarnings("unused")
    private final String maker;
    @SuppressWarnings("unused")
    private final ZonedDateTime madeOnDate;
    @SuppressWarnings("unused")
    private final String processingResult;
    private String dataAsJson;
    @SuppressWarnings("unused")
    private final String officeName;
    @SuppressWarnings("unused")
    private final String groupName;
    @SuppressWarnings("unused")
    private final String url;

    public AuditData(final Long id, final String actionName, final String entityName, final Long resourceId, final String maker, final ZonedDateTime madeOnDate, final String processingResult, final String officeName, final String groupName, final String url) {

        this.id = id;
        this.actionName = actionName;
        this.entityName = entityName;
        this.resourceId = resourceId;
        this.maker = maker;
        this.madeOnDate = madeOnDate;
        this.processingResult = processingResult;
        this.officeName = officeName;
        this.groupName = groupName;
        this.url = url;
    }

    public void setDataAsJson(final String dataAsJson) {
        this.dataAsJson = dataAsJson;
    }

    public String getDataAsJson() {
        return this.dataAsJson;
    }

    public String getEntityName() {
        return this.entityName;
    }
}
