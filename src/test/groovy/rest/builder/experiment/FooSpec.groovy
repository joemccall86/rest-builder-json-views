package rest.builder.experiment

import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder
import grails.validation.Validateable
import org.grails.web.converters.exceptions.ConverterException
import spock.lang.Specification

class FooSpec extends Specification {

    void 'instantiation of RestBuilder does not poison json rendering'() {
        given: 'an invalid command object'
        def cmd = new UpdateLocationCmd(latitude: 30.0)
        cmd.validate()

        def writer = new StringWriter()
        def rest = new RestBuilder()

        when: 'a location update is attempted without a required field'
        (cmd.errors as JSON).writeTo(writer)


        then: 'the error is reported'
        notThrown(ConverterException)
        writer.toString() != null
    }
}

class UpdateLocationCmd implements Validateable {
    BigDecimal latitude
    BigDecimal longitude
}
