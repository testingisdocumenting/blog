import React, {useState} from 'react';

import style from './GamesListScreen.module.scss'
import {GamesList} from './GamesList';
import {services} from './services/services';

import {useQuery} from 'react-query';

export function GamesListScreen() {
    const gamesQuery = useQuery({
        queryKey: 'games',
        queryFn: services.games.list,
        config: {refetchInterval: 500}});

    const [filterText, setFilterText] = useState('');
    const [below60, setBelow60] = useState(false);

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
        </div>
    );

    function handleFilterChange(e) {
        setFilterText(e.currentTarget.value)
    }

    function handleBelow60Change(e) {
        setBelow60(e.currentTarget.checked)
    }
}