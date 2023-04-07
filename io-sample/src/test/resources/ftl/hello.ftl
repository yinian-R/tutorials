Content：

插入值：
${hello}

覆盖数据模型中的数据：
<#assign hello="Tom">
${hello}

使用被覆盖数据模型的数据：
${.globals.hello}

定义简单变量：
<#assign name="Tom">
${name}

定义对象变量：
<#assign info={"mobile":"xxxxx", "address":"china"}>
my mobile is ${info.mobile}, my address is ${info.address}

定义序列：
<#assign seq = ["foo", "bar", "baz"]>
<#list seq as n>
    序列：${n}
</#list>

freemarker 当变量为 null 时会报错，所以需要判断是否为 null：
<#if name??>
    name 不为 null
</#if>
对象判断 null（此语法同时判断 user 和 name 是否存在 null）：
<#if (user.name)??>
    user.name 不为 null
</#if>

通过设置默认值来避免对象为空的错误，如果name为空，就以默认值（"!"后的字符）显示：
${adminName!'默认值'}
${(admin.name)!'默认值'}

if else 语法：
<#if name??>
    name found
<#else>
    No name found
</#if>


FreeMarker list 指令遍历：
FreeMarker list 指令还隐含了两个循环变量：
（1）item_index：当前迭代项在所有迭代项中的位置，是数字值。
（2）item_has_next：用于判断当前迭代项是否是所有迭代项中的最后一项。
注意：在使用上述两个循环变量时，一定要将item换成你自己定义的循环变量名，item其实就是前缀罢了
<#list users as item>
    用户：${item}
    index：${item_index}
 <#if !item_has_next>共有${users?size}，最后一个用户是:${item}</#if>
</#list>

