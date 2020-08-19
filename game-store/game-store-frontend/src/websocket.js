import {Stomp} from '@stomp/stompjs';

export function createWsClient() {
  return Stomp.client('ws://localhost:8080/websocket');
}