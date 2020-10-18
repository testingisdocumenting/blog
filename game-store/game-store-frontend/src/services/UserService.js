import {getJsonData, putJsonData} from '../fetchUtils';

export class UserService {
  loadPreferences() {
    return getJsonData("/api/user-preferences").then(json => {
      console.log(json);
      return json;
    });
  }

  savePreferences(preferences) {
    return putJsonData("/api/user-preferences", preferences);
  }
}
