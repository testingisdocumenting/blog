import React, {useEffect, useState} from 'react';

import style from './AdminScreen.module.scss';
import {createWsClient} from './websocket';

export function AdminScreen() {
    const [message, setMessage] = useState('test');
    const [socketClient, setSocketClient] = useState('');

    useEffect(() => {
        const client = createWsClient();
        client.connect({}, () => {
            console.log('ws connected')

            setSocketClient(client);
        })
    }, [])

    return (
        <div className={style.admin}>
            <div className={style.label}>
                message to users
            </div>
            <input id="message" value={message} onChange={updateMessage}/>
            <button onClick={sendMessage} disabled={!socketClient}>send message</button>
        </div>
    );

    function updateMessage(e) {
        setMessage(e.target.value);
    }

    function sendMessage() {
        socketClient.send('/app/admin-message', {}, JSON.stringify({message: message}));
    }
}