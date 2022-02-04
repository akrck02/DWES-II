# Pools on JDBC

**¿What on earth are database pools?**

> A pool of connections create a limited number of connections to share
> 
> accross all DAO operations.  

## How to create one in java

The easiest approach is to import apache commons dbcp2, logging and pool2 libraries.
So, we impor them into our project.

![](C:\Users\aketz\AppData\Roaming\marktext\images\2022-02-04-07-05-31-image.png)

>  Dbcp library has basicDatasource java object inside, so we will use it in our DAO. 

```java
 public DbDao() {
        //Create the pool 
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);

        // Pool connection size
        dataSource.setInitialSize(50);
 }
```

## How do I use the pool?

Get a new connection from the pool itself :

```java
public Connection getConnection() 
    Connection con = dataSource.getConection(); 
    return con;
}
```
