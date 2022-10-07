package com.microsoft.gbb.rasa.accountingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class OrdersTimeSeries {
    @JsonProperty("storeId")
    private String storeId;

    @JsonProperty("values")
    private List<TimeSeries<Integer>> values;
}
