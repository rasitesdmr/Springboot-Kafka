# 🎯 Apache Kafka ?

<img src="https://github.com/rasitesdmr/Springboot-Kafka/blob/master/images/kafka1.jpg">

* Apache Kafka, gerçek zamanlı veri yayınlama ve takip sistemi olarak tanımlanabilir. Apache Kafka, büyük veri
  uygulamaları için tasarlandı ve veri akışlarını yönetmek, depolamak ve işlemek için kullanılabilir. Apache Kafka,
  dağıtık bir yapıda çalışır ve verileri tüm sistemde eş zamanlı olarak yayınlamak ve takip etmek için kullanılabilir.

* Bir örnek üzerinden Kafka’nın ne olduğunu anlayalım;

* Sahibi olduğunuz bankada bir müşterinin kredi kartı hesabını kapatmak istediğini düşünelim.

* Bunun için; işlemlerin yapıldığı sunucudan bu kredi kartı ile alışveriş hizmeti sonlandırılmalı, sonlanan kredi
  kartında kalan miktar başka bir hesaba aktarılmalı (bu da başka bir sunucu üzerinden yapılmaktadır.), kredi kartına
  ait bilgiler veritabanı sunucusundan silinmeli, mail sunucusundan bu hesaba ait mail adresi silinmeli. Bunun gibi
  yapılacak olan işlemler sıralanabilir.

* Bu işlemlerin yapılması için tüm sunucuların birbirlerine bağlanması gerekmektedir ki bu da sunucuların üzerine fazla
  yük bindirmekte ve sunucuları yavaşlatmaktadır.

* Üstelik her bir sunucunun ürettiği veriler de farklı formatlarda (XML, JSON, CSV vs.), farklı iletim protokolleriyle (
  TCP, HTTP, FTP vs.) olabilir.

* Bu bağlantıları sağlamak da fazladan bir yüktür.

* Oysa bir veri havuzu olsa ve her istemci yapmak istediği işlemi bu havuza aktarsa, cevap verebilecek sunucu sadece onu
  ilgilendiren veri ile muhatap olmuş olur.

* İşte bu veri havuzuna benzetebileceğimiz yapı Apache Kafka’dır.

# 🎯 Apache Kafka Temel Kavramlar ?

<img src="https://github.com/rasitesdmr/Springboot-Kafka/blob/master/images/kafka5.png">

## 📌 Topic ve Partition

* Topic verilerin gönderilip alındığı veri kategorisinin adıdır.
* Kullanıcı tarafından isimlendirilirler.
* Bir Kafka cluster’ında binlerce topic olabilir.
* Topic’ler partition’lara ayrılırlar.
* Partition’lar 0’dan başlayarak artan sayıda devam eder.
* Topic’de 1 partition oluşturulabileceği gibi senaryoya göre bin partition da oluşturulabilir.
* Topic yaratırken verdiğimiz partition sayısını sonradan değiştirebiliriz.
* Veri bir kez bir partition’a yazıldıktan sonra bir daha değiştirilemez. (immutability)
* Partition’ların 0’dan başlayarak artan sayıda giden ID’leri vardır. Bunlara offset denir.
* Offset partition’la beraber bir anlam ifade eder, 5. offset diyemeyiz mesela, partition 1’nin 5. offset’i diyebiliriz.
* Her partition’da farklı sayıda offset olabilir.

## 📌 Partition ve Offset

* Partition’lar kendi içinde sıralıdır.
* Partition 0 için 2. offset’deki veri 3. offset’den önce yazılmıştır.
* Ama Partition 0’deki 5. offset’deki veri ile Partition 1’deki 5. offset’deki veriyi kıyaslayamayız, hangisinin daha
  önce yazıldığını bilme şansımız yoktur.
* Partition sayısı değiştirilmediği sürece Kafka, aynı key ile gönderilen her mesajın aynı partition’a düşeceğini
  garanti eder.
* Partition sayısı değiştirilirse bu garanti ortadan kalkar.
* Key ile veri göndermenin avantajı o veriye daha sonra aynı key ile sıralı bir şekilde erişmektir.
* Key ile gönderilmeyen her veri partition’lara rastgele bir şekilde dağıtılır.
* Kafka veriyi belli bir süre (varsayılan 1 hafta) muhafaza eder, daha sonra veri silinir.
* Ancak eski veri silinse de, partition’a yeni veriler gelince offset değeri kaldığı yerden devam eder, bir daha sıfıra
  dönmez.

