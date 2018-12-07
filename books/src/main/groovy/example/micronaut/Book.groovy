package example.micronaut

import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor

@CompileStatic
@TupleConstructor
class Book {
    String isbn
    String name
}
