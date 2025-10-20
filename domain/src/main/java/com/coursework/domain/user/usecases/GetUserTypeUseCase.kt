package com.coursework.domain.user.usecases

import com.coursework.domain.user.model.UserType

class GetUserTypeUseCase {

    suspend operator fun invoke(): UserType {
        // TODO: Not yet implemented
        return UserType.Teacher
    }
}