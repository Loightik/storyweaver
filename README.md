# StoryWeaver

Кратко: npc, диалог, квест, история, rpg

Это **твой** сгенерированный плагин. Ниже — только то, что нужно тебе для сборки, теста и выкладки на Spigot.

## Что внутри

| Путь | Зачем |
|------|--------|
| `src/main/java/` | исходники |
| `src/main/resources/plugin.yml` | имя, команды, права |
| `src/main/resources/config.yml` | настройки + Discord/Telegram |
| `src/main/resources/messages.yml` | тексты EN/RU |
| `target/storyweaver-1.1.jar` | готовый JAR на сервер |
| `publish/` | всё для Spigot и GitHub (отдельно, чтобы корень не засорять) |

Концепт: `storynpc` · Категория: Misc · Цена: **FREE**

Поддерживаемые MC (из research): `1.16`, `1.17`, `1.18`, `1.19`, `1.20`, `1.21`, `26.2`  
Java: **17+** · api-version: **1.16**


## Быстрый старт

```bash
mvn clean package
```

JAR кладёшь в `plugins/` на Paper/Spigot, рестарт, правишь `plugins/StoryWeaver/config.yml`.

Основные команды:
- `/storyweaver`

## Куда смотреть перед публикацией

Всё в папке **`publish/`**:

1. `FORM_RU.md` — заполнение формы Spigot по пунктам  
2. `DESCRIPTION_RU.bbcode` — текст в Description (без `[B]`; подсказки `(жирный)` / `(наклонный)`)  
3. `DOCUMENTATION_RU.bbcode` — Documentation (команды с описанием функций)  
4. `OVERVIEW_RU.md` — подробная суть для тебя  
5. `icon.png` — иконка 96×96 (инициалы + градиент), рядом `icon.jpg`  
6. `GITHUB_RU.md` — как сделать репозиторий и зачем  
7. `tagline_ru.txt` — Tag Line  

Английские варианты рядом (`FORM_EN.md`, `DESCRIPTION_EN.bbcode`, …). В EN тоже подсказки на русском — удобнее удалять вручную.

## Права

- `storyweaver.use`
- `storyweaver.admin`
- `storyweaver.reload`
- `storyweaver.bypass`
- `storyweaver.notify`

## Заметка

Автор: Loightik.
