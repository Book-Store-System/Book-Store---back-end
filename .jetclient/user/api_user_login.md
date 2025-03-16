```toml
name = '/api/user/login'
description = 'Method to login'
method = 'POST'
url = 'http://localhost:8080/api/user/login'
sortWeight = 2000000
id = '3bb70aca-7893-4880-bd77-edcd2f783a48'

[body]
type = 'JSON'
raw = '''
{
  "email": "string",
  "password": "string"
}'''
```
