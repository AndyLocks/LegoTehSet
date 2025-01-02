# Lego Teh Set

[![gpl](https://img.shields.io/badge/gpl-f9e2af?style=for-the-badge&label=license&labelColor=black)](https://github.com/AndyLocks/LegoTehSet/blob/master/LICENSE)
[![discord](https://img.shields.io/badge/discord-89b4fa?style=for-the-badge&logo=discord&logoColor=white&labelColor=black)]()
[![JDA](https://img.shields.io/badge/JDA-cba6f7?style=for-the-badge&logo=discord&logoColor=white&labelColor=black)](https://github.com/discord-jda/JDA)
[![rebrickable](https://img.shields.io/badge/Rebrickable-fab387?style=for-the-badge&logoColor=white&label=Lego%20API&labelColor=black)](https://rebrickable.com/api/)

[![invite](https://img.shields.io/badge/Bot-f38ba8?style=for-the-badge&logoColor=white&label=Invite&labelColor=black)](https://discord.com/oauth2/authorize?client_id=1015539392393252924)
<p align="center"><img src="images/lts_logo.jpg"></p>

***<p align="center">A bot that will help you find lego sets directly in discord chat</p>***

<p align="center"><img src="images/profile.png"></p>

## Description

This bot automates the search and speeds it up. Using one command, you can request several Lego sets at once directly in the discord chat.

## Commands
- **search** - show all found sets by request

  ![search_command](images/search_command.png)

  There are arrows for scrolling:

  ![buttons](images/search_command_buttons.png)

  The page button helps you enter the desired page:

  ![page_button](images/page_button.png)

  There is also a sorting option:

  ![sorting_option](images/sorting_option.png)

- **random** - get random set

  ![random](images/random_command.png)

  It is possible to choose the theme of the set:

  ![themes](images/random_themes.png)

- **favourites** - show my favourite sets

  ![favourites](images/favourites.png)

  Same control buttons as in the `search` command.

- **add_favourite** - mark a set as a favourite

  ![add_favourite](images/add_favourite.png)

## Lego API

[Rebrickable](https://rebrickable.com/api/)
> Rebrickable provides a number of API/Web Services to assist developers build their own websites or apps which use the Rebrickable database.

## Invite the bot

<a href="https://discord.com/oauth2/authorize?client_id=1015539392393252924">Authorization</a>

## Build

How to run a bot locally on your computer

### Clone this repository

```bash
git clone https://github.com/AndyLocks/LegoTehSet
```

### Configuration

1. Rename `.env.example` to `.env`
2. Write a discord bot token in the `TOKEN` column

   Example: `TOKEN=asSkDSfjwDebt.AbobAkdkjdOnbwdslkfjwelkhfgnkoAJSsdDasdWdHKPjsdkfhn.LKDSJlksdflkjDFlksdjf`
3. Write a [Rebrickable api](https://rebrickable.com/api/) token in the `REBRICKABLE_API_KEY` column

### Install docker and docker compose

Here you can read how to install [Docker compose](https://docs.docker.com/compose/install/).

Here you can read how to install [Docker engine](https://docs.docker.com/engine/install/).

### Run project

```bash
docker compose up --build
```

## Architecture

![Architecture](images/architecture.png)

### LTS

The basic microservice. Manages the bot logic, located in the `.` folder.

### Profile hub

Profile microservice. Saves users' favorite sets and adds new ones. Located in the `profile_hub` folder.
