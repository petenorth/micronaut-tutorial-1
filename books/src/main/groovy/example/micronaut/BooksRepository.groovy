package example.micronaut

interface BooksRepository {
    List<Book> findAll()
}
