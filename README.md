# srm-asset

## Como executar?

### Via Docker Compose com as imagens remotas (Recomendado - Testes incluidos no build)

Requisitos:
- Docker
- Docker Compose

1 - Abra o Terminal:

2 - Vá até a raiz do projeto (/srm-asset), onde esta localizado o arquivo docker-compose.yml
``` 
docker-compose up -d
```

3 - Após a execução do comando, teremos 2 containers em execução referentes à API e o Front-End.

- Front-End: http://localhost:4200/
- API: http://localhost:8080/


### Via Docker com as imagens remotas (Recomendado na ausência de Docker Compose - Testes incluidos no build)

Requisitos:
- Docker

1 - Abra o Terminal

2 - Executar os seguintes comandos:
```
docker run -p 8080:8080 -d danilooliveira28/srm-asset-api:latest
docker run -p 4200:80 -d danilooliveira28/srm-asset-front-end:latest
```

3 - Após a execução dos passos anteriores, teremos 2 containers em execução referentes à API e o Front-End.

- Front-End: http://localhost:4200/
- API: http://localhost:8080/


### Via Docker com a imagem construida localmente (Não Recomendado - Alto volume de download - Testes incluidos no build)

Requisitos:
- Docker

1 - Abra o Terminal

2 - Vá até a raiz da API (/srm-asset/srm-asset-api):
```
docker image build -t srm-asset-api .
docker run -p 8080:8080 -d srm-asset-api
```

3 - Vá até a raiz do Front-End (/srm-asset/srm-asset-front-end):
```
docker image build -t srm-asset-front-end .
docker run -p 4200:80 -d srm-asset-front-end
```

4 - Após a execução dos passos anteriores, teremos 2 containers em execução referentes à API e o Front-End.

- Front-End: http://localhost:4200/
- API: http://localhost:8080/


### Código fonte local sem utilizar o Docker (Não Recomendado - Complexidade de ambiente - Testes incluidos no build)

Requisitos:
- Java 8
- Angular CLI

1 - Abra o Terminal

2 - Vá até a raiz da API (/srm-asset/srm-asset-api):
```
./gradlew clean build test bootrun
```

3 - Abra outro Terminal

4 - Vá até a raiz do Front-End (/srm-asset/srm-asset-front-end):
```
ng serve --open
```

5 - Após a execução dos passos anteriores, teremos as duas aplicações em execução:

- Front-End: http://localhost:4200/
- API: http://localhost:8080/
