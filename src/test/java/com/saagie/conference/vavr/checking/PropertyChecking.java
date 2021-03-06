package com.saagie.conference.vavr.checking;


import io.vavr.CheckedFunction1;
import io.vavr.Function1;
import io.vavr.Tuple;
import io.vavr.test.Arbitrary;
import io.vavr.test.Gen;
import io.vavr.test.Property;
import org.junit.Test;

public class PropertyChecking {

    @Test
    public void should_l33t_string() throws Exception {
        Arbitrary<String> leetCharEto3 = Arbitrary.string(Gen.frequency(
                Tuple.of(1, Gen.choose('A','Z')),
                Tuple.of(1, Gen.choose('a','z'))
        ))
        .filter(s -> s.length() > 10)
        .filter(s -> s.matches("\\w*[eE]+\\w*"));

        Function1<String, String> transformETo3 = s -> s.replaceAll("[eE]", "3");

        CheckedFunction1<String, Boolean> checkTransformETo3 = s -> transformETo3.apply(s).matches("\\w*[^eE]+\\w*") && transformETo3.apply(s).contains("3");

        Property.def("Each e character must be replace by a 3")
                .forAll(leetCharEto3)
                .suchThat(checkTransformETo3)
                .check()
                .assertIsSatisfied();

    }
}
