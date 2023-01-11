package com.microsoft.gbb.reddog.accountingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class ChartKeyValue<T> {
    @JsonProperty("label")
    private String label;

    @JsonProperty("pv")
    private T pointValue;
}
