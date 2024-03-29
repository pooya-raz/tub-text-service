package org.tub.tubtextservice.domain.persondate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tub.tubtextservice.domain.year.persondate.HijriDeath;

class HijriDeathTest {
    public static final String YEAR_687 = "687";
    public static final String GENERIC_TEXT = "xxxx";

    @Test
    @DisplayName("Canonical constructor should return standard result")
    void test1() {
        final var actual = new HijriDeath(YEAR_687, YEAR_687);
        assertThat(actual.year()).isEqualTo(YEAR_687);
        assertThat(actual.gregorian()).isEqualTo(YEAR_687);
    }

    @Test
    @DisplayName("Canonical constructor should strip non-numeric text")
    void test2() {
        final var actual = new HijriDeath(GENERIC_TEXT + YEAR_687, GENERIC_TEXT + YEAR_687);
        assertThat(actual.year()).isEqualTo(GENERIC_TEXT + YEAR_687);
        assertThat(actual.gregorian()).isEqualTo(YEAR_687);
    }
}
