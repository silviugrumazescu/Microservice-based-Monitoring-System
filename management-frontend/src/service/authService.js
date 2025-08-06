import axios from 'axios'

const API_URL = process.env.VUE_APP_USER_SERVICE_IP + '/auth/';

class AuthService {

    login(user) {
        return axios
            .post(API_URL + 'signin', {
                username: user.username,
                password: user.password
            })
            .then(response => {
                if(response.data) {
                    localStorage.setItem('user', JSON.stringify(response.data))
                }
                return response.data;
            })
    }

    register(user) {
        console.log("fasdf")
        return axios.post(API_URL + 'signup', {
            email: user.email,
            name: user.name,
            username: user.username,
            password: user.password,
        })
    }

    logout() {
        localStorage.removeItem('user');
    }
}

export default new AuthService();