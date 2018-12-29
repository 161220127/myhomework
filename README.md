# Java Final Project 程序说明

----------
## 1.开发环境 ##
- IntelliJ IDEA Community Edition 2018.2.3 x64
- jdk-10.0.2
- maven-3.6.0


## 2.项目运行 ##
- 使用Maven对项目进行编译，打包。运行target目录下Calabash-1.0-SNAPSHOT.jar。
### 初始化 ###
![初始化](https://github.com/161220127/myhomework/blob/master/screenpicture/1.png)
- 进入初始化界面，选择点击”开始游戏”或Space键进入准备阶段，选择点击“历史记录”或L键读取历史战斗记录文档，选择点击“退出游戏”或E键退出游戏界面。
![准备](https://github.com/161220127/myhomework/blob/master/screenpicture/2.png)
- 进入准备阶段后，可点击左右两侧的阵型按钮为葫芦娃阵营和妖怪阵营设置阵型（为公平起见，双方各由8个生物体组成）。
### 战斗过程 ###
![战斗过程](https://github.com/161220127/myhomework/blob/master/screenpicture/3.png)
- 准备完毕之后，按下Space键后葫芦娃与妖精自动进行战斗。
- 双方自由移动(速度为1单位/s)，进入指定距离后相互攻击。
- 生物体死亡后，会在原地留下实体，实体会阻碍剩余的生物体的移动。实体会在战场界面刷新20次后自动消失(防止有可能生物被实体围住无法交战）。
### 战斗结束 ###
![战斗结束](https://github.com/161220127/myhomework/blob/master/screenpicture/4.png)
- 某个阵营的生物全部阵亡后，游戏即结束，根据战斗结果显示不同的结束画面。
- 根据画面上的提示，按下S键可打开保存文件框保存刚刚的战斗记录，按下Space键回到初始化界面，新增加的“复盘”按钮点击后可重演刚刚的战斗过程，新增的“保存文件”按钮可同样用来保存战斗记录。
### 记录读取 ###
![记录读取](https://github.com/161220127/myhomework/blob/master/screenpicture/5.png)
- 在战斗未开始或已结束的状态下，可通过点击“历史记录”按钮或L键打开文件打开对话框，读取与target目录同级的history目录下的历史记录文件，若读取了正确的文件，可重演历史战斗记录。
### 运行效果 ###
![运行效果](https://github.com/161220127/myhomework/blob/master/screenpicture/6.gif)
## 3.项目结构 ##
### package gui ###
- 存放通过javafx框架实现的图形化界面代码，包括：
- class Main：整个程序的入口，实例化了Controller类的对象
- class Controller：javafx框架提供的一个控制器。整个处理程序的各种外部事件处理函数包括鼠标事件、键盘事件、画面显示输出函数等都在该类里定义。
### package creature ###
- 存放对生物体基类Creature及其一切派生类定义。定义了生物体的基本特征，实现了生物体的移动和战斗结果判定。
### package battlefield ###
- 存放了区域类Block，战场类Battlefield，阵法类Trooparrays的定义。

## 4.设计思路 ##
### Creature类及其派生类 ###
- Creature类作为基类定义了所有生物体的共有属性，包括生物体类型、阵营、是否存活、所在战场等。其中生物体类型为enum类型：
    `public enum Type {CALABASH, GRANNDPA, SCORPION, SNAKE, MINIONS, NONEXISTENET}`

- 同时，为实现将每个生物体作为一个线程运行，在Creature类中实现了Runnable接口，在run中实现了生物体移动和战斗判定的内容：
首先判断自己是否存活，如果存活则寻找到距离最近的敌人，若距离小于限定距离则会进行战斗判定，随机决定战斗结果：

	<pre>public class Creature  implements Runnable{
		public void run(){...}
	}</pre>

- 在Creature的各派生类中定义了各派生类的独有属性，诸如Calabashbrothers类中的排行（rank），颜色（color），皆为enum类型：
    `public enum Rank{老大, 老二, 老三, 老四, 老五, 老六, 老七}`
    `public enum Color{红色, 橙色, 黄色, 绿色, 青色, 蓝色, 紫色}`
- 同时可在派生类中重新实现Runnable接口，让不同类型的生物体拥有不同方式的移动和战斗判定，从而使战斗过程更加丰富。
- 为保障多线程运行时的安全，避免出现以下情况：一个生物被杀死2次，两个生物进入同一块空间等等，在每个生物体进行移动和战斗判断时，加上synchronized锁住，保证每个生物体进程在进行移动和攻击时独享资源，避免冲突：
    `synchronized (this) {...}`

### Block类 ###
- Block类主要用于表示坐标。
- Block类中有一个Creature对象表明在该坐标上的生物。

### Trooparrays类 ###
- 每个Troop类对象存储了排布某种阵法需要对坐标进行的变换，而Trooparrays中是由Troop类对象聚合而来的，它的troop类数组存储了所有的阵法变换。
- Trooparrays提供给外界接口：根据输入（要选择的阵法编号），返回一个Troop对象给外界用于计算坐标变换。
	`public Troop choose(int i) {
        return troops[i];
    }`

### Battlefield类 ###
- Battlefield类中拥有一个Block类的二维数组，一个Creature类的二维数组及Trooparrays类的对象以及如下的函数：
- init函数，根据为双方阵营选择的阵型，在战场中放置和调整双方阵营的生物体, 其中根据泛型的设计思想，设计了放置（Put）函数和移除（Remove）函数.
- start函数，用于启动所有生物体的线程：
<pre> public void start() {
        for(int i = 0; i < lenx; i++) {
            for (int j = 0; j < leny; j++) {
                if (creatures[i][j].type !=  Creature.Type.NONEXISTENET && creatures[i][j].isalive) {
                    Thread t = new Thread(creatures[i][j]);
                    creatures[i][j].thread = t;
                    t.start();
                }
            }
        }
    }
</pre>
- judgefinished函数，用于判断整个战场的战斗是否已经结束：
<pre>
	public int judgefinished(){
        Boolean good = false;
        Boolean bad = false;
        for(int i = 0; i < lenx; i++){
            for(int j = 0; j < leny; j ++){
                if(getcreature(i,j).isalive && getcreature(i,j).side == 1)
                    good = true;
                if(getcreature(i,j).isalive && getcreature(i,j).side == -1)
                    bad = true;
            }
        }

        if(good && bad){
            return 0;
        }
        else if(good)
            return 1;
        else if(bad)
            return -1;
        else
            return 2;
    }
</pre>

- 除此之外，还有用于保存战斗过程的函数`public void SaveField(){...}`和读取战斗过程的函数 `public Boolean readHistory(String filename, int scene){...}`
     

### Controller类 ###
- Controller类中有一个Battlefield对象，所有关于战场状况画面的输出都是根据该对象而对应输出的。
- Controller类中定义了多个按钮及点击他们时的响应函数，也定义了键盘键入的响应函数，通过这些函数与Battlefield对象进行交互,修改战场中的信息。
- Controller类中定义了文件打开和文件保存的函数，通过文件打开和保存对话框来选择文件。
- Controller类中定义一个定时器线程，定时刷新战场的信息并输出在图形界面上。


## 5.异常处理 ##
- 在程序中易出现异常的地方，诸如文件的存取、线程的睡眠与启动添加异常的捕捉与打印，进行异常处理。
<pre>
	Reader reader;
        try{
            reader = new FileReader(filename);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
	long len = (lenx * leny + 2) * scene;

        try {
            reader.skip(len);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
</pre>
<pre>
	try {
               Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    thread.interrupt();
                    break;
                }
	}
</pre>

## 6.单元测试 ##
- 使用了JUnit对Battlefield的Put、Remove、judgefinished函数，Trooparrays类的choose函数，Creature类和Block类的函数进行了单元测试。
- 示例如下：
<pre>
    @Test
    public void testjudgefinished(){
        field = new Battlefield();
        field.Put(0,0, new Grandpa(field));
        field.Put(0,1, new Snake(field));
        field.getcreature(0,0).isalive = false;
        assertTrue(field.judgefinished() == -1);

        field = new Battlefield();
        field.Put(0,0, new Grandpa(field));
        field.Put(0,1, new Snake(field));
        field.getcreature(0,1).isalive = false;
        assertTrue(field.judgefinished() == 1);

        field = new Battlefield();
        field.Put(0,0, new Grandpa(field));
        field.Put(0,1, new Snake(field));

        assertTrue(field.judgefinished() == 0);
    }
</pre>

## 7.设计原则 ##
### SRP单一职责原则 ###
- Creature类及其派生类void run（）中只进行生物体的移动和战斗判定而将战斗过程的记录分离到其他函数SaveField（）中。
- 游戏内部运行与图形化展示分离开。Controller类中负责图形化界面的输出。
### OCP开发封闭原则 ###
- Creature类作为基类，定义共有属性。
- 其派生类不修改基类的定义，而是在基类基础上扩展。
### ISP 接口隔离原则 ###
- 在所有类的定义与实现中，没有一个类实现了其功能不需要的方法。
### CARP 合成/聚合复用原则 ###
-Battlefield类中有一个Block类的二维数组，一个Creature类的二维数组和Trooparrays类的对象。
- Trooparrays类由一组Troop类对象合成。

## 8.面向对象的体现 ##
### 继承 ###
- CalabashBrothers,Grandpa,Snake等继承了基类Creature。
### 组合 ###
- Battlefield类中有一组Block类对象，一组Creature类对象组合而成。
### 多态 ###
- Creature类的派生类中重新实现了Runnable接口，覆盖了基类中的实现，让不同类型的生物体拥有不同方式的移动和战斗判定，从而使战斗过程更加丰富。


## 9.心得感悟 ##
- java是一门面向对象的程序设计语言，在平常的代码设计中，需尽量采用面向对象的思想方法去构建程序。
- 编写程序，不能仅仅停留在追求运行没有bug的层面，而在此基础上去进行不断地优化，最终实现结构清晰、运行性能高、易于扩展和复用的程序。


