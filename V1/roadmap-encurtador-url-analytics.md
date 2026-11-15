# Roadmap: encurtador de URL com analytics

Projeto: encurtador de links com **Java, Spring Boot e MongoDB**.

Cada fase tem um objetivo e um critério de "pronto". Só passe para a próxima quando terminar a atual.

## Fase 1: Organizar o que você já tem

**Objetivo:** código limpo e com camadas.
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity errorMethod(){
return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
}
- [X] Criar o `UrlService` e tirar a lógica do controller
- [X] Mover o redirect para a raiz (`/{id}`), com o link curto igual ao que a API devolve
- [X] Tirar o `plusMinutes(1)` e definir um tempo de vida razoável

**Pronto quando:** o controller só recebe e responde, e toda a regra está no service.

## Fase 2: Robustez

**Objetivo:** a API não quebra com entrada ruim.

- Validar a URL (só `http`/`https`, não vazia)
- [X] Tratar erros com respostas JSON claras (`@ControllerAdvice`)
- [X] Devolver 404 quando o link não existe ou expirou

**Pronto quando:** você consegue mandar lixo para a API e ela responde com um erro limpo, sem Whitelabel Error Page.

## Fase 3: Testes

**Objetivo:** ter segurança para mexer no código.

- Testes unitários do `UrlService` (JUnit 5 + Mockito)
- Um teste de integração do fluxo: criar e redirecionar

**Pronto quando:** `mvn test` passa e você confia nele.

## Fase 4: Analytics básico

**Objetivo:** saber quantas vezes cada link foi acessado.

- Coleção `clicks`, com um documento por acesso (id da URL, data, user agent, referer)
- Endpoint `GET /stats/{id}` com o total de cliques
- Não deixar uma falha no analytics quebrar o redirect

**Pronto quando:** você abre o link 5 vezes e o endpoint mostra 5.

## Fase 5: Analytics útil

**Objetivo:** transformar dados em informação.

- Cliques por dia (aggregation do MongoDB)
- Top navegadores e top origens (referer)
- Decidir o que fazer com os cliques quando o link expira (manter ou expirar junto)

**Pronto quando:** o endpoint de stats responde algo que você mostraria num print.

## Fase 6: Empacotar e publicar

**Objetivo:** outras pessoas conseguirem rodar.

- `docker-compose.yml` com app e Mongo
- README com o que faz, como rodar, exemplo de `curl` e diagrama
- GitHub e post no LinkedIn

**Pronto quando:** alguém clona o repositório e roda com um comando.

## Fase 7 (opcional): Evoluir

Escolha **uma** coisa, não todas:

- **Kafka** para publicar os cliques como eventos e processar o analytics num segundo serviço
- **Redis** para cache dos redirects mais acessados
- **PostgreSQL + JPA** numa versão paralela
- **Deploy** com Atlas + Render ou Railway

## Resumo das fases

| Fase | Foco | Entrega principal |
|---|---|---|
| 1 | Organização | `UrlService` e controller enxuto |
| 2 | Robustez | Validação e erros em JSON |
| 3 | Testes | JUnit 5 + Mockito |
| 4 | Analytics básico | Coleção `clicks` e `GET /stats/{id}` |
| 5 | Analytics útil | Cliques por dia, navegadores, origens |
| 6 | Publicação | Docker Compose, README, GitHub |
| 7 | Evolução | Kafka, Redis, PostgreSQL ou deploy |

## Dicas

1. **Um passo por vez.** Terminar uma fase pequena vale mais do que começar três grandes.
2. **Commits pequenos.** Faça um commit a cada fase ou subtarefa. O histórico mostra sua evolução, e isso conta em portfólio.
3. **Escreva com suas mãos.** Leia a explicação, feche e implemente do seu jeito. Se travar, mostre a sua tentativa, mesmo quebrada.
4. **Anote o que aprendeu.** Um arquivo `APRENDIZADOS.md` com "erro, causa, solução" (como o caso do TTL e do Compass) vira ótimo material de entrevista.
5. **Cuidado com dados pessoais.** IP é dado pessoal na LGPD. Guarde anonimizado (zere o último octeto) ou não guarde.
6. **Pense em escala só depois.** Kafka e Redis impressionam, mas um projeto simples, bem testado e documentado vale mais do que um complexo e quebrado.
7. **Ritmo realista.** Uma fase a cada 1 ou 2 semanas, estudando pouco por dia, mas todo dia.

## Onde você está agora

Você já tem o núcleo da Fase 1 (salvar e redirecionar). O próximo passo concreto é **criar o `UrlService`**.

> Quando tiver a sua tentativa, mande o código e peça feedback sem que reescrevam tudo para você.
