# Laboratuvar Raporlama Uygulaması Kurulum ve Çalıştırma Kılavuzu

Bu belge, Spring Boot ve React kullanarak geliştirilen laboratuvar raporlama uygulamasını kurma ve çalıştırma
adımlarını içerir. İşte adım adım kılavuz:

## Gereksinimler

- Java Development Kit (JDK) 17 veya daha yeni sürüm
- Node.js ve npm (Node Package Manager)
- Git (isteğe bağlı)

## Adım 1: Projeyi İndirme

GitHub reposunu klonlayarak projeyi bilgisayarınıza indirebilirsiniz. Git kullanmıyorsanız, projenin GitHub
sayfasından "Download ZIP" seçeneğiyle indirebilirsiniz.

```bash
git clone https://github.com/halil-akgun/laboratory-reporting-application.git
```

## Adım 2: Sunucu (Spring Boot) Kısmının Kurulumu ve Çalıştırılması

1. `laboratory-reporting-application` klasörüne gidin:

   ```bash
   cd laboratory-reporting-application
   ```
2. Spring Boot uygulamasını başlatın:
   ```bash
   ./mvnw spring-boot:run
   ```

## Adım 3: İstemci (React) Kısmının Kurulumu ve Çalıştırılması

1. `laboratory-reporting-frontend` klasörüne gidin:
   ```bash
   cd laboratory-reporting-frontend
   ```
2. Bağımlılıkları yüklemek için aşağıdaki komutu çalıştırın:
   ```bash
   npm install
   ```
3. React uygulamasını başlatın:
   ```bash
   npm start
   ```

Bu komut, React uygulamasını başlatır ve varsayılan olarak tarayıcınızda `http://localhost:3000` adresinde görüntüler.

## Uygulamayı Kullanma

Artık uygulamayı başarıyla başlattınız. Tarayıcınızda `http://localhost:3000` adresine giderek uygulamaya erişebilir
ve kullanmaya başlayabilirsiniz.

## Sonuç

Bu kılavuz, Spring Boot ve React ile geliştirilen laboratuvar raporlama uygulamasını kurma ve çalıştırma adımlarını
içerir. Projeyi başarıyla çalıştırdıktan sonra, uygulamayı geliştirmeye devam edebilirsiniz.

Not: Proje bağımlılıkları ve sistem gereksinimleri değişebilir, bu yüzden en son dokümantasyona ve projenin
gereksinimlerine göre ayarlamalar yapmayı unutmayın.