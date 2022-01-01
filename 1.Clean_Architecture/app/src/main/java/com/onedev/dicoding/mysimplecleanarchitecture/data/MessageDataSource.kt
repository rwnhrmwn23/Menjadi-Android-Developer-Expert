package com.onedev.dicoding.mysimplecleanarchitecture.data

import com.onedev.dicoding.mysimplecleanarchitecture.domain.MessageEntity

class MessageDataSource: IMessageDataSource {
    override fun getMessageFromSource(name: String) = MessageEntity("Hello $name! Welcome to Clean Architecture")
}