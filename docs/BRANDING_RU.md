# Брендинг FlameLauncher

FlameLauncher — модификация для Era of Flame, развиваемая ejikflame.
Пользовательские названия, баннеры, ссылки на исходники, названия релизов
и изображения GUI относятся к FlameLauncher.

## Происхождение и лицензии

Сервер и ядро основаны на GravitLauncher (GPLv3), который происходит
от Launcher sashok724. Лицензия ядра находится в корневом LICENSE.
GUI основан на GravitLauncher/LauncherRuntime; точный commit и лицензия
MIT указаны в components/launcher-gui/UPSTREAM.md и LICENSE.
Уведомления об авторских правах и лицензии сторонних шрифтов сохранены.
Новые изменения не означают авторство исходного стороннего кода.

## Сохранённые технические идентификаторы

* Пакеты pro.gravit, имена классов в манифестах и правила ProGuard:
  нужны для совместимости API и модулей.
* Внешняя зависимость com.gravitlauncher.launcher:socketbridge:
  используется под исходными координатами сторонней библиотеки.
  Публикации собственных компонентов используют io.github.ejikflame.flamelauncher.
* https://maven.gravitlauncher.com/ — источник сторонних зависимостей.
* https://mirror.gravitlauncher.com/ — существующие зеркала файлов.
  Не заменяйте их на несуществующий адрес FlameLauncher.
* modules — сторонний подмодуль GravitLauncher/LauncherModules.
* GravitCentralRootCA.crt — историческое имя сертификата в коде его удаления.

Это ссылки совместимости и происхождения, а не бренд или владелец FlameLauncher.

## Регистрация и восстановление пароля

В новом конфиге JavaRuntime createAccountURL и forgotPassURL не заданы.
Соответствующие элементы GUI скрыты до настройки реальных страниц проекта.
Сервис регистрации этим изменением не создаётся.
В существующем конфиге JavaRuntime вручную замените прежние URL на свои
или задайте null, затем пересоберите клиент в консоли LaunchServer командой build.
В LaunchServer.json задайте projectName: Era of Flame.

## Обновление установки

Изменения находятся в feat/flame-gui. После git pull пересоберите дистрибутив,
обновите JavaRuntime.jar и каталог runtime на сервере, сохранив рабочие
конфиги и ключи, затем выполните build и замените Launcher.jar на Windows.
Уже установленный клиент автоматически от одного изменения GitHub не меняется.
