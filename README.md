
![M@AIN 3](https://github.com/SauloMesq/Tentativa-Imagens/assets/136653514/c7d48c02-4286-41f0-b206-647e7e2bafd7)


<h1> M.@.I.N. - Mensagem Automatizada de Informações Novas
<h2>M@IN é uma aplicação que permite o envio de e-mails automatizados a partir de cadastros de usuários em um banco de dados;</h2>


Com o programa é possível criar, buscar, deletar e atualizar o cadastro de usuários, associando-os a endereços eletrônicos e um id, como também a uma organização;

Há também um repositório de mensagens que pode receber temas específios para elas, o qual também é possível gerenciar fazendo buscas por assuntos e registro (ID da mensagem).

Também é possível enviar emails utilizando mensagens pré-cadastradas e customizadas, para usuário de uma mesma organização e também por função do usuário.

Com uma ferramenta auxiliar, é possível confirmar o envio, retornando o status do email (enviado ou mensagem de erro).


### Criado por:
- Basílio Lima de Queiroz Santos;
- Flávio Lins de Oliveira;
- José Gabriel Coelho Oliveira;
- Saulo Roberto Farias de Mesquita


Mentoria:
#### Ana Beatriz Cavalcanti Ribeiro
- Linkedin: https://www.linkedin.com/in/anabeatrizcavalcantir/


#
# Trabalho de conclusão do curso NExT - Nova Experiência de Trabalho (sem fins comerciais)
#

### O que é necessário para executar a aplicação localmente:
```
- OpenJDK 17.0.2 +;
- Maven 3.9.4 +;
- Uma ferramenta auxiliar para fazer REST requests. (Ex: Postman, Insomnia ou a dependência Swagger);
- Spring Boot 3.1.3 +;
- Um conector a banco de dados (JDBC) (JDBC Connector);
- Um endereço de Gmail para uso da aplicação e o envio dos emails.
```
  
##### OBS: É possível copiar o código direto para sua IDE.


## Funcionalidades (CRUD) - Usuários:
Para executar a aplicação localmente, com o código aberto na sua IDE, primeiro você deverá ir em AplicationProperties e atualizar os dados de usuário (username=) e senha (password=), considerando seu banco de dados local de sua preferência (JBDC Connector) e atualizar o username (username=) e senha (password) do Gmail da aplicação.


Depois de executar a aplicação e conectado ao banco de dados na sua máquina, você precisará de uma ferramenta para fazer REST Requests. Todos os testes de funcionalidade do CRUD e do envio de e-mail deste projeto foram feitos usando o Postman ou Insomnia.
O banco de dados recebe informações sobre usuários, com os parâmetros "name", "email", "organization" e "func".

Na feramenta para REST Request, utilize o seguinte formato:
```
{
"name": "JoãoMaria";
"email": "joaomaria@gmail.com";
"organization": "cesar";
"func": "Desenvolvedor";
}
```
Métodos CRUD:
http://localhost:8080/api/v1/user  - (uso local)
- *POST*, para cadastrar usuários preenchendo os atributos acima;
- *GET*, para buscar uma lista com todos os usuários cadastrados e todos os emails recebidos por cada usuário;
  
  EX: 
```
{
    "id": "16c37-041",
    "name": "JoãoMaria",
    "email": "joaoMaria@gmail.com",
    "organization": "cesar",
    "func": "chefe",
    "emailsReceived": [
      {
        "id": "ae18-2b98",
        "sendFrom": "emailsendernext@gmail.com",
        "sendTo": "randomlocalmail@gmail.com",
        "subject": "É isso!",
        "text": "Será?",
        "dateSent": "2023-09-22T21:20:12.653367",
        "statusMail": SENT
      } ],
      { "registrationDate": "2023-09-22T10:07:53.209895"  },

  {
    "id": "8d272a37-4998-4e48-bf2b-25c0d73ca4d3",
    "name": "MariaJoão",
    "email": "mariajoao@cesar.org.br",
    "organization": "cesar",
    "func": "monitora",
    "emailsReceived": ...
  } 
    ... (etc.)
```

Colocando o número do ID do usuário no lugar do "id", você pode usar os seguintes métodos: *GET*, para buscar o usuário por ID; *DELETE*, para apagar o usuário identificado pelo ID; *PUT*, para atualizar dados do usuário identificado pelo ID.

É possível atualizar (*PUT*) o usuário modificando um ou mais dos atributos do mesmo: “email”, “name”, “organization” e “func”.
- EX: http://localhost:8080/api/v1/user/{3592347}  - (uso local)
```
{
"name": "JoãoMaria";
"email": "joaomaria@gmail.com";
"organization": "cesar";
"func": "Analista";
}
```

#

## Funcionalidades (CRUD) - Mensagens:
Há também uma forma de adicionar mensagens pré-formatadas na tabela, com os parâmetros "Subject" e "Text".

Utilize o seguinte formato:
```
{
"Subject": "Infrome de planejamento";
"Text": "Execução do panejamento confirmada.";
}
```

Métodos CRUD:
http://localhost:8080/api/v1/message - (uso local)
- *POST*, para cadastrar mensagens;
- *GET*, para buscar uma lista com todas as mensagens cadastradas;

Colocando o número do ID do usuário depois do "id", você pode usar os seguintes métodos: *GET*, para buscar a mensagem pelo seu ID, *DELETE*, para apagar a mensagem pelo seu ID e *PUT*, para atualizar dados da mensagem pelo seu ID.
- http://localhost:8080/api/v1/message/{id} - (uso local)
 

Também é possível fazer uma busca por assunto da menssagem com o método *GET*, substituindo "subject" na URL:
- http://localhost:8080/api/v1/message/{subject} - (uso local)




## Funcionalidades (CRUD) - Enviando e-mails:
Para enviar e-mails com mensagem personalizada para usuário cadastrado no banco de dados, utilize o método *POST*:
-  http://localhost:8080/api/v1/email  - (uso local)
No corpo do email, você deve preencher os campos "sendTo", "subject" e "text";
Ex:
```
{
"sendTo": "JoaoMaria@gmail.com",
"subject": "Aniversário",
"text": "Parabéns, João !!"
}
```


É possível enviar e-mails usando mensagens pré-cadastradas para usuários de uma mesma organização pelo método *POST*.
Utilizar o ID da mensagem pré-cadastradas ("id) e o nome da organização ("organization"):
Ex.:
- http://localhost:8080/api/v1/{id}/org={organization} - (uso local)
 

Assim como também é possível enviar emails pelo método *POST* para usuários de uma mesma função ("function") utilizando o ID ("id") da mensagem:
- http://localhost:8080/api/v1/{id}/func={function} - (uso local)



##

## Nossos contatos:

| Basílio Queiroz: |
| ------ |
- Github: https://github.com/91basilio
- Linkedin: https://www.linkedin.com/in/basilioqueiroz/

| Flávio Lins: |
| ------ |
- Github: https://github.com/fllins
- Linkedin: https://www.linkedin.com/in/fllins

| José Gabriel Coelho Oliveira: |
| ------ |
- Github: https://github.com/Gabriel-Oliveira25

| Saulo Mesquita: |
| ------ |
- GitHub: https://github.com/SauloMesq
- Linkedin: https://www.linkedin.com/in/saulo-mesquita-09024a249/


![M@AIN 3](https://github.com/SauloMesq/Tentativa-Imagens/assets/136653514/9ea651f5-c1c0-4605-b71f-7193574bf174)


