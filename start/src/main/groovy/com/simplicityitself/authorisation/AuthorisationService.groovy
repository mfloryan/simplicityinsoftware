package com.simplicityitself.authorisation


class AuthorisationService {

  boolean userOk() {
    return System.properties["user.name"] == "mfloryan"
  }

  void executeWhenAuthorisedToLand(Closure whenOK, Closure whenFailed) {
    if (userOk()) whenOK() else whenFailed();
  }

}
