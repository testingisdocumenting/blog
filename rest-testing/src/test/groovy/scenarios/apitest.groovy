scenario("check todo item title") {
    http.get("https://jsonplaceholder.typicode.com/todos/1") { // GET call to a provided URL (full URL is not required)
        title.should == "delectus aut autem" // automatic mapping of the JSON response
    }
    http.doc.capture("todo-item-get")
}