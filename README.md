## 📌 API 명세서
### 📅 일정 API

| 기능       | Method | URL                     | 상태코드               |
| -------- | ------ | ----------------------- | ------------------ |
| 일정 생성    | POST   | /schedules              | 201, 400           |
| 일정 전체 조회 | GET    | /schedules              | 200                |
| 일정 단건 조회 | GET    | /schedules/{scheduleId} | 200, 404           |
| 일정 수정    | PATCH  | /schedules/{scheduleId} | 200, 400, 401, 404 |
| 일정 삭제    | DELETE | /schedules/{scheduleId} | 204, 401, 404      |

<details> <summary><strong>일정 생성</strong></summary>

### Request

- POST /schedules

```
ex)
{
"title": "title",
"content": "content",
"userId": 1
}
```

### Response

- 201 Created
```
ex)
{
"id": 1,
"title": "title",
"content": "content",
"userId": 1,
"createdAt": "2026-04-16T00:00:00",
"updatedAt": "2026-04-16T00:00:00"
}
```

### Error
- 400 Bad Request (필수값 누락)
</details>

<details> <summary><strong>일정 전체 조회</strong></summary>

### Request

- GET /schedules

### Response

- 200 OK
```
ex)
[
{
"id": 1,
"title": "title",
"content": "content",
"userId": 1,
"createdAt": "2026-04-16T00:00:00",
"updatedAt": "2026-04-16T00:00:00"
},
{
"id": 2,
"title": "title2",
"content": "content2",
"userId": 1,
"createdAt": "2026-04-16T00:00:00",
"updatedAt": "2026-04-16T00:00:00"
}
]
```
</details>
<details> <summary><strong>일정 단건 조회</strong></summary>

### Request

- GET /schedules/{scheduleId}

### Response

- 200 OK

```
ex)
{
"id": 1,
"title": "title",
"content": "content",
"userId": 1,
"createdAt": "2026-04-16T00:00:00",
"updatedAt": "2026-04-16T00:00:00"
}
```

### Error
- 404 Not Found (일정 없음)
</details>
<details> <summary><strong>일정 수정</strong></summary>

### Request

- PATCH /schedules/{scheduleId}

```
ex)
{
"title": "title",
"content": "content"
}
```

### Response

- 200 OK

```
ex)
{
"id": 1,
"title": "title",
"content": "content",
"updatedAt": "2026-04-16T00:00:00"
}
```

### Error
- 400 Bad Request (요청값 오류)
- 401 Unauthorized (로그인 필요)
- 404 Not Found (일정 없음)
</details>
<details> <summary><strong>일정 삭제</strong></summary>

### Request

- DELETE /schedules/{scheduleId}

### Response

- 04 No Content

### Error
- 401 Unauthorized (로그인 필요)
- 404 Not Found (일정 없음)
</details>

### 👤 유저 API

| 기능       | Method | URL             | 상태코드               |
| -------- | ------ | --------------- | ------------------ |
| 유저 생성    | POST   | /users          | 201, 400           |
| 유저 전체 조회 | GET    | /users          | 200                |
| 유저 단건 조회 | GET    | /users/{userId} | 200, 404           |
| 유저 수정    | PUT    | /users/{userId} | 200, 400, 401, 404 |
| 유저 삭제    | DELETE | /users/{userId} | 204, 401, 404      |

<details> <summary><strong>유저 생성</strong></summary>

### Request

- POST /users
```
ex)
{
"name": "name",
"email": "email@email.com",
"password": "password"
}
```
### Validation
- password: 8자 이상 필수
- email: 이메일 형식 필요

### Response

- 201 Created

```
ex)
{
"id": 1,
"name": "name",
"email": "email@email.com",
"createdAt": "2026-04-16T00:00:00",
"updatedAt": "2026-04-16T00:00:00"
}
```
### Error
- 400 Bad Request (필수값 누락)
</details>
<details> <summary><strong>유저 전체 조회</strong></summary>

### Request

- GET /users

### Response

- 200 OK

```
ex)
[
{
"id": 1,
"name": "name",
"email": "email@email.com",
"createdAt": "2026-04-16T00:00:00",
"updatedAt": "2026-04-16T00:00:00"
},
{
"id": 2,
"name": "name2",
"email": "email@email.com2",
"createdAt": "2026-04-16T00:00:00",
"updatedAt": "2026-04-16T00:00:00"
}
]
```
</details>
<details> <summary><strong>유저 단건 조회</strong></summary>

### Request

- GET /users/{userId}

### Response

- 200 OK
```
ex)
{
"id": 1,
"name": "name",
"email": "email@email.com",
"createdAt": "2026-04-16T00:00:00",
"updatedAt": "2026-04-16T00:00:00"
}
```
### Error
- 404 Not Found (유저 없음)
</details>
<details> <summary><strong>유저 수정</strong></summary>

### Request

- PUT /users/{userId}
```
ex)
{
"name": "test",
"email": "test"
}
```
### Response

- 200 OK

```
ex)
{
"id": 1,
"name": "test",
"email": "test",
"updatedAt": "2026-04-16T00:00:00"
}
```
### Error
- 400 Bad Request (요청값 오류)
- 401 Unauthorized (로그인 필요)
- 404 Not Found (유저 없음)
</details>
<details> <summary><strong>유저 삭제</strong></summary>

### Request

- DELETE /users/{userId}

### Response

- 204 No Content

### Error
- 401 Unauthorized (로그인 필요)
- 404 Not Found (유저 없음)
</details>

### 🔐 인증 API

| 기능   | Method | URL     | 상태코드          |
| ---- | ------ | ------- | ------------- |
| 회원가입 | POST   | /signup | 201, 400      |
| 로그인  | POST   | /login | 200, 400, 401 |
| 로그아웃 | POST   | /logout | 200           |

<details> <summary><strong>로그인</strong></summary>

### Request

- POST /auth/login
```
ex)
{
"email": "email@email.com",
"password": "password"
}
```
### Response

- 200 OK

```
ex)
{
"userId": 1,
"email": "email@email.com"
}
```
### Behavior
- 세션 생성
  (Set-Cookie: JSESSIONID=...)
### Error
- 400 Bad Request (요청값 오류)
- 401 Unauthorized (이메일 또는 비밀번호 불일치)
</details>
<details> <summary><strong>로그아웃</strong></summary>

### Request

- POST /auth/logout

### Response

- 200 OK

### Behavior
세션 무효화
</details>