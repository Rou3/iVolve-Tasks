# Lab 21: Role-based Authorization in Jenkins

## Objective
The goal of this lab is to configure **role-based access control (RBAC)** in Jenkins by creating users with different permission levels:

- **user1** → Admin role
- **user2** → Read-only role

---

## Steps

### 1. Access Jenkins
1. Open your Jenkins server in a web browser:
```

http://<JENKINS_HOST>:8080

```
2. Log in with an admin account.

---

### 2. Enable Security
1. Go to **Manage Jenkins → Configure Global Security**.
2. Check **Enable security**.
3. Under **Authorization**, select **Project-based Matrix Authorization Strategy** or **Role-Based Strategy** (depending on plugins available).
4. Save changes.

---

### 3. Create Users
1. Go to **Manage Jenkins → Manage Users → Create User**.
2. Create **user1**:
- Username: `user1`
- Password: `<choose_password>`
- Full Name: `User One`
- Email: `<email>`
3. Create **user2**:
- Username: `user2`
- Password: `<choose_password>`
- Full Name: `User Two`
- Email: `<email>`

<img width="1008" height="368" alt="Lab1-J1" src="https://github.com/user-attachments/assets/07576beb-0954-429a-824f-6292f849209c" />

---

### 4. Assign Roles / Permissions

#### Option A: Using Project-based Matrix Authorization
1. Go to **Manage Jenkins → Configure Global Security → Authorization → Matrix-based security**.
2. Assign permissions:
- **user1** → Check all boxes (Admin role)
- **user2** → Only check **Overall → Read** and optionally **Job → Read** (Read-only role)
3. Save changes.

<img width="1238" height="390" alt="Jenkins" src="https://github.com/user-attachments/assets/0d0c77c3-fad1-4d39-9b65-f00baee9e484" />

---

### 5. Verify Access
1. Log out as admin.
2. Log in as **user1**:
- You should be able to create jobs, configure Jenkins, and manage plugins.
3. Log in as **user2**:
- You should only be able to view jobs and configurations.
- Cannot modify jobs, plugins, or settings.

---

## Notes
- Make sure you have **Role Strategy plugin** installed for easier role management.
- Always test user permissions after assigning roles to ensure RBAC works correctly.
- Read-only users should never have **Admin** or **Job Modify** permissions unless intended.

---

## References
- [Jenkins RBAC Documentation](https://www.jenkins.io/doc/book/security/authorization/)
- [Role Strategy Plugin](https://plugins.jenkins.io/role-strategy/)
