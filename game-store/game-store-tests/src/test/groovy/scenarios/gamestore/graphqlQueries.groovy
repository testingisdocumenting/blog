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

// query-definition-start
def query = '''
query {
  game(id: "g1") {
    title
    type
  }
}
'''
// query-definition-end

// query-game-start
scenario('query game') {
    graphql.execute(query) {
        game.title.should == "Slay The Spire"
        title.should == "Slay The Spire"
    }
    http.doc.capture("game-store-graphql-game")
}
// query-game-end

// query-params-definition-start
def queryWithParams = '''
query game($id: String!) {
  game(id: $id) {
    title
    type
  }
}
'''
// query-params-definition-end

// query-game-param-start
scenario('query game with param') {
    graphql.execute(queryWithParams, [id: 'g1']) {
        title.should == "Slay The Spire"
    }
    http.doc.capture("game-store-graphql-game-with-params")
}
// query-game-param-end

scenario('clean up') {
    http.delete("/api/game/g1")
}