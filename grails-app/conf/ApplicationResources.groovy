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
        resource url: 'js/bootstrap/bootstrap.js'
        dependsOn 'jquery'
    }
    angular {
        resource url: 'js/angular.js'
    }
    flow {
        resource url: 'js/flow.js'
    }
    layout {
        resource url: 'js/jquery-ui/jquery.layout-latest.min.js'
        dependsOn 'jquery'
    }
    offcanvas {
        resource url: 'js/bootstrap/offcanvas.js'
        dependsOn 'bootstrap'
    }
}