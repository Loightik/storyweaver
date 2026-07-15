# GitHub и поле Source Code (StoryWeaver)

Владелец **всегда**: **Loightik**.

## Форма New repository

1. **Owner** - Loightik  
2. **Repository name** - `storyweaver`  
3. **Description** - из `publish/tagline_en.txt` / `tagline_ru.txt`  
4. **Public**  
5. **Add README** - Off  
6. **Add .gitignore** - Java  
7. **Add license** - MIT License  
8. Create repository

## Залить код (PowerShell)

Если команда `git` не находится:

```powershell
$git = "C:\Program Files\Git\cmd\git.exe"
```

Потом:

```powershell
cd C:\Users\Миша\OneDrive\Desktop\Spigot\plugin-forge-ai\output\plugins\storyweaver
$git = "C:\Program Files\Git\cmd\git.exe"
& $git init
& $git add .
& $git commit -m "Initial StoryWeaver 1.0"
& $git branch -M main
& $git remote add origin https://github.com/Loightik/storyweaver.git
& $git pull origin main --allow-unrelated-histories
# при конфликте .gitignore - объедини правила, затем:
# & $git add .gitignore
# & $git commit -m "Merge GitHub LICENSE/gitignore with StoryWeaver sources"
& $git push -u origin main
& $git tag v1.0
& $git push origin v1.0
```

В Spigot:

- **Source Code** → `https://github.com/Loightik/storyweaver`  
- **Support** → `https://github.com/Loightik/storyweaver/issues`
