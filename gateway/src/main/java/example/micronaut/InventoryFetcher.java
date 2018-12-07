package example.micronaut;

import io.reactivex.Maybe;

public interface InventoryFetcher { 
    Maybe<Integer> inventory(String isbn); 
}
