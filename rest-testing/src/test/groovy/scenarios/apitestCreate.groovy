scenario("create and update todo item") {
    // create new item
    def itemId = http.post("/posts", [title: "my todo item", body: "write test", userId: 1]) {
        return id // extract auto parsed response field
    }
    http.doc.capture("todo-item-post")

    // use extracted id as part of URL
    http.get("/todos/${itemId}") {
        id.should == itemId
    }
}