package com.oreilly.security.spelutils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import lombok.Setter;

@Component
public class SpelExpressionUtils implements BeanFactoryAware, InitializingBean {

	private @Setter BeanFactory beanFactory;

	private ExpressionParser expressionParser;
	private StandardEvaluationContext context;

	private static SpelExpressionUtils spelExpressionUtils;

	@PostConstruct
	protected void setuUp() {
		expressionParser = new SpelExpressionParser();
		context = new StandardEvaluationContext();
		context.setBeanResolver(new BeanFactoryResolver(this.beanFactory));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		spelExpressionUtils = this;
	}

	public <T> T eval(String expression, Class<T> clazz) {
		return expressionParser.parseExpression(expression).getValue(context, clazz);
	}

	public static <T> T evalate(String expression, Class<T> clazz) {
		return spelExpressionUtils.eval(expression, clazz);
	}

}
