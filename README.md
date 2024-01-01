# Agile Kanban

[![Build Status](https://travis-ci.org/seu-usuario/agile-kanban.svg?branch=main)](https://travis-ci.org/seu-usuario/agile-kanban)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![GitHub Issues](https://img.shields.io/github/issues/seu-usuario/agile-kanban.svg)](https://github.com/seu-usuario/agile-kanban/issues)
[![GitHub Forks](https://img.shields.io/github/forks/seu-usuario/agile-kanban.svg)](https://github.com/seu-usuario/agile-kanban/network)
[![GitHub Stars](https://img.shields.io/github/stars/seu-usuario/agile-kanban.svg)](https://github.com/seu-usuario/agile-kanban/stargazers)

Um sistema moderno e eficiente de Kanban para gerenciar suas tarefas de forma ágil.

## Visão Geral

O Agile Kanban é uma aplicação de gerenciamento de tarefas projetada para proporcionar uma experiência ágil e eficiente no acompanhamento de projetos e tarefas. Com recursos inspirados no método Kanban, nossa aplicação oferece uma visão visual do progresso do trabalho e facilita a colaboração da equipe.

![Agile Kanban Screenshot](./docs/screenshot.png)

## Recursos Principais

- **Quadro Kanban Interativo:** Visualize e mova suas tarefas entre as colunas do quadro Kanban para rastrear o progresso.
- **Gerenciamento de Projetos:** Organize tarefas em projetos para uma visão holística do trabalho.
- **Colaboração em Tempo Real:** Trabalhe em equipe com atualizações em tempo real para uma colaboração eficaz.
- **Integração com MongoDB:** Armazenamento seguro e eficiente de dados utilizando o MongoDB.
- **Documentação Automática da API:** Explore e teste a API facilmente com a documentação Swagger.

## Pré-requisitos

- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [MongoDB](https://www.mongodb.com/try/download/community)

## Configuração Rápida

1. Clone o repositório:

    ```bash
    git clone https://github.com/seu-usuario/agile-kanban.git
    cd agile-kanban
    ```

2. Inicie o MongoDB:

    ```bash
    mongod
    ```

3. Execute o aplicativo:

    ```bash
    ./mvnw spring-boot:run
    ```

4. Acesse a aplicação em http://localhost:8080/swagger-ui/index.html

## Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua contribuição (`git checkout -b feature/sua-feature`)
3. Faça o commit das alterações (`git commit -am 'Adicionando nova funcionalidade'`)
4. Faça o push para a branch (`git push origin feature/sua-feature`)
5. Crie um novo Pull Request

## Licença

Este projeto é licenciado sob a [Licença MIT](LICENSE).

---

**Feito com ❤️ por TeamDev**