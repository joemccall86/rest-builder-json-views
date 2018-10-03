package rest.builder.experiment

import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder
import grails.testing.spring.AutowiredTest
import grails.validation.ValidationErrors
import org.grails.web.converters.exceptions.ConverterException
import spock.lang.Specification
import spock.lang.Unroll

class FooSpec extends Specification implements AutowiredTest {
//class FooSpec extends Specification implements GrailsUnitTest {

    // NOTE: this must implement Autowired test for the happy-path to work.

    Set<String> getIncludePlugins() {
        ["converters"].toSet()
    }

    @Unroll
    void 'init rest builder #initRestBuilder does not poison JSON rendering'() {
        given: 'an errors object to render'
        def errors = new ValidationErrors('foo', 'bar')

        and: 'a new object marshaller is registered, does not matter the class'
        JSON.registerObjectMarshaller(Date, {
            [message: 'this is a date']
        })

        if (initRestBuilder) {
            def rest = new RestBuilder()
        }

        when: 'the object is rendered'
        (errors as JSON).toString()

        then: 'no exception is thrown'
        notThrown(ConverterException)

        where:
        initRestBuilder << [false, true]
    }
}
