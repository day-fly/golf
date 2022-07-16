package com.druwa.common.api.base;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public class ApiUtils {

    //================================================================================
    // Find All - Generic
    //================================================================================
    public interface FindAllGenericInterface<T> {
        Iterable<T> operation();
    }

    public static ResponseEntity<Iterable<?>> findAllGeneric(
            FindAllGenericInterface findAllGenericInterface, String successMessage, String failMessage
    ) {
        try {
            return BaseResponseBody.create(
                    200,
                    successMessage,
                    findAllGenericInterface.operation()
            );
        } catch (Exception ex) {
            return BaseResponseBody.create(
                    200,
                    failMessage + "\n" + ex.getMessage(),
                    new Object()
            );
        }
    }

    public interface FindAllPagedGenericInterface<T> {
        Page<T> operation();
    }

    public static ResponseEntity<Iterable<?>> findAllGenericPaged(
            FindAllPagedGenericInterface findAllPagedGenericInterface,
            String successMessage,
            String failMessage
    ) {
        try {
            return BaseResponseBody.create(
                    200,
                    successMessage,
                    findAllPagedGenericInterface.operation()
            );
        } catch (Exception ex) {
            return BaseResponseBody.create(
                    200,
                    failMessage + "\n" + ex.getMessage(),
                    new Object()
            );
        }
    }
}
