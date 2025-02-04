# Configuração do Ambiente para Rodar o Projeto



## Rodando a Aplicação
Para executar a aplicação no IntelliJ, siga os passos:
1. Navegue até o arquivo principal da aplicação:
   ```
   src/main/java/com.bradesco.DesafioBackEnd/DesafioBackEndApplication
   ```
2. Clique com o botão direito sobre `DesafioBackEndApplication` e selecione `Run`.

## Informações Iniciais
- É preferível que o projeto seja rodado no aplicativo **IntelliJ**.
- Certifique-se de que tenha instalado o **GIT** previamente em sua máquina para clonar o projeto.
- Selecione uma pasta para o repositório, abra o terminal **git bash** para seguir com a clonagem.
- Clone o repositório utilizando o comando:
  ```sh
  git clone https://github.com/gabgolfsierra/DesafioBackEndBradesco.git
  ```
- Abra a pasta do repositório clonado no **IntelliJ**.


## Sumário
- [1. Configurar o SDK](#1-configurar-o-sdk)
- [2. Instalar Plugins Necessários](#2-instalar-plugins-necessários)
- [3. Configurar o Maven](#3-configurar-o-maven)
- [4. Acessar o Console do H2 Database](#4-acessar-o-console-do-h2-database)

## 1. Configurar o SDK
1. No IntelliJ, clique na ferramenta no canto superior direito.
2. Vá para `Project Structure`.
3. `Project Settings` -> `Project`
4. Em `SDK`, clique em `Download JDK` e selecione:
   - **Version:** 23
   - **Vendor:** Amazon Coretto 23
   - **Language Level:** 21

## 2. Instalar Plugins Necessários
1. Acesse `Settings -> Plugins`.
2. Instale o plugin **Lombok**.
3. Instale o plugin **Maven**.

## 3. Configurar o Maven
1. Clique no símbolo **M** (Maven) no menu lateral direito da tela.
2. Vá para `Lifecycle` e execute `Install`.
3. Clique em `Download Sources` nos símbolos superiores.
4. Clique em `Reload All Maven Projects` para garantir que todas as dependências sejam carregadas corretamente.

## 4. Acessar o Console do H2 Database
Após rodar a aplicação, acesse o console do banco H2 pelo navegador:

🔗 [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

Utilize as seguintes credenciais para login:
- **JDBC URL:** `jdbc:h2:mem:bradescodb`
- **User:** `bradesco`
- **Password:** `12345`


