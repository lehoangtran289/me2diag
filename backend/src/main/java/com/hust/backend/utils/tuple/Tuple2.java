package com.hust.backend.utils.tuple;

import com.hust.backend.utils.assertion.Assertions;
import lombok.*;

@SuppressWarnings({"java:S1301", "java:S131", "java:S119"})
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class Tuple2<A0, A1> implements Tuple {
    private A0 a0;
    private A1 a1;

    @Override
    public Object get(int index) {
        Assertions.inRangeChecks(index, 0, 2);
        switch (index) {
            case 0:
                return a0;
            case 1:
                return a1;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static <AS0, AS1> Tuple2<AS0, AS1> of(AS0 arg0, AS1 arg1) {
        return new Tuple2<>(arg0, arg1);
    }
}
