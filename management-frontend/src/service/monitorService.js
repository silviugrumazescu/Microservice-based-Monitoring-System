import axios from 'axios'
import authHeader from './authHeader';

const API_URL = process.env.VUE_APP_MONITOR_SERVICE_IP;

class MonitorService {

    getDataCurrentDay(deviceID, date) {
        return axios
            .post('http://localhost:8082' + '/getDeviceStatInDay', {
                deviceID: deviceID,
                date : date,
            }, {headers: authHeader()})
    }
}

export default new MonitorService();