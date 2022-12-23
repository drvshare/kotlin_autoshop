package ru.drvshare.autoshop.common.exceptions

import ru.drvshare.autoshop.common.models.AsAdLock


class RepoConcurrencyException(expectedLock: AsAdLock, actualLock: AsAdLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
) {
    companion object {
        private const val serialVersionUID: Long = -2801118035896199431L
    }
}
