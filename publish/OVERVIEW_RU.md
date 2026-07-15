# Суть плагина: StoryWeaver

Автор: **Loightik**
Концепт: `storynpc` · Категория: Misc

## Зачем он нужен

npc, диалог, квест, история, rpg

## Подробно (читай перед публикацией)

StoryWeaver (жирный)
StoryWeaver | ветвящиеся диалоги NPC
Ветвящиеся диалоги NPC: условия, предметы и флаги квестов.

Автор: Loightik
Совместимость: Paper/Spigot 1.16, 1.17, 1.18, 1.19, 1.20, 1.21, 26.2
Теги: npc, диалог, квест, история, rpg

О плагине (жирный)
StoryWeaver - маленький сюжетный движок: деревья диалогов в YAML, условия (права, предмет, флаг, шанс), GUI ответов, флаги квестов и небольшой API.

RP нужны NPC с нормальными ветками, а не одной строкой.

Возможности (жирный)
• Деревья диалогов в YAML
• Условия: права, предмет, флаг, шанс
• GUI с ответами
• Флаги квестов и небольшой API
• Прогресс на игрока

Команды (жирный)
Использование: /storyweaver [admin|reload|help]
/storyweaver - меню диалогов / флагов
/storyweaver admin - админ-инструменты и диагностика
/storyweaver reload - перезагрузить config.yml и messages.yml без рестарта
/storyweaver help - краткая справка

Права (жирный)
storyweaver.use - базовые команды - default: true
storyweaver.admin - админ-команды - default: op
storyweaver.reload - разрешить reload - default: op

Конфигурация (жирный)
1. JAR в /plugins, рестарт
2. Подкрути config.yml и messages.yml
3. /storyweaver reload

Поддержка (жирный)
Пришли версию сервера, версию плагина, полный стектрейс. Discussion или Issues. Автор: Loightik.

Перед публикацией замени плейсхолдеры скриншотов. (наклонный)


## Документация (команды / права)

Команды (жирный)
Корень: /storyweaver
/storyweaver - меню диалогов / флагов
/storyweaver admin - админ-инструменты и диагностика
/storyweaver reload - перезагрузить config.yml и messages.yml без рестарта
/storyweaver help - краткая справка

Права (жирный)
storyweaver.use - базовые команды - default: true
storyweaver.admin - админ-команды - default: op
storyweaver.reload - разрешить reload - default: op

Что внутри (жирный)
• dialogue
• conditions
• npc-gui
• flags
• api

Конфиг (жирный)
• Сначала дефолты - убедись что JAR встаёт
• После правок: /storyweaver reload
• Автор: Loightik


---

Этот файл для тебя (автора). На Spigot вставляй DESCRIPTION_RU / DOCUMENTATION_RU.
Подсказки `(жирный)` / `(наклонный)` — выдели в редакторе и удали.
