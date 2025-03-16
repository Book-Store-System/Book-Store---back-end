```toml
name = '/api/user/register'
description = 'Method to register a regular user'
method = 'POST'
url = 'http://localhost:8080/api/user/register'
sortWeight = 1000000
id = '45c9e592-65cb-411e-8b64-7cd450bbcaed'

[body]
type = 'JSON'
raw = '''
{
  "name": "string",
  "surname": "string",
  "email": "string",
  "password": "string"
}'''
```
