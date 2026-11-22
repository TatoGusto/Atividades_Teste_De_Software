# Testes de Integração com Spring Boot e JUnit 5

## Sobre o projeto

Atividade prática de testes de integração em uma aplicação de cadastro de produtos desenvolvida com Spring Boot. O objetivo é verificar a comunicação entre as camadas **Controller, Service, Repository e banco de dados H2**, utilizando componentes reais da aplicação, sem substituir `ProdutoService` ou `ProdutoRepository` por mocks.

## Tecnologias utilizadas

- Java 17
- Maven
- Spring Boot 3.x
- Spring Data JPA
- H2 Database (banco em memória)
- JUnit 5
- Spring Test e MockMvc
- Jackson (`ObjectMapper` e `JsonNode`)

## Estrutura do projeto

```text
src/
├── main/
│   ├── java/br/edu/exemplo/
│   │   ├── Aplicacao.java
│   │   ├── controller/
│   │   │   ├── ProdutoController.java
│   │   │   └── ProdutoRequest.java
│   │   ├── model/Produto.java
│   │   ├── repository/ProdutoRepository.java
│   │   └── service/ProdutoService.java
│   └── resources/application.properties
└── test/java/br/edu/exemplo/
    ├── ProdutoServiceIntegrationTest.java
    └── ProdutoControllerIntegrationTest.java
```

## Exercício 1 — Integração Service + Repository + H2

A classe `ProdutoServiceIntegrationTest` utiliza `@SpringBootTest`, `@Autowired` e `@Transactional` para testar a integração entre as regras de negócio e a persistência.

| Caso | Cenário | Resultado esperado |
|---|---|---|
| **CT01 — Cadastro válido** | Cadastrar um **Teclado** com preço **R$ 150,00** | O produto recebe um ID e é recuperado pelo repositório com nome e preço corretos. |
| **CT02 — Nome inválido** | Tentar cadastrar produto com nome em branco e preço **R$ 80,00** | É lançada `IllegalArgumentException` e a contagem de registros permanece igual. |
| **CT03 — Atualização de preço** | Cadastrar **Mouse** por **R$ 80,00** e atualizar para **R$ 95,00** | Uma consulta posterior ao repositório encontra o preço atualizado. |

### Por que consultar o repositório?

Verificar apenas o objeto retornado por `service.cadastrar()` confirma os dados da resposta do serviço, mas não exercita uma leitura pelo repositório. Usar `repository.findById()` verifica também a capacidade de recuperar a entidade pela camada de persistência. Em testes transacionais com JPA, a entidade ainda pode estar no contexto de persistência; por isso, essa consulta não equivale necessariamente a uma leitura independente diretamente do banco.

## Exercício 2 — Integração HTTP com MockMvc

A classe `ProdutoControllerIntegrationTest` utiliza `@SpringBootTest` e `@AutoConfigureMockMvc` para testar requisições HTTP simuladas sem iniciar um servidor HTTP real.

| Caso | Cenário | Resultado esperado |
|---|---|---|
| **CT04 — Cadastro via API** | Enviar `POST /produtos` com `{"nome":"Mouse","preco":80.0}` | HTTP **200** e JSON com ID numérico, nome `Mouse` e preço `80.0`. |
| **CT05 — Consulta via API** | Cadastrar produto via POST, extrair o ID da resposta e executar `GET /produtos/{id}` | HTTP **200** e JSON com ID, nome e preço correspondentes ao produto cadastrado. |

### Fluxo exercitado

```text
MockMvc (requisição HTTP simulada)
             ↓
     ProdutoController
             ↓
       ProdutoService
             ↓
     ProdutoRepository
             ↓
        Banco H2
```

O MockMvc executa o processamento da requisição pelo Spring MVC dentro do contexto de testes, sem a necessidade de abrir uma porta ou iniciar um servidor HTTP externo.

### Diferença em relação ao teste unitário

Em um teste unitário do controller, as dependências geralmente são substituídas por objetos simulados (mocks), isolando a lógica da camada web. Nesta atividade, o teste de integração utiliza o serviço, o repositório e o banco H2 reais, permitindo verificar a comunicação entre as camadas.

## Isolamento dos testes

A anotação `@Transactional` faz com que os testes sejam executados dentro de transações gerenciadas pelo Spring. Ao término de cada método de teste, o Spring Test normalmente realiza **rollback**, desfazendo as alterações efetuadas. Isso reduz a interferência de dados criados por um teste nos demais.

Como as requisições MockMvc deste projeto são processadas dentro do contexto de testes, elas normalmente participam da transação do teste. Esse comportamento não deve ser generalizado para testes que usam um servidor real em outra thread.

## Como executar

### Pré-requisitos

- JDK 17 instalado
- Maven disponível no terminal

Na pasta raiz do projeto, onde está o arquivo `pom.xml`, execute:

```bash
mvn test
```

Para executar apenas os testes de Service e Repository:

```bash
mvn -Dtest=ProdutoServiceIntegrationTest test
```

Para executar apenas os testes da API REST:

```bash
mvn -Dtest=ProdutoControllerIntegrationTest test
```

## Evidências da execução

> **Preencher após executar os testes no computador.** O resultado da suíte ainda não foi confirmado nesta documentação.

| Informação | Registro |
|---|---|
| Comando utilizado | `mvn test` |
| Total de casos de teste previstos | 5 (CT01 a CT05) |
| Testes executados | A confirmar |
| Falhas | A confirmar |
| Erros | A confirmar |
| Resultado final do Maven | A confirmar (`BUILD SUCCESS` ou `BUILD FAILURE`) |

**Evidência recomendada:** adicionar uma captura de tela do terminal mostrando a execução do Maven e o resultado dos testes. Por exemplo, salvar a imagem em `evidencias/testes-maven.png` e, depois, inserir no README:

```markdown
![Execução dos testes](evidencias/testes-maven.png)
```

## Conclusão

A atividade propõe cinco casos de teste de integração que cobrem cadastro, validação, atualização e consulta de produtos, tanto pela camada de serviço quanto pela API REST. Os testes permitem avaliar o funcionamento conjunto do Spring Boot, JPA, H2 e MockMvc. A aprovação da suíte deve ser confirmada executando `mvn test` após completar os métodos de teste fornecidos no projeto-base.
