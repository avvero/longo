modules = {
    application {
        dependsOn 'moment'
        dependsOn 'bootstrap'
        resource url: 'js/application.js'
    }
    moment {
        resource url: 'js/moment.min.js'
    }
    bootstrap {
        resource url: 'js/bootstrap.js'
        dependsOn 'jquery'
    }
}