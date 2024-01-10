# Используйте OpenJDK 17 в качестве базового образа
FROM eclipse-temurin:17

# Указание пользователя
USER nobody:nogroup

# Определяем порт, на котором будет работать приложение
EXPOSE 8080

# Копирование JAR-файла в образ
COPY target/jira-1.0.jar /app/jira-1.0.jar

# Рабочий каталог
WORKDIR /app

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/jira-1.0.jar"]