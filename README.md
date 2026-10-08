# FlameLauncher

Лаунчер Minecraft для проекта **Era of Flame**, развиваемый **ejikflame**.

* [See license](LICENSE)
* [See code of conduct](CODE_OF_CONDUCT.md)
* [Репозиторий и поддержка FlameLauncher](https://github.com/ejikflame/FlameLauncher)
* [Происхождение компонентов и лицензии](docs/BRANDING_RU.md)
* [Локальная сборка и проверка интерфейса](docs/LOCAL_TEST_RU.md)

GUI разрабатывается в отдельной ветке `feat/flame-gui`. Его исходники и
оформление находятся в `components/launcher-gui` и редактируются прямо
в этом репозитории.

## Скачать FlameLauncher

* [Скачать актуальные исходники FlameLauncher (ZIP, ветка master)](https://github.com/ejikflame/FlameLauncher/archive/refs/heads/master.zip)
* [Релизы FlameLauncher](https://github.com/ejikflame/FlameLauncher/releases)

Готовые сборки пока не опубликованы. ZIP содержит исходники, а не готовый
лаунчер или установщик. Подмодули не включены в ZIP; для сборки скачайте
проект через Git вместе с зависимостями:

```sh
git clone --branch feat/flame-gui --recurse-submodules https://github.com/ejikflame/FlameLauncher.git
```
