<template>
    <div class="container d-flex align-items-center justify-content-center m-5">
        <div class="row border w-100 h-100">
            <div class="col d-flex">
                <ListBox v-model="selectedChat" :options="getChats" optionLabel="name" class="w-100 mt-3 mb-3 pe-3"
                    listStyle="height:650px" @update:model-value="handleChatChange">
                    <template #option="slotProps">
                        <div class="row">
                            <div class="col-md-auto">
                                <img :alt="slotProps.option.name"
                                    src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQeeUl9IZDN97pBQNgeunx6dD1df-4g7vkPFw&usqp=CAU"
                                    style="width: 70px" />
                            </div>
                            <div class="col">
                                <div>
                                    <p class="text-start fw-bold">{{ slotProps.option.name }}</p>
                                </div>
                                <div v-if="slotProps.option.typing">
                                    <p class="text-start fw-lighter">{{ "typing..."}}</p>
                                </div>
                                <div v-else-if="slotProps.option.messages[slotProps.option.messages.length-1] != null">
                                    <p class="text-start fw-lighter">{{ slotProps.option.messages[slotProps.option.messages.length-1].content }}</p>
                                </div>
                            </div>
                        </div>
                    </template>
                </ListBox>
            </div>
            <div class="col p-3">
                <div class="row overflow-auto" style="height: 650px">
                    <div class="vstack">
                        <li v-if="selectedChat != null" v-for="message in selectedChat.messages">
                            <div class="d-flex justify-content-start" v-if="message.received">
                                    <p style="background-color: lightblue; max-width:50%" class="p-2 rounded text-break">
                                    {{ message.content }}</p>
                            </div>
                            <div class="d-flex justify-content-end" v-else>
                                <div class="d-flex flex-column" style="max-width: 50%;">
                                    <p style="background-color:chartreuse; width:100%;" class="p-2 rounded text-break">
                                        {{ message.content }}</p>
                                    <p v-if="message.seen" class="fw-lighter fst-italic">Seen</p>
                                </div>
                            </div>
                        </li>
                    </div>
                </div>
                <div class="row" style="height:8%">
                    <div class="col-8 align-bottom">
                        <InputText class="w-100" v-model="sendMessageInput" @update:modelValue="handleInputChange" />
                    </div>
                    <div class="col-4 align-bottom">
                        <Button class="w-100" @click="sendMessage">Send</Button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

import Button from 'primevue/button'
import ListBox from 'primevue/listbox'
import InputText from 'primevue/inputtext'

export default {
    name: 'UserChatView',
    components: {
        Button, ListBox, InputText
    },
    beforeMount() {
    },
    created() {
        this.chats = this.$store.state.auth.chats;
    },
    data() {
        return {
            sendMessageInput: "",
            isTyping: false,
            typingTimeout: null,
            chats: null,
            selectedChat: {
                username: "",
                name: "",
                messages: [

                ]
            }
        }
    },
    computed: {
        getChats() {
            return this.$store.state.auth.chats;
        }
    },
    methods: {
        handleInputChange() {

            clearTimeout(this.typingTimeout);
            this.typingTimeout = setTimeout(this.changeNotTyping, 1000);

            if (this.isTyping == false) {
                this.isTyping = true;

                this.$store.state.auth.chatClient.publish({
                    destination: '/app/notifyAnotherUser', body: JSON.stringify({
                        message: "TYPING",
                        senderID: this.$store.state.auth.user.jwt,
                        destinationID: this.selectedChat.username
                    })
                });
            }
        },
        changeNotTyping() {
            this.isTyping = false;

            this.$store.state.auth.chatClient.publish({
                destination: '/app/notifyAnotherUser', body: JSON.stringify({
                    message: "NOT_TYPING",
                    senderID: this.$store.state.auth.user.jwt,
                    destinationID: this.selectedChat.username
                })
            });

        },
        handleChatChange() {
            if(this.selectedChat == null) return;
            if(this.selectedChat.newMessages) {
                this.$store.state.auth.chatClient.publish({
                    destination: '/app/notifyAnotherUser', body: JSON.stringify({
                        message: "SEEN",
                        senderID: this.$store.state.auth.user.jwt,
                        destinationID: this.selectedChat.username
                    })
                });
                console.log("sent notif")
                this.selectedChat.newMessages = false;
            }

        },
        sendMessage() {
            console.log("sent")
            console.log(this.$store.state.auth.chatClient)

            this.$store.state.auth.chatClient.publish({
                destination: '/app/sendmessage', body: JSON.stringify({
                    message: this.sendMessageInput,
                    senderID: this.$store.state.auth.user.jwt,
                    destinationID: this.selectedChat.username
                })
            });

            for (let i = 0; i < this.$store.state.auth.chats.length; i++) {
                if (this.$store.state.auth.chats[i].username.localeCompare(this.selectedChat.username) == 0) {
                    this.$store.state.auth.chats[i].messages.push({
                        received: false,
                        content: this.sendMessageInput
                    })
                }
            }
        }

    }
}

</script>

<style> li {
     list-style: none;
 }
</style>