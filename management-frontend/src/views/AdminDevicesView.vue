<template>
    <div class="align-items-center justify-content-center p-4">
        <div class="row flex-nowrap ">
            <div class="col-sm">
                <TabView>
                    <TabPanel header="Create device" headerStyle="{text-decoration:none}">
                        <div class="d-flex justify-content-center">
                            <div class="d-flex flex-column w-50">
                                <label for="description" class="mt-3">Description</label>
                                <InputText id="description" v-model="newDevice.description"
                                    aria-describedby="description-help" />
                                <label for="address" class="mt-3">Address</label>
                                <InputText id="address" v-model="newDevice.address" aria-describedby="address-help" />
                                <label for="maxconsumption" class="mt-3">Maximum energy consumption hourly</label>
                                <InputText id="maxconsumption" v-model="newDevice.maxconsumption"
                                    aria-describedby="maxconsumption-help" />
                                <Button label="Create device" class="mt-3" @click="handleCreate"></Button>
                                <p class="text-center mt-5">{{ errorMessageCreate }}</p>
                            </div>
                        </div>

                    </TabPanel>
                    <TabPanel header="Update/Delete device">
                        <div class="d-flex justify-content-center">
                            <div class="d-flex flex-column w-50">
                                <label for="uuid" class="mt-3">ID</label>
                                <InputText id="uuid" v-model="selectedDevice.uuid" aria-describedby="uuid-help" disabled />
                                <label for="description" class="mt-3">Description</label>
                                <InputText id="description" v-model="selectedDevice.description"
                                    aria-describedby="description-help" />
                                <label for="address" class="mt-3">Address</label>
                                <InputText id="address" v-model="selectedDevice.address" aria-describedby="address-help" />
                                <label for="maxconsumption" class="mt-3">Maximum energy consumption hourly</label>
                                <InputText id="maxconsumption" v-model="selectedDevice.maxconsumption"
                                    aria-describedby="maxconsumption-help" />
                                <div class="d-flex flex-row w-100">
                                    <Button label="Update device" class="mt-3 w-50" @click="handleUpdate" />
                                    <Button label="Delete device" class="mt-3 w-50" style="background-color:red"
                                        @click="handleDelete" />
                                </div>
                                <p class="text-center mt-5">{{ errorMessageUpdate }}</p>
                            </div>
                        </div>
                    </TabPanel>
                    <TabPanel header="Link device to user">
                        <div class="d-flex flex-row">
                            <div class="d-flex flex-column">
                                <p class="mt-3 mb-3 text-center fw-bold">Users</p>
                                <DataTable :value="users" selectionMode="single" v-model:selection="selectedUser" scrollable
                                    scrollHeight="500px">
                                    <Column field="uuid" header="ID"></Column>
                                    <Column field="name" header="Name"></Column>
                                    <Column field="username" header="Username"></Column>
                                    <Column field="email" header="Email"></Column>
                                </DataTable>
                            </div>
                            <div class="d-flex flex-column">
                                <p class="mt-3 mb-3 text-center fw-bold">Available devices</p>
                                <DataTable :value="availableDevices" selectionMode="single"
                                    v-model:selection="selectedAvailableDevice" scrollable scrollHeight="500px">
                                    <Column field="uuid" header="ID"></Column>
                                    <Column field="description" header="Description"></Column>
                                    <Column field="address" header="Address"></Column>
                                    <Column field="maxconsumption" header="Maximum hourly consumption"></Column>
                                </DataTable>
                            </div>
                        </div>
                        <div class="d-flex flex-row">
                            <Button label="Link" @click="handleLink" />
                        </div>
                    </TabPanel>
                </TabView>
            </div>
            <div class="col-sm">
                <p class="mt-3 mb-3 text-center fw-bold">Devices</p>
                <DataTable :value="devices" selectionMode="single" v-model:selection="selectedDevice" scrollable
                    scrollHeight="500px">
                    <Column field="uuid" header="ID"></Column>
                    <Column field="description" header="Description"></Column>
                    <Column field="address" header="Address"></Column>
                    <Column field="maxconsumption" header="Maximum hourly consumption"></Column>
                </DataTable>
            </div>
        </div>

    </div>
