## ppo4
### Создание и запуск интеграционных тестов в Docker.


Биржа умеет создавать/добавлять новые акции, создавать пользователей и управлять их балансом. Пользователи могу скупать и продвать акции. Для управления стоимостью акций, используется спец. запрос.

### Запросы
#### POST

createUser?id=value&money=value
createStock?id=value&price=value&cnt=value
buyStock?userId=value&stockId=value&cnt=value
sellStock?userId=value&stockId=value&cnt=value
addUserMoney?userId=value&money=value
withdrawUserMoney?userId=value&money=value
addStocks?stockId=value&cnt=value
changePrice?id=value&price=value

#### GET

user?id=value
stock?id=value
balance?userId=value
ping


##### Для хранения данных используется in-memory база данных(с другими докер отказался работать *плак-плак*)
### Запуск
#### sudo mvn -am -pl app package
#### sudo mvn -am -pl tests test
