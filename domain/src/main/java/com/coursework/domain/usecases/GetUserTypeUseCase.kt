package com.coursework.domain.usecases

import com.coursework.domain.model.UserType

class GetUserTypeUseCase {

    suspend operator fun invoke(): UserType {
        // TODO: Not yet implemented
        return UserType.Teacher
    }
}