### 什么是观察者模式？

观察者模式定义对象间的一对多依赖，当一个对象状态改变时，所有依赖它的对象都会收到通知并自动更新。

#### 核心思想

被观察者变化时，自动通知所有观察者

---

#### 工作原理

```
                     Subject (被观察者)

  ┌─────────────────────────────────────────────────┐
  │  • registerObserver()  - 注册观察者              │
  │  • removeObserver()    - 移除观察者              │
  │  • notifyObservers()   - 通知所有观察者           │
  └─────────────────────────────────────────────────┘
                         │
                         │ 状态变化时通知
                         ↓
                 观察者列表
  ┌─────────┐ ┌─────────┐ ┌─────────┐
  │Observer1│ │Observer2│ │Observer3│
  └────┬────┘ └────┬────┘ └────┬────┘
       │          │          │
       ↓          ↓          ↓
  ┌──────────┐ ┌──────────┐ ┌──────────┐
  │ update() │ │ update() │ │ update() │
  └──────────┘ └──────────┘ └──────────┘
```

---

#### 核心角色
| 角色 | 说明 | 职责 |
|:------------|:------------|:----------------------------------|
| **Subject** | 被观察者接口 | 定义注册、移除、通知观察者的方法 |
| **ConcreteSubject** | 具体被观察者 | 维护观察者列表，状态变化时通知 |
| **Observer** | 观察者接口 | 定义更新方法 |
| **ConcreteObserver** | 具体观察者 | 实现更新逻辑 |
---

#### 完整代码示例

**场景：股票价格推送系统**

