

import AuthService from '../../service/authService.js'
import { Client, Message } from '@stomp/stompjs'
import { notify } from "@kyvg/vue3-notification";

const user = JSON.parse(localStorage.getItem('user'))

const initialState = user
  ? { status: { loggedIn: true }, user, stompClient: null, chats: [], chatClient: null }
  : { status: { loggedIn: false }, user: null, stompClient: null, chats: [], chatClient: null };

export const auth = {
  state: initialState,
  actions: {
    login({ commit }, user) {
      return AuthService.login(user).then(
        user => {
          try {
            commit('loginSuccess', user);
            commit('initWebsocket', user.jwt);
            commit('initChatWebsocket', user.jwt);
          } catch (error) {
            console.log(error)
          }
          return Promise.resolve(user);
        },
        error => {
          commit('loginFailure');
          return Promise.reject(error);
        }
      )
    },
    logout({ commit }) {
      AuthService.logout();
      commit('disconnectWebsocket');
      commit('disconnectChatWebsocket');
      commit('logout');

    },
    register({ commit }, user) {
      return AuthService.register(user).then(
        response => {
          commit('registerSuccess');
          return Promise.resolve(response.data);
        },
        error => {
          commit('registerFailure');
          return Promise.reject(error);
        }
      );
    }
  },
  mutations: {
    loginSuccess(state, user) {
      console.log("state changed to true");
      state.status.loggedIn = true;
      state.user = user;
      notify({
        title: "Authorization",
        text: "You have been logged in!",
        type: "warn"
      });
    },
    loginFailure(state) {
      console.log("state changed to false");
      state.status.loggedIn = false;
      state.user = null;
    },
    logout(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    registerSuccess(state) {
      state.status.loggedIn = false;
    },
    registerFailure(state) {
      state.status.loggedIn = false;
    },
    initWebsocket(state, jwt) {
      state.stompClient = new Client({
        brokerURL: 'ws://localhost:8082/monitor-websocket',
        connectHeaders: {
          login: 'user',
          passcode: 'password',
        },
        debug: function (str) {
          console.log(str);
        },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
      });

      state.stompClient.onStompError = function (frame) {
        console.log('Broker reported error: ' + frame.headers['message']);
        console.log('Additional details: ' + frame.body);
      };
      state.stompClient.onConnect = function (frame) {
        state.stompClient.subscribe('/user/topic/notifications', message => {
          let notification = JSON.parse(message.body);
          notify({
            title: "WARNING",
            text: notification.content,
            type: "warn"
          });
        })

        state.stompClient.publish({ destination: '/app/subscribe', body: jwt })
      };
      state.stompClient.activate();

    },
    initChatWebsocket(state, jwt) {
      state.chatClient = new Client({
        brokerURL: 'ws://localhost:8083/chat-websocket',
        connectHeaders: {
          login: 'user',
          passcode: 'password',
        },
        debug: function (str) {
          console.log(str);
        },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
      });

      state.chatClient.onStompError = function (frame) {
        console.log('Broker reported error: ' + frame.headers['message']);
        console.log('Additional details: ' + frame.body);
      };
      state.chatClient.onConnect = function (frame) {
        state.chatClient.subscribe('/user/topic/notifications', message => {
          let notification = JSON.parse(message.body);
          if (notification.notificationType === "TYPING") {
            let id = state.chats.findIndex((obj) => { return obj.username.localeCompare(notification.senderID) == 0 });
            state.chats[id].typing = true;
          } else if (notification.notificationType === "NOT_TYPING") {
            let id = state.chats.findIndex(obj => { return obj.username.localeCompare(notification.senderID) == 0 });
            state.chats[id].typing = false;

          } else if (notification.notificationType === "SEEN") {
            let id = state.chats.findIndex(obj => { return obj.username.localeCompare(notification.senderID) == 0 });
            for (let i = 0; i < state.chats[id].messages.length; i++) {
              state.chats[id].messages[i].seen = true;
            }
          }
          else {
            notify({
              title: "WARNING",
              text: notification.content,
              type: "warn"
            });
          }
        })

        state.chatClient.subscribe('/user/topic/chat', message => {
          let chatMessage = JSON.parse(message.body)
          let found = false;
          for (let i = 0; i < state.chats.length; i++) {
            if (state.chats[i].username.localeCompare(chatMessage.senderID) == 0) {
              found = true;
              state.chats[i].messages.push({
                received: true,
                seen: false,
                content: chatMessage.message
              })
              state.chats[i].newMessages = true;
            }
          }
          if (found == false) {
            state.chats.push({
              username: chatMessage.senderID,
              name: chatMessage.senderID,
              typing: false,
              newMessages: true,
              messages: [
                {
                  received: true,
                  seen: false,
                  content: chatMessage.message
                }
              ]
            })
          }
          notify({
            title: "CHAT",
            text: message.body,
            type: "warn"
          });
        })
        state.chatClient.publish({ destination: '/app/subscribe', body: jwt })
      };
      state.chatClient.activate();
      if (state.user.username.localeCompare("admin") != 0) {
        state.chats.push({
          username: "admin",
          name: "Domnul Adminescu",
          typing: false,
          messages: [
          ]
        })
      }
    },
    disconnectWebsocket(state) {
      if (state.stompClient != null) {
        state.stompClient.publish({ destination: '/app/unsubscribe' })
        state.stompClient.deactivate();
      }
      state.stompClient = null;
    },
    disconnectChatWebsocket(state) {
      if (state.chatClient != null) {
        state.chatClient.publish({ destination: '/app/unsubscribe' })
        state.chatClient.deactivate();
      }
      state.chatClient = null;
      state.chats = [];
    }
  },
}
