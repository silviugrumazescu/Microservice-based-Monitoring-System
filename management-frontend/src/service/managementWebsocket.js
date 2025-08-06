import { Client, Message } from '@stomp/stompjs'

export const initWebsocket = (parametrii) => {
    const stompClient = new Client({
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

    stompClient.onStompError = function (frame) {
        // Will be invoked in case of error encountered at Broker
        // Bad login/passcode typically will cause an error
        // Complaint brokers will set `message` header with a brief message. Body may contain details.
        // Compliant brokers will terminate the connection after any error
        console.log('Broker reported error: ' + frame.headers['message']);
        console.log('Additional details: ' + frame.body);
    };
    stompClient.onConnect = function (frame) {
        console.log('connected');
        stompClient.subscribe('/user/topic/notifications', message => {
            console.log(message.body)
        })
        console.log('subscribed')
        stompClient.publish({ destination: '/app/subscribe', body: jwt })
    };
    stompClient.onStompError = function (frame) {
        // Will be invoked in case of error encountered at Broker
        // Bad login/passcode typically will cause an error
        // Complaint brokers will set `message` header with a brief message. Body may contain details.
        // Compliant brokers will terminate the connection after any error
        console.log('Broker reported error: ' + frame.headers['message']);
        console.log('Additional details: ' + frame.body);
    };

    return stompClient;
}