```java
import java.util.ArrayList;
import java.util.List;

// ==================== 观察者接口 ====================

/**
* 观察者接口
* 定义所有观察者必须实现的方法
  */
  interface Observer {
  /**
    * 当被观察者发生变化时，调用此方法
    *
    * @param stockSymbol 股票代码
    * @param newPrice 新价格
    * @param changePercent 价格变化百分比
      */
      void update(String stockSymbol, double newPrice, double changePercent);

  /**
    * 获取观察者名称
    *
    * @return 观察者名称
      */
      String getObserverName();
      }

// ==================== 被观察者接口 ====================

/**
* 被观察者接口（主题）
* 定义被观察者的标准行为
  */
  interface Subject {
  /**
    * 注册一个观察者
    *
    * @param observer 观察者对象
      */
      void registerObserver(Observer observer);

  /**
    * 移除一个观察者
    *
    * @param observer 观察者对象
      */
      void removeObserver(Observer observer);

  /**
    * 通知所有注册的观察者
      */
      void notifyObservers();
      }

// ==================== 具体被观察者 ====================

/**
* 股票对象 - 具体被观察者
* 当股票价格变化时，通知所有观察者
  */
  class Stock implements Subject {

  // ── 基本属性 ──
  private String symbol;     // 股票代码
  private String name;      // 股票名称
  private double price;     // 当前价格
  private double previousPrice;  // 前一次价格

  // ── 观察者管理 ──
  private List<Observer> observers;  // 观察者列表

  // ── 构造方法 ──

  /**
    * 构造方法
    *
    * @param symbol 股票代码
    * @param name 股票名称
    * @param price 初始价格
      */
      public Stock(String symbol, String name, double price) {
      this.symbol = symbol;
      this.name = name;
      this.price = price;
      this.previousPrice = price;
      this.observers = new ArrayList<>();

      System.out.println("📈 股票创建: " + name + " (" + symbol + "), 初始价格: ¥" + price);
      }

  // ── Subject 接口实现 ──

  @Override
  public void registerObserver(Observer observer) {
  // 检查是否已存在，避免重复注册
  if (!observers.contains(observer)) {
  observers.add(observer);
  System.out.println("✅ 观察者已注册: " + observer.getObserverName());
  }
  }

  @Override
  public void removeObserver(Observer observer) {
  if (observers.remove(observer)) {
  System.out.println("❌ 观察者已移除: " + observer.getObserverName());
  }
  }

  @Override
  public void notifyObservers() {
  // 计算价格变化百分比
  double changePercent = calculateChangePercent();

       System.out.println("\n📢 正在通知 " + observers.size() + " 位观察者...\n");

       // 遍历所有观察者，调用它们的 update 方法
       for (Observer observer : observers) {
           observer.update(symbol, price, changePercent);
       }
  }

  // ── 业务方法 ──

  /**
    * 设置新价格
    * 价格变化时会自动通知所有观察者
    *
    * @param newPrice 新价格
      */
      public void setPrice(double newPrice) {
      if (newPrice < 0) {
      System.out.println("⚠️ 价格不能为负数");
      return;
      }

      this.previousPrice = this.price;
      this.price = newPrice;

      // 打印价格变化信息
      printPriceChange();

      // 通知所有观察者
      notifyObservers();
      }

  /**
    * 计算价格变化百分比
    *
    * @return 变化百分比
      */
      private double calculateChangePercent() {
      if (previousPrice == 0) {
      return 0;
      }
      return ((price - previousPrice) / previousPrice) * 100;
      }

  /**
    * 打印价格变化信息
      */
      private void printPriceChange() {
      double change = price - previousPrice;
      double changePercent = calculateChangePercent();

      System.out.println("\n" + "=".repeat(60));
      System.out.println("         📊 " + name + " (" + symbol + ") 价格更新");
      System.out.println("=".repeat(60));
      System.out.println("  前价格: ¥" + String.format("%.2f", previousPrice));
      System.out.println("  新价格: ¥" + String.format("%.2f", price));
      System.out.println("  变化量: " + (change >= 0 ? "+" : "") + String.format("%.2f", change));
      System.out.println("  涨跌幅: " + (changePercent >= 0 ? "📈 +" : "📉 ") + String.format("%.2f%%", changePercent));
      System.out.println("=".repeat(60));
      }

  // ── Getter 方法 ──

  public String getSymbol() { return symbol; }
  public String getName() { return name; }
  public double getPrice() { return price; }
  public int getObserverCount() { return observers.size(); }
  }

// ==================== 具体观察者 ====================

/**
* 交易员观察者
* 当股票价格达到目标价格时执行交易
  */
  class TraderObserver implements Observer {

  private String name;           // 交易员姓名
  private double buyPrice;        // 目标买入价格
  private double sellPrice;       // 目标卖出价格
  private int quantity;           // 交易数量
  private boolean isHolding;      // 是否持有股票

  /**
    * 构造方法
    *
    * @param name 交易员姓名
    * @param buyPrice 目标买入价格
    * @param sellPrice 目标卖出价格
    * @param quantity 交易数量
      */
      public TraderObserver(String name, double buyPrice, double sellPrice, int quantity) {
      this.name = name;
      this.buyPrice = buyPrice;
      this.sellPrice = sellPrice;
      this.quantity = quantity;
      this.isHolding = false;
      }

  @Override
  public void update(String stockSymbol, double newPrice, double changePercent) {
  System.out.println("👤 【" + name + "】收到通知");
  System.out.println("   当前价格: ¥" + String.format("%.2f", newPrice));

       // 判断交易操作
       if (isHolding) {
           // 持有中，判断是否卖出
           checkSellCondition(newPrice, stockSymbol);
       } else {
           // 未持有，判断是否买入
           checkBuyCondition(newPrice, stockSymbol);
       }
  }

  /**
    * 检查买入条件
    *
    * @param price 当前价格
    * @param symbol 股票代码
      */
      private void checkBuyCondition(double price, String symbol) {
      System.out.println("   目标买入价: ¥" + String.format("%.2f", buyPrice));

      if (price <= buyPrice) {
      // 价格低于目标价，执行买入
      executeBuyOrder(symbol, price);
      } else {
      System.out.println("   ❌ 价格未达买入条件，继续观望");
      }
      }

  /**
    * 检查卖出条件
    *
    * @param price 当前价格
    * @param symbol 股票代码
      */
      private void checkSellCondition(double price, String symbol) {
      System.out.println("   目标卖出价: ¥" + String.format("%.2f", sellPrice));
      System.out.println("   当前持仓: " + quantity + " 股");

      if (price >= sellPrice) {
      // 价格高于目标价，执行卖出
      executeSellOrder(symbol, price);
      } else {
      System.out.println("   ❌ 价格未达卖出条件，继续持有");
      }
      }

  /**
    * 执行买入订单
    *
    * @param symbol 股票代码
    * @param price 买入价格
      */
      private void executeBuyOrder(String symbol, double price) {
      double totalAmount = price * quantity;
      isHolding = true;

      System.out.println("   ✅ 执行买入订单！");
      System.out.println("   📋 订单详情:");
      System.out.println("      股票代码: " + symbol);
      System.out.println("      买入价格: ¥" + String.format("%.2f", price));
      System.out.println("      买入数量: " + quantity + " 股");
      System.out.println("      订单总额: ¥" + String.format("%.2f", totalAmount));
      }

  /**
    * 执行卖出订单
    *
    * @param symbol 股票代码
    * @param price 卖出价格
      */
      private void executeSellOrder(String symbol, double price) {
      double totalAmount = price * quantity;
      isHolding = false;

      System.out.println("   ✅ 执行卖出订单！");
      System.out.println("   📋 订单详情:");
      System.out.println("      股票代码: " + symbol);
      System.out.println("      卖出价格: ¥" + String.format("%.2f", price));
      System.out.println("      卖出数量: " + quantity + " 股");
      System.out.println("      订单总额: ¥" + String.format("%.2f", totalAmount));
      }

  @Override
  public String getObserverName() {
  return "交易员-" + name;
  }
  }

/**
* 分析师观察者
* 分析股票走势并给出建议
  */
  class AnalystObserver implements Observer {

  private String name;              // 分析师姓名
  private String publishChannel;    // 报告发布渠道
  private List<String> reports;     // 历史报告

  /**
    * 构造方法
    *
    * @param name 分析师姓名
    * @param publishChannel 报告发布渠道
      */
      public AnalystObserver(String name, String publishChannel) {
      this.name = name;
      this.publishChannel = publishChannel;
      this.reports = new ArrayList<>();
      }

  @Override
  public void update(String stockSymbol, double newPrice, double changePercent) {
  System.out.println("📊 【" + name + "】分析中...");

       // 分析价格变化
       String analysis = analyzePriceChange(newPrice, changePercent);
       String recommendation = getRecommendation(changePercent);

       // 保存分析报告
       String report = buildReport(stockSymbol, newPrice, changePercent, analysis, recommendation);
       reports.add(report);

       // 发布报告
       publishReport(report);
  }

  /**
    * 分析价格变化
    *
    * @param price 当前价格
    * @param changePercent 变化百分比
    * @return 分析结论
      */
      private String analyzePriceChange(double price, double changePercent) {
      double absChange = Math.abs(changePercent);

      if (absChange > 10) {
      return "⚠️ 极端波动，市场情绪剧烈变化";
      } else if (absChange > 5) {
      return "📈 大幅" + (changePercent > 0 ? "上涨" : "下跌") + "，需密切关注";
      } else if (absChange > 2) {
      return "📊 " + (changePercent > 0 ? "稳步" : "温和") + (changePercent > 0 ? "上升" : "下跌") + "，趋势正常";
      } else {
      return "➖ 波动较小，横盘整理中";
      }
      }

  /**
    * 获取投资建议
    *
    * @param changePercent 变化百分比
    * @return 投资建议
      */
      private String getRecommendation(double changePercent) {
      if (changePercent > 8) {
      return "⚠️ 建议谨慎持有，注意风险";
      } else if (changePercent > 2) {
      return "✅ 建议继续持有";
      } else if (changePercent > -2) {
      return "⏸️ 建议观望，等待明确信号";
      } else {
      return "🔍 可考虑逢低建仓";
      }
      }

  /**
    * 构建报告
      */
      private String buildReport(String symbol, double price, double changePercent,
      String analysis, String recommendation) {
      return String.format(
      "[%s] %s - 价格: ¥%.2f, 涨跌幅: %+.2f%%\n分析: %s\n建议: %s",
      java.time.LocalDateTime.now().format(
      java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
      ),
      symbol, price, changePercent, analysis, recommendation
      );
      }

  /**
    * 发布分析报告
    *
    * @param report 报告内容
      */
      private void publishReport(String report) {
      System.out.println("   📄 发布分析报告:");
      for (String line : report.split("\n")) {
      System.out.println("      " + line);
      }
      System.out.println("   📤 发布渠道: " + publishChannel);
      }

  @Override
  public String getObserverName() {
  return "分析师-" + name;
  }
  }

/**
* 风控系统观察者
* 监控异常价格波动
  */
  class RiskControlObserver implements Observer {

  private double maxDailyChange;   // 最大单日波动百分比
  private int alertCount;          // 警告计数器
  private List<RiskAlert> alerts;  // 警告历史

  /**
    * 风险警告记录
      */
      class RiskAlert {
      String symbol;
      double price;
      double changePercent;
      java.time.LocalDateTime timestamp;

      RiskAlert(String symbol, double price, double changePercent) {
      this.symbol = symbol;
      this.price = price;
      this.changePercent = changePercent;
      this.timestamp = java.time.LocalDateTime.now();
      }
      }

  /**
    * 构造方法
    *
    * @param maxDailyChange 最大单日波动百分比（如 8 表示 8%）
      */
      public RiskControlObserver(double maxDailyChange) {
      this.maxDailyChange = maxDailyChange;
      this.alertCount = 0;
      this.alerts = new ArrayList<>();
      }

  @Override
  public void update(String stockSymbol, double newPrice, double changePercent) {
  double absChangePercent = Math.abs(changePercent);

       System.out.println("🛡️ 【风控系统】监控中");
       System.out.println("   当前波动: " + String.format("%.2f%%", absChangePercent));
       System.out.println("   风险阈值: " + maxDailyChange + "%");

       if (absChangePercent > maxDailyChange) {
           // 触发风险预警
           triggerAlert(stockSymbol, newPrice, changePercent);
       } else {
           System.out.println("   ✅ 波动在正常范围内");
       }
  }

  /**
    * 触发风险预警
    *
    * @param stockSymbol 股票代码
    * @param price 当前价格
    * @param changePercent 变化百分比
      */
      private void triggerAlert(String stockSymbol, double price, double changePercent) {
      alertCount++;

      // 记录警告
      alerts.add(new RiskAlert(stockSymbol, price, changePercent));

      System.out.println("   ⚠️⚠️⚠️ 风险预警触发！⚠️⚠️⚠️");
      System.out.println("   📊 波动详情:");
      System.out.println("      股票代码: " + stockSymbol);
      System.out.println("      当前价格: ¥" + String.format("%.2f", price));
      System.out.println("      涨跌幅: " + String.format("%+.2f%%", changePercent));
      System.out.println("   📈 累计预警次数: " + alertCount);
      }

  /**
    * 获取累计预警次数
    *
    * @return 预警次数
      */
      public int getAlertCount() {
      return alertCount;
      }

  /**
    * 打印所有警告记录
      */
      public void printAlertHistory() {
      System.out.println("\n" + "=".repeat(50));
      System.out.println("           风控预警历史记录");
      System.out.println("=".repeat(50));

      for (int i = 0; i < alerts.size(); i++) {
      RiskAlert alert = alerts.get(i);
      System.out.println((i + 1) + ". " + alert.timestamp);
      System.out.println("   股票: " + alert.symbol +
      ", 价格: ¥" + String.format("%.2f", alert.price) +
      ", 波动: " + String.format("%+.2f%%", alert.changePercent));
      }

      System.out.println("=".repeat(50) + "\n");
      }

  @Override
  public String getObserverName() {
  return "风控系统";
  }
  }

// ==================== 客户端代码 ====================

/**
* 观察者模式演示
  */
  class ObserverPatternDemo {
  public static void main(String[] args) {
  System.out.println("╔═════════════════════════════════════════════════════════╗");
  System.out.println("║             📈 观察者模式 - 股票价格推送系统               ║");
  System.out.println("╚═════════════════════════════════════════════════════════╝\n");

       // ── 创建被观察者 ──
       Stock tencentStock = new Stock("0700", "腾讯控股", 350.00);

       // ── 创建观察者 ──
       System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
       System.out.println("                    注册观察者");
       System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

       TraderObserver trader1 = new TraderObserver("张三", 340.00, 380.00, 100);
       TraderObserver trader2 = new TraderObserver("李四", 320.00, 360.00, 200);
       AnalystObserver analyst = new AnalystObserver("王分析师", "APP推送");
       RiskControlObserver riskControl = new RiskControlObserver(8.0);

       // ── 注册观察者 ──
       tencentStock.registerObserver(trader1);
       tencentStock.registerObserver(trader2);
       tencentStock.registerObserver(analyst);
       tencentStock.registerObserver(riskControl);

       System.out.println("\n📊 当前观察者数量: " + tencentStock.getObserverCount() + "\n");

       // ── 模拟价格变化 ──
       System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
       System.out.println("                    模拟价格变化");
       System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

       tencentStock.setPrice(345.50);   // 小幅下跌
       tencentStock.setPrice(341.00);   // 接近交易员A目标价
       tencentStock.setPrice(339.50);   // 触发交易员A买入
       tencentStock.setPrice(329.80);   // 触发交易员B买入
       tencentStock.setPrice(280.00);   // 大幅下跌，触发风控
       tencentStock.setPrice(320.00);   // 回升

       // ── 查看风控历史 ──
       riskControl.printAlertHistory();

       // ── 移除观察者 ──
       System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
       System.out.println("                    移除观察者");
       System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

       tencentStock.removeObserver(trader1);
       System.out.println("📊 剩余观察者数量: " + tencentStock.getObserverCount() + "\n");

       // ── 再次变化，交易员A不会收到通知 ──
       tencentStock.setPrice(335.00);
  }
  }

 ```
