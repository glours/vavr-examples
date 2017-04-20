package com.saagie.conference.vavr.collections;


import javaslang.collection.List;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectionExamplesTest {

    private CollectionExamples examples;

    @Before
    public void setUp() throws Exception {
        this.examples = new CollectionExamples();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void should_verify_behaviour_of_jdk_8_unmodifiable_list() throws Exception {
        java.util.List<Integer> unmodifiableList = this.examples.unmodifiableListJdk8();
        unmodifiableList.add(21);
        assertThat(unmodifiableList)
                .hasSize(21);

    }

    @Test
    public void should_verify_behaviour_of_vavr_list() throws Exception {
        List<Integer> vaverlist = this.examples.immutableVavrList();

        assertThat(vaverlist.push(21,22,23))
                .hasSize(23);
        assertThat(vaverlist.pop())
                .hasSize(19)
                .doesNotContain(0);

        assertThat(vaverlist)
                .contains(0)
                .hasSize(20);
    }

    @Test
    public void should_filter_user_with_valid_address_jdk8() throws Exception {
        assertThat(this.examples.filterUserWithValidAddressJdk8())
                .hasSize(10);
    }

    @Test
    public void should_filter_user_with_valid_address_vavr() throws Exception {
        assertThat(this.examples.filterUserWithValidAddressVavr())
                .hasSize(10);
    }

    @Test
    public void should_filter_address_invalid_from_Rouen_jdk8() throws Exception {
        assertThat(this.examples.filterInvalidAddressFromRouenJdk8())
                .hasSize(5)
                .extracting("city")
                .doesNotContain("Paris");
    }

    @Test
    public void should_filter_address_invalid_from_Rouen_vavr() throws Exception {
        assertThat(this.examples.filterInvalidAddressFromRouenVavr())
                .hasSize(5)
                .extracting("city")
                .doesNotContain("Paris");
    }

    @Test(expected = IllegalStateException.class)
    public void should_test_behaviour_of_Stream_jdk8() throws Exception {
        assertThat(this.examples.mapUserToLowerCaseUserNameWithJdk8Steam().collect(Collectors.toList()))
                .hasSize(2)
                .contains("username7", "username15");
    }

    @Test
    public void should_test_behaviour_of_Stream_vavr() throws Exception {
        assertThat(this.examples.mapUserToLowerCaseUserNameWithVavrStream().toList())
                .hasSize(2)
                .contains("username7", "username15");
    }

    @Test
    public void should_filter_map_of_users_jdk8() throws Exception {
        assertThat(this.examples.filterMapOfUserWithValidAddressJdk8())
                .hasSize(10)
                .containsKeys("userName2", "userName8", "userName9");
    }

    @Test
    public void should_filter_map_of_users_vavr() throws Exception {
        assertThat(this.examples.filterMapOfUserWithValidAddressVavr().toJavaMap())
                .hasSize(10)
                .containsKeys("userName2", "userName8", "userName9");
    }
}