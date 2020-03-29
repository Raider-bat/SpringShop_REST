# Документация для REST SpringShop 

* ### Управление пользователями
* [Авторизация](#auth)
* [Список пользователей](#userList)
* [Добавление пользователя](#addUser)
* [Обновление пользователя](#updateUser)
* [Удаление пользователя](#deleteUser)
***
* ### Управление товарами
* [Приостановление продаж](#stopSale)
* [Добавление товара](#addProduct)
* [Удаление товара](#deleteProduct)
***
___
## Управление пользователями
<a name="auth"></a>
### Авторизация
>URN: /api/v1/auth/login <br>
>Method: POST <br>
>Permit: ALL <br>
>Content-type: JSON

Тело запроса состоит из следующих параметров:
1. username - type-String | min: 3 | max: 20
2. password - type-String | min: 6 | max: 60

### Пример:

URI: http://localhost:8081/api/v1/auth/login <br><br>
Тело запроса:

```json
{
	"username":"Raider",
	"password":"12345699"
}
```
>Status: 200

Тело ответа:

```json
{
    "username": "Raider",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJНYWlkZXIiLCJpYXQiOjE1ODU0MDUyMDMsImV4cCI6MTU4NTQwODgwM30.GEYb1DuRdTUSobjOO8Chqr7_ZhSeNoT7OaySy3Sm5NU"
}
```
Тело ответа состоит из следующих параметров:
1. username - имя пользователя
2. token - сгенерированный токен сервером для пользователя

<a name="userList"></a>
___
### Список пользователей

>URN: /api/v1/admin/users <br>
>Method: GET <br>
>Permit: ADMIN <br>
>Content-type: JSON


Пример:
>Status: 200 <br>

URI: http://localhost:8081/api/v1/admin/users <br>

#### Тело ответа:

```json
[
    {
        "id": 1,
        "username": "Raider",
        "password": null,
        "active": true,
        "roles": [
            "ADMIN"
        ]
    },
    {
        "id": 75,
        "username": "Test",
        "password": null,
        "active": true,
        "roles": [
            "ADMIN"
        ]
    }
]
```

Тип ответа:
```
Collection<Object>

Object:{
    id: long
    username: String
    password: null|String
    active: boolean
    roles: Set<String>
}
```

Подробное описание параметров ответа:
```
    id: уникальный индефикатор пользователя 
    username: имя пользователя
    password: пароль(на данный момент поле пустое)
    active: активен ли пользователь
    roles: роль пользователя в системе
```

<a name="addUser"></a>
___
### Добавление пользователя

>URN: /api/v1/admin/users/create <br>
>Method: POST <br>
>Permit: ADMIN <br>
>Content-type: JSON

Тело запроса: 

```json
{
	"username":"Test",
	"password":"123456789",
	"roles":["ADMIN"]
}
```

Тело запроса состоит из следующих параметров:

```
username: String; min_lenght: 4| max_lenght: 20
password: String; min_lenght: 6| max_lenght: 60
roles: Collection<String>
```

>Status: 201 <br>
>Status: 400



Status: 400<br>

#### Тело ответа:
```json

{
    "status": "BAD_REQUEST",
    "message": "User is exist"
}
```
OR
```json
{
    "status": "BAD_REQUEST",
    "message": "Invalid data"
}
```

Тип параметров в теле ответа:
```
status: String
message: String
```
___
<a name="updateUser"></a>
### Обновление пользователя

>URN: /api/v1/admin/users/update <br>
>Method: PUT <br>
>Permit: ADMIN <br>
>Content-type: JSON

Status: 200 <br>
Status: 400 


Тело запроса состоит из следующих параметров:
1. username
2. newPassword: min_length: 6 | max_length: 60

**Пример:**

Тело запроса: 
```json
{
		"username":"Raider",
		"newPassword":"1234567"
}
```
Ответ: OK
>Status: 200

___
Тело запроса: 
```json
{
		"username":"Raider",
		"newPassword":"1267"
}
```
Ответ: BAD_REQUEST
>Status: 400

Тело ответа:
```json
{
    "status": "BAD_REQUEST",
    "message": "Invalid New Password"
}
```
OR
```json
{
    "status": "BAD_REQUEST",
    "message": "User not Found"
}
```
___
<a name="deleteUser"></a>
### Удаление пользователя 

>URN: /api/v1/admin/users/{**id**} <br>
>Method: DELETE <br>
>Permit: ADMIN <br>
>Content-type: JSON

**id** - type: Long | уникальный индификатор пользователя

Status: 200 <br>
Status: 404

Пример:

URI: http://localhost:8081/api/v1/admin/users/ **2** <br>


>Status: 404

Тело ответа:
```json
{
    "status": "NOT_FOUND",
    "message": "User not found"
}
```
___
## Управление товарами

<a name="stopSale"></a>
### Приостановление продаж

>URN: /api/v1/admin/products/saleControl<br>
>Method: DELETE <br>
>Permit: ADMIN <br>
>Content-type: JSON

**Query params:**
```
pause: type-String
```
Допустимые значения параметра pause: <br>  
YES | yes | Y - приостановление продаж; <br>
NO | no | N - возобновление продаж.

Status: 200 <br>
Status: 400 <br>

Пример:

>Status: 200

URI: http://localhost:8081/api/v1/admin/products/saleControl?pause=YES
<br>
<br> 
<br>
>Status: 400

URI: http://localhost:8081/api/v1/admin/products/saleControl?pause= **erberb**

Тело ответа:
```json

{
    "status": "BAD_REQUEST",
    "message": "Invalid param"
}
```
Тип параметров в теле ответа:

```
status: String
message: String
```
___

<a name="addProduct"></a>
### Добавление товара 

>URN: /api/v1/admin/products/add<br>
>Method: POST <br>
>Permit: ADMIN <br>
>Content-type: JSON

Status: 201<br>
Status: 400

Тело запроса состоит из следующих параметров:
1. name - String min_lenght: 6 | max_lenght: 40
2. price - Long 
3. category: object <br>
        1. id: Long<br>
        2. name: String
4. brand: object<br>
        1. id: Long<br>
        2. name: String
5. description: String min_lenght: 100 | max_lenght: 500

Подробное описание параметров:
```
name: название товара 
price: цена товара
category: категория к которой принадлежит товар(ноутбук, монитор, смартфон)
brand: бренд к которому принадлежит товар(Apple, Samging..)
description: описание товара, его характеристики и т.д.
```

Пример:

URI: http://localhost:8081/api/v1/admin/products/add

Тело запроса:<br>

>Status: 201

```json
{
        "name": "Монитор 33\" SAMSUNG S32R750UEI",
        "price": 235800,

        "category": {
            "id": 2,
            "name": "Мониторы"
        },

        "brand": {
            "id": 2,
            "name": "Samsung"
        },

        "description": "Тип матрицы: VA / Диагональ экрана, дюйм: 31.5 / Соотношение сторон: 16:9 /
                        
                        Максимальное разрешение: 3840 x 2160 Ultra HD 4K /
                        Частота при максимальном разрешении: 60 Гц / Яркость: 250 кд/м2 /
                        Контрастность: 2500:1 / Время отклика: 4 мс / Интерфейс подключения: HDMI;
                        mini-DisplayPort / Прочие разъемы: USB Type-A / Особенности: Регулировка по высоте;
                        Технология устранения мерцания"
}
```
<br><br><br>

>Status: 400

При не прохождении валидации будет один из ответов:

Тело ответа:

```json
{
    "status": "BAD_REQUEST",
    "message": "Invalid data"
}
```
OR
```json
{
    "status": "BAD_REQUEST",
    "message": "Invalid category or brand"
}
```
___

<a name="deleteProduct"></a>
### Удаление товара 

>URN: /api/v1/admin/products/{**id**}<br>
>Method: DELETE <br>
>Permit: ADMIN <br>
>Content-type: JSON

**id**: type-Long | уникальный индификатор товара 

**Пример:**

>Status: 200

URI: http://localhost:8081/api/v1/admin/products/ **76**

<br>

**При ненахождении товара с указанным id:**

>Status: 404

Тело ответа:

```json
{
    "status": "NOT_FOUND",
    "message": "Product not found"
}
```
