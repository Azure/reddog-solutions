package com.microsoft.gbb.reddog.loyaltyservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Order item summary.
 */
@Getter
@Setter
@Builder
public class LoyaltySummary {

    @JsonProperty("loyaltyId")
    private String loyaltyId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("pointsEarned")
    private int pointsEarned;

    @JsonProperty("pointTotal")
    private int pointTotal;

    @Override
    public String toString() {
        return "LoyaltySummary{" +
                "loyaltyId='" + loyaltyId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pointsEarned=" + pointsEarned +
                ", pointTotal=" + pointTotal +
                '}';
    }
}