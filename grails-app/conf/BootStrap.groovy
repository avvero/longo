class BootStrap {

    def socketLogService
    def mongoLogService

    def init = { servletContext ->
//        socketLogService.start()
        mongoLogService.start()
    }
    def destroy = {
//        socketLogService.stop()
        mongoLogService.stop()
    }
}
