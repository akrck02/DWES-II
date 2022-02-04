## DAO - Accessing databases from java

> DAO is a java class that access database and inserts / update / delete / create data on them. 



## Importing the driver

We are using mySQL 8 here so we will import the driver:

![](C:\Users\aketz\AppData\Roaming\marktext\images\2022-02-04-07-53-53-image.png) 

## Basic structure

```java
public class DbGestor {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String USER = "root";
    private static final String PASS = "";
    private static BasicDataSource dataSource;

    public DbGestor() {
        //Create connection pool *see pools.md
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);
        dataSource.setInitialSize(50);
    }

    //Methods to manage database
    public List<Book> getNotReturnedBooks();
    public void returnBooks(Collection<Integer> bookIds);

}
```

## Creating a basic select statement

Let's create or first select statement  with JDBC.

```java
/**
* Get not returned books ordered by date
* @return a list of Books
*/
public List<Book> getNotReturnedBooks() {
    String sql = "SELECT * FROM libro WHERE id IN (SELECT id FROM loanORDER BY date)";
    ArrayList<Book> books = new ArrayList<>();

    try {

        // Get the connection
        Connection con = dataSource.getConnection();

        // Create a statement
        Statement st = con.createStatement();

        // Geting results
        ResultSet rs = st.executeQuery(sql);

        // Adding books to our list
        while (rs.next()) {
            Book.BookBuilder builder = new Book.BookBuilder();
            builder.setId(rs.getInt("id"));
            builder.setTitle(rs.getString("title"));
            builder.setAuthor(rs.getInt("author"));
            builder.setGenre(rs.getString("genre"));
            builder.setPages(rs.getInt("pages"));
            books.add(builder.build());
        }

        rs.close();
        st.close();
        con.close();

     } catch (SQLException ex) {
         System.err.println("Error in method getNotReturnedBooks: " + ex);
     }

     return books;
}
```

## Secure SQL - Prepared statements

To avoid SQL ijection in our database, jdbc offers prepared statements, let's try!

```java
/**
 * Return books with the given IDs into the database
 * @param bookIds The book ids
 */
 public void returnBooks(Collection<Integer> bookIds) {

     try {
         Connection con = dataSource.getConnection();
         bookIds.forEach(id -> {
            try {
                String sql = "DELETE FROM loan WHERE book = ?";
                PreparedStatement st = con.prepareStatement(sql);

                //adding parameters 
                st.setInt(1, id);

                boolean ok = st.execute();
                System.out.println(id + " - " + ok);
                st.close();
            } catch (SQLException ex) {
                System.err.println("Error in method returnBooks: " + ex);
            }
         });

        con.close();
     } catch (SQLException ex) {
         System.err.println("Error in method returnBooks: " + ex);
     }
}
```
