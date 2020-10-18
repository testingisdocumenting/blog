import React, {useEffect, useState} from 'react';

import {GamesList} from './GamesList';
import {services} from './services/services';

import {useQuery} from 'react-query';
import {createWsClient} from './websocket';

import style from './GamesListScreen.module.scss'

export function GamesListScreen() {
  const gamesQuery = useQuery({
    queryKey: 'games',
    queryFn: services.games.list,
    config: {refetchInterval: 1000}
  });

  const [filterText, setFilterText] = useState('');
  const [below60, setBelow60] = useState(false);

  const [adminMessage, setAdminMessage] = useState('');

  useEffect(() => {
    const client = createWsClient();
    client.connect({}, () => {
      console.log('ws connected')

      client.subscribe('/visitors/message', (payload) => {
        const message = JSON.parse(payload.body).message;
        console.log(message);
        setAdminMessage(message);
      })
    })
  }, [])

  return (
    <div className={style.gamesListScreen}>
      <div className={style.header}>
        Game Store
      </div>

      <div className={style.filterArea}>
        <input id="filter" value={filterText} onChange={handleFilterChange} placeholder="filter game titles"/>
        <div className={style.checkBoxArea}>
          <input id="below60" type="checkbox" checked={below60} onChange={handleBelow60Change}/>
          <span>Below $60</span>
        </div>
      </div>

      <div className={style.gamesListArea}>
        {gamesQuery.data && <GamesList games={gamesQuery.data}
                                       filterText={filterText}
                                       below60={below60}/>}
      </div>

      {adminMessage && <div id="admin-message" className={style.adminMessage}>{adminMessage}</div> }
    </div>
  );

  function handleFilterChange(e) {
    setFilterText(e.currentTarget.value)
  }

  function handleBelow60Change(e) {
    setBelow60(e.currentTarget.checked)
  }
}