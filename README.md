Encurtador de URL

API de encurtamento de links, no estilo bit.ly, feita com Java, Spring Boot e MongoDB.

Você envia uma URL longa, a API gera um código curto, salva no banco e devolve um link. Ao acessar esse link, a pessoa é redirecionada para o endereço original. Os links expiram automaticamente usando um índice TTL do MongoDB.

Funcionalidades
Criação de link curto a partir de uma URL (POST)
Redirecionamento HTTP 302 para a URL original (GET)
Código curto aleatório, com verificação de colisão
Expiração automática dos links (índice TTL no MongoDB)
Tecnologias
Java 21
Spring Boot 4.1.2 (Spring Web e Spring Data MongoDB)
MongoDB
Maven
Como rodar
Pré-requisitos
JDK 21 ou superior
Maven
MongoDB rodando localmente (porta 27017) ou uma URI do MongoDB Atlas
Passo a passo
Clone o repositório:
bash
git clone https://github.com/HIRO-Leonardo/Encurtador_URL.git
cd Encurtador_URL
Crie um arquivo .env na raiz do projeto (veja o .env.example):
env
DATABASE_URL=mongodb://localhost:27017/encurtador
Suba a aplicação:
bash
mvn spring-boot:run

A API fica disponível em http://localhost:8080.

Configuração

O Spring Boot não lê o arquivo .env sozinho. O application.properties precisa importá-lo e usar a variável:

properties
spring.config.import=optional:file:.env[.properties]
spring.mongodb.uri=${DATABASE_URL:mongodb://localhost:27017/encurtador}
spring.data.mongodb.auto-index-creation=true
O valor depois dos dois pontos é o padrão usado quando DATABASE_URL não existe.
auto-index-creation=true é necessário para o Spring criar o índice TTL.
O arquivo .env não deve ser commitado. Ele está no .gitignore.
Uso da API
Criar um link curto
bash
curl -X POST http://localhost:8080/shorten-url \
  -H "Content-Type: application/json" \
  -d '{"url":"https://google.com"}'

Resposta (o código é aleatório):

text
http://localhost:8080/vkdRP
Acessar o link curto
bash
curl -i http://localhost:8080/vkdRP

Resposta: 302 Found, com o header Location apontando para a URL original.

Se o código não existir ou o link já tiver expirado, a API devolve 404.

Como funciona
POST /shorten-url
      |
      v
Gera código aleatório (5 a 9 letras) -> confere se já existe -> salva no MongoDB
      |
      v
Devolve http://localhost:8080/{codigo}

GET /{codigo}
      |
      v
Busca no MongoDB -> 302 para a URL original (ou 404)

Cada link é um documento na coleção urls:

Campo	Descrição
_id	Código curto
fullUrl	URL original
expiresAt	Data de expiração, com índice TTL

O MongoDB remove o documento quando expiresAt passa. O monitor de TTL roda a cada 60 segundos, então a remoção pode atrasar até 1 minuto.

Estrutura do projeto
src/main/java/com/encurtadorlink/V1/
├── controller/   # recebe as requisições e devolve as respostas
├── Service/      # regras de negócio (geração do código, salvar, buscar)
├── repository/   # acesso ao MongoDB (Spring Data)
├── entity/       # UrlEntity
└── dto/          # ShortenUrlRequest e ShortUrlResponse
Roadmap
[x] Criar e redirecionar links
[x] Expiração automática com TTL
[x] Separar a lógica em UrlService
[ ] Validação da URL (apenas http e https)
[ ] Tratamento de erros com respostas em JSON (400, 404, 500)
[ ] Testes unitários e de integração (JUnit 5 e Mockito)
[ ] Analytics de cliques (total, por dia, navegadores e origens)
[ ] Docker Compose (aplicação e MongoDB)
[ ] Deploy
Aprendizados

Alguns problemas reais que apareceram durante o desenvolvimento:

Documentos "sumindo" do banco: o índice TTL apagava os links poucos minutos depois da criação. O MongoDB também não altera o TTL de um índice que já existe.
Banco não aparecia no Compass: o Compass não atualiza a lista de bancos sozinho, é preciso clicar em Refresh.
connection string is invalid: o Spring Boot não lê o .env por conta própria e usou o texto ${DATABASE_URL} como se fosse a URI.
@Valid sem efeito: a validação só acontece se o DTO tiver anotações como @NotBlank e @Pattern.
Autor

Leonardo (@HIRO-Leonardo)
