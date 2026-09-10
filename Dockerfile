# Step 1: Build file .war bằng Maven và Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml và toàn bộ thư mục src vào container
COPY pom.xml .
COPY src ./src

# Đóng gói dự án thành file .war (bỏ qua unit test để build nhanh hơn)
RUN mvn clean package -DskipTests

# Step 2: Chạy ứng dụng trên máy chủ Apache Tomcat 10
FROM tomcat:10.1-jdk17-temurin

# Xóa các ứng dụng mặc định của Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file WAR từ bước build sang thư mục webapps của Tomcat với tên ROOT.war
# Tên ROOT.war giúp bạn truy cập ứng dụng trực tiếp từ trang gốc (https://app-cua-ban.onrender.com)
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Mở cổng 8080 cho Tomcat
EXPOSE 8080

# Lệnh khởi chạy Tomcat
CMD ["catalina.sh", "run"]