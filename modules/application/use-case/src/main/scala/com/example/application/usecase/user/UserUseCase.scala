package com.example.application.usecase.user

import com.example.application.usecase.UseCaseError
import com.example.domain.user.User

trait UserUseCase {

  def create(name: String): Either[UseCaseError, User]
}
