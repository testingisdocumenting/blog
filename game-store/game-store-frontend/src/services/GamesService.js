import {getJsonData} from '../fetchUtils';

export class GamesService {
    list() {
        return getJsonData("/api/game").then(json => {
            return json;
        });
    }
}
