package com.saagie.conference.vavr.functions;


import com.saagie.conference.vavr.domain.Address;
import com.saagie.conference.vavr.domain.User;
import io.vavr.Function0;
import io.vavr.Function1;
import io.vavr.Function4;
import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.control.Option;

import static com.saagie.conference.vavr.domain.User.generateUserList;

public class FunctionExamples {

    Map<String, User> usersVavr;

    public FunctionExamples() {
        this.usersVavr = generateUserList().toMap(user -> new Tuple2<>(user.getUserName(), user));
    }


    private Function1<User, String> lastName = User::getLastName;
    private Function1<String, String> toUpperCase = String::toUpperCase;
    private Function1<User, String> lastNameInUpperCase = lastName.andThen(toUpperCase);

    private Function1<Option<User>, Address> getAddressWithSideEffect = user -> user.get().getAddress();
    private Function1<Option<User>, Option<Address>> safeGetAddress = Function1.lift(getAddressWithSideEffect);

    private Function4<Integer, Integer, Integer, Integer, Integer> sum = (a, b, c, d ) -> a + b + c + d;
    private Function1<Integer, Integer> partialApplicationFunc = sum.apply(1,2,3);
    private Function1<Integer, Function1<Integer, Function1<Integer, Integer>>> add3ValuesWith5 = sum.curried().apply(5);

    private Function0<Double> memoizedRandom = Function0.of(Math::random).memoized();

    public String userLastNameToUpperCase(String userName) {
        return usersVavr.get(userName)
                .map(lastNameInUpperCase)
                .getOrElse("User Not Found");
    }

    public Address sideEffectGetAddress(String userName) {
        return getAddressWithSideEffect.apply(usersVavr.get(userName));
    }

    public Option<Address> safeGetAddress(String userName) {
        return safeGetAddress.apply(this.usersVavr.get(userName));
    }

    public int partialApplication(int val) {
        return partialApplicationFunc.apply(val);
    }

    public int currying(int val1, int val2, int val3) {
        return add3ValuesWith5.apply(val1).apply(val2).apply(val3);
    }

    public double memoize() { return memoizedRandom.apply(); }
}
