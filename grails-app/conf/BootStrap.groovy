class BootStrap {

    def socketEventService
    def mongoEventService

    def init = { servletContext ->
//        socketLogService.start()
//        mongoEventService.start()
    }
    def destroy = {
//        socketLogService.stop()
//        mongoEventService.stop()
    }
}
