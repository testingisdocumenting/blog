import {GamesService} from './GamesService';
import {UserService} from './UserService';

export const services = {
    games: new GamesService(),
    user: new UserService()
}
