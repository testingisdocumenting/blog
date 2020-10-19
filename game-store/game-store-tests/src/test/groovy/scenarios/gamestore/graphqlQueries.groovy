package scenarios.gamestore

import static org.testingisdocumenting.webtau.WebTauGroovyDsl.*

scenario('register game') {
    def mutation = '''
mutation createGame($id: String!, $title: String!, $type: String!, $priceUsd: Int!) {
  createGame(id: $id, title: $title, type: $type, priceUsd: $priceUsd) { 
    id
    type
  }
}
'''

    graphql.execute(mutation, [id: 'g1',
                               title: 'Slay The Spire',
                               type: 'card rpg',
                               priceUsd: 30]) {
        id.should == 'g1'
        type.should == 'card rpg'
    }

    http.doc.capture("game-store-graphql-mutation")
}

scenario('query game') {
    def query = '''
query {
  game(id: "g1") {
    title
    type
  }
}
'''
    graphql.execute(query) {
        game.title.should == "Slay The Spire"
        title.should == "Slay The Spire"
    }

    http.doc.capture("game-store-graphql-game")
}

scenario('query game with param') {
    def query = '''
query game($id: String!) {
  game(id: $id) {
    title
    type
  }
}
'''
    graphql.execute(query, [id: 'g1']) {
        title.should == "Slay The Spire"
    }

    http.doc.capture("game-store-graphql-game-with-params")
}

scenario('clean up') {
    http.delete("/api/game/g1")
}