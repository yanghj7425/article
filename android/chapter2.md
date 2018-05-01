# 入手

## 在活动中使用 menu
- 新建 xml 文件使用 `<menu>` 标签创建 menu 菜单。
    - 在 `<menu>` 标签内使用 `<item>` 创建对应的菜单选项。
- 在需要使用 menu 菜单的 activity 中复写 onCreateOptionsMenu() 方法加载菜单。
```java
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

```
> getMenuInflater() 方法返回一个 MenuInflater 对象，再调用的 inflate() 方法就可以加载菜单。
- 在 Activity 中复写 onOptionsItemSelected() 方法来响应对菜单的操作。
    - finish() 销毁一个活动。
```java
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add:
                Toast.makeText(SecondActivity.this, "you clicked add item  ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_remove:
                Toast.makeText(SecondActivity.this, "you click remove item.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_finish:
                finish();
                break;
            default:
        }
        return true;
    }
```

## 使用 Intent 在活动之间穿梭
