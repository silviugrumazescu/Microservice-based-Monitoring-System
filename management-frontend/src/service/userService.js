import axios from 'axios'
import authHeader from './authHeader';
import { auth } from '@/store/modules/auth';

const API_URL =  process.env.VUE_APP_USER_SERVICE_IP + '/admin/'

class UserService {

    getAllUsers() {
        return axios
            .get(API_URL + 'getAllUsers', {headers: authHeader()});
    }

    updateUser(user) {
        return axios.post(API_URL + 'updateUser', {
            uuid: user.uuid,
            name: user.name,
            email: user.email,
            username: user.username
        }, {headers: authHeader()})
    }

    deleteUser(uuid) {
        return axios.post(API_URL + 'deleteUser', uuid, {headers: authHeader()})
    }

    
}

export default new UserService();