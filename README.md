# 🔐 LibAuth

Biblioteca reutilizável para autenticação em aplicações Spring Boot.

A **LibAuth** valida automaticamente tokens JWT, integra com o Spring Security e expõe o endpoint `/me` com os dados do usuário autenticado.

Ela foi projetada para funcionar em uma arquitetura com **serviço de autenticação separado**, onde:

- o **Auth Service** gera os tokens
- as APIs de negócio apenas validam
- a lógica de autenticação fica centralizada em uma biblioteca reutilizável

---

## 🎯 Objetivo

O objetivo da LibAuth é desacoplar autenticação da aplicação de negócio.

Em vez de reimplementar JWT em cada projeto, a biblioteca fornece:

- validação automática de token
- integração com Spring Security
- configuração simples via `application.yml`
- suporte a rotas públicas
- endpoint `/me`
- suporte a **chave pública RSA**

---

## ⚙️ Como funciona

[Serviço de autenticação] → gera JWT (private key)  
[Frontend / Cliente] → envia Bearer Token  
[API usando LibAuth] → valida token (public key)  
[SecurityContext] → endpoints protegidos liberados

A LibAuth atua como um **resource server**.

Ela **não é responsável por:**

- login
- OAuth
- emissão de token

Essas responsabilidades pertencem ao serviço de autenticação.

---

## 🚀 Features

- 🔐 Validação de JWT com Spring Security
- 🔑 Suporte a chave pública RSA (RS256)
- ⚙️ Auto-configuração estilo Spring Boot Starter
- 📄 Configuração via `application.yml`
- 🌐 Suporte a rotas públicas
- 👤 Endpoint `/me`

---

## 📦 Instalação

### Maven

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/matheus-battiston/LibAuth</url>
    </repository>
</repositories>

<dependencies>
	<dependency>
		<groupId>com.matheus</groupId>
		<artifactId>lib-auth</artifactId>
		<version>0.0.2-SNAPSHOT</version>
	</dependency>
</dependencies>
```

## Configuração
As configurações são feitas no `application.yml` da aplicação que utiliza a biblioteca.

A chave pública deve ser fornecida via variável de ambiente.

Exemplo:
```yml
auth:
  enabled: true
  jwt:
    public-key: ${JWT_PUBLIC_KEY}
    expiration: 86400000
  public-paths:
    - /pesquisaAtor
    - /serie/**
```

## Variáveis de ambiente

A biblioteca espera uma chave pública RSA no formato PEM

``` 
JWT_PUBLIC_KEY="-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A...
-----END PUBLIC KEY-----"
```

## Segurança com RSA

A LibAuth utiliza criptografia assimétrica:

🔒 Auth Service
•	possui a private key
•	assina o JWT

🔓 APIs com LibAuth
•	possuem apenas a public key
•	validam o JWT

✔️ Vantagens
•	apenas o Auth Service pode gerar tokens
•	APIs não conseguem falsificar autenticação
•	maior segurança em arquitetura distribuída
•	padrão usado em OAuth2

## Endpoint /me

A biblioteca fornece o endpoint /me, uma forma simples de pegar informações basicas do usuário

```
GET /me
Authorization: Bearer <token>
```

```
{
"id": 1,
"nome": "Matheus",
"email": "matheus@email.com"
}
```

## Fluxo completo

	1.	Usuário faz login no Auth Service
	2.	Auth Service gera JWT (private key)
	3.	Frontend recebe o token
	4.	Frontend chama API com Bearer Token
	5.	LibAuth valida com public key
	6.	Usuário acessa rotas protegidas

## Arquitetura

A lib atua como uma camada entre a requisição e o Spring Security.

    Responsabilidades
    1.	Extrair o Bearer Token
    2.	Validar assinatura do JWT
    3.	Extrair claims (userId, email, nome)
    4.	Preencher o SecurityContext
    5.	Disponibilizar o usuário autenticado

## Diferencial

Diferente de implementações comuns, a LibAuth utiliza criptografia assimétrica:

❌ Modelo tradicional  
todas as APIs conhecem o mesmo segredo → qualquer uma pode gerar token

✅ LibAuth (RSA)  
apenas o Auth Service gera token → APIs apenas validam com public key

Isso reduz acoplamento e aumenta a segurança da arquitetura.


## Tecnologias utilizadas

	•	Java 17
	•	Spring Boot
	•	Spring Security
	•	JWT (jjwt)
	•	Maven
	•	GitHub Packages

## Motivação

Este projeto foi criado para simular uma arquitetura mais próxima de sistemas reais, separando a autenticação das
aplicações além de transformar a lógica em um módulo reutilizável para facilitar futuros desenvolvimentos em que eu utilize
meu serviço de autenticação.

##  Projeto Relacionado

## Projeto Relacionado

Essa biblioteca foi utilizada no projeto **WhoThisActor**, onde é responsável por toda a validação de autenticação da API.

