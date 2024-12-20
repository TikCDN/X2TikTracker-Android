## X2TikTracker

### 概述

**X2TikTracker** 是是一款支持基于 P2P 技术的视频加速工具。它集成了 WebRTC 和 HLS 等技术，旨在降低视频点播或直播的带宽消耗，提升播放体验。X2TikTracker 的核心理念是利用终端设备之间的上传能力，实现带宽共享，从而降低对传统 CDN 的依赖。



#### Demo运行指南

```
Prj-Android/app/src/main/java/org/anyrtc/tiktracker/Config.kt

在上述类中输入您申请的appId即可

object Config {
    val appId = "your appId"
}
```

### 初始化

#### 构造函数

```kotlin
X2TikTrackerEngine(context: Context, appId: String)
```

**参数说明：**

- `context`: Android 应用上下文。
- `appId`: 应用 ID，用于标识接入的合法性。

**初始化示例：**

```kotlin
val engine = X2TikTrackerEngine(context, "your_app_id")
```



### 核心方法

#### 1. **注册事件监听器**

```
fun registerListener(listener: X2HlsShareEngineEventHandler)
```

- **功能**: 注册事件监听器以接收播放和共享相关的回调事件。

- **参数**:

  - `listener`: 实现 `X2HlsShareEngineEventHandler` 接口的实例。

- **示例**:

  ```kotlin
  engine.registerListener(object : X2HlsShareEngineEventHandler {
      override fun onShareResult(code: TKT_CODE?) {
          println("Share result: $code")
      }
      // 实现其他回调方法...
  })
  ```



#### 2. **开始播放**

```
fun startPlay(url: String, share: Boolean): Int
```

- **功能**: 开始播放指定地址的视频，并可选择是否启用 P2P 共享。

- **参数**:

  - `url`: 视频播放地址,目前支持HLS(m3u8)和MPEG-DASH(mpd)。
  - `share`: 是否启用 P2P 共享。

- **返回值**: 操作结果的状态码。

- **示例**:

  ```
  val result = engine.startPlay("http://example.com/video.m3u8", true)
  ```

#### 3. **停止播放**

```
fun stopPlay(): Int
```

- **功能**: 停止当前播放。
- **返回值**: 操作结果的状态码。

#### 4. **开始共享**

```
fun startShare(): Int
```

- **功能**: 开始 P2P 共享。

#### 5. **停止共享**

```
fun stopShare(): Int
```

- **功能**: 停止 P2P 共享。

#### 6. **更新令牌**

```
fun renewToken(token: String): Int
```

- **功能**: 更新令牌。
- 参数
  - `token`: 新的授权令牌。

#### 7. **获取 p2p URL 地址**

```
fun getExUrl(): String?
```

- **功能**: 获取当前播放的p2p URL 地址。

#### 8. **释放资源**

```
fun release()
```

- **功能**: 释放所有资源并清空监听器。

### 回调接口

### **X2TikTrackerEventHandler**

用于接收播放和共享事件的回调接口：

```
interface X2TikTrackerEventHandler {
    fun onShareResult(code: TKT_CODE?)
    fun onLoadDataStats(stats: DataStats)
    fun onRenewTokenResult(token: String, errorCode: RenewTokenErrCode?)
    fun onTokenWillExpire()
    fun onPeerOff(peerId: String, peerData: String)
    fun onPeerOn(peerId: String, peerData: String)
    fun onTokenExpired()
}
```

主要回调方法：

- `onShareResult(code: TKT_CODE?)`: P2P 共享结果。
- `onLoadDataStats(stats: DataStats)`: 数据统计信息回调。
- `onRenewTokenResult(token: String, errorCode: RenewTokenErrCode?)`: 令牌更新结果。
- `onTokenWillExpire()`: 令牌即将过期。
- `onPeerOn(peerId: String, peerData: String)`: 一个新的 P2P 节点成功加入网络时，会触发该回调。此时，P2P 网络中会有新的设备或用户参与共享数据或资源。
- `onPeerOff(peerId: String, peerData: String)`: 一个 P2P 节点或用户从网络中断开时。

