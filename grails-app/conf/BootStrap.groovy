import com.avvero.longo.CollectorFactory

class BootStrap {

    def init = { servletContext ->

    }
    def destroy = {
        CollectorFactory.stopAll()
    }
}