## 📌 Broker

* Broker’lar Kafka Cluster’ı oluşturan sunuculardır.
* Her broker birer sayıdan oluşan ID ile tanımlıdır.
* Kafka Cluster oluşturulurken broker sayısı üç gibi bir sayıyla başlar, gereksinim arttıkça ilave edilir.
* Yüzün üstünde broker koşturan sistemler de vardır.
* Bağlandığımız sunucu bootstrap broker adını alır.
* Bootstrap broker’a bağlanınca tüm broker’ların bilgisinin tutulduğu metadata sayesinde diğer broker’ların adreslerine
  erişilir.
* Tüm kafka cluster’a bağlanmak için sadece bootstrap broker’a bağlanmak yeterlidir.
* Kafka dağıtık yapıda olduğu için tek bir broker topic’in tamamını alamaz.
* Her broker topic’in belli kısımlarını (partition) alır. Brokerlara partition’ların dağıtımı, eşit şekilde yapılmaya
  çalışılır.

## 📌 Broker’lara Topic Ve Partition’ların Dağılımı

<img src="https://github.com/rasitesdmr/Springboot-Kafka/blob/master/images/kafka3.png">

* 101, 102 ve 103 nolu üç broker’ımız olsun.
* Topic A üç partition’dan oluştuğu ve üç broker’ımız olduğu için her broker Topic A’ya ait birer partition’a sahip
  olur.
* Dikkatinizi çekerse partition’lar broker’lara sıralı bir şekilde dağıtılmıyor;
* 101 nolu broker’a 2. partition düşerken, 102’ye 0. partition, 103’e 1. partition düşebiliyor.
* Topic B’ye ait 2 partition olduğu için Broker 101 ve Broker 102’ye birer partition düşüyor, Broker 103 boşta
  kalıyor. (Yine rastgele şekilde Broker 103’e partition atansaydı Broker 102 de boşta kalabilirdi.)
* Topic C’ye ait 4 partition iki broker’a birer tane, bir broker’a iki tane olacak şekilde dağıtılır.

## 📌 Producer

* Producer belli bir topic’e mesaj gönderen Kafka bileşenidir.
* Publisher (yayımcı) pozisyonundadırlar.
* Veriyi topic’e key ile ve key’siz olmak üzere iki farklı yolla gönderebilirler.
* Key ile gönderme durumunda ilk gönderilen mesaj hangi partition’a gittiyse aynı key ile gönderilen diğer mesajlar da
  aynı partition’a gönderilirler.
* Belli bir sınıftaki verimize consumer tarafından sıralı bir şekilde ulaşılmasını istiyorsak key ile gönderme tercih
  edilir.
* Key’siz gönderme yönteminde Kafka iş yükünü dağıtmak için (load balancing) sıralı bir şekilde (round robin)
  gönderecektir. Bu durumda verimize sonradan sıralı bir şekilde ulaşamayız.

## 📌 Consumer

* Consumer, belli bir topic’den mesaj okuyan Kafka bileşenidir.
* Subscriber (abone) pozisyonundadırlar.
* Partition’lardan paralel şekilde okuma yapar.
* Partition bazında sırayla okur.
* Partition’lar arası sırayla okuma yapamaz.
* İki partition’dan okuma yapıyorsa birinde 5. indexde diğerinde 20. indexde olabilir.

# 🎯 ZooKeeper ?

<img src="https://github.com/rasitesdmr/Springboot-Kafka/blob/master/images/kafka6.png">

* ZooKeeper, dağıtık yapıdaki sistemlere koordinasyon hizmeti sağlayan java tabanlı bir yazılımdır.
* Yahoo! Research tarafından geliştirilmeye başlanmıştır.
* Hadoop ile beraber çalışan ekip, Hadoop’taki çoğu uygulamanın hayvan figürü olduğunu göz önüne alarak, bunları
  koordine eden kişiye-uygulamaya da ZooKeeper adını vermiştir.
