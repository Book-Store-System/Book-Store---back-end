```toml
name = '/api/book'
description = 'create stock and book'
method = 'POST'
url = 'http://localhost:8080/api/book'
sortWeight = 1000000
id = '2a8e31eb-0278-410e-b9fd-14395798af9c'

[body]
type = 'JSON'
raw = '''
{
  "book": {
    "title": "string",
    "author": "string",
    "genre": "string",
    "description": "string",
    "picture": "string",
    "language": "string",
    "publisher": "string",
    "publicationDate": "2025-03-16",
    "numberOfPages": 0,
    "dimensions": "string",
    "barcode": "string"
  },
  "quantityInStock": 0,
  "profitMargin": 0,
  "purchasePrice": 0
}'''
```
