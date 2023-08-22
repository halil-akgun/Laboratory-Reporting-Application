# Laboratuvar Raporlama Uygulaması Kurulum ve Çalıştırma Kılavuzu

Bu belge, Spring Boot ve React kullanarak geliştirdiğiniz laboratuvar raporlama uygulamanızı kurma ve çalıştırma
adımlarını içerir. İşte adım adım kılavuz:

## Gereksinimler

- Java Development Kit (JDK) 11 veya daha yeni sürüm
- Node.js ve npm (Node Package Manager)
- Git (isteğe bağlı)

## Adım 1: Projeyi İndirme

GitHub reposunu klonlayarak projeyi bilgisayarınıza indirebilirsiniz. Git kullanmıyorsanız, projenin GitHub
sayfasından "Download ZIP" seçeneğiyle indirebilirsiniz.

```bash
git clone https://github.com/sizin-proje-repo-linki.git
cd proje-klasoru
```

## Adım 2: Sunucu (Spring Boot) Kısmının Kurulumu ve Çalıştırılması

1. `server` klasörüne gidin:

   ```bash
   cd server
   ```
2. Spring Boot uygulamanızı başlatın:
   ```bash
   ./mvnw spring-boot:run
   ```

## Adım 3: İstemci (React) Kısmının Kurulumu ve Çalıştırılması

1. `client` klasörüne gidin:
   ```bash
   cd ../client
   ```
2. Bağımlılıkları yüklemek için aşağıdaki komutu çalıştırın:
   ```bash
   npm install
   ```
3. React uygulamanızı başlatın:
   ```bash
   npm start
   ```

Bu komut, React uygulamanızı başlatır ve varsayılan olarak tarayıcınızda `http://localhost:3000` adresinde görüntüler.

## Uygulamayı Kullanma

Artık uygulamanızı başarıyla başlattınız. Tarayıcınızda `http://localhost:3000` adresine giderek uygulamanıza erişebilir
ve kullanmaya başlayabilirsiniz.

## Sonuç

Bu kılavuz, Spring Boot ve React ile geliştirdiğiniz laboratuvar raporlama uygulamanızı kurma ve çalıştırma adımlarını
içerir. Projeyi başarıyla çalıştırdıktan sonra, uygulamanızı geliştirmeye devam edebilirsiniz.

Not: Proje bağımlılıkları ve sistem gereksinimleri değişebilir, bu yüzden en son dokümantasyona ve projenizin
gereksinimlerine göre ayarlamalar yapmayı unutmayın.