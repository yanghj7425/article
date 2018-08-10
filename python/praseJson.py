import json
 
str = '''
{
"server":"服务器 IP 地址",
"server_port":8388,
"local_address": "127.0.0.1",
"local_port":1080,
"password":"mypassword",
"timeout":300,
"method":"aes-256-cfb",
"fast_open": false,
"workers": 1
}
'''

data = json.loads(str)
print(data['server'])
data['server'] = '5555555'
print(data['server'])
with open('config.json', 'w') as file:
    file.write(json.dumps(data, indent=2))


    #https://blog.csdn.net/atyz123/article/details/63685186