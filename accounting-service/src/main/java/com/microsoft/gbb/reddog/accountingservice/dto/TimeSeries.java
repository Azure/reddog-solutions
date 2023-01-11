package com.microsoft.gbb.reddog.accountingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class TimeSeries<T> {
    @JsonProperty("pointInTime")
    private LocalDateTime pointInTime;

    @JsonProperty("value")
    private T value;
}
