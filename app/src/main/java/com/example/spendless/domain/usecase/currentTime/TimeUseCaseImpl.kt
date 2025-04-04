package com.example.spendless.domain.usecase.currentTime

import com.example.spendless.data.model.SessionExpiryDuration
import com.example.spendless.domain.repository.TimeRepository
import javax.inject.Inject

class TimeUseCaseImpl @Inject constructor(
    private val timeRepository: TimeRepository
): TimeUseCase {
    override fun getCurrentTime(): Long {
        return timeRepository.getCurrentTime()
    }

    override fun updateSessionExpiryDuration(sessionExpiryDuration: SessionExpiryDuration): Long {
        return timeRepository.updateSessionExpiryDuration(sessionExpiryDuration = sessionExpiryDuration)
    }
}