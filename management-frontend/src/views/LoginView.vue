<template>
    <div class="d-flex justify-content-center mt-5">
        <Card style="width: 25em" class="p-4 m-5">
            <template #title>Login</template>
            <template #content>
                <span class="p-float-label mt-4 w-auto">
                    <InputText id="username" v-model="user.username" style="width: 100%" />
                    <label for="username">Username</label>
                </span>
                <span class="p-float-label mt-4">
                    <Password v-model="user.password" inputId="password" style="width: 100%" inputStyle="width: 100%"
                        :feedback="false" />
                    <label for="password">Password</label>
                </span>
                <Button type="button" label="Login" @click="handleLogin" style="width:100%" class="mt-4" />


            </template>
            <template #footer>
                <p class="text-center">{{ errorMessage }}</p>
            </template>
        </Card>

    </div>
</template>

<script>

import AuthService from '../service/authService.js'
import User from '../model/User.js'
import Card from 'primevue/card'
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'
import Password from 'primevue/password'

export default {
    name: 'LoginView',
    components: {
        Card,
        InputText,
        Button,
        Password
    },
    data() {
        return {
            user: new User('', '', '', '', ''),
            errorMessage: ''
        }
    },
    methods: {
        handleLogin() {
            if (this.validateInputs()) {
                this.$store.dispatch('login', this.user).then(
                    user => {
                        this.errorMessage = "Succesfully logged in!"
                        this.$router.push('/')
                    },
                    error => {
                        this.errorMessage = "Log in failed!"
                    }
                )
            }
        },
        validateInputs() {
            console.log(this.user)
            if (this.user.username !== "" && this.user.password !== "") {
                return true;
            } else {
                this.errorMessage = 'Some fields are empty!'
                return false;
            }
        }
    }
}
</script>