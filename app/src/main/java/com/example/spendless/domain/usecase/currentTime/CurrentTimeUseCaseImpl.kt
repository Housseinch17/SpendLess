package com.example.spendless.domain.usecase.currentTime

import com.example.spendless.data.model.SessionExpiryDuration
import com.example.spendless.domain.repository.CurrentTimeRepository
import javax.inject.Inject

class CurrentTimeUseCaseImpl @Inject constructor(
    private val currentTimeRepository: CurrentTimeRepository
): CurrentTimeUseCase {
    override fun getCurrentTime(): Long {
        return currentTimeRepository.getCurrentTime()
    }

    override fun updateSessionExpiryDuration(sessionExpiryDuration: SessionExpiryDuration): Long {
        return currentTimeRepository.updateSessionExpiryDuration(sessionExpiryDuration = sessionExpiryDuration)
    }
}