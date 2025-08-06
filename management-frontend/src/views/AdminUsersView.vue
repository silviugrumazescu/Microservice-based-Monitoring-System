<template>
    <div class="align-items-center justify-content-center p-4">
        <div class="row flex-nowrap ">
            <div class="col-sm">
                <TabView>
                    <TabPanel header="Create user" headerStyle="{text-decoration:none}">
                        <div class = "d-flex justify-content-center">
                            <div class="d-flex flex-column w-50">
                                <label for="name" class="mt-3">Name</label>
                                <InputText id="name" v-model="newUser.name" aria-describedby="name-help"/>
                                <label for="username" class="mt-3">Username</label>
                                <InputText id="username" v-model="newUser.username" aria-describedby="username-help" />
                                <label for="email" class="mt-3">Email</label>
                                <InputText id="email" v-model="newUser.email" aria-describedby="email-help" />
                                <label for="password" class="mt-3">Password</label>
                                <InputText id="password" v-model="newUser.password" aria-describedby="password-help" />
                                <Button label="Create user" class="mt-3" @click="handleCreate"></Button>
                                <p class="text-center mt-5">{{ errorMessageCreate }}</p>
                            </div>
                        </div>

                    </TabPanel>
                    <TabPanel header="Update/Delete user">
                        <div class = "d-flex justify-content-center">
                            <div class="d-flex flex-column w-50">
                                <label for="uuid" class="mt-3">ID</label>
                                <InputText id="uuid" v-model="selectedUser.uuid" aria-describedby="name-help"  disabled/>
                                <label for="name" class="mt-3">Name</label>
                                <InputText id="name" v-model="selectedUser.name" aria-describedby="name-help" />
                                <label for="username" class="mt-3">Username</label>
                                <InputText id="username" v-model="selectedUser.username" aria-describedby="username-help" />
                                <label for="email" class="mt-3">Email</label>
                                <InputText id="email" v-model="selectedUser.email" aria-describedby="email-help" />
                                <div class="d-flex flex-row w-100">
                                    <Button label="Update user" class="mt-3 w-50" @click="handleUpdate"/>
                                    <Button label="Delete user" class="mt-3 w-50" style="background-color:red" @click="handleDelete"/>
                                </div>
                                <p class="text-center mt-5">{{ errorMessageUpdate }}</p>
                            </div>
                        </div>
                    </TabPanel>
                </TabView>
            </div>
            <div class="col-sm">
                <p class="mt-3 mb-3 text-center fw-bold">Users</p>
                <DataTable :value="users" selectionMode="single" v-model:selection="selectedUser" scrollable scrollHeight="500px">
                    <Column field="uuid" header="ID"></Column>
                    <Column field="name" header="Name"></Column>
                    <Column field="username" header="Username"></Column>
                    <Column field="email" header="Email"></Column>
                </DataTable>
            </div>
        </div>

    </div>
</template>

<script>

import User from '@/model/User';
import UserService from '../service/userService'
import AuthService from '@/service/authService';

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
            users: [],
            selectedUser: new User('','','','','',''),
            newUser: new User('','','','','',''),
            errorMessageCreate: '',
            errorMessageUpdate: ''
        }
    },

    beforeMount() {
        this.getAllUsers();
    },

    methods: {
        handleCreate() {
            if(this.validateInputs() && this.validateEmail()) {
                AuthService.register(this.newUser).then(
                response => {
                    this.errorMessageCreate = 'User succesfully created!'
                    this.getAllUsers();
                },
                error => {
                    this.errorMessageCreate = 'Error while creating user.'
                }
            )
            }
            
        },

        handleUpdate() {
            UserService.updateUser(this.selectedUser).then(
                response => {
                    this.errorMessageUpdate = 'Succesfully updated user!';
                    this.getAllUsers();
                },
                error => {
                    this.errorMessageUpdate = 'Error while updating user';
                }
            )
        },

        handleDelete() {
            UserService.deleteUser(this.selectedUser.uuid).then(
                response => {
                    this.getAllUsers();
                    this.errorMessageUpdate = 'Succesfully deleted user';

                },
                error => {
                    this.errorMessageUpdate = 'Error while deleting user';
                }
            )
        },

        getAllUsers() {
            UserService.getAllUsers().then(
            users => {
                this.users = users.data;
            }
        )
        console.log(this.users);
        },
        validateEmail() {
            if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(this.newUser.email)) {
                this.errorMessageCreate = 'Please enter a valid email address';
                return false;
            } else {
                return true;
            }
        },
        validateInputs() {
            console.log(this.user)
            if(this.newUser.name !== "" && this.newUser.email !== "" && this.newUser.username !== "" && this.newUser.password !== "") {
                return true;
            } else {
                this.errorMessageCreate = 'Some fields are empty!'
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