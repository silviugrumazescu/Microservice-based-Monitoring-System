import axios from 'axios'
import authHeader from './authHeader';

const API_URL = process.env.VUE_APP_DEVICE_SERVICE_IP + "/devices/";

class DeviceService {

    getItem() {
        return axios
            .get(API_URL + 'item')
            .then(response => {
                console.log(response.data)
            })
    }

    getAllDevices() {
        return axios
            .get(API_URL + 'getAllDevices', {
                headers: authHeader()
            })
    }

    getUserDevices(userUUID) {
        return axios
            .get(process.env.VUE_APP_DEVICE_SERVICE_IP + '/user/' + 'getUserDevices', {
                params: {
                    uuid: userUUID
                },
                headers: authHeader()
            })
    }

    getAvailableDevices() {
        return axios
            .get(API_URL + 'getAvailableDevices', {
                headers: authHeader()
            })
    }

    createDevice(device) {
        return axios
            .post(API_URL + 'createDevice', {
                description: device.description,
                address : device.address,
                maxconsumption: device.maxconsumption
            }, {headers: authHeader()})
    }

    updateDevice(device) {
        return axios.post(API_URL + 'updateDevice', {
            uuid : device.uuid,
            description: device.description,
            address : device.address,
            maxconsumption: device.maxconsumption
        }, {headers: authHeader()})
    }

    linkDevice(userUUID, deviceUUID) {
        return axios    
            .post(API_URL + 'linkDeviceToUser', {
                userUUID : userUUID,
                deviceUUID: deviceUUID
            }, {headers: authHeader()})
    }

    deleteDevice(uuid) {
        return axios.post(API_URL + 'deleteDevice', uuid, {headers: authHeader()});
    }


}

export default new DeviceService();