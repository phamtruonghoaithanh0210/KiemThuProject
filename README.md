# KiemThuProject
- Bài tập lớn môn kiểm thử:
- Sử dụng công nghệ: JavaFX , MySQL, jUnit 

# Giảng viên Hướng Dẩn - Sinh viên thực hiện :
- Giảng viên hướng dẩn : 
  - Thầy Dương Hữu Thành 
- Sinh viên thực hiện :
  - Phạm Trương Hoài Thanh
  - Phan Thanh Vĩ
  - Huỳnh Linh Khôi
  - Võ Thị Kim Yến

# Bảng Tiến Độ Dự Án
| Nội Dung                 | Ngày          |
| ------------------------ |:-------------:|
| update README và UML     | 30/3/2021     |
| Create Database          | 2/4/2021     |
| Update CLass and connect to database          | 4/4/2021     |
| Update Serice and Test Package          | 5/4/2021     |

## Ghi Chú:
- thay đổi namedatabase, usename, passworld của MySql trên máy của bạn !

```java
 public static Connection getconn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost/mobiledb", "root", "1851050129");
    }
```
