package example.micronaut;

import io.micronaut.http.annotation.Controller; 
import io.micronaut.http.annotation.Get;
//import io.micronaut.security.annotation.Secured; 
import io.reactivex.Flowable;
import java.util.List;

@Controller("/api") 
public class BooksController {

    private final BooksFetcher booksFetcher; 
    private final InventoryFetcher inventoryFetcher;

    public BooksController(BooksFetcher booksFetcher, InventoryFetcher inventoryFetcher) {
        this.booksFetcher = booksFetcher;
        this.inventoryFetcher = inventoryFetcher; 
    }

    @Get("/books") Flowable<Book> findAll() { 
        return booksFetcher.fetchBooks()
                   .flatMapMaybe(b -> inventoryFetcher.inventory(b.getIsbn())
                        .filter(stock -> stock > 0)
                        .map(stock -> { 
                            b.setStock(stock); 
                            return b; 
                        })
                    );

    }
}
