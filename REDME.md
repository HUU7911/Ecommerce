# He thong thuong mai dien tu thiet ke theo kien truc monolithic

# ▶️ Running the Project

## 1. Clone project

```bash
git clone https://github.com/HUU7911/Ecommerce.git
```

```bash
cd Ecomerce
```

---

## 2. Start Infrastructure

```bash
docker compose up -d
```

Kiểm tra:

```bash
docker ps
```

---

## 3. Build Project

Nếu sử dụng Maven:

```bash
mvn clean install
```

Hoặc:

```bash
./mvnw clean install
```

---

## 4. Run Services

Có thể chạy từng service bằng Maven:

```bash
cd discovery-service
mvn spring-boot:run
```

Sau đó:

```bash
cd ../identity-service
mvn spring-boot:run
```

```bash
cd ../product-service
mvn spring-boot:run
```

```bash
cd ../order-service
mvn spring-boot:run
```
---