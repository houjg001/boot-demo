package org.example.demo.db.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Collections;

@Configuration
//@ImportResource("classpath:context.xml")
public class BootTransactionConfig {

    private static final String[] READ_ONLY_METHOD_PREFIX = new String[]{"select*", "query*", "search*", "count*", "find*"};
    private static final String[] REQUIRED_METHOD_PREFIX = new String[]{"insert*", "create*", "add*", "save*", "update*", "del*"};

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txInterceptor() {
        NameMatchTransactionAttributeSource attributeSource = new NameMatchTransactionAttributeSource();

        RuleBasedTransactionAttribute readOnlyRule = new RuleBasedTransactionAttribute();
        readOnlyRule.setReadOnly(true);

        RuleBasedTransactionAttribute requiredRule = new RuleBasedTransactionAttribute();
        requiredRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        requiredRule.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));

        //查询方法前缀
        for (String prefix : READ_ONLY_METHOD_PREFIX) {
            attributeSource.addTransactionalMethod(prefix, readOnlyRule);
        }
        //增删改方法前缀
        for (String prefix : REQUIRED_METHOD_PREFIX) {
            attributeSource.addTransactionalMethod(prefix, requiredRule);
        }

        return new TransactionInterceptor(transactionManager, attributeSource);
    }

    @Bean
    public Advisor txAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* org.example.demo.db.service.*.*(..))");
        return new DefaultPointcutAdvisor(pointcut, txInterceptor());
    }
}
