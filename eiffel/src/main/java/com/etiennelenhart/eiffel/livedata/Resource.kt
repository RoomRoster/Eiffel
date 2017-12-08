package com.etiennelenhart.eiffel.livedata

import com.etiennelenhart.eiffel.ErrorType
import com.etiennelenhart.eiffel.Status

/**
 * Wrapper class to associate a status to a LiveData value.
 *
 * @property[status] The LiveData value's current status.
 * @property[value] The LiveData's actual value.
 */
sealed class Resource<out T>(val status: Status, val value: T) {
    /**
     * Resource variant signaling a successful LiveData operation.
     *
     * @param[value] The actual value.
     */
    class Success<out T>(value: T) : Resource<T>(Status.SUCCESS, value)

    /**
     * Resource variant signaling a pending LiveData operation.
     *
     * @param[value] The actual value.
     */
    class Pending<out T>(value: T) : Resource<T>(Status.PENDING, value)

    /**
     * Resource variant signaling a failed LiveData operation.
     *
     * @param[value] The actual value.
     * @param[type] Optional [ErrorType]. Defaults to [ErrorType.Unspecified].
     * @property[type] Optional [ErrorType]. Defaults to [ErrorType.Unspecified].
     */
    class Error<out T>(value: T, val type: ErrorType = ErrorType.Unspecified) : Resource<T>(Status.ERROR, value)
}
