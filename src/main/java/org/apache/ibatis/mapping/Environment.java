/**
 *    Copyright 2009-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.mapping;

import javax.sql.DataSource;

import org.apache.ibatis.transaction.TransactionFactory;

/**
 * 环境配置
 * 参考URL：http://www.mybatis.org/mybatis-3/zh/configuration.html#environments
 * @author Clinton Begin
 */
public final class Environment {

  /**
   * 环境 ID
   */
  private final String id;
  /**
   * 事务管理器
   * 1.JDBC 直接使用 JDBC 的提交和回滚设置，从数据源得到的连接来管理事务作用域。
   * 2.MANAGED 从来不提交或回滚一个连接，而是让容器来管理事务的整个生命周期.默认会关闭连接，建议将 closeConnection 属性设置为 false 来阻止它默认的关闭行为。
   * 提示：在使用 Spring + MyBatis，则没有必要配置事务管理器， 因为 Spring 模块会使用自带的管理器来覆盖前面的配置。
   */
  private final TransactionFactory transactionFactory;
  /**
   * 数据源
   * 1.UNPOOLED 每次被请求时打开和关闭连接。
   * 2.POOLED 利用“池”的概念将 JDBC 连接对象组织起来，避免了创建新的连接实例时所必需的初始化和认证时间
   * 3.JNDI – 为了能在如 EJB 或应用服务器这类容器中使用，容器可以集中或在外部配置数据源，然后放置一个 JNDI 上下文的引用
   */
  private final DataSource dataSource;

  public Environment(String id, TransactionFactory transactionFactory, DataSource dataSource) {
    if (id == null) {
      throw new IllegalArgumentException("配置文件出错，环境ID不能为空!");
    }
    if (transactionFactory == null) {
        throw new IllegalArgumentException("配置文件出错，事务管理器不能为空!");
    }
    this.id = id;
    if (dataSource == null) {
      throw new IllegalArgumentException("配置文件出错，数据源不能为空!");
    }
    this.transactionFactory = transactionFactory;
    this.dataSource = dataSource;
  }

  /**
   * 构建者模式
   */
  public static class Builder {
      private String id;
      private TransactionFactory transactionFactory;
      private DataSource dataSource;

    public Builder(String id) {
      this.id = id;
    }

    public Builder transactionFactory(TransactionFactory transactionFactory) {
      this.transactionFactory = transactionFactory;
      return this;
    }

    public Builder dataSource(DataSource dataSource) {
      this.dataSource = dataSource;
      return this;
    }

    public String id() {
      return this.id;
    }

    public Environment build() {
      return new Environment(this.id, this.transactionFactory, this.dataSource);
    }

  }

  public String getId() {
    return this.id;
  }

  public TransactionFactory getTransactionFactory() {
    return this.transactionFactory;
  }

  public DataSource getDataSource() {
    return this.dataSource;
  }

}
