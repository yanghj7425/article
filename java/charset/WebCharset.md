# Java Web 常见的编码问题

> 相信每一个使用 Java 的小伙伴们都遇到过这个问题，这里主要说下我在路上遇到的编码问题。然后最近在看一本书，恰好有个地方讲了这个，就顺便记一下。有的内容是摘自[博客](http://blog.itpub.net/29254281/viewspace-1073278/)，喜欢的童鞋可直接过去看哈。

就我个人使用的情况来说：

## 字符流向字节流转换的时候需要编码

- 字节到字符的解码，会委托给 StreamDecoder 去处理。在 StreamDecoder 处理过程中需要用户自己指定 Charset 编码格式，如果没有指定，就会按照系统默认的编码进行。
```java
     public static void main(String[] args) throws IOException {
        String file = "D:/test.log";
        String charset = "UTF-8";
        FileOutputStream outputStream = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, java.nio.charset.Charset.forName(charset));
        try {
            writer.write("hello,你好");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
        FileInputStream inputStream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(inputStream);
        char[] buffer = new char[100];
        StringBuffer stringBuffer = new StringBuffer();
        int count = 0;
        try {
            while ((count = reader.read(buffer)) != -1) {
                stringBuffer.append(buffer, 0, count);
            }
        } finally {
            reader.close();
        }
        System.out.println(stringBuffer.toString());
    }

```
> 上面代码在编译器里面运行是没有问题的，不过用 javac 编译运行就会有问题。但是如果返回来，由于 UTF-8 可以向下兼容（不知道这么说恰不恰当哈）GBK，所以写入的时候用默认的，我这里是 win 的 GBK，读取的时候是 UTF-8 就不会有问题。

## Web 中需要的编码

 - 有的压缩算法，可能只是减少了字符数，但是没有减少字节数。压缩只是将多个字符通过编码转换为一个多字节字符，减少的是 String.length()，而并没有减少字节数。同样的字符采用不同的编码方式，最终存储的大小也会有不同，所以从字符到字节一定要看编码类型。
    - eg1：将两个字符压缩为一个奇怪的字符，如果是采用 UTF-8 编码，可能压缩后的字符还占更多字节。
    - eg2: `1234567` 这几个数字当作用 UTF-8 编码会占 7 个字节，UTF-16 编码占 14 个字节，但是做 int 存储则占 4 个字节。

### URL [的编码]()
相信，都用过 request.getParameter 获取参数。详情可查看
    [其他博客](http://blog.itpub.net/29254281/viewspace-1073278/)。
- Get 请求
 对于请求字符串（QueryString）的解码字符集要么是 Header 中 ContentType 定义的 Charset，要么就是默认的 ISO-8859-1。可以通过 URIEncoding和useBodyEncodingForURI都可以处理中文乱码的问题。

 - Post 请求
一定要在第一次调用 request.getParameter 方法之前就设置 request.setCharacterEncoding(charset)，否则 POST 提交上来的数据可能出现乱码。
相信到这里，就清楚了，为什么我们之前会设置一个 Filter 来处理字符集，Filter 在 servlet 之前调用，会更早的先解析好参数。这样就不会出现乱码问题。
    - 默认情况下，提交表单是不会有 charset 信息的，但是 tomcat 代码里面还是去检查了这个设置。




