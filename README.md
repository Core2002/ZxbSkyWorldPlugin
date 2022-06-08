# ZxbSkyWorldPlugin
小白中学时期手写的给自己服务器FiFu.Fun定制的CraftBukkit空岛插件（完工）

# 部署方法
这里使用 `1.18.2` 作例子
```shell
git clone https://github.com/FiFuProject/ZxbSkyWorldPlugin
gradlew shadowJar
``` 

然后得到编译产物 `ZxbSkyWorldPlugin-1.0-SNAPSHOT.jar`  
将他复制到服务器的 `plugins` 目录下  
启动服务器
第一次运行，可见控制台输出如下
```shell
[13:48:36 INFO]: [ZxbSkyWorld] Loading ZxbSkyWorld v2.33
[13:48:36 INFO]: [ZxbSkyWorld] [STDOUT] 正在加载json文件：./plugins/ZxbSkyWorld/base_config.json
[13:48:36 WARN]: Nag author(s): '[NekokeCore]' of 'ZxbSkyWorld' about their usage of System.out/err.print. Please use your plugin's logger instead (JavaPlugin#getLogger).
[13:48:36 INFO]: [ZxbSkyWorld] [STDOUT] 文件不存在，尝试生成。./plugins/ZxbSkyWorld/base_config.json
[13:48:36 INFO]: [ZxbSkyWorld] [STDOUT] 正在加载json文件：./plugins/ZxbSkyWorld/util_config.json
[13:48:36 INFO]: [ZxbSkyWorld] [STDOUT] 文件不存在，尝试生成。./plugins/ZxbSkyWorld/util_config.json
[13:48:36 INFO]: [ZxbSkyWorld] [STDOUT] 正在加载json文件：./plugins/ZxbSkyWorld/index.json
[13:48:36 INFO]: [ZxbSkyWorld] [STDOUT] 文件不存在，尝试生成。./plugins/ZxbSkyWorld/index.json
[13:48:36 INFO]: [ZxbSkyWorld] [STDOUT] 正在加载json文件：./plugins/ZxbSkyWorld/unAntiExplosion.json
[13:48:36 INFO]: [ZxbSkyWorld] [STDOUT] 文件不存在，尝试生成。./plugins/ZxbSkyWorld/unAntiExplosion.json
[13:48:36 INFO]: [ZxbSkyWorld] [STDOUT] 正在加载json文件：./plugins/ZxbSkyWorld/date/(0,0).json
[13:48:36 INFO]: [ZxbSkyWorld] [STDOUT] 文件不存在，尝试生成。./plugins/ZxbSkyWorld/date/(0,0).json
[13:48:36 INFO]: [ZxbSkyWorld] [STDOUT] 正在加载json文件：./plugins/ZxbSkyWorld/playerHomeInfoPATH.json
[13:48:36 INFO]: [ZxbSkyWorld] [STDOUT] 文件不存在，尝试生成。./plugins/ZxbSkyWorld/playerHomeInfoPATH.json
[13:48:36 INFO]: [ZxbSkyWorld] [STDOUT] 正在加载json文件：./plugins/ZxbSkyWorld/playerName.json
[13:48:36 INFO]: [ZxbSkyWorld] [STDOUT] 文件不存在，尝试生成。./plugins/ZxbSkyWorld/playerName.json
[13:48:36 INFO]: [ZxbSkyWorld] [STDOUT] 正在加载json文件：./plugins/ZxbSkyWorld/playerIP.json
[13:48:36 INFO]: [ZxbSkyWorld] [STDOUT] 文件不存在，尝试生成。./plugins/ZxbSkyWorld/playerIP.json
[13:48:36 INFO]: [ZxbSkyWorld] 数据缓冲模块清理器已开始工作

[13:48:50 INFO]: [ZxbSkyWorld] Enabling ZxbSkyWorld v2.33
[13:48:50 INFO]: [ZxbSkyWorld] 开始注册命令。
[13:48:50 INFO]: [ZxbSkyWorld] 完毕。
[13:48:50 INFO]: [ZxbSkyWorld] 开始注册监听器。
[13:48:50 INFO]: [ZxbSkyWorld] 完毕。
```

至此，安装完毕  
下面开始配置此插件
