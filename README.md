finance-analyzer-pr2

Невеликий консольний застосунок на Java, який читає банківські транзакції з CSV і показує прості звіти.

Що вміє

підрахувати загальний баланс;

порахувати кількість транзакцій за місяць (формат MM-yyyy);

знайти топ-10 найбільших витрат;

визначити найменшу/найбільшу транзакції у вибраному періоді;

підсумувати витрати по категоріях і по місяцях та показати їх як прості “діаграми” зі *.

Як це влаштовано 

model/Transaction — проста модель транзакції (дату/суму/опис). Lombok генерує гетери/сетери/конструктори.

io/DataSource + UrlDataSource / FileDataSource — звідки беремо дані (URL або файл).

io/TransactionCSVReader (абстрактний клас зі static-методами) — читає CSV і повертає список Transaction.

logic/TransactionAnalyzer (абстрактний + static) — рахує все потрібне (баланс, підрахунки, топ-10, мін/макс, групування).

report/TransactionReportGenerator (абстрактний + static) — красиво друкує результати у консоль.

Main — збирає все разом.

Структура проєкту
src/
├─ main/java/ua/dut/finance/
│  ├─ Main.java
│  ├─ model/Transaction.java
│  ├─ io/
│  │   ├─ DataSource.java
│  │   ├─ UrlDataSource.java
│  │   ├─ FileDataSource.java
│  │   └─ TransactionCSVReader.java
│  ├─ logic/TransactionAnalyzer.java
│  └─ report/TransactionReportGenerator.java
└─ test/java/ua/dut/finance/
├─ io/TransactionCSVReaderTest.java
└─ logic/TransactionAnalyzerTest.java


Запустити всі тести:

mvn -q test
