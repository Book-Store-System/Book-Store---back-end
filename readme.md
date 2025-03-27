# Order

* **Endpoints**
    * POST
    ```shell
    {
        [
            {
                "book_stock_id": 0,
                "quantity": 0
            },
        ],
        "payment": "string",
        "shipping": 0
    }
    ```

  * GET
    ```shell
    {
        [
         {
            {
              "title": "string",
              "picture": "string"
            },
            "quantity": 0,
            "subtotalIem": 0  
          },
        ],
        "shipping": 0,
        "order_date": "string",
        "totalValue": 0
    }
    ```