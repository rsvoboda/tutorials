package com.baeldung.di.spring;

import io.quarkus.arc.Arc;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.AnnotationLiteral;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class SpringUnitTest {

	@Test
	public void givenAccountServiceAutowiredToUserService_WhenGetAccountServiceInvoked_ThenReturnValueIsNotNull() {
		UserService userService = CDI.current().select(UserService.class).get();
		assertNotNull(userService.getAccountService());
	}

	@Test
	public void givenBookServiceIsRegisteredAsBeanInContext_WhenBookServiceIsRetrievedFromContext_ThenReturnValueIsNotNull() {
		BookService bookService = CDI.current().select(BookService.class).get();
		assertNotNull(bookService);
	}

	@Test
	public void givenBookServiceIsRegisteredAsBeanInContextByOverridingAudioBookService_WhenAudioBookServiceIsRetrievedFromContext_ThenNoSuchBeanDefinitionExceptionIsThrown() {
		BookService bookService = CDI.current().select(BookService.class).get();
		assertNotNull(bookService);
		AudioBookService audioBookService = CDI.current().select(AudioBookService.class).get();
		assertNotNull(audioBookService);
	}

	@Test
	public void givenAuthorServiceAutowiredToBookServiceAsOptionalDependency_WhenBookServiceIsRetrievedFromContext_ThenNoSuchBeanDefinitionExceptionIsNotThrown() {
		BookService bookService = CDI.current().select(BookService.class).get();
		assertNotNull(bookService);
	}

	@Test
	public void givenSpringPersonServiceConstructorAnnotatedByAutowired_WhenSpringPersonServiceIsRetrievedFromContext_ThenInstanceWillBeCreatedFromTheConstructor() {
		SpringPersonService personService = CDI.current().select(SpringPersonService.class).get();
		assertNotNull(personService);
	}

	@Test
	public void givenPersonDaoAutowiredToSpringPersonServiceBySetterInjection_WhenSpringPersonServiceRetrievedFromContext_ThenPersonDaoInitializedByTheSetter() {
		SpringPersonService personService = CDI.current().select(SpringPersonService.class).get();
		assertNotNull(personService);
		assertNotNull(personService.getPersonDao());
	}

	@Test
	public void cdiAndArcWayReturnTheSameBean() {
		SpringPersonService personService1 = CDI.current().select(SpringPersonService.class).get();
		SpringPersonService personService2 = Arc.container().select(SpringPersonService.class).get();
		assertNotNull(personService1);
		assertNotNull(personService2);
		assertEquals(personService1, personService2);
	}

	@Test
	public void cdiAndArcInstanceWayReturnTheSameBean() {
		SpringPersonService personService1 = CDI.current().select(SpringPersonService.class).get();
		SpringPersonService personService2 = Arc.container().instance(SpringPersonService.class).get();
		assertNotNull(personService1);
		assertNotNull(personService2);
		assertEquals(personService1, personService2);
	}

	@Test
	public void cdiAndArcStringWayReturnTheSameBean() {

		Arc.container().beanManager().getBeans(Object.class,new AnnotationLiteral<Any>() {})
				.forEach(bean -> System.out.println(bean.getName() + " - " + bean.getBeanClass().getName()));

		SpringPersonService personService1 = CDI.current().select(SpringPersonService.class).get();
		SpringPersonService personService2 = (SpringPersonService) Arc.container().instance("springPersonService").get();
		assertNotNull(personService1);
		assertNotNull(personService2);
		assertEquals(personService1, personService2);
	}

}
