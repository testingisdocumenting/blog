import React, {useEffect, useState} from 'react';

import style from './UserPreferencesScreen.module.scss'
import {services} from '../services/services';

export function UserPreferencesScreen() {
  const [genre, setGenre] = useState('');

  const [isLoading, setIsLoading] = useState(true);
  const [loadingError, setLoadingError] = useState('');

  const [isSaving, setIsSaving] = useState(false);
  const [saveResult, setSaveResult] = useState('');

  const [userPreferences, setUserPreferences] = useState({});

  useEffect(() => {
    services.user.loadPreferences().then(pref => {
      handleUserPreferences(pref);
      setIsLoading(false);
    }).catch(error => {
      setLoadingError("can't load use preferences: " + error.message);
      setIsLoading(false);
    })
  }, [])

  return (
    <div className={style.screen}>
      <div className={style.card}>
        {isLoading && <div className={style.loading}>Loading ...</div>}
        {loadingError && <div className={style.loadingError}>{loadingError}</div>}

        {!isLoading && (
          <>
            <div className={style.form}>
              <div>user id</div>
              <div id="user-id">{userPreferences.userId}</div>
              <div>favorite genre</div>
              <input id="favorite-genre" type="text" value={genre} onChange={updateGenre}/>
            </div>

            <button id="save"
                    className={style.saveButton}
                    onClick={savePreferences}>
              Save
            </button>
          </>
        )}

        {isSaving && <div className={style.saving}>Saving ...</div>}
        {saveResult && <div id="save-result" className={style.saveResult}>{saveResult}</div>}
      </div>
    </div>
  )

  function handleUserPreferences(pref) {
    setUserPreferences(pref);
    setGenre(pref.favoriteGenre);
  }

  function updateGenre(e) {
    setGenre(e.target.value)
  }

  function savePreferences() {
    setIsSaving(true);
    services.user.savePreferences({favoriteGenre: genre}).then(() => {
      setIsSaving(false)
      setSaveResult('Saved')
    }).catch(() => {
      setIsSaving(false)
      setSaveResult('Cannot Save Preferences')
    })
  }
}