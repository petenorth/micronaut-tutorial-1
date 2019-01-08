package example.micronaut

import groovy.transform.CompileStatic
import javax.inject.Singleton

@CompileStatic
@Singleton
class BooksRepositoryImpl implements BooksRepository {

    @Override
    List<Book> findAll() {
        [
            new Book("1491950358", "Building Microservices v1.0.152"),
            new Book("1680502395", "Release It!"),
        ]
    }
}
