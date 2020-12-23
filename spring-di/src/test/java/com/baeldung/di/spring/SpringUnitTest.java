package com.baeldung.di.spring;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.spi.CDI;

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

}
