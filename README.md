# Lego Teh Set

[![gpl](https://img.shields.io/badge/gpl-f9e2af?style=for-the-badge&label=license&labelColor=black)](https://github.com/AndyLocks/LegoTehSet/blob/master/LICENSE)
[![discord](https://img.shields.io/badge/discord-89b4fa?style=for-the-badge&logo=discord&logoColor=white&labelColor=black)]()
[![JDA](https://img.shields.io/badge/JDA-cba6f7?style=for-the-badge&logo=discord&logoColor=white&labelColor=black)](https://github.com/discord-jda/JDA)
[![rebrickable](https://img.shields.io/badge/Rebrickable-fab387?style=for-the-badge&logoColor=white&label=Lego%20API&labelColor=black)](https://rebrickable.com/api/)

[![invite](https://img.shields.io/badge/Bot-f38ba8?style=for-the-badge&logoColor=white&label=Invite&labelColor=black)](https://discord.com/oauth2/authorize?client_id=1015539392393252924)
<p align="center"><img src="https://cdn.discordapp.com/app-icons/1015539392393252924/6838c1cfa6cf3d73cc5a0d37991e4d13.png"></p>

***<p align="center">A bot that will help you find lego sets directly in discord chat</p>***

<p align="center"><img src="https://github.com/AndyLocks/LegoTehSet/assets/112815007/8bb662ce-86c7-403f-b8fe-877f73ab94dd"></p>

## Description
This bot automates the search and speeds it up. Using one command, you can request several Lego sets at once directly in the discord chat.
## Commands
- **search** - show all found sets by request

  ![изображение](https://github.com/AndyLocks/LegoTehSet/assets/112815007/87ea057b-229a-49a7-9e58-6b114b7e2a64)

  There are arrows for scrolling.

  ![изображение](https://github.com/AndyLocks/LegoTehSet/assets/112815007/e4cb5635-cf8e-4ca1-9236-d47d0246a007)

  The page button helps you enter the desired page

  ![изображение](https://github.com/AndyLocks/LegoTehSet/assets/112815007/a78f588e-13ba-4cef-ba62-257e0a7b5fe7)

  There is also a sorting option

  ![изображение](https://github.com/AndyLocks/LegoTehSet/assets/112815007/ce3e4156-10e7-4407-8318-940a391ddde8)

- **random** - get random set

  ![изображение](https://github.com/AndyLocks/LegoTehSet/assets/112815007/49b5c6f0-11d5-412b-b3d3-8ec3703216a7)

  It is possible to choose the theme of the set

  ![изображение](https://github.com/AndyLocks/LegoTehSet/assets/112815007/a359144b-a8cb-4796-8680-8c342b0ac941)

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