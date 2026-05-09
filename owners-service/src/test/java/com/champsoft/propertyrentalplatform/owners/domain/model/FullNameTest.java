package com.champsoft.propertyrentalplatform.owners.domain.model;

import com.champsoft.propertyrentalplatform.owners.domain.exception.InvalidOwnerNameException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FullNameTest {

    @Test
    void shouldCreateValidFullName() {

        FullName fullName = new FullName("John Smith");

        assertThat(fullName.value()).isEqualTo("John Smith");
    }

    @Test
    void shouldTrimFullName() {

        FullName fullName = new FullName("  John Smith  ");

        assertThat(fullName.value()).isEqualTo("John Smith");
    }

    @Test
    void shouldThrowExceptionWhenFullNameIsNull() {

        assertThrows(InvalidOwnerNameException.class, () -> new FullName(null));
    }

    @Test
    void shouldThrowExceptionWhenFullNameIsBlank() {

        assertThrows(InvalidOwnerNameException.class, () -> new FullName("   "));
    }

    @Test
    void shouldThrowExceptionWhenFullNameIsTooShort() {

        assertThrows(InvalidOwnerNameException.class, () -> new FullName("A"));
    }

    @Test
    void shouldThrowExceptionWhenFullNameIsTooLong() {

        String longName = "A".repeat(121);

        assertThrows(InvalidOwnerNameException.class, () -> new FullName(longName));
    }

    @Test
    void shouldCompareFullNamesCorrectly() {

        FullName name1 = new FullName("John Smith");
        FullName name2 = new FullName("John Smith");

        FullName name3 = new FullName("Jane Smith");

        assertThat(name1).isEqualTo(name2);

        assertThat(name1).isNotEqualTo(name3);

        assertThat(name1.hashCode()).isEqualTo(name2.hashCode());

        assertThat(name1.toString()).isEqualTo("John Smith");
    }
}