<template>
    <div class="d-flex justify-content-center mt-5">
        <Card style="width: 25em" class="p-4 m-5">
            <template #title> Register </template>
            <template #content>

                <span class="p-float-label">
                    <InputText id="name" v-model="user.name" style="width: 100%" />
                    <label for="name">Name</label>
                </span>


                <span class="p-float-label mt-4 w-auto">
                    <InputText id="username" v-model="user.username" style="width: 100%" />
                    <label for="username">Username</label>
                </span>


                <span class="p-float-label mt-4">
                    <InputText id="email" v-model="user.email" style="width: 100%" />
                    <label for="email">Email</label>
                </span>


                <span class="p-float-label mt-4">
                    <Password v-model="user.password" inputId="password" style="width: 100%" inputStyle="width: 100%" />
                    <label for="password">Password</label>
                </span>
                <Button type="button" label="Register" @click="handleRegister" style="width:100%" class="mt-4" />
                
            </template>
            <template #footer>
                <p class="text-center">{{ errorMessage }}</p>
            </template>
        </Card>
    </div>
</template>

<script>
import User from '@/model/User';
import AuthService from '../service/authService.js'
import Card from 'primevue/card'
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'
import Password from 'primevue/password'


export default {
    name: 'RegisterView',
    components: {
        Card,
        InputText,
        Button,
        Password
    },
    data() {
        return {
            user: new User('', '', '', '', ''),
            errorMessage: '',
        }
    },
    methods: {
        handleRegister() {
            if (this.validateInputs() && this.validateEmail()) {
                this.$store.dispatch('register', this.user).then(
                    user => {
                        console.log('user: ' + user);
                        this.user = new User('', '', '', '', '');
                        this.errorMessage = 'Succesfully registered!';
                    },
                    error => {
                        this.errorMessage = 'Failed to register';
                    }
                )
            }
        },
        validateEmail() {
            if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(this.user.email)) {
                this.errorMessage = 'Please enter a valid email address';
                return false;
            } else {
                return true;
            }
        },
        validateInputs() {
            console.log(this.user)
            if(this.user.name !== "" && this.user.email !== "" && this.user.username !== "" && this.user.password !== "") {
                return true;
            } else {
                this.errorMessage = 'Some fields are empty!'
                return false;
            }
        }
    }
}
</script>