* Proje daha sonra üst seviye bir Apache projesi haline gelmiştir.
* ZooKeeper, dağıtık yapıdaki sunucuların akıl hocası gibidir.
* Bir işe başlamadan önce ZooKeeper’a başvururlar veya ZooKeeper verileri değerlendirip uygun işi uygun olan sunucuya
  verir.
* Her zaman iletişim halindedirler ve kritik verileri ZooKeeper koordine eder.
* Lider seçmek, verilerin senkronizasyonunu, koordinasyonunu sağlamak, gibi görevleri yerine getirir.

## 📌 Maven Dependencies

```xml

<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
```

## 📌 Docker Compose

```yaml

version: '3.8'
services:

  kafka-producer:
    image: rasitesdmr1486/kafka-producer:v1.1
    container_name: kafka-producer
    restart: always
    ports:
      - "2001:2001"
    depends_on:
      - kafka
      - jaeger
    networks:
      - my-network


  kafka-consumer:
    image: rasitesdmr1486/kafka-consumer:v1.0
    container_name: kafka-consumer
    restart: always
    ports:
      - "2002:2002"
    depends_on:
      - kafka-producer
    networks:
      - my-network



  zookeeper:
    container_name: zookeeper
    image: confluentinc/cp-zookeeper:5.4.9
    restart: always
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
    networks:
      - my-network


  kafka:
    container_name: kafka
    image: confluentinc/cp-kafka:6.0.9
    restart: always
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_ZOOKEEPER_CONNECT: "zookeeper:2181"
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://kafka:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: "1"
      KAFKA_DELETE_TOPIC_ENABLE: "true"
      KAFKA_ADVERTISED_HOST_NAME:
    networks:
      - my-network

  kafdrop:
    image: obsidiandynamics/kafdrop
    container_name: kafdrop
    restart: always
    depends_on:
      - zookeeper
      - kafka
    ports:
      - "9000:9000"
    environment:
      KAFKA_BROKER_CONNECT: kafka:29092
    networks:
      - my-network

  jaeger:
    image: jaegertracing/all-in-one:latest
    ports:
      - "6831:6831/udp"
      - "16686:16686"
    networks:
      - my-network

  hotrod:
    image: jaegertracing/example-hotrod:latest
    ports:
      - "8080:8080"
    command: [ "all" ]
    environment:
      - JAEGER_AGENT_HOST=jaeger
      - JAEGER_AGENT_PORT=6831
    networks:
      - my-network
    depends_on:
      - jaeger

networks:
  my-network:
    driver: bridge

```

## 📌 docker-compose.yaml

```yaml
docker-compose up -d
```

```yaml
docker-compose down -v
```

## 📌 Docker Containers

<img src="https://github.com/rasitesdmr/Springboot-Kafka/blob/master/images/container.png">

## 📌 KafDrop

▶️ http://localhost:9000/

<img src="https://github.com/rasitesdmr/Springboot-Kafka/blob/master/images/kafdrop1.png">

<img src="https://github.com/rasitesdmr/Springboot-Kafka/blob/master/images/kafdrop2.png">

## 📌 kafka-producer / kafka-consumer / H2 DB

▶️ kafka-producer = localhost:2001/student/createStudent
▶️ kafka-consumer = localhost:2002/student/list
▶️ H2 DB = http://localhost:2002/h2-console/

## 📌 Postman

```json

{
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "studentNo": 0,
  "school": {
    "name": "string"
  },
  "imageName": "string"
}
```

<img src="https://github.com/rasitesdmr/Springboot-Kafka/blob/master/images/postman1.png">
<img src="https://github.com/rasitesdmr/Springboot-Kafka/blob/master/images/postman2.png">
<img src="https://github.com/rasitesdmr/Springboot-Kafka/blob/master/images/postman3.png">
<img src="https://github.com/rasitesdmr/Springboot-Kafka/blob/master/images/postman4.png">
<img src="https://github.com/rasitesdmr/Springboot-Kafka/blob/master/images/postman5.png">
<img src="https://github.com/rasitesdmr/Springboot-Kafka/blob/master/images/postman7.png">
<img src="https://github.com/rasitesdmr/Springboot-Kafka/blob/master/images/postman6.png">

## 📌 Jaeger

▶️ http://localhost:16686/

<img src="https://github.com/rasitesdmr/Springboot-Kafka/blob/master/images/postman8.png">


* Örnek resimler : images/student dosyasından bulabilirsiniz.