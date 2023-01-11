// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.microsoft.gbb.reddog.accountingservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cosmos")
@Getter
@Setter
public class CosmosProperties {

    private String uri;

    private String key;

    private String secondaryKey;

    private String databaseName;

    private boolean queryMetricsEnabled;

    private String database;

    public boolean isQueryMetricsEnabled() {
        return queryMetricsEnabled;
    }

    public void setQueryMetricsEnabled(boolean enableQueryMetrics) {
        this.queryMetricsEnabled = enableQueryMetrics;
    }
}
