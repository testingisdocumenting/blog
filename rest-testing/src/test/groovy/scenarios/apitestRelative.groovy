scenario("check todo item title") {
    http.get("/todos/1") {
        title.should == "delectus aut autem"
    }
}