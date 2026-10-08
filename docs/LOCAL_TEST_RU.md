# Локальная проверка FlameLauncher

В репозитории есть LaunchServer (сервер авторизации, профилей и обновлений) и
ядро клиентского лаунчера. Это не сам игровой сервер Minecraft.

Графический интерфейс скопирован в components/launcher-gui из
GravitLauncher/LauncherRuntime, commit
`bef1c22e545d7d68af6acc99c0284ebc78a707c8` (JavaRuntime 5.0.9 для ядра 5.7.13).
Он использует JavaFX, включает вход, выбор сервера, загрузку клиента и настройки.
Исходная лицензия MIT сохранена; лицензия шрифта находится в runtime/styles.

## Подготовка

Установи Git и JDK 21 с JavaFX для своей ОС (например, вариант Full у Liberica).
Проверь `java -version`. Для сборки Gradle отдельно скачивает JavaFX 22;
для запуска интерфейса нужна Java с JavaFX. Не используй готовый чужой JAR.

```sh
git clone --recurse-submodules https://github.com/ejikflame/FlameLauncher.git
cd FlameLauncher
git submodule update --init --recursive
```

Если проверяешь PR до слияния, сначала переключись на его ветку
`feat/flame-gui`, затем повтори обновление подмодулей.

## Сборка

Windows (PowerShell):

```powershell
.\gradlew.bat :components:launchserver:installDist
```

Linux/macOS:

```sh
sh ./gradlew :components:launchserver:installDist
```

Дистрибутив находится в `components/launchserver/build/install/launchserver`.
Скопируй его целиком в отдельную папку для теста. Он включает JavaRuntime.jar,
файлы дизайна runtime и modules.json с включённым только JavaRuntime.
При обновлении существующей установки не перезаписывай свои конфигурации:
добавь `JavaRuntime` к `loadLauncherModules` в своём modules.json.

## Первый запуск

Открой терминал в корне скопированного дистрибутива. На Windows запусти
`.\bin\launchserver.bat`, на Linux/macOS — `./bin/launchserver`.
Следуй вопросам первоначальной настройки. Используй адрес своего компьютера
`127.0.0.1`, а не публичный IP. В конфигурации `netty.address` укажи
`ws://127.0.0.1:9274/api`, если оставлен стандартный порт 9274.
В `netty.binds` замени адрес `0.0.0.0` на `127.0.0.1`, сохранив порт 9274,
и перезапусти сервер. Адрес в netty.address сам по себе не ограничивает
прослушивание. До этой настройки ограничь входящие подключения брандмауэром.
Это тестовый вариант без TLS, для публичного запуска требуются HTTPS/WSS.

В консоли LaunchServer выполни `build`. Полученный клиент ищи в папке
`build` тестовой установки; точный путь смотри в логе сборки. Запускай
полученный JAR через Java с JavaFX. На первом этапе проверь открытие окна.
По умолчанию используется RejectAuthCoreProvider: вход будет отклоняться,
пока не настроен собственный провайдер авторизации. Запуск Minecraft требует
отдельного профиля, клиента и assets, а также настроенной авторизации.

Изменять оформление можно в `components/launcher-gui/runtime` (FXML, CSS, картинки),
поведение интерфейса — в `components/launcher-gui/src/main/java`. Код и оформление — обычные файлы
твоего репозитория: меняй их и коммить в ветку feat/flame-gui.
Подмодуль LauncherRuntime не используется; отдельный fork GUI не нужен.

Подключение интерфейса не исправляет уязвимости из аудита. Не публикуй эту
тестовую установку до исправления проверки обновлений и распаковки архивов.
