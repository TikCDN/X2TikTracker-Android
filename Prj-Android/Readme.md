## X2TikTrackerEngine 使用文档

### 概述

**X2TikTrackerEngine** 是一个用于 P2P CDN 的 Kotlin 库，通过传入一个播放地址实现 P2P 共享，旨在加快播放速度和节省 CDN 流量。

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

  - `url`: 视频播放地址。
  - `share`: 是否启用 P2P 共享。

- **返回值**: 操作结果的状态码。

- **示例**:

  ```
  val result = engine.startPlay("http://example.com/video.mp4", true)
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

### **X2HlsShareEngineEventHandler**

用于接收播放和共享事件的回调接口：

```
interface X2HlsShareEngineEventHandler {
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
- `onPeerOn(peerId: String, peerData: String)`: 新p2p用户加入共享。
- `onPeerOff(peerId: String, peerData: String)`: p2p用户停止共享。

