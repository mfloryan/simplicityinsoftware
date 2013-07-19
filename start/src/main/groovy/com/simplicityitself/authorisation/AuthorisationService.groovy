package com.simplicityitself.authorisation

import com.simplicityitself.commands.Command


class AuthorisationService {

  boolean userOk() {
    return System.properties["user.name"] == "mfloryan"
  }

  def boolean isAuthorised(Command command) {
    userOk()
  }

}
