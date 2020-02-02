package com.bookstore;

import com.bookstore.ds1.BookService;
import com.bookstore.ds2.AuthorService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {

    private final BookService bookService;
    private final AuthorService authorService;

    public MainApplication(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            System.out.println("\n Saving an author (check the 'authorsdb' database) ...");
            authorService.persistAuthor();

            System.out.println("\n Saving a book (check the 'booksdb' database) ...");
            bookService.persistBook();
        };
    }
}
/*
 * How To Auto-Create And Migrate Two Databases In MySQL Using Flyway

Note: For production, don't rely on hibernate.ddl-auto (or counterparts) to export schema DDL to the database. Simply remove (disable) hibernate.ddl-auto or set it to validate. Rely on Flyway or Liquibase.

Description: This application is an example of auto-creating and migrating two databases in MySQL using Flyway. In addition, each data source uses its own HikariCP connection pool. In case of MySQL, where a database is the same thing with schema, we create two databases, authorsdb and booksdb.

Key points:

for Maven, in pom.xml, add the Flyway dependency
remove (disable) spring.jpa.hibernate.ddl-auto or set it to validate
in application.properties, configure the JDBC URL for booksdb as jdbc:mysql://localhost:3306/booksdb?createDatabaseIfNotExist=true and for authorsdb as jdbc:mysql://localhost:3306/authorsdb?createDatabaseIfNotExist=true
in application.properties, set spring.flyway.enabled=false to disable default behavior
programmatically create two DataSource, one for booksdb and one for authorsdb
programmatically create two FlywayDataSource, one for booksdb and one for authorsdb
programmatically create two EntityManagerFactory, one for booksdb and one for authorsdb
for booksdb, place the migration SQLs files in db\migration\booksdb
for authorsdb, place the migration SQLs files in db\migration\authorsdb
 */
