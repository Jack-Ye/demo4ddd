package com.demo.demo4ddd.domain.shared;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.Objects;

public class Price implements ValueObject<Price>, Comparable<Price> {

    private static final long serialVersionUID = -5843905896295865999L;

    @JsonValue
    private final int value;

    /**
     * Instantiates a new Id object.
     *
     * @param value the value
     */

    @JsonCreator
    public Price(int value) {
        if (value < 0) {
            throw DomainException.error(DomainExceptionCode.PRICE_SHOULD_BE_POSITIVE);
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    @Override
    public boolean sameValueAs(Price other) {
        return other != null && value == other.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Price price = (Price) o;
        return value == price.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int compareTo(@NotNull Price o) {
        return Comparator.comparing(Price::getValue)
                .compare(this, o);
    }

    public Price plus(Price addPrice) {
        return new Price(this.value + addPrice.getValue());
    }

    public Price minus(Price minusPrice) {
        int afterPrice = this.value - minusPrice.getValue();
        afterPrice = afterPrice <= 0 ? 0 : afterPrice;
        return new Price(afterPrice);
    }

    public boolean isPositive() {
        return value > 0;
    }
}
