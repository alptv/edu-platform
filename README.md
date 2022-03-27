## Локальная сборка
Из корня репозитория (для ubuntu):
* экспортировать переменную окружения ``HOST=localhost`` (``export HOST=localhost`` для ubuntu)
* ``docker-compose build``
* ``docker-compose up``
  

Остановка:
* ``docker-compose down``
  
## В директориях *client* и *server* есть описания по сборке и тестированию

## Homeworks
* **ДЗ-1**
  * React клиент 
  * Взаимодействие с сервером
  * Unit/Component/e2e тесты
  * Авторизация/Регистрация
  * Тесты на авторизацию/регистрацию
  * Несколько наборов тестов
* **Homework-2** 
  * Spring сервер
  * Postgresql база данных
  * Взаимодействие с клиентом
  * Test containers
  * Mockito
  * Unit/Component/Integration тесты
  * Тесты на авторизацию/регистрацию

* **Homework-3** 
   * Github actions для клиента
   * Github actions для сервера

* **Homework-4** 
  * Поддержка allure

* **Homework-7** 
  * Selenide
  * Selenoide
  * Allure на добавленные тесты
  * Запуск добавленных тестов на github actions