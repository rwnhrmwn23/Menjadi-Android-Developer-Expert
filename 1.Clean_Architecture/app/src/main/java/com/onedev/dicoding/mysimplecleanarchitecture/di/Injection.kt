package com.onedev.dicoding.mysimplecleanarchitecture.di

import com.onedev.dicoding.mysimplecleanarchitecture.data.IMessageDataSource
import com.onedev.dicoding.mysimplecleanarchitecture.data.MessageDataSource
import com.onedev.dicoding.mysimplecleanarchitecture.data.MessageRepository
import com.onedev.dicoding.mysimplecleanarchitecture.domain.IMessageRepository
import com.onedev.dicoding.mysimplecleanarchitecture.domain.MessageInteractor
import com.onedev.dicoding.mysimplecleanarchitecture.domain.MessageUseCase

object Injection {
    fun provideUseCase(): MessageUseCase {
        val messageRepository = provideRepository()
        return MessageInteractor(messageRepository)
    }

    private fun provideRepository(): IMessageRepository {
        val messageDataSource = provideDataSource()
        return MessageRepository(messageDataSource)
    }

    private fun provideDataSource(): IMessageDataSource {
        return MessageDataSource()
    }
}