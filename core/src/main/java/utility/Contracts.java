package utility;


import lombok.ToString;
import org.jetbrains.annotations.Contract;

import java.util.function.Supplier;


@ToString
public final class Contracts
{
    private Contracts() {}


    /**
     * Ensures that the provided object is not null. If the object is null, a runtime exception provided by the exception supplier is thrown.
     *
     * @param obj               the object to check for nullity
     * @param exceptionSupplier the supplier of the runtime exception to be thrown if the object is null
     */
    @Contract("null, _ -> fail")
    public static void notNull(final Object obj, final Supplier<? extends RuntimeException> exceptionSupplier)
    {
        if (obj == null)
        {
            throw exceptionSupplier.get();
        }
    }
}
