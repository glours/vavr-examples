package com.saagie.conference.vavr.values;

import com.saagie.conference.vavr.domain.Address;
import com.saagie.conference.vavr.domain.User;
import javaslang.collection.List;
import javaslang.control.Either;
import javaslang.control.Option;
import javaslang.control.Validation;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class ValuesExamplesTest {

    private ValuesExamples examples;

    @Before
    public void setUp() throws Exception {
        this.examples = new ValuesExamples();
    }

    @Test
    public void should_return_empty_optional_jdk8() throws Exception {
        assertThat(this.examples.optionalOfUserAddress("JohnDoe"))
            .isEqualTo(Optional.empty());
    }

    @Test
    public void should_return_empty_optional_of_null_jdk8() throws Exception {
        assertThat(this.examples.optionalOfNullUsageForUserAddress("JohnDoe"))
                .isEqualTo(Optional.empty());
    }

    @Test
    public void should_return_non_empty_optional_jdk8() throws Exception {
        assertThat(this.examples.optionalOfUserAddress("userName1"))
                .isEqualTo(Optional.of(User.generateUser(1).getAddress()));
    }

    @Test
    public void should_return_empty_option_vavr() throws Exception {
        assertThat(this.examples.optionOfUserAddress("JohnDoe"))
                .isEqualTo(Option.none());
    }

    @Test
    public void should_return_non_empty_option_vavr() throws Exception {
        assertThat(this.examples.optionOfUserAddress("userName7"))
                .isEqualTo(Option.of(User.generateUser(7).getAddress()));
    }

    @Test
    public void should_return_a_success_try() throws Exception {
        assertThat(this.examples.tryOfUserAddress("userName7").get())
                .isEqualTo(Option.of(User.generateUser(7).getAddress()));

    }

    @Test
    public void should_return_a_failure_try() throws Exception {
        assertThat(this.examples.tryOfUserAddress("userName15").isFailure());

    }

    @Test
    public void should_return_a_right_either() throws Exception {
        assertThat(this.examples.eitherOfUser("userName7").get())
            .isEqualTo(User.generateUser(7));
    }

    @Test
    public void should_return_a_left_either() throws Exception {
        assertThat(this.examples.eitherOfUser("JohnDoe").getLeft())
                .isEqualTo("Not Found");
    }

    @Test
    public void should_validate_user() throws Exception {
        User validUser = new User("John2Doe", "John", "Doe", "john.doe@saagie.com",
                "Sup3rHyp3Pass1^",
                new Address(12, "street name", "76000", "Rouen", "France"));

        assertThat(this.examples.validateUser(Option.of(validUser)).isValid());
        assertThat(this.examples.validateUser(Option.of(validUser)).get()).isEqualTo(validUser);
    }

    @Test
    public void should_return_list_of_issues_when_validate_user() throws Exception {
        User invalidUser = new User("John2Doe!", "John", "Doe", "john.doe@.saagie.com",
                "badPass", null);

        Validation<List<String>, User>  validation = this.examples.validateUser(Option.of(invalidUser));
        assertThat(validation.isInvalid());
        assertThat(validation.getError())
                .contains("Invalid email", "Invalid password", "Empty address", "userName contains invalid characters");
    }

    @Test
    public void should_return_either_of_valid_and_invalid_addresses_with_pattern_matching() throws Exception {
        List<Either<Address, Address>> addresses = this.examples.patternMatchingList();
        assertThat(addresses.filter(Either::isRight).size())
                .isEqualTo(addresses.filter(Either::isLeft).size());

    }
}