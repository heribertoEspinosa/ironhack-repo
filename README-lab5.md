# SQL Query Optimization

## Orders Query
```
SELECT Orders.OrderID, SUM(OrderDetails.Quantity * OrderDetails.UnitPrice) AS TotalPrice
FROM Orders
JOIN OrderDetails ON Orders.OrderID = OrderDetails.OrderID
WHERE OrderDetails.Quantity > 10
GROUP BY Orders.OrderID;
```

## Analisis

* La consulta podría buscar ordenes con muchos productos y calcula el precio total de cada pedido por lo cual a falta de índice podría ser ineficiente
* La consulta filtra los pedidos después de juntar toda la información, lo que podria ser  lento si hay muchos datos
## Solución y mejoras

```
CREATE INDEX idx_OrderDetails_OrderQuantityPrice ON OrderDetails (OrderID, Quantity);
CREATE INDEX idx_quantity ON OrderDetails (Quantity);

SELECT Orders.OrderID, 
       SUM(OrderDetails.Quantity * OrderDetails.UnitPrice) AS TotalPrice
FROM Orders
JOIN OrderDetails ON Orders.OrderID = OrderDetails.OrderID
WHERE OrderDetails.Quantity > 10
GROUP BY Orders.OrderID;
```

## Comentarios

* Indexación de la columna "Quantity"
    * Se crea un índice en la columna "Quantity" de la tabla "OrderDetails" para acelerar la búsqueda de pedidos con una cantidad mayor que 10 esto mejorará la eficiencia
* Indexación de la columna "OrderID" y "Quantity"
    * Este índice es útil porque la consulta tiene una condición de filtro en OrderDetails.Quantity. Si la cantidad de pedidos es grande y se filtra frecuentemente por la cantidad de pedidos, este índice puede mejorar significativamente el rendimiento de esas consultas

## Customer Query
```
SELECT CustomerName FROM Customers WHERE City = 'London' ORDER BY CustomerName;

```

## Analisis

* La consulta puede resultar lenta en la busqueda ya que no contiene un indice en la columna city y esto hace que cada fila sea escaneada para encontrar coincidencias
* La consulta puede ser lenta al momento de ordenar el resultado por CustomerName ya que no tiene índice
## Solución y mejoras

```
CREATE INDEX idx_city ON Customers (City);

SELECT CustomerName 
FROM Customers 
WHERE City = 'London' 
ORDER BY CustomerName;

```

## Comentarios

* Indexación de la columna City
  * Mejorar la eficiencia de la búsquedaya que crea una estructura adicional que almacena los valores de esa columna en un formato optimizado

# NoSQL Query Implementation

## User Posts Query
```
db.posts
  .find({ status: "active" }, { title: 1, likes: 1 })
  .sort({ likes: -1 });

```

## Analisis

* Si la colección es grande y el campo "status" se utiliza con frecuencia para filtrar, la base de datos tendría que escanear todas las filas de la colección para encontrar los documentos que coincidan con el valor de "status", esto puede resultar en un tiempo de búsqueda más largo
* El campo "likes" del mismo modo que el campo "status" se utiliza con frecuencia para filtrar y esta es una colección grande, la base de datos tendría que recorrer todas las filas para ordenar los documentos según la cantidad de likes, lo que podría ser muy costoso en términos de rendimiento
## Solución y mejoras

```
db.posts.createIndex({ status: 1 });

db.posts.createIndex({ likes: -1 });

db.posts
  .find({ status: "active" }, { title: 1, likes: 1 })
  .sort({ likes: -1 });

```

## Comentarios

* Indexación del campo "status"
  * Se crea un índice en el campo "status" para mejorar el rendimiento de la búsqueda de posts activos. Se decide hacerlo de forma ascendente (1) porque para este caso se realiza una busqueda por el status "Active" y con esto facilita y acelera esta consulta
* Indexación del campo "likes"
  * Se crea un índice en el campo "likes" para mejorar el rendimiento de la ordenación de cantidad de likes. Se decide hacerlo descendente (-1) por la posible busqueda enfocada a los posts con mayores likes


## User Data Aggregation
```
db.users.aggregate([
  { $match: { status: "active" } },
  { $group: { _id: "$location", totalUsers: { $sum: 1 } } },
]);

```

## Analisis

* Si la colección es grande y el campo "status" se utiliza con frecuencia para filtrar, la base de datos tendría que escanear todas las filas de la colección para encontrar los documentos que coincidan con el valor de "status", esto puede resultar en un tiempo de búsqueda más largo
* El campo "location" del mismo modo que el campo "status" se utiliza con frecuencia para filtrar y esta es una colección grande, la base de datos tendría que recorrer todas las filas para ordenar los documentos según la ubicación, lo que podría ser muy costoso en términos de rendimiento
## Solución y mejoras

```
db.users.createIndex({ status: 1 });

db.users.createIndex({ location: 1 });

db.users.aggregate([
  { $match: { status: "active" } },
  { $group: { _id: "$location", totalUsers: { $sum: 1 } } },
]);


```

## Comentarios

* Indexación de la columna City
  * Mejorar la eficiencia de la búsqueda porque crea una estructura adicional que almacena los valores de esa columna en un formato optimizado
