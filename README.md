# service-hotels
API com integração ao Broker de Hotéis da CVC.



## **GET /totalPrice/city/{cityCode}**

Realiza consultas aos hotéis a partir do código da cidade, e realiza o calculo do valor total da hospedagem partir da data de checkin, checkout, quantidade de adultos e crianças.

**Parâmetros de entrada:**

| Nome     | Tipo        | Requerido | Formato    | Descrição              |
| -------- | ----------- | --------- | ---------- | ---------------------- |
| cityCode | Path        | Sim       | -          | Código da cidade       |
| checkin  | QueryString | Sim       | dd-MM-yyyy | Data do Checkin        |
| checkout | QueryString | Sim       | dd-MM-yyyy | Data do Checkout       |
| adults   | QueryString | Sim       | -          | Quantidade de Adultos  |
| child    | QueryString | Sim       | -          | Quantidade de Crianças |

**Exemplo de requisição:**

```
GET localhost:8080/totalPrice/city/1032?checkin=12-12-2021&checkout=18-12-2021&adults=2&child=1
```

**Exemplo de resposta:**

```
[
    {
        "id": 1,
        "cityName": "Porto Seguro",
        "rooms": [
            {
                "roomID": 0,
                "categoryName": "Standard",
                "totalPrice": 15401.53,
                "priceDetail": {
                    "pricePerDayAdult": 1372.54,
                    "pricePerDayChild": 848.61
                }
            }
        ]
    },
    {
        "id": 4,
        "cityName": "Porto Seguro",
        "rooms": [
            {
                "roomID": 0,
                "categoryName": "Standard",
                "totalPrice": 6281.4,
                "priceDetail": {
                    "pricePerDayAdult": 341.76,
                    "pricePerDayChild": 782.14
                }
            },
            {
                "roomID": 1,
                "categoryName": "Luxo",
                "totalPrice": 6674.44,
                "priceDetail": {
                    "pricePerDayAdult": 483.02,
                    "pricePerDayChild": 591.33
                }
            }
        ]
    }
    [...]
 }
```



## **GET /totalPrice/hotel/{hotelId}**

Recupera informações de um hotel específico a partir do código do hotel, e realiza calculo do valor total da hospedagem partir da data de checkin, checkout, quantidade de adultos e crianças.

**Parâmetros de entrada:**

| Nome     | Tipo        | Requerido | Formato    | Descrição              |
| -------- | ----------- | --------- | ---------- | ---------------------- |
| hotelId  | Path        | Sim       | -          | Código do hotel        |
| checkin  | QueryString | Sim       | dd-MM-yyyy | Data do Checkin        |
| checkout | QueryString | Sim       | dd-MM-yyyy | Data do Checkout       |
| adults   | QueryString | Sim       | -          | Quantidade de Adultos  |
| child    | QueryString | Sim       | -          | Quantidade de Crianças |

**Exemplo de requisição:**

```
GET /totalPrice/hotel/1?checkin=12-12-2021&checkout=18-12-2021&adults=2&child=1
```

**Exemplo de resposta:**

```
{
    "id": 1,
    "cityName": "Porto Seguro",
    "rooms": [
        {
            "roomID": 0,
            "categoryName": "Standard",
            "totalPrice": 15401.53,
            "priceDetail": {
                "pricePerDayAdult": 1372.54,
                "pricePerDayChild": 848.61
            }
        }
    ]
}
```

