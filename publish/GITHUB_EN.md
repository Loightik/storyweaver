# GitHub + Spigot Source Code (StoryWeaver)

Owner on the form: **Loightik** (always).

## Fill the GitHub form

1. **Owner** - Loightik  
2. **Repository name** - `storyweaver`  
3. **Description** - from `publish/tagline_en.txt`  
4. **Visibility** - Public  
5. **Add README** - Off  
6. **Add .gitignore** - Java  
7. **Add license** - MIT License  
8. Create repository

## Push (PowerShell)

If `git` is not in PATH, use: `$git = "C:\Program Files\Git\cmd\git.exe"` then `& $git ...`

```powershell
cd C:\Users\Миша\OneDrive\Desktop\Spigot\plugin-forge-ai\output\plugins\storyweaver
$git = "C:\Program Files\Git\cmd\git.exe"
& $git init
& $git add .
& $git commit -m "Initial StoryWeaver 1.0"
& $git branch -M main
& $git remote add origin https://github.com/Loightik/storyweaver.git
& $git pull origin main --allow-unrelated-histories
# if .gitignore conflict: keep both useful rules, then:
# & $git add .gitignore
# & $git commit -m "Merge GitHub LICENSE/gitignore with StoryWeaver sources"
& $git push -u origin main
& $git tag v1.0
& $git push origin v1.0
```

Spigot **Source Code**: `https://github.com/Loightik/storyweaver`  
Support: `https://github.com/Loightik/storyweaver/issues`
