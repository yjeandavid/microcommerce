import { InjectableRxStompConfig } from '@stomp/ng2-stompjs';

export const myStompConfig : InjectableRxStompConfig = {
    brokerURL: 'ws://localhost:15674/ws',
    connectHeaders: {
        login: 'guest',
        passcode: 'guest'
    },
    reconnectDelay: 200
}