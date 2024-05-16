package com.microsoft.gbb.reddog.loyaltyservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

/**
 * The type Order item summary.
 */
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@RedisHash("loyaltysummary")
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

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("storeLatitude")
    private String storeLatitude;

    @JsonProperty("storeLongitude")
    private String storeLongitude;

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