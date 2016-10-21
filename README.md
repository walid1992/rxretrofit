# 前言 

网络请求在项目开发中必不可少，封装的好坏很大程度上影响的项目质量，本篇博文 [草民-walid](http://www.jianshu.com/users/a279a2f8ed63/latest_articles) 与大家分享一下本人的愚见与rxretrofit框架讲解~

# 坏的请求框架的表现

1.与业务逻辑严重耦合
2.存在很多复杂冗余代码
3.请求统一处理不佳
  ...
  
# rxretrofit 框架介绍

## 技术概要

rxretrofit库采用了rxjava + retrofit 2.0 进行整合封装， [retrofit2.0](http://www.jianshu.com/p/9d1d58e170f4) 与 [rxjava](http://www.jianshu.com/p/e3c4280ce397) 在之前文章中都有所介绍，相信大家也都会有所了解，rxjava 与 retrofit
的思想就不和大家进行过多的解读了，接下来直接看草民的封装吧~

