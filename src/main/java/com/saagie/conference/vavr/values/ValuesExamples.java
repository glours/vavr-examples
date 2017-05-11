package com.saagie.conference.vavr.values;

import com.saagie.conference.vavr.domain.Address;
import com.saagie.conference.vavr.domain.User;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;

import java.util.Optional;

import static com.saagie.conference.vavr.domain.User.generateUserList;
import static io.vavr.API.*;
import static io.vavr.Patterns.$Invalid;
import static io.vavr.Patterns.$Valid;

public class ValuesExamples {

    Map<String, User> usersVavr;
    java.util.Map<String, User> usersJdk8;

    public ValuesExamples() {
        this.usersVavr = generateUserList().toMap(user -> new Tuple2<>(user.getUserName(), user));
        this.usersJdk8 = this.usersVavr.toJavaMap();
    }

    public Optional<Address>  optionalOfUserAddress(String userName) {
        if( !this.usersJdk8.containsKey(userName)) {
            return Optional.empty();
        }
        return Optional.of(this.usersJdk8.get(userName).getAddress());
    }

    public Optional<Address> optionalOfNullUsageForUserAddress(String userName) {
        return Optional.ofNullable(this.usersJdk8.get(userName)).map(User::getAddress);
    }

    public Option<Address> optionOfUserAddress(String userName) {
        return this.usersVavr.get(userName).map(User::getAddress);
    }

    public Try tryOfUserAddress(String userName) {
        return Try.of(() -> this.usersVavr.get(userName).get().getAddressIfValid());
    }

    public Either<String, User> eitherOfUser(String userName)  {
        return this.usersVavr.get(userName).toRight("Not Found");

    }

    public Validation<Seq<String>, User> validateUser(Option<User> user) {
        return user.map(User::validate).getOrElse(Validation.invalid(List.of("Invalid user")));
    }

    public List<Either<Address, Address>> patternMatchingList() {
        List<User> users = this.usersVavr.map(tuple -> tuple._2).toList();
        return users.map(user ->
                Match(validateUser(Option.of(user))).of(
                  Case($Valid($()), validUser -> Either.right(validUser.getAddress())),
                    Case($Invalid($()), errorList -> Either.left(user.getAddress()))
                )
        );
    }

}
