package com.saagie.conference.vavr.collections;


import com.saagie.conference.vavr.domain.Address;
import com.saagie.conference.vavr.domain.User;
import io.vavr.Predicates;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Stream;
import io.vavr.control.Try;

import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.saagie.conference.vavr.domain.User.generateUser;
import static com.saagie.conference.vavr.domain.User.generateUserList;

public class CollectionExamples {

    List<User> usersVavr;
    java.util.List<User> usersJdk8;

    public CollectionExamples() {
        this.usersVavr = generateUserList();
        this.usersJdk8 = this.usersVavr.toJavaList();
    }

    public java.util.List<Integer> unmodifiableListJdk8() {
        java.util.List<Integer> jdkList = IntStream.range(0,20).boxed().collect(Collectors.toList());
        return Collections.unmodifiableList(jdkList);
    }

    public List<Integer> immutableVavrList() {
        return List.range(0,20);
    }

    public java.util.List<User> filterUserWithValidAddressJdk8() {
        return this.usersJdk8
                .stream()
                .filter(user -> {
                    try {
                        return user.isAddressValid();
                    } catch (IllegalArgumentException e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }


    public List<User> filterUserWithValidAddressVavr() {
        return this.usersVavr
                .filter(user -> Try.of(() -> user.isAddressValid()).getOrElse(false));
    }

    public java.util.List<User> filterUserWithPredicateJdk8(Predicate<User> predicate) {
        return this.usersJdk8.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public List<User> filterUserWithPredicateVavr(Predicate<User> predicate) {
        return this.usersVavr
                .filter(predicate);
    }

    public java.util.List<Address> filterInvalidAddressFromRouenJdk8() {
        return this.usersJdk8.stream()
                .filter(User::isInvalidAddressFromRouen)
                .map(User::getAddress)
                .collect(Collectors.toList());
    }

    public List<Address> filterInvalidAddressFromRouenVavr() {
        return this.usersVavr
                .filter(User::isInvalidAddressFromRouen)
                .map(User::getAddress);
    }

    public java.util.stream.Stream<String> mapUserToLowerCaseUserNameWithJdk8Steam() {
        java.util.stream.Stream<User> userNameStream = java.util.stream.Stream.of(generateUser(7), generateUser(15));
        userNameStream.map(user -> user.getUserName().toUpperCase());

        return userNameStream
                .map(user -> user.getUserName().toLowerCase());
    }

    public Stream<String> mapUserToLowerCaseUserNameWithVavrStream() {
        Stream<User> userNameStream = Stream.of(generateUser(7), generateUser(15));
        userNameStream.map(user -> user.getUserName().toUpperCase());

        return userNameStream
                .map(user -> user.getUserName().toLowerCase());
    }

    public java.util.Map<String, User> filterMapOfUserWithValidAddressJdk8() {
        java.util.Map<String, User> usersMap = this.usersToMapJdk8();
        return usersMap.entrySet().stream()
                .filter(entry -> {
                    try {
                        return entry.getValue().isAddressValid();
                    } catch (IllegalArgumentException e) {
                        return false;
                    }
                })
                .collect(Collectors.toMap((entry -> entry.getKey()), entry -> entry.getValue()));
    }


    public Map<String, User> filterMapOfUserWithValidAddressVavr() {
        Map<String, User> userMap = this.usersToMap();
        return userMap
                .filter(tuple -> Try.of(() -> tuple._2.isAddressValid()).getOrElse(false));
    }

    public java.util.Map<String, User> filterMapOfUserWithPredicateJdk8(Predicate<User> predicate) {
        java.util.Map<String, User> usersMap = this.usersToMapJdk8();
        return usersMap.entrySet().stream()
                .filter(entry -> predicate.test(entry.getValue()))
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
    }

    public Map<String, User> filterMapOfUserWithPredicateVavr(Predicate<User> predicate) {
        Map<String, User> userMap = this.usersToMap();
        return userMap.filter(tuple -> predicate.test(tuple._2));
    }


    private Map<String, User> usersToMap() {
        return this.usersVavr.toMap(user -> new Tuple2<String, User>(user.getUserName(), user));
    }

    private java.util.Map<String, User> usersToMapJdk8() {
        return this.usersToMap().toJavaMap();
    }
}
