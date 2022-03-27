## Allure
* Запустить тесты
* ``./gradlew allureReport``
* ``./gradlew allureServe``

## Unit тесты 
``./gradlew unitTest``

## Integration тесты 
``./gradlew integrationTest``

## E2E тесты 
### Локально
* Поднять приложение на *localhost* (описание в корне репозитория)
* ``./gradlew e2eTest``
### C помощью Selenoid
* Узнать IP машины (``hostname -I`` на ubuntu). Далее предполагается что он равен 172.17.0.1
* Поднять приложение на *172.17.0.1* (описание в корне репозитория). Для этого в нужно следовать описанию в корне репозитория, заменив *localhost* на *172.17.0.1*
* Поднять [selenoid configuration manager](https://aerokube.com/cm/latest/) 
и узнать его url. Далее предполагается что он равен *http://localhost:4444*
* ``./gradlew e2eTest
  -Dselenide.host=172.17.0.1 
  -Dselenide.remote=http://localhost:4444/wd/hub`` 