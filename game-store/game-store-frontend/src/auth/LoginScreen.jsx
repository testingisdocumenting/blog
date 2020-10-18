import React, {useState} from 'react';

import {storeAuthToken} from './authTokens';

import {useHistory, useLocation} from 'react-router-dom';

import style from './LoginScreen.module.scss'

export function LoginScreen() {
  const location = useLocation()
  const history = useHistory()

  const [name, setName] = useState('');
  const [password, setPassword] = useState('');

  return (
    <div className={style.screen}>
      <div className={style.form}>
        <div>name</div>
        <input id="name" type="text" value={name} onChange={updateName}/>

        <div>password</div>
        <input id="password" type="text" value={password} onChange={updatePassword}/>
      </div>
      <button id="login"
              className={style.loginButton}
              disabled={!name}
              onClick={login}>
        Login
      </button>
    </div>
  )

  function updateName(e) {
    setName(e.target.value)
  }

  function updatePassword(e) {
    setPassword(e.target.value)
  }

  // this is for demo purposes only
  // DO NOT use this in production
  function login() {
    storeAuthToken(name, password)

    if (location.state && location.state.from) {
      history.push(location.state.from)
    } else {
      history.push("/")
    }
  }
}