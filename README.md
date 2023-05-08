# 🦜Parrot - BACKEND

## 💻 Sobre o projeto
 Esse projeto faz parte do Programa Trainee SysMap de Excelência Full Stack | 3ª edição e trata-se do back-end de uma rede social feita em Java com Spring.
 
## 🧰 Tecnologias utilizadas

* Java
* Springboot
* Spring Web
* Spring Data
* Spring Security
* Lombok
* Bcrypt
* MongoDB
* Swagger API
* JWT (JSON Web Token) Authentication
* Docker
* Mini IO
* Kafka

<br>

## ⚙ Requisitos
 Para poder rodar este projeto, você deverá ter os seguintes programas instalados:
 - Docker
  
  <br>

## 👩‍💻Instalação

  * Clone este projeto
  * Entre na pasta raiz de <i>"backend-rede-social"</i> e abra o arquivo <a href="https://github.com/bc-fullstack-02/Gabriel-Ribeiro/blob/main/backend/backend-rede-social/docker-compose.yaml">docker-compose.yaml</a>
  * Após isso, insira no terminal o comando abaixo:

```
docker-compose up
```
 * Entre no terminal do container do localhost
 * Insira o comando para definir o usuário padrão 
 ```
aws configure --profile default
```
 ```
 AWS Access Key ID [None]: mykey
AWS Secret Access Key [None]: mykey
Default region name [None]: us-west-2
Default output format [None]: json
 ```

* Após definir o usuário, insira o código abaixo para criar o bucket S3.
```
 aws s3 mb s3://demo-bucket --endpoint-url http://localhost:4566
 ```
 
 ![terminal](https://user-images.githubusercontent.com/80289718/236905442-d81e9d9f-0035-48ce-816a-6e0506f0cdc1.jpg)

  ```diff
@@ CASO VOCÊ PARE O CONTAINER, TERÁ QUE CRIAR O BUCKET NOVAMENTE ! @@
```
 <h3 style="color:red">🚏 PS: Caso você pare o container, você terá que criar o bucket novamente</h3> 
  
### Agora você está pronto para utilizar o sistema!

 <br>

## 🛣 Documentação SWAGGER
  Para acessar  a documentação SWAGGER e fazer os testes de requisições, use a seguinte rota :
###  ```GET /swagger-ui/index.html```
###  ```localhost:8080/swagger-ui/index.html```

![swagger](https://user-images.githubusercontent.com/80289718/236886929-2b8c53a6-291a-470e-addf-f2cd8a12befe.png)

## 🦸 Autor

<a href="https://github.com/Gahbr">
 <img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/80289718?v=4" width="100px;" alt="Gabriel Ribeiro"/>
 <br />
 <sub><b>Gabriel Ribeiro</b></sub></a> <a href="https://github.com/Gahbr" title="github"></a>
 <br />

[![Linkedin Badge](https://img.shields.io/badge/-Gabriel-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/gabriellribeiro1/)](https://www.linkedin.com/in/gabriellribeiro1/)
[![Yahoo!](https://img.shields.io/badge/Yahoo!-6001D2?style=flat-square&logo=Yahoo!&logoColor=white)](mailto:gabriell.ribeiro@yahoo.com)
[![GitHub](https://img.shields.io/badge/Gahbr-%23121011.svg?style=flat-square&logo=github&logoColor=white)](https://github.com/Gahbr)
