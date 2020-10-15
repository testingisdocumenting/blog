import {getJsonData} from '../fetchUtils';

export class GamesService {
    list() {
        return getJsonData("/api/game").then(json => {
            console.log(json._embedded.games);
            return json._embedded.games;
        });
    }
}