#### 实际应用：Spring 事件机制

```
                  Spring 事件机制架构

   ApplicationEventPublisher (事件发布器)                      
            │                                                
            │ publishEvent(event)                            
            ↓                                                
         ApplicationEventMulticaster               
         (事件广播器，管理所有监听器)                          
            │                                                
            │ multicastEvent(event)                          
            ├────────────────┬──────────────┬─────────────┤  
            ↓                ↓              ↓             ↓  
   ┌─────────────┐  ┌─────────────┐  ┌─────────────┐     
   │ Listener 1  │  │ Listener 2  │  │ Listener N  │     
   │ @EventListener│ │ @EventListener│ │ @EventListener│ 
   │ 监听并处理   │  │ 监听并处理   │  │ 监听并处理   │     
   └─────────────┘  └─────────────┘  └─────────────┘     

```
**Spring 事件示例**

```java

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

// ── 定义事件 ──

/**
* 用户注册事件
  */
  class UserRegisteredEvent extends ApplicationEvent {
  private String username;
  private String email;

  public UserRegisteredEvent(Object source, String username, String email) {
  super(source);
  this.username = username;
  this.email = email;
  }

  public String getUsername() { return username; }
  public String getEmail() { return email; }
  }

// ── 定义监听器 ──

/**
* 邮件服务监听器
  */
  @Component
  class EmailListener {

  @EventListener
  public void handleUserRegistration(UserRegisteredEvent event) {
  System.out.println("📧 发送欢迎邮件给: " + event.getEmail());
  System.out.println("   邮件内容: 亲爱的 " + event.getUsername() + "，欢迎加入我们！");
  }
  }

/**
* 积分服务监听器
  */
  @Component
  class PointsListener {

  @EventListener
  public void awardRegistrationPoints(UserRegisteredEvent event) {
  System.out.println("🎁 为用户 " + event.getUsername() + " 奖励 100 积分");
  }
  }

/**
* 日志服务监听器
  */
  @Component
  class LogListener {

  @EventListener
  public void logUserRegistration(UserRegisteredEvent event) {
  System.out.println("📝 记录注册日志: " + event.getUsername());
  System.out.println("   注册时间: " + java.time.LocalDateTime.now());
  }
  }

// ── 发布事件 ──

/**
* 用户服务
  */
  @Service
  class UserService {

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  /**
    * 注册用户
      */
      public void registerUser(String username, String email) {
      System.out.println("🔐 用户注册中: " + username);

      // 执行注册逻辑...
      System.out.println("✅ 注册成功！");

      // ── 发布事件，所有监听器都会收到通知 ──
      UserRegisteredEvent event = new UserRegisteredEvent(this, username, email);
      eventPublisher.publishEvent(event);
      }
      }

// ── 使用示例 ──

class SpringEventDemo {
public static void main(String[] args) {
UserService userService = ...;  // 从 Spring 容器获取

          System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
          System.out.println("      用户注册 - 观察者模式");
          System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

          userService.registerUser("张三", "zhangsan@example.com");

          System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
          System.out.println("      所有监听器已自动处理");
          System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
      }
}
```

---

#### 优缺点对比

| 类型 | 说明 |
|:----|:----|
| ✅ 优点 | • 被观察者和观察者是松耦合的<br>• 支持广播通信，一个事件可通知多个观察者<br>• 符合开闭原则，易于扩展 |
| ❌ 缺点 | • 观察者过多时通知效率降低<br>• 观察者只知道变化，不知道变化的具体原因<br>• 如果观察者和被观察者之间有循环依赖，可能导致循环调用 |

---

#### 适用场景

| 场景 | 示例 |
|:----:|:----|
| 1 | 股票价格变化通知 |
| 2 | 消息队列的订阅发布 |
| 3 | GUI 事件处理（按钮点击等） |
| 4 | Spring 事件机制 |
| 5 | 微服务中的消息通知 |

---

#### 总结

| 概念 | 说明 |
|:----|:----|
| 核心思想 | 一对多依赖，自动通知 |
| 关键角色 | Subject（被观察者）、Observer（观察者） |
| 应用场景 | 事件驱动、消息推送、GUI 事件 |
  ---