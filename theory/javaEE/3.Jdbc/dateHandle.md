# What's going on with dates?

>  JDBC uses java.sql.Date object by default and java uses java.util.Date instead, that make handling dates on JDBC a little bit tedious.

## Easy conversion from Util date to SQL date

You can use the following code snippet:

```java
java.sql.Date today = new java.sql.Date(new Date().getTime());
```

## Getting Util dates from JDBC statement

You can indeed get a Util date from a resultset:

```java
Date date = resultset.getDate("birthday")
```
