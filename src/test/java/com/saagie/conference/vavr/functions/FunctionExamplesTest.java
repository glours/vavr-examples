package com.saagie.conference.vavr.functions;


import io.vavr.collection.List;
import io.vavr.control.Option;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctionExamplesTest {

    private FunctionExamples examples;

    @Before
    public void setUp() throws Exception {
        this.examples = new FunctionExamples();
    }

    @Test
    public void should_return_lastName_in_upper_case() throws Exception {
        assertThat(this.examples.userLastNameToUpperCase("userName7"))
                .isEqualTo("LASTNAME7");
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void should_throw_exception_with_sideEffetGetAddress() throws Exception {
        assertThat(this.examples.sideEffectGetAddress("JohnDoe"));

    }

    @Test
    public void should_not_throw_exception_with_safeGetAddressIsValid() throws Exception {
        assertThat(this.examples.safeGetAddress("JonhDoe"))
                .isEqualTo(Option.none());
    }

    @Test
    public void should_add_value_with_partial_application() throws Exception {
        assertThat(this.examples.partialApplication(1)).isEqualTo(7);
        assertThat(this.examples.partialApplication(5)).isEqualTo(11);

    }

    @Test
    public void should_add_value_with_currying() throws Exception {
        assertThat(this.examples.currying(1,2,3)).isEqualTo(11);
        assertThat(this.examples.currying(5, 5, 5)).isEqualTo(20);
    }

    @Test
    public void should_use_memoization_to_add() throws Exception {
        double firstCall  = this.examples.memoize();

        assertThat(List.range(0, 20)
                .map(val -> this.examples.memoize()))
                .allMatch(val -> val == firstCall);
    }
}