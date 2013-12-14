modules = {
    application {
        dependsOn 'moment'
        resource url: 'js/application.js'
    }
    moment {
        resource url: 'js/moment.min.js'
    }
}