</template>

<script>

import User from '@/model/User';
import Device from '@/model/Device';
import UserService from '../service/userService'
import AuthService from '@/service/authService';
import DeviceService from '@/service/deviceService'

import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import TabView from 'primevue/tabview'
import TabPanel from 'primevue/tabpanel'
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'

export default {
    name: 'AdminView',
    components: {
        DataTable, Column, TabView, TabPanel, InputText, Button
    },
    data() {
        return {
            devices: [],
            availableDevices: [],
            selectedDevice: new Device('', '', '', ''),
            selectedAvailableDevice: new Device('', '', '', ''),
            newDevice: new Device('', '', '', ''),
            errorMessageCreate: '',
            errorMessageUpdate: '',
            newUser: '',
            users: [],
            selectedUser: new User('', '', '', '', '', ''),
        }
    },

    beforeMount() {
        this.getAllDevices();
        this.getAllUsers();
        this.getAvailableDevices();
    },

    methods: {
        handleCreate() {
            if (this.validateInputs()) {
                DeviceService.createDevice(this.newDevice).then(
                    response => {
                        this.errorMessageCreate = 'Device succesfully created!'
                        this.getAllDevices();
                        this.getAvailableDevices();
                    },
                    error => {
                        this.errorMessageCreate = 'Error while creating device.'
                    }
                )
            }

        },

        handleUpdate() {
            if (this.validateInputsUpdate()) {
                DeviceService.updateDevice(this.selectedDevice).then(
                    response => {
                        this.errorMessageUpdate = 'Succesfully updated device!';
                        this.getAllDevices();
                        this.getAvailableDevices();
                    },
                    error => {
                        this.errorMessageUpdate = 'Error while updating device';
                    }
                )
            }

        },

        handleDelete() {
            DeviceService.deleteDevice(this.selectedDevice.uuid).then(
                response => {
                    this.getAllDevices();
                    this.getAvailableDevices();
                    this.errorMessageUpdate = 'Succesfully deleted device';

                },
                error => {
                    this.errorMessageUpdate = 'Error while deleting device';
                }
            )
        },
        handleLink() {
            if(!this.selectedAvailableDevice.uuid.localeCompare('') && !this.selectedUser.uuid.localeCompare('')) {

            } else {
                DeviceService.linkDevice(this.selectedUser.uuid, this.selectedAvailableDevice.uuid).then(
                response => {
                    console.log(response.data);
                    this.getAvailableDevices();
                }
                )
            }

        },

        getAllDevices() {
            DeviceService.getAllDevices().then(
                devices => {
                    this.devices = devices.data;
                }
            )
            console.log(this.devices);
        },
        getAllUsers() {
            UserService.getAllUsers().then(
                users => {
                    this.users = users.data;
                }
            )
        },
        getAvailableDevices() {
            DeviceService.getAvailableDevices().then(
                devices => {
                    this.availableDevices = devices.data;
                }
            )
            console.log(this.devices);
        },
        validateInputs() {
            if (this.newDevice.description !== "" && this.newDevice.address !== "" && this.newDevice.maxconsumption !== "") {
                if (!isNaN(this.newDevice.maxconsumption)) {
                    return true;
                } else {
                    this.errorMessageCreate = 'Max consumption has to be a number'
                    return false;
                }
            } else {
                this.errorMessageCreate = 'Some fields are empty!'
                return false;
            }
        },
        validateInputsUpdate() {
            if (this.selectedDevice.description !== "" && this.selectedDevice.address !== "" && this.selectedDevice.maxconsumption !== "") {
                if (!isNaN(this.selectedDevice.maxconsumption)) {
                    return true;
                } else {
                    this.errorMessageUpdate = 'Max consumption has to be a number'
                    return false;
                }
            } else {
                this.errorMessageUpdate = 'Some fields are empty!'
                return false;
            }
        }
    }
}
</script>

<style>
a {
    text-decoration: none !important;
    color: black !important
}
</style>