package com.onedev.dicoding.mysimplecleanarchitecture.data

import com.onedev.dicoding.mysimplecleanarchitecture.domain.MessageEntity

interface IMessageDataSource {
    fun getMessageFromSource(name: String): MessageEntity
}