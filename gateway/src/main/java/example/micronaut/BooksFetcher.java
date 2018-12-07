package example.micronaut;

import io.reactivex.Flowable;

public interface BooksFetcher { 
    Flowable<Book> fetchBooks(); 
}
