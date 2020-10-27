package basicScenarios

// basic-http-invocation
scenario('basic http invocation') {
    http.get('https://jsonplaceholder.typicode.com/todos/1') {
        body.title.should == 'delectus aut autem' // automatic mapping of a field name to a JSON response
        completed.should == false // specifying body is optional
    }
    http.doc.capture('basic-http-invocation')
}
// basic-http-invocation-end
