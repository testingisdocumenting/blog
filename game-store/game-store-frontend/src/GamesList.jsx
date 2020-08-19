import React from 'react';

import style from './GamesList.module.scss'

export function GamesList({games, filterText, below60}) {
    const sorted = [...games].sort((a, b) => a.title > b.title ? 1 : -1)

    filterText = filterText.toLowerCase()

    return (
        <div className={style.gameList}>
            {sorted
                .filter(game => game.title.toLowerCase().indexOf(filterText) !== -1)
                .filter(game => !below60 || game.priceUsd < 60)
                .map(game => <GameCard key={game.id} game={game}/>)}
        </div>
    );
}

function GameCard({game}) {
    return (
        <div className={style.gameCard}>
            <div className={style.title}>{game.title}</div>
            <div className={style.content}>
                <div className={style.label}>type</div>
                <div className={style.value}>{game.type}</div>
                <div className={style.label}>price</div>
                <div className={style.value}>${game.priceUsd}</div>
            </div>
        </div>
    )
}