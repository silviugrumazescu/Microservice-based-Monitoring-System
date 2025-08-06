<template>
    <Toolbar style="padding:0%">
        <template #end>
            <router-link class="nav-link" to="/">
                <Button label="Home" />
            </router-link>
            <router-link class="nav-link" to="/devices" v-if="isUser">
                <Button label="Devices"/>
            </router-link>
            <router-link class="nav-link" to="/userchat" v-if="isUser || isAdmin">
                <Button label="Chat"/>
            </router-link>
            <router-link class="nav-link" to="/admin/devices" v-if="isAdmin">
                <Button label="Manage Devices"/>
            </router-link>
            <router-link class="nav-link" to="/admin/users" v-if="isAdmin">
                <Button label="Manage Users" />
            </router-link>
            <router-link class="nav-link" to="/login" v-if="!loginStatus">
                <Button label="Sign in" />
            </router-link>
            <router-link class="nav-link" to="/register" v-if="!loginStatus">
                <Button label="Register" />
            </router-link>
            <router-link class="nav-link" to="/" v-if="loginStatus">
                <Button label="Logout" @click="handleLogout"/>
            </router-link>
            
        </template>
    </Toolbar>
</template>

<script>

import Toolbar from 'primevue/toolbar';
import Button from 'primevue/button';
import managementWebsocket from '@/service/managementWebsocket.js';
export default {
    name: 'Navbar',
    components: {
        Toolbar,
        Button
    },
    data() {
        return {
            stompClient: null
        }
    },
    computed: {
        loginStatus() {
            return this.$store.state.auth.status.loggedIn;
        },
        isAdmin() {
            if(this.loginStatus) {
                return this.$store.state.auth.user.role.localeCompare("ROLE_ADMIN") == 0;
            } else {
                return false;
            }
            
        },
        isUser() {
            if(this.loginStatus) {
                console.log(this.loginStatus)
                return this.$store.state.auth.user.role.localeCompare("ROLE_USER") == 0;
            } else {
                return false;
            }
        }
    },
    methods: {
        handleLogout() {
            this.$store.dispatch('logout');
            this.$router.push('/');
        },
    },
    created() {
        //this.stompClient = managementWebsocket.initSocket()
    }
}

</script>

<style></style>